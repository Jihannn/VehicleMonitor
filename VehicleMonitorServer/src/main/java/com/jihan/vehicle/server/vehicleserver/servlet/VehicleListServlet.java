package com.jihan.vehicle.server.vehicleserver.servlet;

import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;
import com.jihan.vehicle.server.vehicleserver.service.UserService;
import com.jihan.vehicle.server.vehicleserver.service.VehicleService;
import com.jihan.vehicle.server.vehicleserver.service.impl.UserServiceImpl;
import com.jihan.vehicle.server.vehicleserver.service.impl.VehicleServiceImpl;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/vehicle/list")
@Log4j2
public class VehicleListServlet extends HttpServlet {
    private VehicleService mVehicleService;
    private UserService mUserService;
    @Override
    public void init() throws ServletException {
        mVehicleService = new VehicleServiceImpl();
        mUserService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Vehicle> vehicleList = mVehicleService.selectAllVehiclesByActiveFalse();

        req.setAttribute("vehicleList", vehicleList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/vehicle_list.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int vehicle_id = Integer.parseInt(req.getParameter("id"));
        String applicant = String.valueOf(req.getParameter("applicant"));
        mVehicleService.updateVehicleActive(vehicle_id);
        int user_id = mUserService.getUser(applicant).getId();
        mUserService.addUserVehicle(user_id,vehicle_id);
        resp.sendRedirect(req.getContextPath() + "/vehicle/list");
    }
}