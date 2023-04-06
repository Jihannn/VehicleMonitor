package com.jihan.vehicle.server.vehicleserver.service;

import com.jihan.vehicle.server.vehicleserver.entity.User;
import com.jihan.vehicle.server.vehicleserver.entity.Vehicle;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    User getUser(String username);
    boolean auth(String username, String password);

    boolean exist(String username);

    void addUser(String username, String password);

    List<Vehicle> findVehiclesByUserId(int userID);
    void addUserVehicle(int userId,int vehicleId);

    String getUsernameByDeviceId(String deviceId);
}
