package com.jihan.monitor.service.model;

public class Vehicle {

    private String model;
    private Integer modelYear;
    private String manufacturer;
    private Float fuelCapacity;
    private Float fuelLevel;
    private Integer ignitionState;
    private Float speed;

    private String updateTime;

    public Vehicle(){

    }

    public Vehicle(String model, Integer modelYear, String manufacturer, Float fuelCapacity) {
        this.model = model;
        this.modelYear = modelYear;
        this.manufacturer = manufacturer;
        this.fuelCapacity = fuelCapacity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Float getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(Float fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public Float getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(Float fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public Integer getIgnitionState() {
        return ignitionState;
    }

    public void setIgnitionState(Integer ignitionState) {
        this.ignitionState = ignitionState;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "model='" + model + '\'' +
                ", modelYear=" + modelYear +
                ", manufacturer='" + manufacturer + '\'' +
                ", fuelCapacity=" + fuelCapacity +
                ", fuelLevel=" + fuelLevel +
                ", ignitionState=" + ignitionState +
                ", speed=" + speed +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
