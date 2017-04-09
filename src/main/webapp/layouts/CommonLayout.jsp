<%--
  Created by IntelliJ IDEA.
  User: Kot
  Date: 09.04.2017
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<html>
    <head>
        <title><tiles:getAsString name="title"/></title>
    </head>
    <body>
        <table>
            <tr><td><tiles:insert attribute="header"/></td></tr>
            <tr><td><tiles:insert attribute="body"/></td></tr>
            <tr><td><tiles:insert attribute="footer"/></td></tr>
        </table>
    </body>
</html>