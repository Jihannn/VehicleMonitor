package com.jihan.monitor.model;

public interface HvacCallback {

    default void onTemperatureChanged(String temp){

    }

}