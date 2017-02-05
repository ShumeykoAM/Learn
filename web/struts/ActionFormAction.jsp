<%-- что бы кириллица отображалась правильно --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--Как настроить подключение библиотек struts написано тут: https://www.mkyong.com/struts/configure-the-struts-tag-libraries/
      я выбрал второй вариант, поэтому в uri используются длинные имена.
    Так же необходимо что бы после разворачивания проекта jarники struts попадали в папку \WEB-INF\lib, настроить артефакт или через Gradle
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<html:html>
    <head>
        <title>Struts страница</title>
    </head>
    <body>
        <html:form action="/struts/MyFirsAction">      <%--Если DerivedActionForm или DerivedAction заполнили ActionErrors или ActionMessages, то выведется соответствующий текст--%>
            <bean:message key="reg.message.userid"/>   <%--Подкачаем текст из "web\WEB-INF\classes\my_first_struts.properties"--%>
            <html:text property="userId" size="20" />  <%--Здесь будет обращение к DerivedActionForm.setUserId и DerivedActionForm.getUserId--%>
            <html:errors property="userID"/>
            <p/>
            <bean:message key="reg.message.password"/>      <%--Подкачаем текст из "web\WEB-INF\classes\my_first_struts.properties"--%>
            <html:password property="password" size="20"/>  <%--Здесь будет обращение к DerivedActionForm.setPassword и DerivedActionForm.getPassword--%>
            <html:errors property="password"/>              <%--Если DerivedActionForm или DerivedAction заполнили ActionErrors или ActionMessages, то выведется соответствующий текст--%>
            <p/>
            <html:submit>
                Поехали
            </html:submit>
        </html:form>

    </body>
</html:html>