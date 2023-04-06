package com.jihan.monitor.phone.ui;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.MenuItem;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.navigation.NavigationBarView;
import com.jihan.monitor.lib_common.base.BaseBindingActivity;
import com.jihan.monitor.lib_common.base.BaseMvvmActivity;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.BR;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.databinding.ActivityMainBinding;
import com.jihan.monitor.phone.factory.AppInjection;
import com.jihan.monitor.phone.model.UserManager;
import com.jihan.monitor.phone.model.VehicleStatus;
import com.jihan.monitor.phone.network.VehicleSocketClient;
import com.jihan.monitor.phone.network.VehicleSocketClientService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;

public class MainActivity extends BaseMvvmActivity<MainViewModel,ActivityMainBinding> {
    private static final String TAG = TAG_PHONE + MainActivity.class.getSimpleName();
    private WarningMessageReceiver receiver;
    private IntentFilter filter;

    public static class LAUNCHER{
        public static void launch(Context context){
            context.startActivity(new Intent(context,MainActivity.class));
        }
    }
    private CarFragment mCarFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrentFragment;
    private VehicleSocketClient client;
    private VehicleSocketClientService.VehicleSocketClientBinder binder;
    private VehicleSocketClientService service;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            LogUtils.logI(TAG,"[onServiceConnected]");
            binder = (VehicleSocketClientService.VehicleSocketClientBinder) iBinder;
            service = binder.getService();
            client = service.client;
            service.registerWarningMessage();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.logI(TAG,"[onServiceDisconnected]");
        }
    };

    private class WarningMessageReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            LogUtils.logI(TAG,"[onReceive]:"+message);
            checkLockAndShowNotification(message);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Object getViewModelOrFactory() {
        return AppInjection.getViewModelFactory();
    }

    @Override
    protected int getViewModelVariable() {
        return BR.viewModel;
    }

    @Override
    protected void initObservable(MainViewModel viewModel) {

    }

    @Override
    protected void loadData(MainViewModel viewModel) {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        bindService();
        mCarFragment = new CarFragment();
        mMineFragment = new MineFragment();
        mCurrentFragment = mCarFragment;
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container_main,mCarFragment,"car").commitAllowingStateLoss();
        }else{
            Integer tab = mViewModel.currentPositionResIdLiveData.getValue();
            if(tab != null){
                mBinding.navBottom.setSelectedItemId(tab);
                onNavBarItemSelected(tab);
            }
        }
        mBinding.navBottom.setOnItemSelectedListener(item -> {
            onNavBarItemSelected(item.getItemId());
            return true;
        });
        // mViewModel.registerWarningMessage();
        checkNotification(this);
        registerReceiver();
    }

    private void onNavBarItemSelected(Integer itemResId){
        mViewModel.switchTab(itemResId);
        switch (itemResId){
            case R.id.menu_car:
                switchFragment(mCarFragment);
                break;
            case R.id.menu_mine:
                switchFragment(mMineFragment);
                break;
            default:
                break;
        }
    }

    private void switchFragment(Fragment fragment){
        if(fragment != mCurrentFragment){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(mCurrentFragment);
            if(!fragment.isAdded()){
                transaction.add(R.id.container_main,fragment);
            }
            transaction.show(fragment);
            transaction.commitAllowingStateLoss();
            mCurrentFragment = fragment;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //避免Fragment重叠
        super.onSaveInstanceState(new Bundle());
    }

    private void registerReceiver(){
        receiver = new WarningMessageReceiver();
        filter = new IntentFilter("com.jihan.monitor.warning");
        registerReceiver(receiver, filter);
    }

    private void bindService(){
        Intent intent = new Intent(this,VehicleSocketClientService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    /**
     * 检查锁屏状态
     */
    private void checkLockAndShowNotification(String content) {
        //管理锁屏的一个服务
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (km.inKeyguardRestrictedInputMode()) {//锁屏
            //获取电源管理器对象
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            if (!pm.isScreenOn()) {
                @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
                wl.acquire();  //点亮屏幕
                wl.release();  //任务结束后释放
            }
            sendNotification(content);
        } else {
            sendNotification(content);
        }
    }

    /**
     * 发送通知
     */
    // private void sendNotification(String content) {
    //     Intent intent = new Intent();
    //     intent.setClass(this, MainActivity.class);
    //     PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    //     NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    //     Notification notification = new NotificationCompat.Builder(this)
    //             .setAutoCancel(true)
    //             // 设置该通知优先级
    //             .setPriority(Notification.PRIORITY_MAX)
    //             .setSmallIcon(R.mipmap.ic_launcher)
    //             .setContentTitle("收到紧急信息！")
    //             .setContentText(content)
    //             .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    //             .setWhen(System.currentTimeMillis())
    //             // 向通知添加声音、闪灯和振动效果
    //             .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_ALL | Notification.DEFAULT_SOUND)
    //             .setContentIntent(pendingIntent)
    //             .build();
    //     notifyManager.notify(1, notification);//id要保证唯一
    // }
    private void sendNotification(String content) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "110";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "警告信息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(content);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_car_48dp)
                .setContentTitle("收到紧急信息！")
                .setContentText(content)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_ALL | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .build();
        notifyManager.notify(1, notification);
    }

    /**
     * 检测是否开启通知
     *
     */
    private void checkNotification(final Context context) {
        if (!isNotificationEnabled(context)) {
            new AlertDialog.Builder(context).setTitle("温馨提示")
                    .setMessage("你还未开启系统通知，将影响警告消息的接收，要去开启吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setNotification(context);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }

    /**
     * 如果没有开启通知，跳转至设置界面
     */
    private void setNotification(Context context) {
        Intent localIntent = new Intent();
        //直接跳转到应用通知设置的代码：
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            localIntent.putExtra("app_package", context.getPackageName());
            localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
        } else {
            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
        }
        context.startActivity(localIntent);
    }

    /**
     * 获取通知权限,检测是否开启了系统通知
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean isNotificationEnabled(Context context) {
        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}