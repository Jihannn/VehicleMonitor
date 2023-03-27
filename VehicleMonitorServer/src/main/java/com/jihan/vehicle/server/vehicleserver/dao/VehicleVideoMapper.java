package com.jihan.vehicle.server.vehicleserver.dao;

import com.jihan.vehicle.server.vehicleserver.entity.VehicleVideo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VehicleVideoMapper {
    @Insert("INSERT INTO vehicle_videos(vehicle_id, video_url, timestamp) VALUES(#{vehicleId}, #{videoUrl}, #{timestamp})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertVehicleVideo(VehicleVideo vehicleVideo);

    @Select("SELECT * FROM vehicle_videos WHERE vehicle_id=#{vehicleId}")
    List<VehicleVideo> selectVehicleVideosByVehicleId(Integer vehicleId);
}
