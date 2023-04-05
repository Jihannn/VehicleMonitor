package com.jihan.vehicle.server.vehicleserver.service.impl;

import com.jihan.vehicle.server.vehicleserver.dao.VehicleMapper;
import com.jihan.vehicle.server.vehicleserver.dao.VehicleStatusMapper;
import com.jihan.vehicle.server.vehicleserver.entity.VehicleStatus;
import com.jihan.vehicle.server.vehicleserver.service.VehicleStatusService;
import com.jihan.vehicle.server.vehicleserver.utils.MybatisUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@Log4j2
public class VehicleStatusServiceImpl implements VehicleStatusService {
    @Override
    public void insertVehicleStatus(VehicleStatus vehicleStatus) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            VehicleStatusMapper mapper = sqlSession.getMapper(VehicleStatusMapper.class);
            boolean result = mapper.insertVehicleStatus(vehicleStatus);
            log.info("result:"+result);
        }
    }

    @Override
    public List<VehicleStatus> findVehicleStatusByDriver(String username) {
        return null;
    }

    @Override
    public void updateVehicleStatus(VehicleStatus vehicleStatus) {

    }

    @Override
    public void deleteVehicle(int id) {

    }

    @Override
    public List<VehicleStatus> findAll() {
        return null;
    }

    @Override
    public VehicleStatus getLatestVehicleStatus(String device_id) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            VehicleStatusMapper mapper = sqlSession.getMapper(VehicleStatusMapper.class);
            return mapper.getLatestVehicleStatus(device_id);
        }
    }
}
