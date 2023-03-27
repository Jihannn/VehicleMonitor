package com.jihan.vehicle.server.vehicleserver.entity;

import lombok.Data;

import java.util.Date;

@Data
public class VehicleVideo {
    private Integer id;
    private Integer vehicleId;
    private String videoUrl;
    private Date timestamp;
}