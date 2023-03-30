package com.jihan.monitor.model;

import com.jihan.lib_common.base.MyApplication;
import com.jihan.monitor.CarApp;
import com.jihan.monitor.utils.SPUtils;

public class UserManager {
    private static final String KEY_USER = "USER";

    public static void saveUser(String username){
        SPUtils.put(KEY_USER,username);
    }

    public static boolean isLogin(){
        return SPUtils.get(KEY_USER,false);
    }

    public static void logout(){
        SPUtils.remove(MyApplication.appContext,KEY_USER);
    }
}
