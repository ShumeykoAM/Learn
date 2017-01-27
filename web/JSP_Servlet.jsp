<%--
  7)
  JSP + Servlet + JavaBean
  Все страницы .jsp неявно превращаются в объекты сервлетов,
    таким образом нам доступены переменные из java: request (HttpServletRequest), response (HttpServletResponse),
    out = response.getWriter();  через теги <% %> или <%= %> // - но рекомендуется использовать Bean
  JSP использует синтаксис html в который можно дополнительно вставлять теги jsp
--%>

<%--
  есть теги <%@ здесь прописываются директивы jsp %>
  есть теги <%= здесь пишем допустимое вычисляемое выражение,
    допустимо использовать переменные java %> //лучше использовать Bean
  есть теги <% здесь пишем код java %>  --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP и обработчик в сервлете</title>
</head>
<body>

  <!-- создаем объект класса beans.MySimpleBean по имени mySimpleBean1 -->
  <jsp:useBean id="mySimpleBean1" class="beans.MySimpleBean" />
  <!-- создаем еще один объект класса beans.MySimpleBean по имени mySimpleBean1 -->
  <jsp:useBean id="mySimpleBean2" class="beans.MySimpleBean" />

  <!-- вызываем геттер из объекта бина -->
  HTML Created by <jsp:getProperty name="mySimpleBean2" property="nameUser" /> goes here

  <!-- вызываем cеттер из объекта бина -->
  <jsp:setProperty name="mySimpleBean1" property="nameUser" value="Сестра мотильды" />
  <br>

  <!-- вставим код java (лучше использовать bean) -->
  <%out.print("Простая математика");%>
  <br>

  <!-- работаем с объектом Bean через имя объекта, как в обычном java коде -->
  <!-- выражение ниже само преобразуется в out.print( mySimpleBean1.sum(2, 2) ) (лучше использовать bean)-->
  Вы может не знаете что 2 + 2 = <%= mySimpleBean1.sum(2, 2)%>
  <br>

  <!-- вызываем геттер из объекта бина -->
  Далее код создан <jsp:getProperty name="mySimpleBean1" property="nameUser" />
</body>
</html>
