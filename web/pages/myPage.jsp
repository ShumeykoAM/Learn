<%--
  Created by IntelliJ IDEA.
  User: SBT-Shumeyko-AM
  Date: 08.02.2017
  Time: 9:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>

<%--Используем разметку layouts\simple-layout.jsp--%>
<tiles:insert page="/layouts/simple-layout.jsp">
    <%--Задаем элементы разметки--%>
    <tiles:put name="title" value="A simple page" />
    <tiles:put name="header" value="/common/header.jsp" />
    <tiles:put name="footer" value="/common/footer.jsp" />
    <tiles:put name="body" value="/bodys/mypage-content.jsp" />
</tiles:insert>