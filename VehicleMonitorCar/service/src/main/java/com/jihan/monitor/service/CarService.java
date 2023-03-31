package com.jihan.monitor.service;

import static com.jihan.lib_common.base.MyApplication.appContext;
import static com.jihan.monitor.service.PrintConfigKt.printSupportConfigList;
import static com.jihan.monitor.service.ServiceApp.TAG_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.car.Car;
import android.car.CarInfoManager;
import android.car.VehicleAreaType;
import android.car.VehiclePropertyIds;
import android.car.hardware.property.CarPropertyManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;

import com.jihan.lib_common.model.BaseResponse;
import com.jihan.lib_common.network.ServiceCreator;
import com.jihan.lib_common.utils.LogUtils;
import com.jihan.monitor.R;
import com.jihan.monitor.service.binder.VehicleBinder;
import com.jihan.monitor.service.listener.Callback;
import com.jihan.monitor.service.listener.CarListenerStrategy;
import com.jihan.monitor.service.listener.IgnitionStateListener;
import com.jihan.monitor.service.listener.SpeedListener;
import com.jihan.monitor.service.model.MyCar;
import com.jihan.monitor.service.model.Vehicle;
import com.jihan.monitor.service.model.VehicleRepository;
import com.jihan.monitor.service.network.UploadService;
import com.jihan.monitor.service.utils.LocationProvider;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;

public class CarService extends LifecycleService {

    private static final String TAG = TAG_SERVICE + CarService.class.getSimpleName();

    private static final String CHANNEL_ID_STRING = "CarService";
    private static final int CHANNEL_ID = 0x11;

    private Vehicle mCar;
    private Car mCarApi;
    private CarInfoManager mCarInfoManager;
    private CarPropertyManager mCarPropertyManager;
    private CarListenerStrategy mCarListenerStrategy;
    private VehicleBinder mVehicleBinder;
    private VehicleRepository mVehicleRepository;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return mVehicleBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startServiceForeground();
        LogUtils.logI(TAG,"onCreate");
        mCar = MyCar.getInstance();
        mVehicleBinder = new VehicleBinder();
        mVehicleRepository = VehicleRepository.getInstance();
        initCarApi();
        initCarManager();
        LocationProvider.getInstance().initProvider(appContext);
        initListener();
        printSupportConfigList(mCarPropertyManager);
        // CarPropertyValue<Float> value = mCarPropertyManager.getProperty();
        // printProperty(mCarPropertyManager,Float.class, VehiclePropertyIds.PERF_VEHICLE_SPEED, VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL);
        getStaticStates();
        initUpload();
    }

    private void startServiceForeground() {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID_STRING, getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(getApplicationContext(),
                    CHANNEL_ID_STRING).build();
            startForeground(CHANNEL_ID, notification);
        }
    }


    private void initCarApi() {
        if (mCarApi != null && mCarApi.isConnected()) {
            mCarApi.disconnect();
            mCarApi = null;
        }
        mCarApi = Car.createCar(this);
    }

    private void initCarManager() {
        mCarInfoManager = (CarInfoManager) mCarApi.getCarManager(Car.INFO_SERVICE);
        mCarPropertyManager = (CarPropertyManager) mCarApi.getCarManager(Car.PROPERTY_SERVICE);
        mCarListenerStrategy = new CarListenerStrategy(mCarPropertyManager);
    }

    private void initListener(){
        LogUtils.logI(TAG,"[initListener]");
        mCarListenerStrategy.registerListener(new IgnitionStateListener(new Callback() {
            @Override
            public void onSuccess() {
                LogUtils.logI(TAG,"[initListener-汽车启动]");
                mCarListenerStrategy.registerListener(new SpeedListener(new SpeedListener.SpeedChangeCallback() {
                    @Override
                    public void onChange(float speed) throws RemoteException {
                        LogUtils.logI(TAG,"[initListener]:onSpeedChanged"+speed+"fuelLevel:"+mCar.getFuelLevel());
                        mCar.setSpeed(speed);
                        mCar.setUpdateTime(String.valueOf(System.currentTimeMillis()));
                        if(mVehicleBinder.getStatusCallback() != null) {
                            mVehicleBinder.getStatusCallback().onStatusChanged(MyCar.getInstance());
                        }
                    }
                }),"Speed",VehiclePropertyIds.PERF_VEHICLE_SPEED,VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL,CarPropertyManager.SENSOR_RATE_FAST);
            }

            @Override
            public void onFailure() {
                LogUtils.logI(TAG,"汽车未发动");
            }
        }),"IgnitionState",VehiclePropertyIds.IGNITION_STATE,VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL,CarPropertyManager.SENSOR_RATE_ONCHANGE);
    }

    private void initUpload() {
        Timer mTimer = new Timer();
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mVehicleRepository.upload();
            }
        };
        mTimer.schedule(mTimerTask,0,10000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void getStaticStates() {
        mCar.setModel(mCarInfoManager.getModel());
        mCar.setModelYear(mCarInfoManager.getModelYearInInteger());
        mCar.setManufacturer(mCarInfoManager.getManufacturer());
        mCar.setFuelCapacity(mCarInfoManager.getFuelCapacity());
        mCar.setFuelLevel(mCarInfoManager.getFuelCapacity() - (float)(Math.random() * 1000));
        mCar.setUpdateTime(String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.logI(TAG,"onDestroy");
    }
}
