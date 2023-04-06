package com.jihan.vehicle.server.vehicleserver.service;

import com.jihan.vehicle.server.vehicleserver.entity.WarningMessage;

import java.util.List;

public interface WarningMessageService{

    void insertWarningMessage(String username,String deviceId,String message);

    List<WarningMessage> selectWarnMessageByUserID(int user_id);

    List<WarningMessage> selectWarnMessageByDeviceID(int device_id);
}
