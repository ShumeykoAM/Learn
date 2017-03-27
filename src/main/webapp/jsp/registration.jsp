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
        <title><bean:message bundle="bundleRes1" key="registration.jsp.title"/></title>
    </head>
    <body>
    <h1><bean:message bundle="bundleRes1" key="registration.jsp.heading"/></h1>
    <html:form action="/struts/Registration.do" focus="userId">
        <p>
            <bean:message bundle="bundleRes1" key="registration.jsp.prompt.userId"/>
            <html:text property="userId" size="20" />
            <html:errors bundle="bundleRes1"  property="userId" />
        </p><p>
        <bean:message bundle="bundleRes1" key="registration.jsp.prompt.password"/>
        <html:password property="password" size="20" />
        <html:errors bundle="bundleRes1"  property="password" />
    </p><p>
        <bean:message bundle="bundleRes1" key="registration.jsp.prompt.password2"/>
        <html:password property="password2" size="20" />
        <html:errors bundle="bundleRes1"  property="password2" />
    </p>
        <html:submit>
            <bean:message bundle="bundleRes1" key="registration.jsp.prompt.submit"/>
        </html:submit>
        <html:reset>
            <bean:message bundle="bundleRes1" key="registration.jsp.prompt.reset"/>
        </html:reset>
    </html:form>
    </body>
</html:html>