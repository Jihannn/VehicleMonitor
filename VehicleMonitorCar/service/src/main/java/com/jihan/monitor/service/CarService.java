package com.jihan.monitor.service;

import static com.jihan.monitor.service.ServiceApp.TAG_SERVICE;

import android.app.Service;
import android.car.Car;
import android.car.CarInfoManager;
import android.car.hardware.CarSensorManager;
import android.car.hardware.property.CarPropertyManager;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.jihan.lib_common.model.BaseResponse;
import com.jihan.lib_common.network.ServiceCreator;
import com.jihan.lib_common.utils.LogUtils;
import com.jihan.monitor.service.listener.Callback;
import com.jihan.monitor.service.listener.IgnitionStateListener;
import com.jihan.monitor.service.listener.SpeedListener;
import com.jihan.monitor.service.model.Vehicle;
import com.jihan.monitor.service.network.UploadService;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;

public class CarService extends Service {

    private static final String TAG = TAG_SERVICE + CarService.class.getSimpleName();

    private Vehicle mCar;
    private Car mCarApi;
    private CarInfoManager mCarInfoManager;
    private CarPropertyManager mCarPropertyManager;
    private CarSensorManager mCarSensorManager;
    private CarListenerStrategy mCarListenerStrategy;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.logI(TAG,"onCreate");
        mCar = MyCar.getInstance();
        initCarApi();
        initCarManager();
        // initListener();
        getStaticStates();
        initUpload();
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
        mCarSensorManager = (CarSensorManager) mCarApi.getCarManager(Car.SENSOR_SERVICE);
        mCarListenerStrategy = new CarListenerStrategy(mCarSensorManager);
    }

    private void initListener(){
        mCarListenerStrategy.registerListener(new IgnitionStateListener(new Callback() {
            @Override
            public void onSuccess() {
                mCarSensorManager.registerListener(new SpeedListener(new SpeedListener.SpeedChangeCallback() {
                    @Override
                    public void onChange(float speed) {
                        mCar.setSpeed(speed);
                    }
                }),CarSensorManager.SENSOR_TYPE_CAR_SPEED,CarSensorManager.SENSOR_RATE_NORMAL);
            }

            @Override
            public void onFailure() {
                LogUtils.logI(TAG,"汽车未发动");
            }
        }),CarSensorManager.SENSOR_TYPE_IGNITION_STATE,CarSensorManager.SENSOR_RATE_ONCHANGE);
    }

    private void initUpload() {
        Timer mTimer = new Timer();
        UploadService uploadService = ServiceCreator.INSTANCE.create(UploadService.class);
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Call<BaseResponse<String>> call = uploadService.upload(mCar);
                call.enqueue(new retrofit2.Callback<BaseResponse<String>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                        LogUtils.logI(TAG,response.body().getErrorMsg());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                        LogUtils.logI(TAG,t.toString());
                    }
                });
                LogUtils.logI(TAG,mCar.toString());
            }
        };
        mTimer.schedule(mTimerTask,0,5000);
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
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.logI(TAG,"onDestroy");
    }
}
