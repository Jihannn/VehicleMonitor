package com.jihan.monitor.ui;

import androidx.lifecycle.MutableLiveData;

import com.jihan.lib_common.utils.AppExecutors;
import com.jihan.lib_common.viewmodel.BaseViewModel;
import com.jihan.monitor.CarApp;
import com.jihan.monitor.model.VehicleRepository;
import com.jihan.monitor.sdk.listener.StatusCallback;
import com.jihan.monitor.service.model.Vehicle;

public class MainViewModel extends BaseViewModel<VehicleRepository> {

    private static final String TAG = CarApp.TAG_HMI + HvacViewModel.class.getSimpleName();

    private final AppExecutors mAppExecutors;

    private MutableLiveData<Vehicle> mVehicleData;

    public MainViewModel(VehicleRepository repository, AppExecutors executors) {
        super(repository);
        this.mAppExecutors = executors;
    }

    public void requestVehicleData(StatusCallback callback){
        if(getVehicleLiveData().getValue() == null){
            mVehicleData.setValue(new Vehicle());
        }
        mRepository.requestVehicleData(callback);
    }

    public void updateVehicle(Vehicle vehicle){
        mVehicleData.setValue(vehicle);
    }

    public MutableLiveData<Vehicle> getVehicleLiveData(){
        if(mVehicleData == null){
            mVehicleData = new MutableLiveData<>();
        }
        return mVehicleData;
    }

    public int warning(){
        return mRepository.warning();
    }
}
