package com.jihan.vehicle.server.vehicleserver.entity;

import java.sql.Timestamp;

public class VehicleStatus {
    private Integer id;
    private Integer vehicleId;
    private Float speed;
    private Float fuelLevel;
    private Boolean engineStatus;
    private Boolean doorStatus;
    private Timestamp timestamp;
}