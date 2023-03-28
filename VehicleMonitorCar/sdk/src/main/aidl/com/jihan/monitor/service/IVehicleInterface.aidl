package com.jihan.monitor.service;
import com.jihan.monitor.service.model.Vehicle;
import com.jihan.monitor.service.IVehicleCallback;

interface IVehicleInterface {

    boolean registerCallback(in IVehicleCallback callback);

    boolean unregisterCallback(in IVehicleCallback callback);

    void getVehicleStatus(inout Vehicle vehicle);
}