package com.jihan.monitor.service.listener;

import static com.jihan.monitor.service.ServiceApp.TAG_SERVICE;

import android.car.hardware.CarPropertyValue;
import android.car.hardware.CarSensorEvent;
import android.car.hardware.CarSensorManager;
import android.car.hardware.property.CarPropertyManager;

import com.jihan.lib_common.utils.LogUtils;
import com.jihan.monitor.service.CarService;
import com.jihan.monitor.service.MyCar;

public class IgnitionStateListener implements CarPropertyManager.CarPropertyEventCallback {

    private static final String TAG = TAG_SERVICE + IgnitionStateListener.class.getSimpleName();

    private Callback mCallback;
    /**
     * Ignition state is unknown.
     * The constants that starts with IGNITION_STATE_ represent values for
     * */
    public static final int IGNITION_STATE_UNDEFINED = 0;
    /**
     * Steering wheel is locked.
     */
    private static final int IGNITION_STATE_LOCK = 1;
    /** Typically engine is off, but steering wheel is unlocked. */
    private static final int IGNITION_STATE_OFF = 2;
    /** Accessory is turned off, but engine is not running yet (for EV car is not ready to move). */
    private static final int IGNITION_STATE_ACC = 3;
    /** In this state engine typically is running (for EV, car is ready to move). */
    private static final int IGNITION_STATE_ON = 4;
    /** In this state engine is typically starting (cranking). */
    public static final int IGNITION_STATE_START = 5;

    public IgnitionStateListener(Callback callback){
        this.mCallback = callback;
    }

    @Override
    public void onChangeEvent(CarPropertyValue carPropertyValue) {
        Integer value = (Integer) carPropertyValue.getValue();
        LogUtils.logI(TAG,"[onChangeEvent]:value"+value);
        MyCar.getInstance().setIgnitionState(value);
        MyCar.getInstance().setUpdateTime(String.valueOf(System.currentTimeMillis()));
        if(value == IGNITION_STATE_START){
            mCallback.onSuccess();
        }else{
            mCallback.onFailure();
        }
    }

    /**
     * Called when an error is detected when setting a property.
     *
     * @param propId 错误的属性
     * @param zone 错误的区域
     *
     */
    @Override
    public void onErrorEvent(int propId, int zone) {
        LogUtils.logI(TAG,"[onErrorEvent]"+propId+":"+zone);
    }

}
