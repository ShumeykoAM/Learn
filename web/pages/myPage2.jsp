<%--
  Created by IntelliJ IDEA.
  User: SBT-Shumeyko-AM
  Date: 08.02.2017
  Time: 9:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>

<%--Используем разметку и ее наполнение через tiles definition--%>
<tiles:insert definition=".simple.admin">
    <%-- Задаем заголовок прописанный в layouts\simple-layout.jsp, он нам доступен через
    definition .simple.admin который наследуется от definition .simple который использует
    layouts\simple-layout.jsp, он еще ни разу не задавался --%>
    <tiles:put name="title" value="Заголовок админа"/>

    <%--Эксперимент показывает, что если не задан хотя бы один элемент разметки, то страница не отрисуется--%>

</tiles:insert>