package com.jihan.monitor.service.listener;

import static com.jihan.monitor.service.ServiceApp.TAG_SERVICE;

import android.car.VehiclePropertyIds;
import android.car.hardware.property.CarPropertyManager;

import com.jihan.lib_common.utils.LogUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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

        private final AtomicInteger speed = new AtomicInteger(0);

        public GenerateSpeedTask(SpeedListener listener){
            this.mListener = listener;
        }
        @Override
        public void run() {
            while(isRunning.get()){
                float randomSpeed = (float) (Math.random() * 10);
                speed.set((int)(speed.get() + (speed.get() >= 0 && speed.get() < 80 ? randomSpeed : -randomSpeed)));
                mListener.onSimulationChanged(speed.get());
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
