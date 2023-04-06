package com.jihan.monitor.phone.ui;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import androidx.lifecycle.MutableLiveData;

import com.jihan.monitor.lib_common.model.BaseResponse;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.lib_common.viewmodel.BaseViewModel;
import com.jihan.monitor.phone.model.Constants;
import com.jihan.monitor.phone.model.UserManager;
import com.jihan.monitor.phone.model.Vehicle;
import com.jihan.monitor.phone.model.VehicleRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarViewModel extends BaseViewModel<VehicleRepository> {
    private static final String TAG = TAG_PHONE + CarViewModel.class.getSimpleName();

    public MutableLiveData<Response<BaseResponse<List<Vehicle>>>> vehicleListLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> registerLiveData = new MutableLiveData<>();

    public CarViewModel(VehicleRepository repository) {
        super(repository);
    }

    public void getVehicleList(){
        mRepository.getVehicleList(new Callback<BaseResponse<List<Vehicle>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Vehicle>>> call, Response<BaseResponse<List<Vehicle>>> resp) {
                if(resp.body() != null && resp.body().getErrorCode() == Constants.CODE_SUCCESS){
                    LogUtils.logI(TAG,"[getVehicleList]-success："+resp.body().toString());
                    vehicleListLiveData.setValue(resp);
                }else if(resp.body() != null && resp.body().getErrorCode() == Constants.CODE_TOKEN_PASS){
                    vehicleListLiveData.setValue(resp);
                }else{
                    LogUtils.logI(TAG,"[getVehicleList]-failure");
                    vehicleListLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Vehicle>>> call, Throwable t) {
                LogUtils.logI(TAG,"[getVehicleList]-onFailure");
                vehicleListLiveData.setValue(null);
            }
        });
    }

    public void register(String plateNumber,String brand,String model,String deviceId){
        mRepository.registerVehicle(new Vehicle(plateNumber, brand, model, deviceId), new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> resp) {
                if(resp.body() != null && resp.body().getErrorCode() == Constants.CODE_SUCCESS){
                    registerLiveData.setValue(Constants.CODE_SUCCESS);
                }else{
                    LogUtils.logI(TAG,"[register]-body is null");
                    registerLiveData.setValue(Constants.CODE_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                LogUtils.logI(TAG,"[register]-error"+t.getMessage());
                registerLiveData.setValue(Constants.CODE_FAILURE);
            }
        });
    }
}
