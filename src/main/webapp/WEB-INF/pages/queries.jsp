<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- <meta http-equiv="Refresh" content="20; URL=/harvesting/nodes.html"> -->
<html>
<head>
    <style type="text/css">
        .table-queries {
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
<div>
    <div class="table-queries">
        <h2>Просмотр текущих запросов</h2>

        <form class="form-horizontal" action="createquery" method="POST">
            <div class="form-group">
                <input type="hidden" value="${token}" name="token"/>
                <button class="btn btn-primary" type="submit" id="btnSignUp">Новый запрос</button>
            </div>
        </form>
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
            <c:forEach var="query" items="${queries}">
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
                    <td>
                        <form action="queryinfo">
                            <input type="hidden" value="${query.getId()}" name="qid">
                            <input type="submit" value="Подробнее">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>

    </div>

</div>


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/resources//js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources//js/bootstrap.min.js"></script>
</body>


</html>


















