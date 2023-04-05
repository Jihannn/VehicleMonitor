<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jihan.vehicle.server.vehicleserver.entity.Vehicle" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Vehicle List</title>
  <!-- 引入Bootstrap样式 -->
  <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.1.0/css/bootstrap.min.css">
  <style>
    body {
      background-color: #f8f9fa; /* 设置背景颜色 */
    }

    .table {
      background-color: #ffffff; /* 设置表格背景颜色 */
      border-radius: 0.25rem; /* 设置圆角 */
    }

    .table thead th {
      background-color: #007bff; /* 设置表头背景颜色 */
      color: #ffffff; /* 设置表头文字颜色 */
    }
  </style>
</head>
<body>
<div class="container">
  <h1 class="text-center my-5">申请记录</h1>
  <table class="table">
    <thead>
    <tr>
      <th scope="col">Id</th>
      <th scope="col">车牌号</th>
      <th scope="col">汽车唯一标识</th>
      <th scope="col">品牌</th>
      <th scope="col">车型</th>
      <th scope="col">出厂年份</th>
      <th scope="col">申请人</th>
      <th scope="col">操作</th>
    </tr>
    </thead>
    <tbody>
    <% List<Vehicle> vehicleList = (List<Vehicle>) request.getAttribute("vehicleList"); %>
    <% for (Vehicle vehicle : vehicleList) { %>
    <tr>
      <th scope="row"><%= vehicle.getId() %></th>
      <td><%= vehicle.getPlate_number() %></td>
      <td><%= vehicle.getDevice_id() %></td>
      <td><%= vehicle.getBrand() %></td>
      <td><%= vehicle.getModel() %></td>
      <td><%= vehicle.getProduction_year() %></td>
      <td><%= vehicle.getApplicant() %></td>
      <td>
        <form method="post" action="../vehicle/list">
          <input type="hidden" name="id" value="<%= vehicle.getId() %>">
          <input type="hidden" name="device_id" value="<%= vehicle.getDevice_id() %>">
          <input type="hidden" name="applicant" value="<%= vehicle.getApplicant() %>">
          <button type="submit" class="btn btn-primary">通过</button>
        </form>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
</div>
<!-- 引入Bootstrap JS -->
<script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.1.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>