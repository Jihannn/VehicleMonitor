package com.jihan.monitor.phone.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.jihan.monitor.phone.utils.ParcelUtils;

public class Vehicle implements Parcelable {
    private int id;
    private String plate_number;
    private String brand;
    private String model;
    private String device_id;

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Vehicle(Parcel in){
        readFromParcel(in);
    }

    public Vehicle(String plate_number, String brand, String model, String deviceId) {
        this.plate_number = plate_number;
        this.brand = brand;
        this.model = model;
        this.device_id = deviceId;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", plate_number='" + plate_number + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", device_id='" + device_id + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
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
        ParcelUtils.writeInteger(dest,id);
        ParcelUtils.writeString(dest,plate_number);
        ParcelUtils.writeString(dest,brand);
        ParcelUtils.writeString(dest,model);
        ParcelUtils.writeString(dest, device_id);
    }

    public void readFromParcel(Parcel in){
        id = in.readInt();
        plate_number =in.readString();
        brand = in.readString();
        model = in.readString();
        device_id = in.readString();
    }
}
