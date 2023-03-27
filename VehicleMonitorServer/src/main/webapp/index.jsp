<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<form method="post" action="login">
    <input type="text" placeholder="用户名称" class="ad-input" name="username">
    <input type="password" placeholder="密码" class="ad-input" name="password">
    <button>登录</button>
</form>
</body>
</html>