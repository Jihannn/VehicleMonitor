package com.jihan.vehicle.server.vehicleserver.dao;

import com.jihan.vehicle.server.vehicleserver.entity.VehicleStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface VehicleStatusMapper {
    @Insert("INSERT INTO vehicle_status(vehicle_id, speed, fuel_level, engine_status, door_status, timestamp) " +
            "VALUES(#{vehicleId}, #{speed}, #{fuelLevel}, #{engineStatus}, #{doorStatus}, #{timestamp})")
    void insertVehicleStatus(VehicleStatus vehicleStatus);

    @Select("SELECT * FROM vehicle_status WHERE id=#{id}")
    VehicleStatus getVehicleStatusById(Integer id);

    @Select("SELECT * FROM vehicle_status WHERE vehicle_id=#{vehicleId}")
    List<VehicleStatus> getVehicleStatusByVehicleId(Integer vehicleId);

    @Update("UPDATE vehicle_status SET vehicle_id=#{vehicleId}, speed=#{speed}, fuel_level=#{fuelLevel}, " +
            "engine_status=#{engineStatus}, door_status=#{doorStatus}, timestamp=#{timestamp} WHERE id=#{id}")
    void updateVehicleStatus(VehicleStatus vehicleStatus);

    @Delete("DELETE FROM vehicle_status WHERE id=#{id}")
    void deleteVehicleStatus(Integer id);
}
