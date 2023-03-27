package com.jihan.vehicle.server.vehicleserver.service;

import com.jihan.vehicle.server.vehicleserver.entity.VehicleVideo;

import java.util.List;

public interface VehicleVideoService {

    void insertVehicleVideo(VehicleVideo vehicleVideo);

    List<VehicleVideo> selectVehicleVideosByVehicleId(Integer vehicleId);
}
