package com.jihan.monitor.phone.ui;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import androidx.lifecycle.MutableLiveData;

import com.jihan.monitor.lib_common.model.BaseResponse;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.lib_common.viewmodel.BaseViewModel;
import com.jihan.monitor.phone.model.Constants;
import com.jihan.monitor.phone.model.Vehicle;
import com.jihan.monitor.phone.model.VehicleRepository;
import com.jihan.monitor.phone.model.WarningMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarningMessageViewModel extends BaseViewModel<VehicleRepository> {
    private static final String TAG = TAG_PHONE + WarningMessageViewModel.class.getSimpleName();

    public MutableLiveData<BaseResponse<List<WarningMessage>>> WarningMessageLiveData = new MutableLiveData<>();

    public WarningMessageViewModel(VehicleRepository repository) {
        super(repository);
    }

    public void getWarningMessageList(){
        mRepository.getWarningMessageList(new Callback<BaseResponse<List<WarningMessage>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<WarningMessage>>> call, Response<BaseResponse<List<WarningMessage>>> resp) {
                if(resp.body() != null && resp.body().getErrorCode() == Constants.CODE_SUCCESS){
                    LogUtils.logI(TAG,"[getWarningMessageList]-success："+resp.body().toString());
                    WarningMessageLiveData.setValue(resp.body());
                }else{
                    LogUtils.logI(TAG,"[getWarningMessageList]-failure");
                    WarningMessageLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<WarningMessage>>> call, Throwable t) {
                LogUtils.logI(TAG,"[getWarningMessageList]-onFailure");
                t.printStackTrace();
                WarningMessageLiveData.setValue(null);
            }
        });
    }
}
