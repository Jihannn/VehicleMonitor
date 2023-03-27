package com.jihan.vehicle.server.vehicleserver.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

@Data
public class User {
    private int id;
    private String username;
    @JSONField(serialize = false)
    private String password;
}
