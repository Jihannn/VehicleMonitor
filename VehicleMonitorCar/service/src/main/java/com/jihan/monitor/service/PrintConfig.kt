package com.jihan.monitor.service

import android.car.hardware.CarPropertyConfig
import android.car.hardware.property.CarPropertyManager
import com.jihan.lib_common.utils.LogUtils

fun printSupportConfigList(manager: CarPropertyManager){
    val configList = manager.propertyList
    for (config in configList){
        LogUtils.logI("config","能否读写:${config.access}," +
                "区域类型：${config.areaType}" +
                "区域ID：${config.areaIds}" +
                "变化类型：${config.changeMode}" +
                "属性ID：${config.propertyId}" +
                "额外的配置属性：${config.configArray}")
    }
}

fun <E> printProperty(manager: CarPropertyManager,clazz:Class<E>,propertyId:Int,areaType:Int){
    val value = manager.getProperty(clazz, propertyId, areaType)
    LogUtils.logI("config","propertyId:${value.propertyId}" +
            "value:${value.value}" +
            "areaId:${value.areaId}" +
            "status:${value.status}" +
            "timestamp:${value.timestamp}")
}