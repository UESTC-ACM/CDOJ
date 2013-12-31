<%--
 Admin problem editor page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<c:if test="${action eq 'new'}">
  <title>New article</title>
</c:if>
<c:if test="${action eq 'edit'}">
  <title>Edit article - article${targetArticle.articleId}</title>
</c:if>
</head>
<body>
  <div id="article-editor">
      <div class="row">
        <c:if test="${action eq 'new'}">
          <div class="col-md-12" id="article-editor-title" value="new">
            <h1>New article</h1>
          </div>
        </c:if>
        <c:if test="${action eq 'edit'}">
          <div class="col-md-12" id="article-editor-title"
            value="${targetArticle.articleId}">
            <h1>Edit problem ${targetArticle.articleId}</h1>
          </div>
        </c:if>
        <div class="form-group">
          <div class="col-sm-12">
            <input type="text" name="title" maxlength="50"
              value="${targetArticle.title}" id="title"
              class="form-control" placeholder="Enter title here" />
          </div>
        </div>
        <div class="col-md-12">
          <div id="content">
            <c:out value="${targetArticle.content}" escapeXml="true" />
          </div>
        </div>
        <div class="col-md-12 text-center">
          <button type="button" class="btn btn-primary" id="submit">Submit</button>
        </div>
      </div>
    </div>
</body>
</html>