package com.jihan.vehicle.server.vehicleserver.dao;

import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface VehicleMapper {
    @Select("SELECT * FROM vehicles WHERE id = #{id}")
    Vehicle selectById(int id);

    @Select("SELECT * FROM vehicles")
    List<Vehicle> selectAll();

    @Insert("INSERT INTO vehicles(plate_number, brand, model, production_year) VALUES (#{plateNumber}, #{brand}, #{model}, #{productionYear})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Vehicle vehicle);

    @Update("UPDATE vehicles SET plate_number = #{plateNumber}, brand = #{brand}, model = #{model}, production_year = #{productionYear} WHERE id = #{id}")
    int update(Vehicle vehicle);

    @Delete("DELETE FROM vehicles WHERE id = #{id}")
    int delete(int id);
}
