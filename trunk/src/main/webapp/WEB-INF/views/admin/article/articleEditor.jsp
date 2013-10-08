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
<page:applyDecorator name="head"
  page="/WEB-INF/views/common/fileUploaderHeader.jsp" />
<script src="<c:url value="/plugins/epiceditor/js/epiceditor.js"/>"></script>
<script src="<c:url value="/scripts/cdoj/cdoj.util.picture.js"/>"></script>
<script src="<c:url value="/scripts/cdoj/cdoj.admin.articleEditor.js"/>"></script>
<title>Article</title>
</head>
<body>
  <div class="row" id="articleEditor">

    <div class="span10">
      <h3>
      <c:if test="${action eq 'new'}">
        New article<span id="articleId" type="<c:out value="${action}"/>"></span>
      </c:if>
      <c:if test="${action eq 'edit'}">
        Edit article <span id="articleId" type="<c:out value="${action}"/>">${targetArticle.articleId}</span>
      </c:if>
      </h3>
      <div class="control-group">
        <div class="controls">
          <input type="text" name="title" maxlength="50"
            value="${targetArticle.title}" id="title"
            class="span10" placeholder="Enter title here">
        </div>
      </div>

    </div>

    <div class="span10">
      <div id="content"
        class="textarea-content textarea-large">${targetArticle.content}</div>
    </div>

    <div class="span10">
      <h2>Author</h2>
      <div class="control-group">
        <div class="controls">
          <input type="text" name="author" maxlength="50"
            value="${targetArticle.author}" id="author"
            class="span10" placeholder="Enter author here">
        </div>
      </div>
    </div>

    <div class="span10">
      <input type="submit" id="submit" name="submit" value="Submit"
        class="btn btn-primary">
    </div>
  </div>

  <page:applyDecorator name="body"
    page="/WEB-INF/views/admin/pictureModal.jsp" />

</body>
</html>