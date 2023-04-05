package com.jihan.monitor.phone.model;

import java.sql.Timestamp;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return "VehicleStatus{" +
                "id=" + id +
                ", vehicle_id=" + vehicle_id +
                ", driver='" + driver + '\'' +
                ", model='" + model + '\'' +
                ", modelYear=" + modelYear +
                ", manufacturer='" + manufacturer + '\'' +
                ", fuelCapacity=" + fuelCapacity +
                ", fuelLevel=" + fuelLevel +
                ", ignitionState=" + ignitionState +
                ", speed=" + speed +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", updateTime=" + updateTime +
                '}';
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public float getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(float fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public float getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(float fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public int getIgnitionState() {
        return ignitionState;
    }

    public void setIgnitionState(int ignitionState) {
        this.ignitionState = ignitionState;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public VehicleStatus(){}

    public VehicleStatus(int id, int vehicle_id, String driver, String model, int modelYear, String manufacturer, float fuelCapacity, float fuelLevel, int ignitionState, float speed, double latitude, double longitude, Timestamp updateTime) {
        this.id = id;
        this.vehicle_id = vehicle_id;
        this.driver = driver;
        this.model = model;
        this.modelYear = modelYear;
        this.manufacturer = manufacturer;
        this.fuelCapacity = fuelCapacity;
        this.fuelLevel = fuelLevel;
        this.ignitionState = ignitionState;
        this.speed = speed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updateTime = updateTime;
    }
}
