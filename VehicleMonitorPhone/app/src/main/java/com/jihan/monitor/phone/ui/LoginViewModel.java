package com.jihan.monitor.phone.ui;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.jihan.monitor.lib_common.model.BaseResponse;
import com.jihan.monitor.lib_common.viewmodel.BaseViewModel;
import com.jihan.monitor.phone.model.User;
import com.jihan.monitor.phone.network.LoginRepository;

public class LoginViewModel extends BaseViewModel<LoginRepository> {

    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();

    public MutableLiveData<BaseResponse<User>> registerLiveData = new MutableLiveData<>();

    public LoginViewModel(LoginRepository repository) {
        super(repository);
    }


    public void register(String username,String password){
        // registerLiveData.setValue();
    }
}
