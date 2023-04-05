package com.jihan.vehicle.server.vehicleserver.servlet;

import com.alibaba.fastjson.JSON;
import com.jihan.vehicle.server.vehicleserver.Constants;
import com.jihan.vehicle.server.vehicleserver.entity.Response;
import com.jihan.vehicle.server.vehicleserver.entity.VehicleStatus;
import com.jihan.vehicle.server.vehicleserver.service.VehicleStatusService;
import com.jihan.vehicle.server.vehicleserver.service.impl.VehicleStatusServiceImpl;
import com.jihan.vehicle.server.vehicleserver.utils.JWTUtils;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * 车载端上传数据
 */
@WebServlet("/upload")
@Log4j2
public class VehicleStatusServlet extends HttpServlet {

    private VehicleStatusService service;

    @Override
    public void init() throws ServletException {
        service = new VehicleStatusServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("post");
        response.setContentType("application/json;charset=utf-8");
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String token = request.getHeader("Authorization");
        log.info("requestBody:" + requestBody);
        log.info("token:" + token);
        Response<Void> result = new Response<>();
        if (!JWTUtils.verify(token)) {
            log.info("token过期");
            result.setErrorCode(Constants.CODE_FAILURE);
            result.setErrorMsg("token过期");
        } else {
            VehicleStatus vehicleStatus = JSON.parseObject(requestBody, VehicleStatus.class);
            log.info("vehicleStatus:" + vehicleStatus);
            if (vehicleStatus.getModel() == null) {
                log.info("上传数据不完整");
                result.setErrorCode(Constants.CODE_FAILURE);
                result.setErrorMsg("上传数据不完整");
            } else {
                log.info("Upload Success");
                vehicleStatus.setDriver(JWTUtils.getUsername(token));
                service.insertVehicleStatus(vehicleStatus);
                result.setErrorCode(Constants.CODE_SUCCESS);
                result.setErrorMsg("Upload Success");
            }
        }
        response.getWriter().write(JSON.toJSONString(result));
    }
}