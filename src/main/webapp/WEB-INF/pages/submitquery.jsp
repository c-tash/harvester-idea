<%--
  Created by IntelliJ IDEA.
  User: k.kosolapov
  Date: 30.03.2015
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация запроса</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Запрос успешно создан</h2>

    <div align="left">
        <form action="/createquery" method="post">
            <input type="hidden" value="${token}" name="token">
            <input type="submit" value="Создать еще один запрос">
        </form>
    </div>

    <div align="left">
        <form action="/queryinfo" method="post">
            <input type="hidden" value="${token}" name="token">
            <input type="hidden" value="${query.getId()}" name="qid"><input
                type="submit" value="Вернуться к информации о запросе">
        </form>
    </div>

    <div align="right">
        <form action="/queries" method="post">
            <input type="hidden" value="${token}" name="token">
            <input type="submit" value="Вернуться к просмотру запросов">
        </form>
        </td>
    </div>
</body>
</html>
