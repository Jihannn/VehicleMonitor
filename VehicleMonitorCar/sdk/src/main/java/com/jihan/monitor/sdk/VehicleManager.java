package com.jihan.monitor.sdk;

import static com.jihan.monitor.sdk.Constants.CODE_SERVICE_NOT_CONNECT;

import android.os.IBinder;
import android.os.RemoteException;

import com.jihan.monitor.sdk.base.BaseConnectManager;
import com.jihan.monitor.sdk.listener.LoginCallback;
import com.jihan.monitor.sdk.listener.StatusCallback;
import com.jihan.monitor.sdk.utils.Remote;
import com.jihan.monitor.sdk.utils.SdkLogUtils;
import com.jihan.monitor.service.ILoginCallback;
import com.jihan.monitor.service.IStatusCallback;
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
    private final List<StatusCallback> mStatusCallbacks = new ArrayList<>();
    private final List<LoginCallback> mLoginCallbacks = new ArrayList<>();
    private final IStatusCallback.Stub mStatusCallback = new IStatusCallback.Stub() {
        @Override
        public void onStatusChanged(Vehicle vehicle) {
            SdkLogUtils.logV(TAG, "[onStatusChanged] " + vehicle.getSpeed()+"fuelLevel:"+vehicle.getFuelLevel());
            getMainHandler().post(() -> {
                for (StatusCallback callback : mStatusCallbacks) {
                    callback.onStatusChanged(vehicle);
                }
            });
        }
    };

    private final ILoginCallback.Stub mLoginCallback = new ILoginCallback.Stub() {
        @Override
        public void onResponse(String token) throws RemoteException {
            SdkLogUtils.logV(TAG, "[onResponse] " + token);
            getMainHandler().post(() -> {
                for (LoginCallback callback : mLoginCallbacks) {
                    callback.onResponse(token);
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

    public void getVehicleStatus(StatusCallback callback) {
        Remote.exec(() -> {
            if (isServiceConnected(true)) {
                mStatusCallbacks.add(callback);
                boolean result = getProxy().getVehicleStatus(mStatusCallback);
                if(!result){
                    mStatusCallbacks.remove(callback);
                }
                SdkLogUtils.logV(TAG, "[getVehicleStatus]:"+result);
            } else {
                SdkLogUtils.logV(TAG, "[getVehicleStatus-ERROR]:service is not connect");
            }
            return true;
        });
    }

    public void login(String username, String password, LoginCallback callback) {
        Remote.exec(() -> {
            if (isServiceConnected(true)) {
                mLoginCallbacks.add(callback);
                boolean result = getProxy().login(username, password,mLoginCallback);
                if(!result){
                    mLoginCallbacks.remove(callback);
                }
            } else {
                SdkLogUtils.logV(TAG, "[getVehicleStatus-ERROR]:service is not connect");
            }
            callback.onResponse(null);
            return true;
        });
    }

    public int warning() {
        return Remote.exec(() -> {
            if (isServiceConnected(true)) {
                return getProxy().warning();
            } else {
                SdkLogUtils.logV(TAG, "[getVehicleStatus-ERROR]:service is not connect");
            }
            return CODE_SERVICE_NOT_CONNECT;
        });
    }
}
