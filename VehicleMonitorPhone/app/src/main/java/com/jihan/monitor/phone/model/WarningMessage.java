package com.jihan.monitor.phone.model;

import java.sql.Timestamp;

public class WarningMessage {
    int id;
    int user_id;
    int vehicle_id;
    String driver;
    String message;
    Timestamp timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WarningMessage{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", vehicle_id=" + vehicle_id +
                ", driver='" + driver + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public WarningMessage(int id, int user_id, int vehicle_id, String driver, String message, Timestamp timestamp) {
        this.id = id;
        this.user_id = user_id;
        this.vehicle_id = vehicle_id;
        this.driver = driver;
        this.message = message;
        this.timestamp = timestamp;
    }
}
