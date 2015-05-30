<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Network Management</title>
</head>
<body>
Click "Continue" to process data files
<form action="process" method="post">
	<p style="float:left;">
        <button type="submit">Continue</button>
    </p>
    <%@page import = "structs.*" %>
    <%@page import = "java.util.ArrayList" %>
    <% if (request.getAttribute("parse")!=null){
    Gps [] APs = (Gps []) session.getAttribute("Gps");
    for(Gps ap: APs){
    %>
    	<tr>
         <td><%=ap.getId()%></td>
         <td><%=ap.getUser()%></td>
         <td><%=ap.getTime()%></td>
    </tr>
    <%}
    }%>
</form>
</body>
</html>