package com.jihan.vehicle.server.vehicleserver.service.impl;

import com.jihan.vehicle.server.vehicleserver.dao.UserMapper;
import com.jihan.vehicle.server.vehicleserver.dao.UserVehicleMapper;
import com.jihan.vehicle.server.vehicleserver.entity.User;
import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;
import com.jihan.vehicle.server.vehicleserver.service.UserService;
import com.jihan.vehicle.server.vehicleserver.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpSession;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(int id) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.getUser(id);
        }
    }

    @Override
    public boolean auth(String username, String password, HttpSession session) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.authUser(username, password);
            if(user == null) return false;
            session.setAttribute("user", user);
            return true;
        }
    }

    @Override
    public boolean exist(String username) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.exist(username) != null;
        }
    }

    @Override
    public void addUser(String username, String password) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.addUser(username,password);
        }
    }

    @Override
    public List<Vehicle> getUserVehicles(int id) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            UserVehicleMapper mapper = sqlSession.getMapper(UserVehicleMapper.class);
            return mapper.findVehiclesByUserId(id);
        }
    }
}