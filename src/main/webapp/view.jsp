<%@ page import="com.kings.glory.vo.Hero" contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1 style="text-align: center;">王者英雄荣耀要榜</h1>
<%
    @SuppressWarnings("unchecked")
    List<Hero> heroList = (List<Hero>) request.getAttribute("hero");
    for (Hero hero : heroList) {
%>
<div style="width:80%;margin:0 auto;">
    <div>
        <h1>英雄人物：<%=hero.getHero()%></h1>
    </div>
    <div>
        <img src="<%=hero.getPicture()%>" height="100" width="100" alt="<%=hero.getHero()%>"/>
    </div>
    <%
      if(StringUtils.isNotEmpty(hero.getDetail())){
    %>
    <div style="width: 100%;height: 300px;overflow: scroll;">
        <span>
            <%=hero.getDetail()%>
        </span>
    </div>
    <%
        }
    %>
</div>
<%
    }
%>
</body>
</html>