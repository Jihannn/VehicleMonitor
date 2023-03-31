package com.jihan.monitor.ui;

import androidx.lifecycle.MutableLiveData;

import com.jihan.lib_common.utils.AppExecutors;
import com.jihan.lib_common.utils.LogUtils;
import com.jihan.lib_common.viewmodel.BaseViewModel;
import com.jihan.monitor.CarApp;
import com.jihan.monitor.model.LoginRepository;
import com.jihan.monitor.model.UserManager;
import com.jihan.monitor.model.VehicleRepository;
import com.jihan.monitor.sdk.Constants;
import com.jihan.monitor.sdk.listener.LoginCallback;
import com.jihan.monitor.utils.SPUtils;

public class LoginViewModel extends BaseViewModel<LoginRepository> {
    private static final String TAG = CarApp.TAG_HMI + LoginViewModel.class.getSimpleName();
    private MutableLiveData<Integer> mLoginResult;
    private final AppExecutors mAppExecutors;

    public LoginViewModel(LoginRepository repository,AppExecutors executors) {
        super(repository);
        this.mAppExecutors = executors;
    }

    public void requestLogin(String username,String password){
        LogUtils.logI(TAG,"[requestLogin]");
        mAppExecutors.execute(new Runnable() {
            @Override
            public void run() {
                mRepository.requestLogin(username, password, new LoginCallback() {
                    @Override
                    public void onResponse(String token) {
                        int result = Constants.CODE_SERVICE_NOT_CONNECT;
                        if(token == null){
                            LogUtils.logI(TAG,"token is null");
                            result = Constants.CODE_LOGIN_FAILURE;
                        }else{
                            UserManager.saveUser(token);
                            result = Constants.CODE_SUCCESS;
                        }
                        final int fResult = result;
                        mAppExecutors.post(() -> mLoginResult.setValue(fResult));
                    }
                });
            }
        });
    }

    public MutableLiveData<Integer> getLoginResultLiveData(){
        if(mLoginResult == null){
            mLoginResult = new MutableLiveData<>();
        }
        return mLoginResult;
    }
}
