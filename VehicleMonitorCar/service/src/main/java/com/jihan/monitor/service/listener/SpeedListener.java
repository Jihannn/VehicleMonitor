package com.jihan.monitor.service.listener;

import android.car.hardware.CarSensorEvent;
import android.car.hardware.CarSensorManager;

public class SpeedListener implements CarSensorManager.OnSensorChangedListener{

    private SpeedChangeCallback mCallback;

    public interface SpeedChangeCallback{
        void onChange(float speed);
    }

    public SpeedListener(SpeedChangeCallback callback){
        this.mCallback = callback;
    }

    @Override
    public void onSensorChanged(CarSensorEvent carSensorEvent) {
        if(carSensorEvent.sensorType == CarSensorManager.SENSOR_TYPE_CAR_SPEED){
            CarSensorEvent.CarSpeedData carSpeedData = null;
            carSpeedData = carSensorEvent.getCarSpeedData(carSpeedData);
            mCallback.onChange(carSpeedData.carSpeed);
        }
    }

}