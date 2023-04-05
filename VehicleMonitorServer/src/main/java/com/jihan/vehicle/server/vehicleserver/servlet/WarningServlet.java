package com.jihan.vehicle.server.vehicleserver.servlet;

import com.alibaba.fastjson.JSON;
import com.jihan.vehicle.server.vehicleserver.Constants;
import com.jihan.vehicle.server.vehicleserver.entity.Response;
import com.jihan.vehicle.server.vehicleserver.entity.User;
import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;
import com.jihan.vehicle.server.vehicleserver.service.UserService;
import com.jihan.vehicle.server.vehicleserver.service.impl.UserServiceImpl;
import com.jihan.vehicle.server.vehicleserver.utils.JWTUtils;
import com.jihan.vehicle.server.vehicleserver.utils.PasswordUtils;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * 车载端发送警报
 */
@WebServlet(value = "/warning")
@Log4j2
public class WarningServlet extends HttpServlet {
//    UserService service;
    @Override
    public void init() throws ServletException {
//        service = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String token = req.getHeader("Authorization");
        log.info("post");
        log.info(requestBody);
        log.info(token);
        Response<Void> result = new Response<>();
        result.setErrorCode(Constants.CODE_SUCCESS);
        result.setErrorMsg("已收到报警信息");
        //TODO:处理并推送信息
        resp.getWriter().write(JSON.toJSONString(result));
    }
}