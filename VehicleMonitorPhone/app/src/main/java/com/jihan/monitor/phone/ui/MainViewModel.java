package com.jihan.monitor.phone.ui;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.jihan.monitor.lib_common.model.BaseRepository;
import com.jihan.monitor.lib_common.utils.AppExecutors;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.lib_common.viewmodel.BaseViewModel;
import com.jihan.monitor.phone.model.UserManager;
import com.jihan.monitor.phone.model.VehicleStatus;
import com.jihan.monitor.phone.network.VehicleSocketClient;
import java.net.URI;

public class MainViewModel extends BaseViewModel<BaseRepository> {
    private static final String TAG = TAG_PHONE + MainViewModel.class.getSimpleName();
    public MutableLiveData<Integer> currentPositionResIdLiveData = new MutableLiveData<>();
    public URI uri;
    public VehicleSocketClient client;
    private final AppExecutors mAppExecutors;

    public MainViewModel(AppExecutors executors){
        this.mAppExecutors = executors;
    }

    public void switchTab(Integer resId){
        currentPositionResIdLiveData.setValue(resId);
    }

    public void registerWarningMessage(){
        uri = URI.create("ws://192.168.0.103:8090/?"+ "type=" + "warning" + "&token="+ UserManager.getUser());
        client = new VehicleSocketClient(uri){
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                LogUtils.logI(TAG,"[onMessage]"+message);
                // VehicleStatus vehicleStatus = JSONObject.parseObject(message, VehicleStatus.class);
                // LogUtils.logI(TAG,"[onMessage-vehicle]"+vehicleStatus.toString());
                // mAppExecutors.post(() -> vehicleStatusLiveData.setValue(vehicleStatus));
            }
        };
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}