package com.jihan.vehicle.server.vehicleserver.service;

import com.jihan.vehicle.server.vehicleserver.entity.User;
import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    User getUser(int id);
    boolean auth(String username, String password, HttpSession session);

    boolean exist(String username);

    void addUser(String username, String password);

    List<Vehicle> getUserVehicles(int id);
}
