<%-- 1)
 При использовании сервера GlassFish может возникнуть проблема развертывания аннотированных сервлетов
 Искать искать ошибки развертывания и другие

 --%>
<%@ page contentType="text/html;charset=UTF-8" %> <!-- что бы кириллица отображалась нормально -->


<html>
<head>
    <title>Мой первый сервлет</title>
</head>
<body>

<Form action="http://localhost:8080/web1_war_exploded/tld/Hello.jsp">
    <input type=Submit value="Перейти на страницу использующую tld библиотеку">
</Form>

<Form action="http://localhost:8080/web1_war_exploded/struts/ActionFormAction.jsp">
    <input type=Submit value="Перейти на страницу Struts">
</Form>

</body>
</html>