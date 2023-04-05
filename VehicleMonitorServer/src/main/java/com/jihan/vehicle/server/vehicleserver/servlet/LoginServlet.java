package com.jihan.vehicle.server.vehicleserver.servlet;

import com.alibaba.fastjson.JSON;
import com.jihan.vehicle.server.vehicleserver.Constants;
import com.jihan.vehicle.server.vehicleserver.entity.Response;
import com.jihan.vehicle.server.vehicleserver.service.MyWebSocketServer;
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

@WebServlet(value = "/login")
@Log4j2
public class LoginServlet  extends HttpServlet {
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
        log.info("login:"+username+"pwd:"+password+"md5:"+PasswordUtils.md5(password));
        Response<String> result = new Response<>();
        if(service.auth(username, PasswordUtils.md5(password))){
            String token = JWTUtils.createToken(username);
            log.info("token:"+token);
            req.getSession().setAttribute(Constants.TOKEN,token);
            req.getSession().setAttribute(Constants.USER,service.getUser(username));
            result.setData(token);
            result.setErrorCode(Constants.CODE_SUCCESS);
            result.setErrorMsg("登录成功");
            log.info("登录成功");
            resp.getWriter().write(JSON.toJSONString(result));
        }else{
            log.info("登录失败");
            result.setErrorCode(Constants.CODE_FAILURE);
            result.setErrorMsg("登录失败");
            resp.getWriter().write(JSON.toJSONString(result));
        }
    }
}