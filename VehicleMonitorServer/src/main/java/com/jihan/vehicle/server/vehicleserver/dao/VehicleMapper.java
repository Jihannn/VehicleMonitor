package com.jihan.vehicle.server.vehicleserver.dao;

import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface VehicleMapper {
    @Select("SELECT * FROM vehicles WHERE id = #{id}")
    Vehicle selectById(int id);

    @Select("SELECT id, plate_number, brand, model, production_year FROM vehicles WHERE active = true")
    List<Vehicle> selectAllActive();

    @Insert("INSERT INTO vehicles(plate_number, brand, model, device_id,applicant) VALUES (#{plate_number}, #{brand}, #{model}, #{device_id}, #{applicant})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Vehicle vehicle);

    @Update("UPDATE vehicles SET plate_number = #{plateNumber}, brand = #{brand}, model = #{model}, production_year = #{productionYear} WHERE id = #{id}")
    int update(Vehicle vehicle);

    @Delete("DELETE FROM vehicles WHERE id = #{id}")
    int delete(int id);

    @Select("SELECT * FROM vehicles WHERE active = false")
    List<Vehicle> selectVehiclesByActiveFalse();

    @Update("UPDATE vehicles SET active = 1 WHERE id = #{id}")
    void updateVehiclesActive(int id);
}
