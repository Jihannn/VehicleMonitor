package com.jihan.monitor.phone.ui;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.jihan.monitor.lib_common.model.BaseResponse;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.lib_common.viewmodel.BaseViewModel;
import com.jihan.monitor.phone.R;
import com.jihan.monitor.phone.model.Constants;
import com.jihan.monitor.phone.model.User;
import com.jihan.monitor.phone.model.UserManager;
import com.jihan.monitor.phone.model.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModel<UserRepository> {
    private static final String TAG = TAG_PHONE + LoginViewModel.class.getSimpleName();

    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();

    public MutableLiveData<Integer> registerLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> loginLiveData = new MutableLiveData<>();

    public LoginViewModel(UserRepository repository) {
        super(repository);
    }

    public void register(String username,String password){
        mRepository.register(username, password,new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> resp) {
                if(resp.body() != null && resp.body().getErrorCode() == Constants.CODE_SUCCESS){
                    registerLiveData.setValue(Constants.CODE_SUCCESS);
                }else{
                    LogUtils.logI(TAG,"[register]-body is null");
                    registerLiveData.setValue(Constants.CODE_FAILURE);
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                LogUtils.logI(TAG,"[register]-error"+t.getMessage());
                registerLiveData.setValue(Constants.CODE_FAILURE);
            }
        });
    }

    public void login(String username,String password){
        mRepository.login(username, password,new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> resp) {
                if(resp.body() != null && resp.body().getErrorCode() == Constants.CODE_SUCCESS){
                    loginLiveData.setValue(Constants.CODE_SUCCESS);
                    UserManager.saveUser(resp.body().getData());
                }else{
                    LogUtils.logI(TAG,"[login]-body is null");
                    loginLiveData.setValue(Constants.CODE_FAILURE);
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                LogUtils.logI(TAG,"[login]-error"+t.getMessage());
                loginLiveData.setValue(Constants.CODE_FAILURE);
            }
        });
    }
}
