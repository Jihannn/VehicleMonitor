package com.jihan.monitor.service.listener;

import static com.jihan.monitor.service.ServiceApp.TAG_SERVICE;

import android.car.VehiclePropertyIds;
import android.car.hardware.property.CarPropertyManager;

import com.jihan.lib_common.utils.LogUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class CarListenerStrategy {

    private static final String TAG = TAG_SERVICE + CarListenerStrategy.class.getSimpleName();

    private final CarPropertyManager mCarPropertyManager;

    private ExecutorService mExecutorService;
    private AtomicBoolean isRunning;

    public CarListenerStrategy(CarPropertyManager carPropertyManager){
        this.mCarPropertyManager = carPropertyManager;
        this.mExecutorService = Executors.newFixedThreadPool(1);
        this.isRunning = new AtomicBoolean(false);
    }

    public void registerListener(CarPropertyManager.CarPropertyEventCallback callback,String sensorName, int propertyId,int areaType, float rate){
        if(propertyId == VehiclePropertyIds.PERF_VEHICLE_SPEED) {
            speedSimulation(callback);
        }else if(mCarPropertyManager.isPropertyAvailable(propertyId, areaType)){
                mCarPropertyManager.registerCallback(callback, propertyId, rate);
        }else{
            LogUtils.logI(TAG,"[registerListener]不支持传感器："+sensorName);
        }
    }

    private void speedSimulation(CarPropertyManager.CarPropertyEventCallback callback){
        SpeedListener speedListener = (SpeedListener) callback;
        isRunning.set(true);
        mExecutorService.execute(new GenerateSpeedTask(speedListener));
    }

    private class GenerateSpeedTask implements Runnable{

        private SpeedListener mListener;

        private float speed = 0f;

        public GenerateSpeedTask(SpeedListener listener){
            this.mListener = listener;
        }
        @Override
        public void run() {
            while(isRunning.get()){
                float randomSpeed = (float) (Math.random() * 10);
                speed = speed + (randomSpeed < 7 ? (speed > 100 ? -randomSpeed : 5) : randomSpeed);
                mListener.onSimulationChanged((int)speed);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            mListener = null;
        }
    }
}
