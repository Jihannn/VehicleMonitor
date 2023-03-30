package com.jihan.monitor.service.binder;

import android.os.Parcel;
import android.os.RemoteException;

import com.jihan.lib_common.utils.LogUtils;
import com.jihan.monitor.service.IVehicleCallback;
import com.jihan.monitor.service.IVehicleInterface;
import com.jihan.monitor.service.MyCar;
import com.jihan.monitor.service.model.Vehicle;

public class VehicleBinder extends IVehicleInterface.Stub {

    public static final String TAG = VehicleBinder.class.getSimpleName();

    private IVehicleCallback mCallback;

    public IVehicleCallback getCallback(){
        return mCallback;
    }

    @Override
    public boolean registerCallback(IVehicleCallback callback) throws RemoteException {
        mCallback = callback;
        return true;
    }

    @Override
    public boolean unregisterCallback(IVehicleCallback callback) throws RemoteException {
        mCallback = null;
        return true;
    }

    @Override
    public void getVehicleStatus(Vehicle vehicle) throws RemoteException {
        LogUtils.logI(TAG,"[getVehicleStatus]Client-Vehicle:"+vehicle.getModel());
        LogUtils.logI(TAG,"[getVehicleStatus]ServerLocal-Vehicle:"+MyCar.getInstance().getModel());
        vehicle.update(MyCar.getInstance());
        LogUtils.logI(TAG,"[getVehicleStatus]Changed-Vehicle:"+vehicle.getModel());
    }

    @Override
    public int login(String username, String password) throws RemoteException {
        return 200;
    }

    @Override
    public int warning() throws RemoteException {
        return -1;
    }

    @Override
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        try {
            return super.onTransact(code, data, reply, flags);
        } catch (RemoteException e) {
            LogUtils.logI(TAG,"[onTransact-ERROR]");
            throw new RuntimeException(e);
        }
    }
}
