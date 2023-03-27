package com.jihan.vehicle.server.vehicleserver.service;

import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    Vehicle selectVehicleById(int id);

    List<Vehicle> selectAllVehicles();

    void insertVehicle(Vehicle vehicle);

    void updateVehicle(Vehicle vehicle);

    void deleteVehicle(int id);
}
