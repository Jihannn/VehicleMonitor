package com.jihan.monitor.sdk.listener;

import com.jihan.monitor.service.model.Vehicle;

public interface StatusCallback {
    void onStatusChanged(Vehicle vehicle);
}
