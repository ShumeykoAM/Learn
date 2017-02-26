<%--
  Created by IntelliJ IDEA.
  User: Kot
  Date: 25.02.2017
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html"  uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean"  uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>

<html:html>
    <head>
        <title><bean:message bundle="bundleRes2" key="userAccount.jsp.title"/></title>
    </head>
    <body>
    <h1><bean:message bundle="bundleRes2" key="userAccount.jsp.heading"/></h1>
    <html:form action="/struts/UserAccount.do">
        <html:submit>
            <bean:message bundle="bundleRes2" key="userAccount.jsp.prompt.submit"/>
        </html:submit>
    </html:form>
    </body>
</html:html>