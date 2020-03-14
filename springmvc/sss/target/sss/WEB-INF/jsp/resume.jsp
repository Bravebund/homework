<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 2020/3/14
  Time: 17:34
  To change this template use File | Settings | File Templates.
--%>
<%@page import="com.lagou.edu.pojo.Resume " %>
<%@page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>简历表</title>
    <%--<script type="text/javascript" src="/js/jquery.min.js"></script>--%>
</head>
<body>
<div align="right">当前登录用户：<%out.print(pageContext.getSession().getAttribute("username")); %></div>
<table border="1px" width="800px">
    <tr>
        <th>编号</th>
        <th>名称</th>
        <th>电话</th>
        <th>地址</th>
        <th colspan="2"><a href="http://localhost:8080/page/add">新增</a></th>
    <tr>
        <%
            List<Resume> lists = (List<Resume>)request.getAttribute("resumes");
            for(Resume resume : lists){
        %>
    <tr>
        <td align="center"><%=resume.getId() %></td>
        <td align="center"><%=resume.getName() %></td>
        <td align="center"><%=resume.getPhone() %></td>
        <td align="center"><%=resume.getAddress() %></td>
        <td align="center"><a href="http://localhost:8080/resume/queryOne?id=<%=resume.getId()%>">修改</a></td>
        <td align="center"><a href="http://localhost:8080/resume/delete?id=<%=resume.getId()%>">删除</a></td>
    </tr>
        <%
            }
        %>
</table>
</body>
</html>