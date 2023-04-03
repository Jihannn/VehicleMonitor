package com.jihan.monitor.phone.model;

import static com.jihan.monitor.phone.PhoneApplication.TAG_PHONE;

import com.jihan.monitor.lib_common.model.BaseRepository;
import com.jihan.monitor.lib_common.model.BaseResponse;
import com.jihan.monitor.lib_common.network.ServiceCreator;
import com.jihan.monitor.phone.network.UserService;
import com.jihan.monitor.phone.utils.SPUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class VehicleRepository extends BaseRepository {

    private static final String TAG = TAG_PHONE + VehicleRepository.class.getSimpleName();

    private final UserService mUserService;

    public VehicleRepository(){
        mUserService = ServiceCreator.INSTANCE.create(UserService.class,TokenInterceptor.getInstance());
    }

    public void getVehicleList(Callback<BaseResponse<List<Vehicle>>> callback){
        Call<BaseResponse<List<Vehicle>>> call = mUserService.getAllVehicle();
        call.enqueue(callback);
    }
}
