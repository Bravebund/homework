<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 2020/3/14
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@page import="com.lagou.edu.pojo.Resume " %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改简历</title>
</head>
<body>
<div align="right">当前登录用户：<%out.print(pageContext.getSession().getAttribute("username")); %></div>
<%
    Resume resume = (Resume) request.getAttribute("resume");
%>
<form action="http://localhost:8080/resume/update" method="post">
    编号：<input type="text" name="id" value="<%=resume.getId() %>" readonly="readonly"><br>
    姓名：<input type="text" name="name" value="<%=resume.getName() %>"><br>
    电话：<input type="text" name="phone" value="<%=resume.getPhone() %>"><br>
    地址：<input type="text" name="address" value="<%=resume.getAddress() %>"><br>
    <input type="hidden" name="_method" value="put">
    <input type="submit">
</form>
</body>
</html>
