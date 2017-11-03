<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path;
    application.setAttribute("ctx",basePath);
    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
    String newLocation = "/manage";
    response.setHeader("Location",newLocation);
%>