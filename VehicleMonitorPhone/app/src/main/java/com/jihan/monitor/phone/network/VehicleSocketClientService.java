package com.jihan.monitor.phone.network;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.model.UserManager;
import com.jihan.monitor.phone.ui.MainViewModel;

import java.net.URI;

public class VehicleSocketClientService extends Service {
    private static final String TAG = TAG_PHONE + MainViewModel.class.getSimpleName();

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

    public void registerWarningMessage(){
        LogUtils.logI(TAG,"[registerWarningMessage]");
        uri = URI.create("ws://192.168.0.103:8090/?"+ "type=" + "warning" + "&token="+ UserManager.getUser());
        client = new VehicleSocketClient(uri){
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                LogUtils.logI(TAG,"[onMessage]"+message);
                // VehicleStatus vehicleStatus = JSONObject.parseObject(message, VehicleStatus.class);
                // LogUtils.logI(TAG,"[onMessage-vehicle]"+vehicleStatus.toString());
                // mAppExecutors.post(() -> vehicleStatusLiveData.setValue(vehicleStatus));
                Intent intent = new Intent();
                intent.setAction("com.jihan.monitor.warning");
                intent.putExtra("message",message);
                sendBroadcast(intent);
            }
        };
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
