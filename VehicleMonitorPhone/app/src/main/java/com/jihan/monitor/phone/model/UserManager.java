package com.jihan.monitor.phone.model;


import com.jihan.monitor.phone.PhoneApplication;
import com.jihan.monitor.phone.utils.SPUtils;

public class UserManager {
    private static final String KEY_TOKEN = "TOKEN";

    public static void saveUser(String token){
        SPUtils.put(KEY_TOKEN,token);
    }

    public static String getUser(){
        return SPUtils.get(KEY_TOKEN,"NaN");
    }

    public static boolean isLogin(){
        return !SPUtils.get(KEY_TOKEN,"NaN").equals("NaN");
    }

    public static void logout(){
        SPUtils.remove(PhoneApplication.appContext, KEY_TOKEN);
    }
}
