package com.jihan.vehicle.server.vehicleserver.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class WarningMessage {
    int id;
    int user_id;
    int vehicle_id;
    String driver;
    String message;
    Timestamp timestamp;
}
