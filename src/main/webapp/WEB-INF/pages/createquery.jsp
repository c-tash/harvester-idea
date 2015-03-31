<%--
  Created by IntelliJ IDEA.
  User: k.kosolapov
  Date: 30.03.2015
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Регистрация запроса</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<a href="nodes.jsp">Назад</a>

<h1>Регистрация запроса</h1>

<form:form class="form-horizontal" method="post" action="/submitquery" commandName="query">
    <input type="hidden" name="token" value="${token}">
    <div class="form-group">
    <label for="inputName" class="col-sm-2 control-label">Имя</label>

    <div class="col-sm-10">
    <form:input type="text" class="form-control" id="inputName" placeholder="Имя" path="name">
        </div>
        </div>
        <div class="form-group">
        <label for="inputEndURL" class="col-sm-2 control-label">URL для загрузки</label>

        <div class="col-sm-10">
        <form:input type="url" class="form-control" id="inputEndURL" placeholder="URL для загрузки"
                    path="endURL">
            </div>
            </div>
            <div class="form-group">
            <label for="inputStartURL" class="col-sm-2 control-label">URL для выгрузки</label>

            <div class="col-sm-10">
            <form:input type="url" class="form-control" id="inputStartURL" placeholder="URL для выгрузки"
                        path="startURL">
                </div>
                </div>
                <div class="form-group">
                <label for="inputProtocol" class="col-sm-2 control-label">Протокол</label>

                <div class="col-sm-10">
                <form:select class="form-control" id="inputProtocol" path="protocol">
                    <c:forEach var="protocol" items="${protocols}">
                        <option value="${protocol.getId()}">${protocol.getName()}</option>
                    </c:forEach>
                    </div>
                    </div>
                    <div class="form-group">
                    <label for="inputTime" class="col-sm-2 control-label">Время запуска</label>

                    <div class="col-sm-10">
                    <form:input type="datetime" class="form-control" id="inputTime" placeholder="03:00" path="time">
                        </div>
                        </div>
                        <%--<div class="form-group">--%>
                        <%--<label for="inputDate" class="col-sm-2 control-label">Дата первого запуска</label>--%>

                        <%--<div class="col-sm-10">--%>
                        <%--<form:input type="date" class="form-control" id="inputDate" path="date">--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="form-group">
                        <label for="inputReg" class="col-sm-2 control-label">Регулярность</label>

                        <div class="col-sm-10">
                        <form:input type="text" class="form-control" id="inputReg" path="reg">
                            </div>
                            </div>
                            <div class="form-group">
                            <label for="inputStruct" class="col-sm-2 control-label">Дополнительные параметры для
                            протокола(XML)</label>

                            <div class="col-sm-10">
                            <form:textarea rows="20" class="form-control" id="inputStruct"
                                           path="struct"></form:textarea>
                            </div>
                            </div>
                            <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-default">Создать</button>
                            </div>
                            </div>
                            </form>
                            </div>
                            <script src="${pageContext.request.contextPath}/resources//js/jquery.js"></script>
                            <script src="${pageContext.request.contextPath}/resources//js/bootstrap.min.js"></script>
                            </body>
                            </html>
