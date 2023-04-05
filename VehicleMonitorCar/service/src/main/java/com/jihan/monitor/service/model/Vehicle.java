package com.jihan.monitor.service.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.jihan.monitor.service.utils.ParcelUtils;

public class Vehicle implements Parcelable {

    private String model;
    private Integer modelYear;
    private String manufacturer;
    private Float fuelCapacity;
    private Float fuelLevel;
    private Integer ignitionState;
    private Float speed;
    private String updateTime;
    private Double longitude;
    private Double latitude;
    private String deviceId;
    private String driver;

    public Vehicle(){

    }

    public Vehicle(Parcel in){
        readFromParcel(in);
    }

    public Vehicle(String model, Integer modelYear, String manufacturer, Float fuelCapacity, Float fuelLevel, Integer ignitionState, Float speed, String updateTime, Double longitude, Double latitude, String deviceId, String driver) {
        this.model = model;
        this.modelYear = modelYear;
        this.manufacturer = manufacturer;
        this.fuelCapacity = fuelCapacity;
        this.fuelLevel = fuelLevel;
        this.ignitionState = ignitionState;
        this.speed = speed;
        this.updateTime = updateTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.deviceId = deviceId;
        this.driver = driver;
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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
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
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", deviceId='" + deviceId + '\'' +
                ", driver='" + driver + '\'' +
                '}';
    }

    public static final Parcelable.Creator<Vehicle> CREATOR = new Parcelable.Creator<Vehicle>() {
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        ParcelUtils.writeString(dest,model);
        ParcelUtils.writeInteger(dest,modelYear);
        ParcelUtils.writeString(dest,manufacturer);
        ParcelUtils.writeFloat(dest,fuelCapacity);
        ParcelUtils.writeFloat(dest,fuelLevel);
        ParcelUtils.writeInteger(dest,ignitionState);
        ParcelUtils.writeFloat(dest,speed);
        ParcelUtils.writeString(dest,updateTime);
        ParcelUtils.writeDouble(dest,longitude);
        ParcelUtils.writeDouble(dest,latitude);
        ParcelUtils.writeString(dest,deviceId);
        ParcelUtils.writeString(dest,driver);
    }

    public void readFromParcel(Parcel in){
        model = in.readString();
        modelYear = in.readInt();
        manufacturer = in.readString();
        fuelCapacity = in.readFloat();
        fuelLevel = in.readFloat();
        ignitionState = in.readInt();
        speed = in.readFloat();
        updateTime = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        deviceId = in.readString();
        driver = in.readString();
    }

    public void update(Vehicle vehicle){
        model = vehicle.model;
        modelYear = vehicle.modelYear;
        manufacturer = vehicle.manufacturer;
        fuelCapacity = vehicle.fuelCapacity;
        fuelLevel = vehicle.fuelLevel;
        ignitionState = vehicle.ignitionState;
        speed = vehicle.speed;
        updateTime = vehicle.updateTime;
        longitude = vehicle.longitude;
        latitude = vehicle.latitude;
    }
}
