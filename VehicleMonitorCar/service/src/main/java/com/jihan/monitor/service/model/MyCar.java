package com.jihan.monitor.service.model;

import com.jihan.monitor.service.model.Vehicle;

public class MyCar {
    private static Vehicle mCar;

    private MyCar() {
    }

    public static synchronized Vehicle getInstance() {
        if (mCar == null) {
            mCar = new Vehicle();
        }
        return mCar;
    }

}
