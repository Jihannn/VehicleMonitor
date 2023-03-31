package com.jihan.monitor.service.model;

import static com.jihan.monitor.service.ServiceApp.TAG_SERVICE;

import android.location.Location;
import android.os.RemoteException;
import android.util.Log;

import com.jihan.lib_common.model.BaseRepository;
import com.jihan.lib_common.model.BaseResponse;
import com.jihan.lib_common.network.ServiceCreator;
import com.jihan.lib_common.utils.LogUtils;
import com.jihan.monitor.service.ILoginCallback;
import com.jihan.monitor.service.network.UploadService;
import com.jihan.monitor.service.utils.LocationProvider;
import com.jihan.monitor.service.utils.SPUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleRepository {
    private static final String TAG = TAG_SERVICE + VehicleRepository.class.getSimpleName();

    private static VehicleRepository INSTANCE;

    private final UploadService mUploadService;

    private VehicleRepository() {
        mUploadService = ServiceCreator.INSTANCE.create(UploadService.class);
    }

    public static synchronized VehicleRepository getInstance(){
        if(INSTANCE == null){
            INSTANCE = new VehicleRepository();
        }
        return INSTANCE;
    }

    public void login(String username, String password, ILoginCallback callback){
        Call<BaseResponse<String>> call = mUploadService.login(username, password);
        try {
            Response<BaseResponse<String>> response = call.execute();
            if(response.body() != null){
                String token = response.body().getData();
                LogUtils.logI(TAG,"[login-onResponse]"+token);
                SPUtils.put(SPUtils.KEY_TOKEN,token);
                callback.onResponse(token);
            }else{
                LogUtils.logI(TAG,"[login] response body is null");
            }
        } catch (IOException | RemoteException e) {
            LogUtils.logI(TAG,"[login-RuntimeException]"+e.getMessage());
            throw new RuntimeException(e);
        }
        // call.enqueue(new Callback<BaseResponse<String>>() {
        //     @Override
        //     public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
        //         if(response.body()!=null){
        //             String token = response.body().getData();
        //             LogUtils.logI(TAG,"[login-onResponse]"+token);
        //             SPUtils.put(SPUtils.KEY_TOKEN,token);
        //         }else{
        //             LogUtils.logI(TAG, "[login-onResponse] response body is null");
        //         }
        //     }
        //
        //     @Override
        //     public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
        //         LogUtils.logI(TAG,"[login-onFailure]");
        //     }
        // });
        // return result;
    }

    public void upload() {
        Location location = LocationProvider.getInstance().getBestLocation();
        if (location != null) {
            MyCar.getInstance().setLongitude(location.getLongitude());
            MyCar.getInstance().setLatitude(location.getLatitude());
        }
        Call<BaseResponse<String>> call = mUploadService.upload(SPUtils.get(SPUtils.KEY_TOKEN,"NaN"),MyCar.getInstance());
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if (response.body() != null) {
                    LogUtils.logI(TAG, response.body().getErrorMsg());
                } else {
                    LogUtils.logI(TAG, "response body is null");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                LogUtils.logI(TAG, t.toString());
            }
        });
        LogUtils.logI(TAG, MyCar.getInstance().toString());
    }

    public int warning(){
        Location location = LocationProvider.getInstance().getBestLocation();
        if (location != null) {
            MyCar.getInstance().setLongitude(location.getLongitude());
            MyCar.getInstance().setLatitude(location.getLatitude());
        }
        Call<BaseResponse<String>> call = mUploadService.warning(SPUtils.get(SPUtils.KEY_TOKEN,"NaN"),MyCar.getInstance());
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if (response.body() != null) {
                    LogUtils.logI(TAG, response.body().getErrorMsg());
                } else {
                    LogUtils.logI(TAG, "response body is null");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                LogUtils.logI(TAG, t.toString());
            }
        });
        LogUtils.logI(TAG, MyCar.getInstance().toString());
        return Constants.REQUEST_SUCCESS;
    }
}
