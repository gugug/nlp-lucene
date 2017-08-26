<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>主页</title>
<link rel="stylesheet" href="../public/stylesheets/index.css">
<link rel="stylesheet" href="../public/stylesheets/header.css">
<link rel="stylesheet" href="../public/stylesheets/footer.css">
</head>
<body>
<header> <img src="../public/images/logo.jpg" alt="beatles" width="43" height="44" class="logo" ><img src="../public/images/title.png" width="265" height="104"  alt="beatles" class="title"></header>
<div class="clear"></div>
<div class="index">

  <center>
  <div id="guge"> <img src="../public/images/guge.png" width="381" height="114"></div>
    <form class="search" action="${pageContext.request.contextPath }/index/search.action" method="post">
      <input type="text" name="searchContent">
      <button type="submit"><img src="../public/images/search.png" alt="search"></button>
    </form>
  </center>
</div>
<div class="clear"></div>
<div  id="index_footer">
  <footer>
    <p>copyright©我爱学习</p>
  </footer>
</div>
</body>
</html>