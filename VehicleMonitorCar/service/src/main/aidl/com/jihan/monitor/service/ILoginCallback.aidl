package com.jihan.monitor.service;
import com.jihan.monitor.service.model.Vehicle;

interface ILoginCallback {
    void onResponse(in String token);
}