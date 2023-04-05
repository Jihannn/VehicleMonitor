package com.jihan.vehicle.server.vehicleserver.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class VehicleStatus {
    private int id;
    private int vehicle_id;
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
}