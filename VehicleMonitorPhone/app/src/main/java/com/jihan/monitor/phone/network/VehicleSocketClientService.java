package com.jihan.monitor.phone.network;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.net.URI;

public class VehicleSocketClientService extends Service {
    private URI uri;
    public VehicleSocketClient client;
    private VehicleSocketClientBinder mBinder = new VehicleSocketClientBinder();

    public class VehicleSocketClientBinder extends Binder{
        public VehicleSocketClientService getService(){
            return VehicleSocketClientService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
