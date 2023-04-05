package com.jihan.vehicle.server.vehicleserver.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class VehicleStatus {
    private String driver;
    private String model;
    private int modelYear;
    private String manufacturer;
    private float fuelCapacity;
    private float fuelLevel;
    private int ignitionState;
    private float speed;
    private double latitude;
    private double longitude;
    private Timestamp updateTime;
    private String deviceId;
}