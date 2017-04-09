<%--
  Created by IntelliJ IDEA.
  User: Kot
  Date: 16.02.2017
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Мой первый сервлет</title>
    </head>
    <body>
        <Form action="server" method="post">
            <input type=Text name="RequestText">
            <input type=Submit value="Перейти к сервлету методом post">
        </Form>
        <Form action="server" method="get">
            <input type=Text name="RequestText">
            <input type=Submit value="Перейти к сервлету методом  get">
        </Form>
        <a href="jsp/registration.jsp">Регистрация</a>
        <a href="jsp/homePage.jsp">Домашняя страница</a>
    </body>
</html>