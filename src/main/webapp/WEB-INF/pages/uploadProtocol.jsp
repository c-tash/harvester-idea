<%--
  Created by IntelliJ IDEA.
  User: ctash_000
  Date: 11.03.2015
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload new protocol for Harvester.</title>
</head>
<body>
<form method="POST" enctype="multipart/form-data" action="douploadprotocol">
    File to upload(path to .jar file): <input type="file" name="file">
    <br/>
    Full reference name (e.g. java.util.ArrayList): <input type="text" name="className">
    <br/>
    <br/>
    <input type="hidden" value="${token}" name="token">
    <input type="submit" value="Upload"> Press here to upload the file!
</form>
</body>
</html>
