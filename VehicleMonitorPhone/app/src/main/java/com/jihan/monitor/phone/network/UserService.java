package com.jihan.monitor.phone.network;

import com.jihan.monitor.lib_common.model.BaseResponse;
import com.jihan.monitor.phone.model.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @FormUrlEncoded
    @POST("register")
    Call<BaseResponse<Void>> register(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<BaseResponse<String>> login(@Field("username") String username, @Field("password") String password);

    @GET("userVehicles")
    Call<BaseResponse<List<Vehicle>>> getAllVehicle();
}
