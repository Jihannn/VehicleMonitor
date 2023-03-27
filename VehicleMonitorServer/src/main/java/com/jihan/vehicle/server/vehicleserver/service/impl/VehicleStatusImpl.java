package com.jihan.vehicle.server.vehicleserver.service.impl;

import com.jihan.vehicle.server.vehicleserver.dao.VehicleMapper;
import com.jihan.vehicle.server.vehicleserver.dao.VehicleStatusMapper;
import com.jihan.vehicle.server.vehicleserver.entity.VehicleStatus;
import com.jihan.vehicle.server.vehicleserver.service.VehicleStatusService;
import com.jihan.vehicle.server.vehicleserver.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class VehicleStatusImpl implements VehicleStatusService {
    @Override
    public void insertVehicleStatus(VehicleStatus vehicleStatus) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            VehicleStatusMapper mapper = sqlSession.getMapper(VehicleStatusMapper.class);
            mapper.insertVehicleStatus(vehicleStatus);
        }
    }

    @Override
    public VehicleStatus getVehicleStatusById(Integer id) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            VehicleStatusMapper mapper = sqlSession.getMapper(VehicleStatusMapper.class);
            return mapper.getVehicleStatusById(id);
        }
    }

    @Override
    public List<VehicleStatus> getVehicleStatusByVehicleId(Integer vehicleId) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            VehicleStatusMapper mapper = sqlSession.getMapper(VehicleStatusMapper.class);
            return mapper.getVehicleStatusByVehicleId(vehicleId);
        }
    }
}
