package com.jihan.monitor.phone.model;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Constants {

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_FAILURE = 400;
    public static final Interceptor TOKEN_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original.newBuilder().header("token", UserManager.getUser()).build();
            return chain.proceed(request);
        }
    };
}
