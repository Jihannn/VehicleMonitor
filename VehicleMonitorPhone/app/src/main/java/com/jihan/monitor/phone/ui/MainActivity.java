package com.jihan.monitor.phone.ui;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.jihan.monitor.lib_common.base.BaseBindingActivity;
import com.jihan.monitor.lib_common.base.BaseMvvmActivity;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.databinding.ActivityMainBinding;
import com.jihan.monitor.phone.factory.AppInjection;
import com.jihan.monitor.phone.network.VehicleSocketClient;
import com.jihan.monitor.phone.network.VehicleSocketClientService;

public class MainActivity extends BaseMvvmActivity<MainViewModel,ActivityMainBinding> {


    private static final String TAG = TAG_PHONE + MainActivity.class.getSimpleName();

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

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            LogUtils.logI(TAG,"[onServiceConnected]");
            binder = (VehicleSocketClientService.VehicleSocketClientBinder) iBinder;
            service = binder.getService();
            client = service.client;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.logI(TAG,"[onServiceDisconnected]");

        }
    };

    private class WarningMessageReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.logI(TAG,"[onReceive]");
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
        return 0;
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

    private void bindService(){
        Intent intent = new Intent(this,VehicleSocketClientService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    private void registerReceiver(){
        WarningMessageReceiver receiver = new WarningMessageReceiver();
        IntentFilter intentFilter = new IntentFilter("com.jihan.monitor.warning");
        registerReceiver(receiver,intentFilter);
    }
}