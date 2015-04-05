<%--
  Created by IntelliJ IDEA.
  User: ctash_000
  Date: 05.04.2015
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style type="text/css">
        .table-query-info {
            max-width: 1024px;
            margin-left: auto;
            margin-right: auto;
            width: 70%;
        }
    </style>
    <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="http://getbootstrap.com/examples/dashboard/dashboard.css" rel="stylesheet">

</head>

<body>
<div class="table-query-info">
    <h3>
        <c:if test="${messageColor == 'green'}">
            <div class="alert alert-success" role="alert">
                ${message}
            </div>
        </c:if>
        <c:if test="${messageColor == 'red'}">
            <div class="alert alert-danger" role="alert">
                    ${message}
            </div>
        </c:if>
    </h3>
    <div>
        <form action="queries" method="POST">
            <div>
                <input type="hidden" value="${token}" name="token"/>
                <button class="btn btn-default" type="submit" id="btnQueries">Назад к списку запросов</button>
            </div>
        </form>
    </div>
    <h2>Просмотр информации о запросе</h2>


    <table class="table table-condensed">
        <thead>
        <tr>
            <th>Имя</th>
            <th>URL конечного узла</th>
            <th>URL точки доступа</th>
            <th>Протокол</th>
            <th>Время начала</th>
            <th>Регулярность</th>
            <th>Структура</th>
            <th>Активность</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${query.getName()}
            </td>
            <td>${query.getEndURL()}
            </td>
            <td>${query.getStartURL()}
            </td>
            <td>${query.getProtocol_id()}
            </td>
            <td>${query.getTime()}
            </td>
            <td>${query.getReg()}
            </td>
            <td>${query.getStruct_loc()}
            </td>
            <td>${query.getActive()}
            </td>
        </tr>
        </tbody>
    </table>
    <div class="row">
        <div class="col-sm-4">
            <form action="updatequery" method="POST">
                <div>
                    <input type="hidden" value="${token}" name="token"/>
                    <input type="hidden" value="${query.id}" name="qid"/>
                    <button class="btn btn-primary" type="submit" id="btnUpdateQuery">Изменить запрос</button>
                </div>
            </form>
        </div>
        <div class="col-sm-4">
            <form action="changeactive" method="POST">
                <div>
                    <input type="hidden" value="${token}" name="token"/>
                    <input type="hidden" value="${query.id}" name="qid"/>
                    <input type="hidden" value="${query.active}" name="active"/>
                    <button class="btn btn-warning" type="submit" id="btnChangeActive">
                        <c:choose>
                            <c:when test="${query.active == '1'}">
                                Деактивировать запрос
                            </c:when>
                            <c:otherwise>
                                Активировать запрос
                            </c:otherwise>
                        </c:choose>
                    </button>
                </div>
            </form>
        </div>
        <div class="col-sm-4">
            <form action="deletequery" method="POST">
                <div>
                    <input type="hidden" value="${token}" name="token"/>
                    <input type="hidden" value="${query.id}" name="qid"/>
                    <button class="btn btn-danger" type="submit" id="btnDeleteQuery">Удалить запрос</button>
                </div>
            </form>
        </div>
    </div>
    <h2>
        Неудачные попытки
    </h2>
    <table class="table table-condensed">
        <thead>
        <tr>
            <th><b>Дата</b></th>
            <th><b>Номер попытки</b></th>
            <th><b>Сообщение</b></th>
            <th><b>Код ошибки</b></th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="schedule" items="${scheduleList}">
                <tr>
                    <td>${schedule.datetime}</td>
                    <td>${schedule.attempt}</td>
                    <td>${schedule.message}</td>
                    <td>${schedule.result}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>
