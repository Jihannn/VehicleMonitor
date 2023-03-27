package com.jihan.vehicle.server.vehicleserver.servlet;

import com.alibaba.fastjson.JSON;
import com.jihan.vehicle.server.vehicleserver.entity.Response;
import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;
import com.jihan.vehicle.server.vehicleserver.service.UserService;
import com.jihan.vehicle.server.vehicleserver.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userVehicles")
public class UserVehiclesServlet extends HttpServlet {

    UserService service;
    @Override
    public void init() throws ServletException {
        service = new UserServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        // 获取用户 ID
        int userId = Integer.parseInt(request.getParameter("userId"));

        // 查询用户拥有的车辆
        List<Vehicle> vehicles = service.getUserVehicles(userId);

        // 构造返回结果
        Response<List<Vehicle>> baseResponse = new Response<>();
        if (vehicles != null && !vehicles.isEmpty()) {
            baseResponse.setData(vehicles);
            baseResponse.setErrorCode(0);
            baseResponse.setErrorMsg("Success");
        } else {
            baseResponse.setData(null);
            baseResponse.setErrorCode(-1);
            baseResponse.setErrorMsg("用户名下没有任何车辆");
        }
        response.getWriter().write(JSON.toJSONString(baseResponse));
    }
}