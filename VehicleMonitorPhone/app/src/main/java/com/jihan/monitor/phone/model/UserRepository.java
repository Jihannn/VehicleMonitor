package com.jihan.monitor.phone.model;


import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;
import static com.jihan.monitor.phone.model.Constants.TOKEN_INTERCEPTOR;

import com.jihan.monitor.lib_common.model.BaseRepository;
import com.jihan.monitor.lib_common.model.BaseResponse;
import com.jihan.monitor.lib_common.network.ServiceCreator;
import com.jihan.monitor.lib_common.utils.LogUtils;
import com.jihan.monitor.phone.network.UserService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class UserRepository extends BaseRepository {

    private static final String TAG = TAG_PHONE + UserRepository.class.getSimpleName();

    private final UserService mUserService;

    public UserRepository(){
        mUserService = ServiceCreator.INSTANCE.create(UserService.class,TokenInterceptor.getInstance());
    }

    public void register(String username,String password,Callback<BaseResponse<Void>> callback){
        Call<BaseResponse<Void>> call = mUserService.register(username, password);
        call.enqueue(callback);
    }

    public void login(String username,String password,Callback<BaseResponse<String>> callback){
        Call<BaseResponse<String>> call = mUserService.login(username, password);
        call.enqueue(callback);
    }


}
