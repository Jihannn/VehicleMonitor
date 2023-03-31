package com.jihan.vehicle.server.vehicleserver.servlet;

import com.alibaba.fastjson.JSON;
import com.jihan.vehicle.server.vehicleserver.Constants;
import com.jihan.vehicle.server.vehicleserver.entity.Response;
import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;
import com.jihan.vehicle.server.vehicleserver.service.VehicleService;
import com.jihan.vehicle.server.vehicleserver.service.impl.VehicleServiceImpl;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/vehicle")
@Log4j2
public class VehicleServlet extends HttpServlet {

    private VehicleService service;

    @Override
    public void init() throws ServletException {
        service = new VehicleServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        log.info("get");
        String idString = request.getParameter("id");
        if (idString == null) {
            // 查询全部车辆
            List<Vehicle> vehicles = service.selectAllVehicles();
            Response<List<Vehicle>> result = new Response<>();
            result.setData(vehicles);
            response.getWriter().write(JSON.toJSONString(result));
        } else {
            // 根据id查询车辆
            int id = Integer.parseInt(idString);
            Vehicle vehicle = service.selectVehicleById(id);
            Response<Vehicle> result = new Response<>();
            if (vehicle != null) {
                result.setData(vehicle);
            } else {
                result.setErrorCode(404);
                result.setErrorMsg("车辆不存在");
            }
            response.getWriter().write(JSON.toJSONString(result));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("post");
        response.setContentType("application/json;charset=utf-8");
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String token = request.getHeader("Authorization");
        log.info("requestBody:"+requestBody);
        log.info("token:"+token);
        Vehicle vehicle = JSON.parseObject(requestBody, Vehicle.class);
        log.info("vehicle:"+vehicle);
        Response<Void> result = new Response<>();
        if (vehicle.getPlate_number() == null || vehicle.getBrand() == null || vehicle.getModel() == null) {
            result.setErrorCode(Constants.CODE_FAILURE);
            result.setErrorMsg("车牌号、品牌、车型不能为空");
        } else {
//            service.insertVehicle(vehicle);
            result.setErrorCode(Constants.CODE_SUCCESS);
            result.setErrorMsg("Upload Success");
        }
        String s = JSON.toJSONString(result);
        response.getWriter().write(s);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Vehicle vehicle = JSON.parseObject(requestBody, Vehicle.class);
        Response<Void> result = new Response<>();
        if (vehicle == null) {
            result.setErrorCode(400);
            result.setErrorMsg("车辆id不能为空");
        } else {
            Vehicle oldVehicle = service.selectVehicleById(vehicle.getId());
            if (oldVehicle == null) {
                result.setErrorCode(404);
                result.setErrorMsg("车辆不存在");
            } else {
                service.updateVehicle(vehicle);
            }
        }
        response.getWriter().write(JSON.toJSONString(result));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        String idString = request.getParameter("id");
        Response<Void> result = new Response<>();
        if (idString == null) {
            result.setErrorCode(400);
            result.setErrorMsg("车辆id不能为空");
        } else {
            int id = Integer.parseInt(idString);
            Vehicle oldVehicle = service.selectVehicleById(id);
            if (oldVehicle == null) {
                result.setErrorCode(404);
                result.setErrorMsg("车辆不存在");
            } else {
                service.deleteVehicle(id);
            }
        }
        response.getWriter().write(JSON.toJSONString(result));
    }
}