package com.jihan.monitor.service.listener;

import android.car.hardware.CarSensorEvent;
import android.car.hardware.CarSensorManager;

public class IgnitionStateListener implements CarSensorManager.OnSensorChangedListener {

    private Callback mCallback;

    public IgnitionStateListener(Callback callback){
        this.mCallback = callback;
    }

    @Override
    public void onSensorChanged(CarSensorEvent carSensorEvent) {
        if(carSensorEvent.sensorType == CarSensorManager.SENSOR_TYPE_IGNITION_STATE){
            CarSensorEvent.IgnitionStateData ignitionStateData = null;
            ignitionStateData = carSensorEvent.getIgnitionStateData(ignitionStateData);
            if(CarSensorEvent.IGNITION_STATE_START == ignitionStateData.ignitionState){
                mCallback.onSuccess();
            }else{
                //TODO:汽车未发动
                mCallback.onFailure();
            }
        }
    }
}
