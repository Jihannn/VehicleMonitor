package com.jihan.monitor.phone.ui;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.jihan.monitor.lib_common.utils.AppExecutors;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.lib_common.viewmodel.BaseViewModel;
import com.jihan.monitor.phone.model.UserManager;
import com.jihan.monitor.phone.model.VehicleRepository;
import com.jihan.monitor.phone.model.VehicleStatus;
import com.jihan.monitor.phone.network.VehicleSocketClient;

import java.net.URI;

public class CarDetailViewModel extends BaseViewModel<VehicleRepository> {
    private static final String TAG = TAG_PHONE + CarDetailViewModel.class.getSimpleName();

    public MutableLiveData<VehicleStatus> vehicleStatusLiveData = new MutableLiveData<>();

    private final AppExecutors mAppExecutors;

    public URI uri;
    public VehicleSocketClient client;

    public CarDetailViewModel(VehicleRepository repository,AppExecutors executors) {
        super(repository);
        this.mAppExecutors = executors;
    }

    public void getLatestVehicleStatus(int vehicle_id){
        uri = URI.create("ws://192.168.0.103:8090/?"+"type="+ "detail" +"&token="+ UserManager.getUser() + "&vehicle_id="+vehicle_id);
        client = new VehicleSocketClient(uri){
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                LogUtils.logI(TAG,"[onMessage]"+message);
                VehicleStatus vehicleStatus = JSONObject.parseObject(message, VehicleStatus.class);
                LogUtils.logI(TAG,"[onMessage-vehicle]"+vehicleStatus.toString());
                mAppExecutors.post(() -> vehicleStatusLiveData.setValue(vehicleStatus));
            }
        };
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeClientConnection(){
        client.close();
        client = null;
    }
}
