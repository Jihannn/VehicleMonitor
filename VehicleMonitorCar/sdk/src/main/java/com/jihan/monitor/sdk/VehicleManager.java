package com.jihan.monitor.sdk;

import android.os.IBinder;

import com.jihan.monitor.sdk.base.BaseConnectManager;
import com.jihan.monitor.sdk.utils.Remote;
import com.jihan.monitor.sdk.utils.SdkLogUtils;
import com.jihan.monitor.service.IVehicleCallback;
import com.jihan.monitor.service.IVehicleInterface;
import com.jihan.monitor.service.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleManager extends BaseConnectManager<IVehicleInterface> {
    private static final String TAG = SdkLogUtils.TAG_SDK + VehicleManager.class.getSimpleName();
    private static volatile VehicleManager mVehicleManager;
    public static final String SERVICE_PACKAGE = "com.jihan.monitor.service";
    public static final String SERVICE_CLASSNAME = "com.jihan.monitor.service.CarService";
    private static final long RETRY_TIME = 5000L;
    private final List<VehicleCallback> mCallbacks = new ArrayList<>();
    private final IVehicleCallback.Stub mSpeedCallback = new IVehicleCallback.Stub() {
        @Override
        public void onSpeedChanged(float speed) {
            SdkLogUtils.logV(TAG, "[onSpeedChanged] " + speed);
            getMainHandler().post(() -> {
                for (VehicleCallback callback : mCallbacks) {
                    callback.onSpeedChanged(speed);
                }
            });
        }
    };

    public static VehicleManager getInstance() {
        if (mVehicleManager == null) {
            synchronized (VehicleManager.class) {
                if (mVehicleManager == null) {
                    mVehicleManager = new VehicleManager();
                }
            }
        }
        return mVehicleManager;
    }

    @Override
    protected String getServicePkgName() {
        return SERVICE_PACKAGE;
    }

    @Override
    protected String getServiceClassName() {
        return SERVICE_CLASSNAME;
    }

    @Override
    protected IVehicleInterface asInterface(IBinder service) {
        return IVehicleInterface.Stub.asInterface(service);
    }

    @Override
    protected long getRetryBindTimeMill() {
        return RETRY_TIME;
    }

    public boolean registerCallback(VehicleCallback callback) {
        return Remote.exec(() -> {
            if (isServiceConnected(true)) {
                boolean result = getProxy().registerCallback(mSpeedCallback);
                if (result) {
                    mCallbacks.remove(callback);
                    mCallbacks.add(callback);
                }
                return result;
            } else {
                getTaskQueue().offer(() -> {
                    registerCallback(callback);
                });
                return false;
            }
        });
    }

    public boolean unregisterCallback(VehicleCallback callback) {
        return Remote.exec(() -> {
            if (isServiceConnected(true)) {
                boolean result = getProxy().unregisterCallback(mSpeedCallback);
                if (result) {
                    mCallbacks.remove(callback);
                }
                return result;
            } else {
                getTaskQueue().offer(() -> {
                    unregisterCallback(callback);
                });
                return false;
            }
        });
    }

    public void getVehicleStatus(Vehicle vehicle){
        Remote.exec(() ->{
            if (isServiceConnected(true)) {
                getProxy().getVehicleStatus(vehicle);
            }else{
                SdkLogUtils.logV(TAG,"[getVehicleStatus-ERROR]:service is not connect");
            }
            return null;
        });
    }
}
