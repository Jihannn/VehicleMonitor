package com.jihan.vehicle.server.vehicleserver.dao;

import com.jihan.vehicle.server.vehicleserver.entity.VehicleStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface VehicleStatusMapper {

    @Insert("INSERT INTO vehicle_status (driver, model, modelYear, manufacturer, fuelCapacity, fuelLevel, ignitionState, speed, latitude, longitude, updateTime) VALUES (#{driver}, #{model}, #{modelYear}, #{manufacturer}, #{fuelCapacity}, #{fuelLevel}, #{ignitionState}, #{speed}, #{latitude}, #{longitude}, #{updateTime})")
    boolean insertVehicleStatus(VehicleStatus vehicleStatus);

    @Select("SELECT * FROM vehicle_status WHERE driver = #{driver}")
    List<VehicleStatus> findVehicleStatusByDriver(String username);

    @Update("UPDATE vehicle_status SET driver = #{driver}, model = #{model}, modelYear = #{modelYear}, manufacturer = #{manufacturer}, fuelCapacity = #{fuelCapacity}, fuelLevel = #{fuelLevel}, ignitionState = #{ignitionState}, speed = #{speed}, latitude = #{latitude}, longitude = #{longitude}, updateTime = #{updateTime} WHERE id = #{id}")
    void updateVehicleStatus(VehicleStatus vehicleStatus);

    @Delete("DELETE FROM vehicle_status WHERE id = #{id}")
    void deleteVehicle(int id);

    @Select("SELECT * FROM vehicle_status")
    List<VehicleStatus> findAll();
}
