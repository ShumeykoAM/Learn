<%--
  Created by IntelliJ IDEA.
  User: sbt-shumeyko-am
  Date: 03.04.2017
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%--<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"  %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"  %>--%>

<tiles:insert definition="AllLayOut" flush="false">
    <%--у этого definition есть put со значением по умолчанию, их и не задаем--%>
    <tiles:put name="body"  value="/jsp/homeBody.jsp"/> <%-- задаем только body, для него нет значения по умолчанию--%>
</tiles:insert>