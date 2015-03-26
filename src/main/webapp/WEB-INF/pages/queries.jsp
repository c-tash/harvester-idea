<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <meta http-equiv="Refresh" content="20; URL=/harvesting/nodes.html"> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>


<table cellpadding="5" cellspacing="0" align="center">
    <tr>
        <td colspan="8"><br>

            <h1>Просмотр текущих запросов</h1></td>
    </tr>
    <tr>
        <td width="10%"><b>Имя</b></td>
        <td width="20%"><b>URL конечного узла</b></td>
        <td width="20%"><b>URL точки доступа</b></td>
        <td width="10%"><b>Протокол</b></td>
        <td width="20%"><b>Время начала</b></td>
        <td width="5%"><b>Регулярность</b></td>
        <td width="10%"><b>Структура</b></td>
        <td width="5%"><b>Активность</b></td>
    </tr>
    <c:forEach var="query" items="${queryList}">
        <tr>
            <td width="11%">${query.}
            </td>
            <td width="17%"><%=qrArr[i].getEndURL()%>
            </td>
            <td width="17%"><%=qrArr[i].getStartURL()%>
            </td>
            <%
                StringMessage strMsg = new StringMessage();
                try {
                    strMsg = ManagerService.getProtocolNameForId(user, pw,
                            Integer.parseInt(qrArr[i].getProtocol_id()));
                } catch (Exception e) {
                }
            %>
            <td width="10%"><%=strMsg.getData()%>
            </td>
            <td width="15%"><%=qrArr[i].getTime()%>
            </td>
            <td width="5%"><%=qrArr[i].getReg()%>
            </td>
            <td width="15%"><%=qrArr[i].getStruct_loc()%>
            </td>
            <td width="15%"><%=qrArr[i].getActive()%>
            </td>
            <td width="10%">
                <form action="node_info.jsp">
                    <input type="hidden" value="<%=qrArr[i].getId()%>" name="qid"><input
                        type="submit" value="Подробнее">
                </form>
            </td>
        </tr>
    </c:forEach>

    <%
        }
    %>

    <%
            }
        }
    %>
</table>

<table width="100%">

    <tr>
        <td align="center"><a href="choose_protocol.jsp">Создать
            новый запрос</a></td>
    </tr>
</table>
</body>
</html>


















