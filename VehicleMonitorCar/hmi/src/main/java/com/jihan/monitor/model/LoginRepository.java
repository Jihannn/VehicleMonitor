package com.jihan.monitor.model;

import static com.jihan.monitor.CarApp.TAG_HMI;

import android.text.TextUtils;

import com.jihan.lib_common.model.BaseRepository;
import com.jihan.lib_common.utils.LogUtils;
import com.jihan.monitor.sdk.VehicleManager;
import com.jihan.monitor.sdk.listener.LoginCallback;

public class LoginRepository extends BaseRepository {
    private static final String TAG = TAG_HMI + LoginRepository.class.getSimpleName();
    private final VehicleManager mVehicleManager;
    public LoginRepository(VehicleManager manager){
        this.mVehicleManager = manager;
    }

    public void requestLogin(String username,String password,LoginCallback callback){
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            LogUtils.logI(TAG,"用户名和密码为空");
        }
        mVehicleManager.login(username.trim(), password.trim(),callback);
    }
}
