package com.jihan.monitor.phone.network;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.ui.CarDetailActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class VehicleSocketClient extends WebSocketClient {
    private static final String TAG = TAG_PHONE + VehicleSocketClient.class.getSimpleName();

    public VehicleSocketClient(URI serverUri) {
        super(serverUri);
        LogUtils.logI(TAG,"[VehicleSocketClient]");
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        LogUtils.logI(TAG,"[onOpen]");
    }

    @Override
    public void onMessage(String message) {
        LogUtils.logI(TAG,"[onMessage]");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LogUtils.logI(TAG,"[onClose]");
    }

    @Override
    public void onError(Exception ex) {
        LogUtils.logI(TAG,"[onError]");
        ex.printStackTrace();
    }

}
