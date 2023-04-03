package com.jihan.monitor.phone.model;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import com.jihan.monitor.lib_common.utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    public static final String TAG = TAG_PHONE + TokenInterceptor.class.getSimpleName();

    private static TokenInterceptor INSTANCE;

    private static String TOKEN = "token";

    private TokenInterceptor(){}

    public static synchronized TokenInterceptor getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TokenInterceptor();
        }
        return INSTANCE;
    }

    public static void updateToken(String token){
        TOKEN = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        LogUtils.logI("[intercept]",UserManager.getUser());
        Request original = chain.request();
        Request request = original.newBuilder().header(TOKEN, UserManager.getUser()).build();
        return chain.proceed(request);
    }
}
