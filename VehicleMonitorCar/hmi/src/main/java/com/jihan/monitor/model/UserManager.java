package com.jihan.monitor.model;

import com.jihan.lib_common.base.MyApplication;
import com.jihan.monitor.utils.SPUtils;

public class UserManager {
    private static final String KEY_TOKEN = "TOKEN";

    public static void saveUser(String token){
        SPUtils.put(KEY_TOKEN,token);
    }

    public static boolean isLogin(){
        return SPUtils.get(KEY_TOKEN,false);
    }

    public static void logout(){
        SPUtils.remove(MyApplication.appContext, KEY_TOKEN);
    }
}
