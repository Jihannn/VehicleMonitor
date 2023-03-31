package com.jihan.monitor.service;
import com.jihan.monitor.service.model.Vehicle;

interface IStatusCallback {
    void onStatusChanged(in Vehicle vehicle);
}