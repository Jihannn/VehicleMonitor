package com.jihan.monitor.model;

import com.jihan.lib_common.model.BaseRepository;
import com.jihan.lib_common.utils.LogUtils;
import com.jihan.monitor.sdk.VehicleCallback;
import com.jihan.monitor.sdk.VehicleManager;
import com.jihan.monitor.service.model.Vehicle;

public class VehicleRepository extends BaseRepository {

    private final VehicleManager mVehicleManager;

    public VehicleRepository(VehicleManager manager){
        this.mVehicleManager = manager;
    }

    public void requestVehicleData(Vehicle vehicle){
        mVehicleManager.getVehicleStatus(vehicle);
    }

    public void registerCallback(VehicleCallback callback){
        mVehicleManager.registerCallback(callback);
    }

    public int warning(){
        return mVehicleManager.warning();
    }
}
