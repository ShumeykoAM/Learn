<%--
  Created by IntelliJ IDEA.
  User: Kot
  Date: 04.02.2017
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<html:html>
    <head>
        <title>Struts страница</title>
    </head>
    <body>
        <html:form action="/struts/MyFirsAction">
            <bean:message key="reg.message.userid"/>
            <html:text property="userId" size="20" />
            <html:errors property="userID"/>
            <p/>
            <bean:message key="reg.message.password"/>
            <html:password property="password" size="20"/>
            <html:errors property="password"/>
            <p/>
            <html:submit>
                Поехали
            </html:submit>
        </html:form>

    </body>
</html:html>