package com.jihan.vehicle.server.vehicleserver.servlet;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.jihan.vehicle.server.vehicleserver.Constants;
import com.jihan.vehicle.server.vehicleserver.entity.Response;
import com.jihan.vehicle.server.vehicleserver.entity.User;
import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;
import com.jihan.vehicle.server.vehicleserver.service.UserService;
import com.jihan.vehicle.server.vehicleserver.service.impl.UserServiceImpl;
import com.jihan.vehicle.server.vehicleserver.utils.JWTUtils;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userVehicles")
@Log4j2
public class UserVehiclesServlet extends HttpServlet {

    UserService service;

    @Override
    public void init() throws ServletException {
        service = new UserServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        log.info("get");
        String token = request.getHeader("token");
        // 构造返回结果
        Response<List<Vehicle>> baseResponse = new Response<>();
        if(token == null || !JWTUtils.verify(token)){
            log.info((token == null) +"-" + !JWTUtils.verify(token));
            baseResponse.setData(null);
            baseResponse.setErrorCode(Constants.CODE_FAILURE);
            baseResponse.setErrorMsg("Success");
        }else{
            User user = service.getUser(JWTUtils.getUsername(token));
            request.getSession().setAttribute(Constants.USER,user);
            // 查询用户拥有的车辆
            List<Vehicle> vehicles = service.findVehiclesByUserId(user.getId());
            if (vehicles != null && !vehicles.isEmpty()) {
                log.info("success");
                baseResponse.setData(vehicles);
                baseResponse.setErrorCode(Constants.CODE_SUCCESS);
                baseResponse.setErrorMsg("Success");
            } else {
                log.info("failure");
                baseResponse.setData(null);
                baseResponse.setErrorCode(Constants.CODE_FAILURE);
                baseResponse.setErrorMsg("用户名下没有任何车辆");
            }

        }
        response.getWriter().write(JSON.toJSONString(baseResponse));
    }
}