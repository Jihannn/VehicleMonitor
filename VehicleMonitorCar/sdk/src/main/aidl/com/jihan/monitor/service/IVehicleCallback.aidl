package com.jihan.monitor.service;

interface IVehicleCallback {
    oneway void onSpeedChanged(float speed);
}