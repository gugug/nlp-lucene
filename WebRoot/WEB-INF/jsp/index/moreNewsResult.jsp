<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>搜索结果</title>
<link rel="stylesheet" href="../public/stylesheets/index.css">
<link rel="stylesheet" href="../public/stylesheets/header.css">
<link rel="stylesheet" href="../public/stylesheets/footer.css">
<link rel="stylesheet" type="text/css" href="../public/stylesheets/normalize.css" />
<link rel="stylesheet" type="text/css" href="../public/stylesheets/default.css">
<script src="../public/javascript/jquery.min.js"></script>
</head>
<body>
<header> <img src="../public/images/logo.jpg" alt="beatles" width="43" height="44" class="logo" >
<a href="javascript :;" onClick="javascript :history.back(1);">
  <img src="../public/images/title.png" width="265" height="104"  alt="beatles" class="title">
  </a>
  <form class="search" action="${pageContext.request.contextPath }/index/search.action" method="post">
    <input type="text" name="searchContent">
    <button type="submit"><img src="../public/images/search.png" alt="search"></button>
  </form>
  </header>
<div class="clear"></div>
<div class="index_page"> 
  
  <!-- 搜索结果 -->
  
  <div class="results">
    <div class="container">
      <div class="htmleaf-container">
        <div class="demo-btns">
          <div class="info">
            <div class="buttons">
            
            
            <c:forEach items="${newsList }" var="news" varStatus="i">
              <div id="container01-1">
                <div id="container01-1-left">
                  <div id="container01-1-left-top-banner"></div>
                  <div id="container01-1-left-top-pic"> <img src="../public/images/logo.jpg" width="120" height="120" ></div>
                </div>
                <div id="container01-1-right">
                  <div id="container01-1-right-top">
                    <div id="btPlace1">
                      <p><a href="" data-modal="#modal${i.count }" class="modal__trigger"> ${news.nTitle }
                       <span class="media">${news.nMedia }</span> <span class="date">${news.nTime }</span></a>
                      </p>
                    </div>
                  </div>
                  <div class="container01-1-right-bottom">
                    <p>${news.nContent }</p>
                  </div>
                </div>
              </div>
			</c:forEach>
            
            </div>
          </div>
        </div>
        
        <!-- Modal -->
        	<c:forEach items="${newsList }" var="news" varStatus="i">
                 <div id="modal${i.count }" class="modal modal--align-top modal__bg" role="dialog" aria-hidden="true">
		          <div class="modal__dialog">
		            <div class="modal__content">
		              <h1>${news.nTitle }</h1>
		              
		              
		              <p>${news.nContent }</p>
		              
		              <!-- modal close button --> 
		              <a href="" class="modal__close demo-close"> <svg class="" viewBox="0 0 24 24">
		              <path d="M19 6.41l-1.41-1.41-5.59 5.59-5.59-5.59-1.41 1.41 5.59 5.59-5.59 5.59 1.41 1.41 5.59-5.59 5.59 5.59 1.41-1.41-5.59-5.59z"/>
		              <path d="M0 0h24v24h-24z" fill="none"/>
		              </svg> </a> </div>
		          </div>
        		</div>
			</c:forEach>
        
      </div>
      <script src="../public/javascript/modal.js" type="text/javascript"></script> 
      <script type="text/javascript">
$(document).ready(function(){
//限制字符个数
$(".container01-1-right-bottom").each(function(){
var maxwidth=55;
if($(this).text().length>maxwidth){
$(this).text($(this).text().substring(0,maxwidth));
$(this).html($(this).html()+'…');
}
});
});
</script> 
    </div>
  </div>
</div>
<footer>
  <p>copyright©我爱学习</p>
</footer>

</body>
</html>