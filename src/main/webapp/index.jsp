<%--
  Created by IntelliJ IDEA.
  User: lonecloud
  Date: 2017/8/30
  Time: 下午8:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试</title>
</head>
<body>
普通上传文件
<form action="manage/product/upload" enctype="multipart/form-data" method="post">
    <input type="file" name="file">
    <button>上传</button>
</form>
富文本上传文件
<form action="manage/product/richUpload" enctype="multipart/form-data" method="post">
    <input type="file" name="file">
    <button>上传</button>
</form>
</body>
</html>
