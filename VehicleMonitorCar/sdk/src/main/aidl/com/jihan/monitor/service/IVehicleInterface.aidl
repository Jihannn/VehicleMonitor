package com.jihan.monitor.service;
import com.jihan.monitor.service.IStatusCallback;
import com.jihan.monitor.service.ILoginCallback;
import com.jihan.monitor.service.model.Vehicle;

interface IVehicleInterface {

    boolean getVehicleStatus(in IStatusCallback callback);

    boolean login(in String username,in String password,in ILoginCallback callback);

    int warning();
}