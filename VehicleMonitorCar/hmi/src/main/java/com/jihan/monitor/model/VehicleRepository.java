package com.jihan.monitor.model;

import com.jihan.lib_common.model.BaseRepository;
import com.jihan.monitor.sdk.listener.StatusCallback;
import com.jihan.monitor.sdk.VehicleManager;

public class VehicleRepository extends BaseRepository {

    private final VehicleManager mVehicleManager;

    public VehicleRepository(VehicleManager manager){
        this.mVehicleManager = manager;
    }

    public void requestVehicleData(StatusCallback callback){
        mVehicleManager.getVehicleStatus(callback);
    }

    public int warning(){
        return mVehicleManager.warning();
    }
}
