package com.jihan.monitor.service.utils;

import static com.jihan.monitor.service.ServiceApp.TAG_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import com.jihan.lib_common.utils.LogUtils;

public class LocationProvider implements LocationListener {

    private static final String TAG = TAG_SERVICE + LocationProvider.class.getSimpleName();

    public interface LocationGetListener {
        void getLocationInfo(Location location);
    }

    private Context mContext;
    private LocationManager mLocationManager;
    private LocationGetListener mListener;

    private static LocationProvider mLocationProvider;

    private LocationProvider() {

    }

    public static synchronized LocationProvider getInstance() {
        if (mLocationProvider == null) {
            mLocationProvider = new LocationProvider();
        }
        return mLocationProvider;
    }

    public void initProvider(Context context) {
        this.mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    public void getBestLocation(LocationGetListener listener) {
        mListener = listener;
        Location location;
        Criteria criteria = new Criteria();
        String provider = mLocationManager.getBestProvider(criteria, true);
        if (TextUtils.isEmpty(provider)) {
            location = getNetWorkLocation(mContext);
        } else {
            location = mLocationManager.getLastKnownLocation(provider);
        }
        if (location == null) {
            provider = LocationManager.PASSIVE_PROVIDER;
            location = mLocationManager.getLastKnownLocation(provider);
            if (mListener != null) {
                mListener.getLocationInfo(location);
            }
            mLocationManager.requestLocationUpdates(provider, 5000, 1, this);
        } else if (mListener != null) {
            mListener.getLocationInfo(location);
        }
    }

    @SuppressLint("MissingPermission")
    public Location getBestLocation() {
        Location location;
        Criteria criteria = new Criteria();
        String provider = mLocationManager.getBestProvider(criteria, true);
        if (TextUtils.isEmpty(provider)) {
            location = getNetWorkLocation(mContext);
        } else {
            location = mLocationManager.getLastKnownLocation(provider);
        }
        if (location == null) {
            provider = LocationManager.PASSIVE_PROVIDER;
            location = mLocationManager.getLastKnownLocation(provider);
            // mLocationManager.requestLocationUpdates(provider, 5000, 1, this);
        }
        return location;
    }

    @SuppressLint("MissingPermission")
    public Location getNetWorkLocation(Context context) {
        Location location = null;
        if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return location;
    }

    public void unregisterLocationUpdaterListener() {
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        LogUtils.logV(TAG,"location : onLocationChanged");
        if (mListener != null) {
            mListener.getLocationInfo(location);
        }
        unregisterLocationUpdaterListener();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LogUtils.logV(TAG,"location : " + provider + " onStatusChanged. status = " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        LogUtils.logV(TAG,"location : " + provider + " onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        LogUtils.logV(TAG,"location : " + provider + " onProviderDisabled");
    }
}
