package com.jihan.vehicle.server.vehicleserver.dao;

import com.jihan.vehicle.server.vehicleserver.entity.UserVehicle;
import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserVehicleMapper {

    @Select("SELECT * FROM user_vehicles WHERE user_id = #{userId}")
    List<UserVehicle> getUserVehiclesByUserId(int userId);

    @Select("SELECT v.id, v.plate_number, v.brand, v.model, v.production_year " +
            "FROM user_vehicles uv " +
            "JOIN vehicles v ON uv.vehicle_id = v.id " +
            "WHERE uv.user_id = #{userId}")
    List<Vehicle> findVehiclesByUserId(int userId);

    @Insert("INSERT INTO user_vehicles(user_id, vehicle_id) VALUES (#{userId}, #{vehicleId})")
    void addUserVehicle(@Param("userId") int userId, @Param("vehicleId") int vehicleId);

    @Delete("DELETE FROM user_vehicles WHERE user_id = #{userId} AND vehicle_id = #{vehicleId}")
    void deleteUserVehicle(@Param("userId") int userId, @Param("vehicleId") int vehicleId);

    @Select("SELECT * FROM user_vehicles WHERE vehicle_id = #{vehicleId}")
    List<UserVehicle> getUserVehiclesByVehicleId(int vehicleId);

}