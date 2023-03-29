package com.jihan.monitor.service.utils;

import android.os.Parcel;

public class ParcelUtils {
    private static final int INT_NULL_VALUE = -1;
    private static final String STRING_NULL_VALUE = "NaN";

    public static void writeString(Parcel dest, String value) {
        if (value == null) {
            dest.writeString(STRING_NULL_VALUE);
        } else {
            dest.writeString(value);
        }
    }

    public static void writeInteger(Parcel dest, Integer value) {
        if (value == null) {
            dest.writeInt(INT_NULL_VALUE);
        } else {
            dest.writeInt(value);
        }
    }

    public static void writeFloat(Parcel dest, Float value) {
        if (value == null) {
            dest.writeFloat(INT_NULL_VALUE);
        } else {
            dest.writeFloat(value);
        }
    }

    public static void writeDouble(Parcel dest, Double value) {
        if (value == null) {
            dest.writeDouble(INT_NULL_VALUE);
        } else {
            dest.writeDouble(value);
        }
    }
}
