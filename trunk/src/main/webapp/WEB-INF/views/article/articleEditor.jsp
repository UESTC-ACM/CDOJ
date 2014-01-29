<%--
 Admin problem editor page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <c:if test="${action eq 'new'}">
    <title>New article</title>
  </c:if>
  <c:if test="${not (action eq 'new')}">
    <title>Edit article - article${action}</title>
  </c:if>
</head>
<body>
<div id="article-editor"
     ng-controller="ArticleEditorController"
     ng-init="action='${action}'">
  <div class="row">
    <c:if test="${action eq 'new'}">
      <div class="col-md-12">
        <h1>New article</h1>
      </div>
    </c:if>
    <c:if test="${not (action eq 'new')}">
      <div class="col-md-12">
        <h1>Edit article <span ng-bind="action"></span></h1>
      </div>
    </c:if>
    <div class="form-group">
      <div class="col-sm-12">
        <input type="text"
               ng-model="article.title"
               ng-require="true"
               ng-maxlength="50"
               class="form-control" placeholder="Enter title here"/>
        <ui-validate-info value="fieldInfo" for="title"></ui-validate-info>
      </div>
    </div>
    <div class="col-md-12">
      <div ng-model="article.content"
           ui-flandre
           upload-url="/picture/uploadPicture/article/${action}">
      </div>
    </div>
    <div class="col-md-12 text-center">
      <button type="button" class="btn btn-primary" ng-click="submit()">Submit</button>
    </div>
  </div>
</div>
</body>
</html>