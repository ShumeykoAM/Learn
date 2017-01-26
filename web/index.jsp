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

<Form action="http://localhost:8080/web1_war_exploded/FindBooks/FindMethodPost" method="post">
    <input type=Text name="FindText">
    <input type=Submit value="Найти методом post">
</Form>
<Form action="http://localhost:8080/web1_war_exploded/FindBooks/FindMethodGet" method="get">
    <input type=Text name="FindText">
    <input type=Submit value="Найти методом get">
</Form>

<Form action="http://localhost:8080/web1_war_exploded/hw_cookie">
    <input type=Submit value="Перейти к HelloWorldCookie">
</Form>

<Form action="http://localhost:8080/web1_war_exploded/hw_session">
    <input type=Submit value="Перейти к HelloWorldSession">
</Form>

</body>
</html>