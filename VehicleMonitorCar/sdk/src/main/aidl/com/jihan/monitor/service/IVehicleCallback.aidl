package com.jihan.monitor.service;

interface IVehicleCallback {
    oneway void onStatusChanged(float speed,float fuelLevel);
}