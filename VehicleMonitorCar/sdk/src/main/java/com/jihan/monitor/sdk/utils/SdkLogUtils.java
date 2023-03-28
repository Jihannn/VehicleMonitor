package com.jihan.monitor.sdk.utils;

import android.util.Log;

public class SdkLogUtils {

    public static final String TAG_SDK = "VEHICLE_SDK_";

    private static boolean VERBOSE = true;

    public static void init(boolean verbose) {
        VERBOSE = verbose;
    }

    public static void logV(String tag, String info) {
        if (VERBOSE) {
            Log.v(tag, "[thread:" + Thread.currentThread().getName() + "] - " + info);
        }
    }
}