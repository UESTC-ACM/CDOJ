<%--
Problem statement
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>${targetArticle.title}</title>
</head>
<body>
  <div id="article-show">
    <div class="row">
      <div class="col-md-12">
        <h1>${targetArticle.title}</h1>
        <c:if test="${currentUser.type == 1}">
              <a
                href="<c:url value="/article/editor/${targetArticle.articleId}"/>"><i
                class="fa fa-pencil"></i> Edit</a>
        </c:if>
      </div>

      <div class="col-md-12" id="article-content" type="markdown">
        <textarea>${targetArticle.content}</textarea>
      </div>
    </div>
  </div>

</body>
</html>
