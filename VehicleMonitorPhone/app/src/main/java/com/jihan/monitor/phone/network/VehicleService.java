package com.jihan.monitor.phone.network;

import com.jihan.monitor.lib_common.model.BaseResponse;
import com.jihan.monitor.phone.model.Vehicle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VehicleService {

    @POST("vehicle")
    Call<BaseResponse<String>> registerVehicle(@Body Vehicle vehicle);

}
