package com.jihan.monitor.sdk;

import static com.jihan.monitor.sdk.utils.SdkLogUtils.TAG_SDK;

import android.app.Application;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SdkAppGlobal {
    private static final String TAG = TAG_SDK + SdkAppGlobal.class.getSimpleName();

    public static final String CLASS_FOR_NAME = "android.app.ActivityThread";
    public static final String CURRENT_APPLICATION = "currentApplication";
    public static final String GET_INITIAL_APPLICATION = "getInitialApplication";

    private SdkAppGlobal() {

    }

    public static Application getApplication() {
        Application application = null;
        try {
            Class atClass = Class.forName(CLASS_FOR_NAME);
            Method method = atClass.getDeclaredMethod(CURRENT_APPLICATION);
            method.setAccessible(true);
            application = (Application) method.invoke(null);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | ClassNotFoundException exception) {
            Log.e(TAG, "exception:" + exception.toString());
        }

        if (application != null) {
            return application;
        }

        try {
            Class atClass = Class.forName(CLASS_FOR_NAME);
            Method method = atClass.getDeclaredMethod(GET_INITIAL_APPLICATION);
            method.setAccessible(true);
            application = (Application) method.invoke(null);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | ClassNotFoundException exception) {
            Log.e(TAG, "exception:" + exception.toString());
        }

        return application;
    }

}