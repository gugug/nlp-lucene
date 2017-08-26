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
</head>
<header> <img src="../public/images/logo.jpg" alt="beatles" width="43" height="44" class="logo" >
<a href="../index/index.action">
 <img src="../public/images/title.png" width="265" height="104"  alt="beatles" class="title"></a>
  <form class="search" action="${pageContext.request.contextPath }/index/search.action" method="post">
    <input type="text" name="searchContent" value="${scontent }">
    <button type="submit"><img src="../public/images/search.png" alt="search"></button>
  </form>
  <h4 class="label"><font color="#36f8ff">用时${time }毫秒</font></h4>
</header>
<body>

<div class="clear"></div>

<c:set var="keyWord" value="${scontent }" />
<c:set var="replaceCont" value="<span style='color:#36e75e;'>${keyWord }</span>" />

<div class="index_page"> 
  
  <!-- 搜索结果 -->
  
  <div class="results">
    <div class="container"> 
      
      <!-- 新闻 -->
      
      <div class="section news">
        <h4 class="label">新闻 >>></h4>
        <div class="info"> <span class="title"> <a href="${news.nUrl }">${news.nTitle }</a></span> <span class="media">${news.nMedia }</span> <span class="date">${news.nTime }</span> </div>
        <p class="text">
        
		<c:set var="nSummary" value="${news.nSummary }" />
		<c:set var="colorSummary" value="${fn:replace(nSummary, keyWord, replaceCont)}" />
		${colorSummary }
        </p>
        <div  class="more"> <a href="${pageContext.request.contextPath }/index/moreNewsResult.action?id=${news.nId }">
          <p>查看更多</p>
          <button>></button>
          </a> </div>
      </div>
      
      
      <!-- 百度百科 -->
      
       <div class="section baike">
        <h4 class="label">百度百科</h4>
        <p class="text">
        <c:set var="string1" value="${baiduBaike.bContent }"/>
		<c:set var="string2" value="${fn:substring(string1, 0, 300)}" />
		<c:set var="baikeCont" value="${string2 }" />
		<c:set var="colorBaikeCont" value="${fn:replace(baikeCont, keyWord, replaceCont)}" />
		${colorBaikeCont }...
        
        </p>
		 <div  class="more"> <a href="http://baike.baidu.com/item/${baiduBaike.bUrl }" class="more">
          <p>查看更多</p>
          <button>></button>
          </a> </div>
      </div>
      
      
      
      <!-- 豆瓣 -->
      
     <div class="section douban">
        <h4 class="label">豆瓣>>></h4>
        <p class="text">
        
       	<c:set var="doubanCont" value="${douban.dContent }" />
		<c:set var="colorDoubanCont" value="${fn:replace(doubanCont, keyWord, replaceCont)}" />
		${colorDoubanCont }...
        </p>
        <div  class="more"> <a href="${douban.dUrl }" class="more">
          <p>查看更多</p>
          <button>></button>
          </a> </div>
      </div>
      
      
      <!-- 微博 -->
      
      <div class="section weibo">
        <h4 class="label">微博>>></h4>
        <div class="info"> <span class="title"> <a>${weibo.wName }</a></span></div>
       
        <p class="text">
        <c:set var="weiboCont" value="${weibo.wContent }" />
		<c:set var="colorWeiboCont" value="${fn:replace(weiboCont, keyWord, replaceCont)}" />
        ${colorWeiboCont }
        </p>
        <div  class="more"> <a href="../index/moreWeiboResult.action?id=${weibo.wId }" class="more">
          <p>查看更多</p>
          <button>></button>
          </a> </div>
      </div>
      
    </div>
  </div>
  <div class="related">
    <div class="container">
      <!-- <div class="related_person">
        <h4>相关人物</h4>
        <ul>
          <li> <img src="../public/images/tom.png" alt="">
            <p>汤姆·汉克斯</p>
          </li>
          <li> <img src="../public/images/tom.png" alt="">
            <p>汤姆·汉克斯</p>
          </li>
          <li> <img src="../public/images/tom.png" alt="">
            <p>汤姆·汉克斯</p>
          </li>
          <li> <img src="../public/images/tom.png" alt="">
            <p>汤姆·汉克斯</p>
          </li>
        </ul>
      </div> -->
      <div class="related_film">
        <h4>相关电影人物</h4>
        
        <ul>
        
           <c:forEach items="${baiduBaike.imgList }" var="imgjpg" varStatus="i">
           
			 <li> 
          		<c:set var="imgname" value="${fn:replace(imgjpg, '.jpg', '')}" />
          		<a href="http://baike.baidu.com/item/${imgname }">
          		<img src="/baikeImg/${baiduBaike.bUrl }/img/${imgjpg }" style="width:90px;height:90px;" alt="">
          		<p>${imgname }</p></a>
          	</li>
          		
			</c:forEach>
          
        </ul>
      </div>
    </div>
  </div>
</div>
<footer>
  <p>copyright©我爱学习</p>
</footer>

</body>
</html>