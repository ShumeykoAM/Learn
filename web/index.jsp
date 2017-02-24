<%--
  Created by IntelliJ IDEA.
  User: Kot
  Date: 16.02.2017
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/my_tags_lib" prefix="my_tags_lib"%>

<html>
    <head>
        <title>Заголовок</title>
    </head>
    <body>
        Температура в цельсиях 36.6 =
        <my_tags_lib:convert_c_to to="F">36.6</my_tags_lib:convert_c_to> по Фаренгейту и
        <my_tags_lib:convert_c_to to="K">36.6</my_tags_lib:convert_c_to> по Кельвину
    </body>
</html>
