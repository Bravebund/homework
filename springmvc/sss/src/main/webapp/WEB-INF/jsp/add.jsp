<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 2020/3/14
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>新增简历</title>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
</head>
<body>
<div align="right">当前登录用户：<%out.print(pageContext.getSession().getAttribute("username")); %></div>
<form action="http://localhost:8080/resume/add" method="post">
    姓名：<input type="text" name="name"><br>
    电话：<input type="text" name="phone"><br>
    地址：<input type="text" name="address"><br>
    <input type="submit">
</form>
</body>
</html>
