package com.jihan.monitor.phone.network;

import com.jihan.monitor.lib_common.model.BaseResponse;
import com.jihan.monitor.phone.model.Vehicle;
import com.jihan.monitor.phone.model.WarningMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface VehicleService {

    @POST("vehicle")
    Call<BaseResponse<String>> registerVehicle(@Body Vehicle vehicle);


    @GET("warning")
    Call<BaseResponse<List<WarningMessage>>> getWarningMessageList();
}
