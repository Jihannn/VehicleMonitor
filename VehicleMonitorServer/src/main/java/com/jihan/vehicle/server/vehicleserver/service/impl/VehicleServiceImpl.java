package com.jihan.vehicle.server.vehicleserver.service.impl;

import com.jihan.vehicle.server.vehicleserver.dao.VehicleMapper;
import com.jihan.vehicle.server.vehicleserver.entity.User;
import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;
import com.jihan.vehicle.server.vehicleserver.service.VehicleService;
import com.jihan.vehicle.server.vehicleserver.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class VehicleServiceImpl implements VehicleService {

    @Override
    public Vehicle selectVehicleById(int id) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            VehicleMapper mapper = sqlSession.getMapper(VehicleMapper.class);
            return mapper.selectById(id);
        }
    }

    @Override
    public List<Vehicle> selectAllVehicles() {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            VehicleMapper mapper = sqlSession.getMapper(VehicleMapper.class);
            return mapper.selectAll();
        }
    }

    @Override
    public void insertVehicle(Vehicle vehicle) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            VehicleMapper mapper = sqlSession.getMapper(VehicleMapper.class);
            mapper.insert(vehicle);
        }
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            VehicleMapper mapper = sqlSession.getMapper(VehicleMapper.class);
            mapper.update(vehicle);
        }
    }

    @Override
    public void deleteVehicle(int id) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            VehicleMapper mapper = sqlSession.getMapper(VehicleMapper.class);
            mapper.delete(id);
        }
    }
}
