package com.jihan.monitor.service.binder;

import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

import com.jihan.lib_common.utils.LogUtils;
import com.jihan.monitor.service.ILoginCallback;
import com.jihan.monitor.service.IStatusCallback;
import com.jihan.monitor.service.model.Constants;
import com.jihan.monitor.service.IVehicleInterface;
import com.jihan.monitor.service.model.MyCar;
import com.jihan.monitor.service.model.Vehicle;
import com.jihan.monitor.service.model.VehicleRepository;

public class VehicleBinder extends IVehicleInterface.Stub {

    public static final String TAG = VehicleBinder.class.getSimpleName();

    private IStatusCallback mStatusCallback;

    public IStatusCallback getStatusCallback(){
        return mStatusCallback;
    }

    @Override
    public boolean getVehicleStatus(IStatusCallback callback) throws RemoteException {
        LogUtils.logI(TAG, "[getVehicleStatus]Server-Vehicle:" + MyCar.getInstance().getModel());
        mStatusCallback = callback;
        return true;
    }

    @Override
    public boolean login(String username, String password, ILoginCallback callback) throws RemoteException {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return false;
        }
        VehicleRepository.getInstance().login(username,password,callback);
        return true;
    }


    @Override
    public int warning() throws RemoteException {
        return VehicleRepository.getInstance().warning();
    }

    @Override
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        try {
            return super.onTransact(code, data, reply, flags);
        } catch (RemoteException e) {
            LogUtils.logI(TAG, "[onTransact-ERROR]");
            throw new RuntimeException(e);
        }
    }
}
