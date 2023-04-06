package com.jihan.monitor.service.network;

import com.jihan.lib_common.model.BaseResponse;
import com.jihan.monitor.service.model.Vehicle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UploadService {

    @FormUrlEncoded
    @POST("login")
    Call<BaseResponse<String>> login(@Field("username") String username,@Field("password") String password);

    @POST("upload")
    Call<BaseResponse<String>> upload(@Header("Authorization") String token,@Body Vehicle vehicle);

    @POST("warning")
    Call<BaseResponse<String>> warning(@Header("Authorization") String token,@Body Vehicle vehicle);
}