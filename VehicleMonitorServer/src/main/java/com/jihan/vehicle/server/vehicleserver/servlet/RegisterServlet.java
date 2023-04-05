package com.jihan.vehicle.server.vehicleserver.servlet;

import com.alibaba.fastjson.JSON;
import com.jihan.vehicle.server.vehicleserver.Constants;
import com.jihan.vehicle.server.vehicleserver.entity.Response;
import com.jihan.vehicle.server.vehicleserver.entity.User;
import com.jihan.vehicle.server.vehicleserver.service.UserService;
import com.jihan.vehicle.server.vehicleserver.service.impl.UserServiceImpl;
import com.jihan.vehicle.server.vehicleserver.utils.PasswordUtils;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/register")
@Log4j2
public class RegisterServlet  extends HttpServlet {
    UserService service;
    @Override
    public void init() throws ServletException {
        service = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        log.info("username:"+username+"pwd"+password);
        Response<User> result = new Response<>();
        if(service.exist(username)){
            result.setErrorCode(Constants.CODE_FAILURE);
            result.setErrorMsg("用户名已被注册");
            resp.getWriter().write(JSON.toJSONString(result));
        }else{
            service.addUser(username, PasswordUtils.md5(username+password));
            result.setErrorCode(Constants.CODE_SUCCESS);
            result.setErrorMsg("注册成功");
            resp.getWriter().write(JSON.toJSONString(result));
        }
    }
}