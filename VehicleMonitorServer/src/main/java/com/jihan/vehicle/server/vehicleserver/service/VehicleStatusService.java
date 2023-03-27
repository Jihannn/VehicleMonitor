package com.jihan.vehicle.server.vehicleserver.service;

import com.jihan.vehicle.server.vehicleserver.entity.VehicleStatus;

import java.util.List;

public interface VehicleStatusService {

    void insertVehicleStatus(VehicleStatus vehicleStatus);

    VehicleStatus getVehicleStatusById(Integer id);

    List<VehicleStatus> getVehicleStatusByVehicleId(Integer vehicleId);


}
