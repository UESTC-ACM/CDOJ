<%--
Problem statement
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>{{$root.title}}</title>
</head>
<body>
<div id="article-show"
     ng-controller="ArticleController"
     ng-init="articleId=${articleId};">
  <div class="row">
    <div class="col-md-12">
      <h1 ng-bind="article.title"></h1>
    </div>
    <div class="col-md-12" ng-show="$root.isAdmin">
      <a href="/article/editor/{{article.articleId}}">
        <i class="fa fa-pencil no-margin-right"></i> Edit
      </a>
    </div>

    <div class="col-md-12" ui-markdown content="article.content">
    </div>
  </div>
</div>

</body>
</html>
