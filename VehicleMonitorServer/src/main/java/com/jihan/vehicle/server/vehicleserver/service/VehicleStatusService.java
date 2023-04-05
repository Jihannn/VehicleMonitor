package com.jihan.vehicle.server.vehicleserver.service;

import com.jihan.vehicle.server.vehicleserver.entity.VehicleStatus;

import java.util.List;

public interface VehicleStatusService {

    void insertVehicleStatus(VehicleStatus vehicleStatus);

    List<VehicleStatus> findVehicleStatusByDriver(String username);

    void updateVehicleStatus(VehicleStatus vehicleStatus);

    void deleteVehicle(int id);

    VehicleStatus getLatestVehicleStatus(int vehicle_id);

    List<VehicleStatus> findAll();
}
