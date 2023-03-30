package com.jihan.monitor.ui;

import androidx.lifecycle.MutableLiveData;

import com.jihan.lib_common.utils.AppExecutors;
import com.jihan.lib_common.utils.LogUtils;
import com.jihan.lib_common.viewmodel.BaseViewModel;
import com.jihan.monitor.CarApp;
import com.jihan.monitor.model.LoginRepository;
import com.jihan.monitor.model.VehicleRepository;

public class LoginViewModel extends BaseViewModel<LoginRepository> {
    private static final String TAG = CarApp.TAG_HMI + LoginViewModel.class.getSimpleName();
    private MutableLiveData<Integer> mLoginResult;

    public LoginViewModel(LoginRepository repository) {
        super(repository);
    }

    public void requestLogin(String username,String password){
        LogUtils.logI(TAG,"[requestLogin]");
        int result = mRepository.requestLogin(username,password);
        mLoginResult.setValue(result);
    }

    public MutableLiveData<Integer> getLoginResultLiveData(){
        if(mLoginResult == null){
            mLoginResult = new MutableLiveData<>();
        }
        return mLoginResult;
    }
}
