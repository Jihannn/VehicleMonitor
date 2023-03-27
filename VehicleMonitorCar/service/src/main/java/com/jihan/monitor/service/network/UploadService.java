package com.jihan.monitor.service.network;

import com.jihan.lib_common.model.BaseResponse;
import com.jihan.monitor.service.model.Vehicle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UploadService {

    @POST("vehicle")
    Call<BaseResponse<String>> upload(@Body Vehicle vehicle);

}
