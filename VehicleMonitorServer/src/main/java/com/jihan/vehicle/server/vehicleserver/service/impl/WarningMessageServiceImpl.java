package com.jihan.vehicle.server.vehicleserver.service.impl;

import com.jihan.vehicle.server.vehicleserver.dao.VehicleStatusMapper;
import com.jihan.vehicle.server.vehicleserver.dao.WarningMessageMapper;
import com.jihan.vehicle.server.vehicleserver.entity.WarningMessage;
import com.jihan.vehicle.server.vehicleserver.service.WarningMessageService;
import com.jihan.vehicle.server.vehicleserver.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class WarningMessageServiceImpl implements WarningMessageService {
    @Override
    public void insertWarningMessage(String username, String deviceId, String message) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            WarningMessageMapper mapper = sqlSession.getMapper(WarningMessageMapper.class);
            mapper.insertWarningMessage(username, deviceId, message);
        }
    }

    @Override
    public List<WarningMessage> selectWarnMessageByUserID(int user_id) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            WarningMessageMapper mapper = sqlSession.getMapper(WarningMessageMapper.class);
            return mapper.selectWarnMessageByUserID(user_id);
        }
    }

    @Override
    public List<WarningMessage> selectWarnMessageByDeviceID(int device_id) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            WarningMessageMapper mapper = sqlSession.getMapper(WarningMessageMapper.class);
            return mapper.selectWarnMessageByDeviceID(device_id);
        }
    }
}
