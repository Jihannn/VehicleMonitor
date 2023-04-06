package com.jihan.vehicle.server.vehicleserver.servlet;

import com.alibaba.fastjson.JSON;
import com.jihan.vehicle.server.vehicleserver.Constants;
import com.jihan.vehicle.server.vehicleserver.entity.*;
import com.jihan.vehicle.server.vehicleserver.service.MyWebSocketServer;
import com.jihan.vehicle.server.vehicleserver.service.UserService;
import com.jihan.vehicle.server.vehicleserver.service.WarningMessageService;
import com.jihan.vehicle.server.vehicleserver.service.impl.UserServiceImpl;
import com.jihan.vehicle.server.vehicleserver.service.impl.WarningMessageServiceImpl;
import com.jihan.vehicle.server.vehicleserver.utils.JWTUtils;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 车载端发送警报
 */
@WebServlet(value = "/warning")
@Log4j2
public class WarningServlet extends HttpServlet {
    WarningMessageService mWarningService;
    UserService mUserService;
    @Override
    public void init() throws ServletException {
        mWarningService = new WarningMessageServiceImpl();
        mUserService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String token = req.getHeader("Authorization");
        log.info("post");
        log.info(requestBody);
        log.info(token);
        VehicleStatus vehicleStatus = JSON.parseObject(requestBody, VehicleStatus.class);
        String driver = JWTUtils.getUsername(vehicleStatus.getDriver());
        String deviceId = vehicleStatus.getDeviceId();
        String username = mUserService.getUsernameByDeviceId(deviceId);
        String message = "驾驶员"+driver+"发出了报警信号";
        mWarningService.insertWarningMessage(deviceId,driver,message);
        MyWebSocketServer.getInstance().processWarningMessage(username,message);
        Response<Void> result = new Response<>();
        result.setErrorCode(Constants.CODE_SUCCESS);
        result.setErrorMsg("已收到报警信息");
        resp.getWriter().write(JSON.toJSONString(result));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        log.info("get");
        String token = request.getHeader("token");
        // 构造返回结果
        Response<List<WarningMessage>> baseResponse = new Response<>();
        if(token == null || !JWTUtils.verify(token)){
            log.info((token == null) +"-" + !JWTUtils.verify(token));
            baseResponse.setData(null);
            baseResponse.setErrorCode(Constants.CODE_FAILURE);
            baseResponse.setErrorMsg("Success");
        }else{
            User user = mUserService.getUser(JWTUtils.getUsername(token));
            List<WarningMessage> warningMessageList = mWarningService.selectWarnMessageByUserID(user.getId());
            if (warningMessageList != null && !warningMessageList.isEmpty()) {
                log.info("success:"+warningMessageList);
                baseResponse.setData(warningMessageList);
                baseResponse.setErrorCode(Constants.CODE_SUCCESS);
                baseResponse.setErrorMsg("Success");
            } else {
                log.info("failure");
                baseResponse.setData(null);
                baseResponse.setErrorCode(Constants.CODE_FAILURE);
                baseResponse.setErrorMsg("没有任何消息");
            }
        }
        response.getWriter().write(JSON.toJSONString(baseResponse));
    }
}