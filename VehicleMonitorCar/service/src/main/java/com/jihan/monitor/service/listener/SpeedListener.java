package com.jihan.monitor.service.listener;

import static com.jihan.monitor.service.ServiceApp.TAG_SERVICE;

import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.os.RemoteException;

import com.jihan.lib_common.utils.LogUtils;
import com.jihan.monitor.service.model.MyCar;

public class SpeedListener implements CarPropertyManager.CarPropertyEventCallback{

    private static final String TAG = TAG_SERVICE + SpeedListener.class.getSimpleName();
    private SpeedChangeCallback mCallback;
    private int mSpeedChangeTimes;

    @Override
    public void onChangeEvent(CarPropertyValue carPropertyValue) {
        Float value = (Float) carPropertyValue.getValue();
        LogUtils.logI(TAG,"[onChangeEvent]:value"+value);
        try {
            mCallback.onChange(value);
        } catch (RemoteException e) {
            LogUtils.logI(TAG,"[onChangeEvent]:ERROR"+value);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onErrorEvent(int propId, int zone) {
        LogUtils.logI(TAG,"[onErrorEvent]"+propId+":"+zone);
    }

    public interface SpeedChangeCallback{
        void onChange(float speed) throws RemoteException;
    }

    public SpeedListener(SpeedChangeCallback callback){
        this.mCallback = callback;
        this.mSpeedChangeTimes = 0;
    }

    public void onSimulationChanged(float speed){
        LogUtils.logI(TAG,"[onSimulationChanged]:speed"+speed);
        mSpeedChangeTimes++;
        if(mSpeedChangeTimes == 60){
            mSpeedChangeTimes = 0;
            MyCar.getInstance().setFuelLevel(MyCar.getInstance().getFuelLevel() - 5f);
        }
        try {
            mCallback.onChange(speed);
        } catch (RemoteException e) {
            LogUtils.logI(TAG,"[onSimulationChanged]:ERROR"+speed);
            throw new RuntimeException(e);
        }
    }
}