<%--
  Created by IntelliJ IDEA.
  User: Kot
  Date: 05.02.2017
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert definition=".simple">
    <tiles:put name="footer"/>
    <tiles:put name="title"  value="A simple page" />
    <tiles:put name="body"   value="/struts/tiles/mypage-content.jsp" />
</tiles:insert>
