package com.jihan.vehicle.server.vehicleserver.dao;

import com.jihan.vehicle.server.vehicleserver.entity.WarningMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WarningMessageMapper {
    @Insert("INSERT INTO warning_message (user_id, vehicle_id, message, driver) " +
            "SELECT DISTINCT user_vehicles.user_id, vehicles.id, #{message}, vehicle_status.driver " +
            "FROM vehicles, vehicle_status, user_vehicles " +
            "WHERE vehicles.device_id = #{device_id} " +
            "AND vehicle_status.driver = #{driver} " +
            "AND vehicle_status.device_id = vehicles.device_id " +
            "AND user_vehicles.vehicle_id = vehicles.id")
    void insertWarningMessage(@Param("device_id") String deviceId, @Param("driver") String driver, @Param("message") String message);

    @Select("SELECT * FROM warning_message where user_id = #{user_id} ORDER BY timestamp DESC LIMIT 10")
    List<WarningMessage> selectWarnMessageByUserID(int user_id);

    @Select("SELECT * FROM warning_message where device_id = #{device_id}  ORDER BY timestamp DESC LIMIT 10")
    List<WarningMessage> selectWarnMessageByDeviceID(int device_id);
}
