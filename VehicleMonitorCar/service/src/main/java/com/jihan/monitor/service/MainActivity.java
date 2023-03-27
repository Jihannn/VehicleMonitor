package com.jihan.monitor.service;

import static com.jihan.monitor.service.ServiceApp.TAG_SERVICE;

import androidx.appcompat.app.AppCompatActivity;

import android.car.Car;
import android.car.CarInfoManager;
import android.car.VehicleAreaType;
import android.car.VehiclePropertyIds;
import android.car.hardware.CarPropertyConfig;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.CarSensorEvent;
import android.car.hardware.CarSensorManager;
import android.car.hardware.property.CarPropertyManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.jihan.lib_common.base.utils.LogUtils;
import com.jihan.monitor.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        startService(new Intent(this,CarService.class));
        finish();
    }
}