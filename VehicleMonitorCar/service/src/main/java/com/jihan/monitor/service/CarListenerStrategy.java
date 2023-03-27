package com.jihan.monitor.service;

import android.car.hardware.CarSensorManager;

public class CarListenerStrategy {

    private CarSensorManager mCarSensorManager;

    public CarListenerStrategy(CarSensorManager carSensorManager){
        this.mCarSensorManager = carSensorManager;
    }

    public void registerListener(CarSensorManager.OnSensorChangedListener listener,int sensorType,int sensorRate){
        if(mCarSensorManager.isSensorSupported(sensorType)){
            mCarSensorManager.registerListener(listener, sensorType, sensorRate);
        }else{
            //TODO：不支持该传感器
        }
    }
}
