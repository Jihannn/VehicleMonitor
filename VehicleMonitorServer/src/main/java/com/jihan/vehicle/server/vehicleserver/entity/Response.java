package com.jihan.vehicle.server.vehicleserver.entity;

import lombok.Data;

@Data
public class Response <T> {
    T data;
    int errorCode;
    String errorMsg;
}
