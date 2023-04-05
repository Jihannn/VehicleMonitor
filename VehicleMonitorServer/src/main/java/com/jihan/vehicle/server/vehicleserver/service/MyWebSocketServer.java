package com.jihan.vehicle.server.vehicleserver.service;


import com.alibaba.fastjson.JSONObject;
import com.jihan.vehicle.server.vehicleserver.entity.VehicleStatus;
import com.jihan.vehicle.server.vehicleserver.service.impl.VehicleStatusServiceImpl;
import com.jihan.vehicle.server.vehicleserver.utils.ByteUtils;
import com.jihan.vehicle.server.vehicleserver.utils.JWTUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Log4j2
public class MyWebSocketServer extends WebSocketServer {

    // 用于存储已连接的用户
    private Map<String, WebSocket> connectedUsers = new HashMap<>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private VehicleStatusServiceImpl service;


    MyWebSocketServer(InetSocketAddress host){
        super(host);
        service = new VehicleStatusServiceImpl();
    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        log.info("websocket"+ "onOpen()一个客户端连接成功："+conn.getRemoteSocketAddress());
        // 获取JWT token
        String resourceDescriptor = handshake.getResourceDescriptor();
        log.info("resourceDescriptor:"+resourceDescriptor);
        String token = getTokenValue(resourceDescriptor);
        String type = getTypeValue(resourceDescriptor);
        String device_id = getDeviceIdValue(resourceDescriptor);
        log.info("token:"+token);
        log.info("type:"+type);
        log.info("device_id:"+device_id);
        // 验证JWT token并获取用户身份（例如用户ID）
        String username = JWTUtils.getUsername(token);
        if (username != null) {
            if("detail".equals(type) && device_id != null) {
                log.info("detail");
                log.info("User " + username + " connected");
//                if(!connectedUsers.containsKey(username)){
                    connectedUsers.put(username, conn);
                    scheduler.scheduleAtFixedRate(() -> sendMessageToUser(username, device_id), 0, 10, TimeUnit.SECONDS);
//                }
            }else if("warning".equals(type)){
                log.info("warning");
            }
        } else {
            // 如果JWT token无效，关闭连接
            conn.close();
            log.info("username is null");
        }
    }
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        log.info("websocket"+ "onClose()服务器关闭");
    }
    @Override
    public void onMessage(WebSocket conn, String message) {
        // 这里在网页测试端发过来的是文本数据 测试网页 http://www.websocket-test.com/
        // 需要保证android 和 加载网页的设备(我这边是电脑) 在同一个网段内，连同一个wifi即可
        log.info("websocket"+"onMessage()网页端来的消息->"+message);
        conn.send("server-收到");
    }
    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        // 接收到的是Byte数据，需要转成文本数据，根据你的客户端要求
        // 看看是string还是byte，工具类在下面贴出
        log.info("websocket"+ "onMessage()接收到ByteBuffer的数据->"+ ByteUtils.byteBufferToString(message));
        conn.send("server-收到");
    }
    @SneakyThrows
    @Override
    public void onError(WebSocket conn, Exception ex) {
        // 异常  经常调试的话会有缓存，导致下一次调试启动时，端口未关闭,多等待一会儿
        // 可以在这里回调处理，关闭连接，开个线程重新连接调用startMyWebsocketServer()
        log.info("websocket"+ "->onError()出现异常："+ex);
    }
    @Override
    public void onStart() {
        log.info("websocket"+ "onStart()，WebSocket服务端启动成功");
    }

    public void sendMessageToUser(String username,String device_id) {
        WebSocket userConnection = connectedUsers.get(username);
        if (userConnection != null) {
            VehicleStatus latestVehicleStatus = service.getLatestVehicleStatus(device_id);
            String json = JSONObject.toJSONString(latestVehicleStatus);
            log.info("send vehicleStatus:"+json);
            userConnection.send(json);
        } else {
            System.err.println("User " + username + " not found");
        }
    }

    public static String getTokenValue(String input) {
        Pattern pattern = Pattern.compile("token=([^&]*)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public static String getTypeValue(String input) {
        Pattern pattern = Pattern.compile("type=([^&]*)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public static String getDeviceIdValue(String input) {
        Pattern pattern = Pattern.compile("device_id=([^&]*)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}