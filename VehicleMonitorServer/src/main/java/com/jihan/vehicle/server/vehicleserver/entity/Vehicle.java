package com.jihan.vehicle.server.vehicleserver.entity;

import lombok.Data;

@Data
public class Vehicle {
    private int id;
    private String plate_number;
    private String brand;
    private String model;
    private int production_year;
    private String device_id;
    private String applicant;
}