
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--Импортим библиотеку тегов прописанную в web.xml--%>
<%@ taglib uri="/my_tld" prefix="mytld"%>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <%-- Создаем объекты и задаем свойства (вызываются сеттеры) этих объектов из библиотеки тегов--%>
    <mytld:no_body propStr="Текст свойства"/>
    <h1><mytld:no_body propStr="Еще какой то формат" formatOut="None"/></h1>

    <%--А этот тег может иметь тело--%>
    <h2>
        Температура <mytld:temperature>100</mytld:temperature> в цельсиях
        равна температуре <mytld:temperature to="k">100</mytld:temperature> в кельвинах
    </h2>


</body>
</html>
