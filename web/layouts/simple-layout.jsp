<%--
  Created by IntelliJ IDEA.
  User: SBT-Shumeyko-AM
  Date: 08.02.2017
  Time: 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>

<html>
<head>
    <title> <tiles:getAsString name="title"/> </title>
</head>
<body>
<table>
    <tr><td> <tiles:insert attribute="header"/> </td></tr>
    <tr><td> <tiles:insert attribute="body"/> </td></tr>
    <tr><td> <tiles:insert attribute="footer"/> </td></tr>
</table>
</body>
</html>