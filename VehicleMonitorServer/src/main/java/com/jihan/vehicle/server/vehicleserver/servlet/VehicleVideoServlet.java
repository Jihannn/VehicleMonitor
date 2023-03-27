package com.jihan.vehicle.server.vehicleserver.servlet;

import com.jihan.vehicle.server.vehicleserver.entity.Response;
import com.jihan.vehicle.server.vehicleserver.service.VehicleVideoService;
import com.jihan.vehicle.server.vehicleserver.service.impl.VehicleVideoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
@WebServlet("/vehicleVideo")
public class VehicleVideoServlet  extends HttpServlet {

    private VehicleVideoService service;

    @Override
    public void init() throws ServletException {
        service = new VehicleVideoServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        // 获取请求体中的数据流
        InputStream inputStream = request.getInputStream();
        // 创建一个目标文件
        File videoFile = new File("/path/to/save/video");
        // 创建文件输出流
        FileOutputStream outputStream = new FileOutputStream(videoFile);
        // 将输入流写入到输出流中
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        // 关闭流
        inputStream.close();
        outputStream.close();
        //TODO:。。。
        Response<Void> result = new Response<>();
        result.setErrorCode(0);
        result.setErrorMsg("视频提交成功");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取要传输的视频文件的路径
        String videoPath = "path/to/video.mp4";
        // 读取视频文件到字节数组中
        FileInputStream fileInputStream = new FileInputStream(new File(videoPath));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fileInputStream.read(buffer)) > 0) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        byteArrayOutputStream.flush();
        byte[] videoData = byteArrayOutputStream.toByteArray();
        fileInputStream.close();
        byteArrayOutputStream.close();
        // 设置响应头信息
        response.setContentType("video/mp4");
        response.setContentLength(videoData.length);
        response.setHeader("Content-Disposition", "inline; filename=video.mp4");
        // 将视频数据写入响应输出流
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(videoData);
        outputStream.flush();
        outputStream.close();
        //TODO:。。。
    }
}