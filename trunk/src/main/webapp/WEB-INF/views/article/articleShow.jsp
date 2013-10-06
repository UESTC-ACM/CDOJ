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
<script src="<c:url value="/scripts/marked.js"/>"></script>
<script src="<c:url value="/scripts/cdoj/cdoj.util.markdown.js"/>"></script>
<script src="<c:url value="/scripts/cdoj/cdoj.article.articleShow.js"/>"></script>
<script
  src="<c:url value="/plugins/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"/>"></script>
<title>${targetArticle.title}</title>
</head>
<body>

  <div class="subnav">
    <ul id="TabMenu" class="nav nav-tabs">
      <li class="active"><a href="#tab-article-show"
        data-toggle="tab">Article</a></li>
      <li class="disabled"><a href="#tab-article-discussion"
        data-toggle="tab">Discussion</a></li>
    </ul>
  </div>

  <div id="articleContent" class="subnav-content">
    <div id="TabContent" class="tab-content">
      <div class="tab-pane fade active in" id="tab-article-show">
        <div class="row" id="article">
          <div class="span12" id="article_title"
            value="${targetArticle.articleId}">
            <h1 class="pull-left">${targetArticle.title}</h1>

            <c:if test="${currentUser.type == 1}">
              <div class="pull-right" style="margin: 10px 0;">
                <a href="/admin/article/editor/${targetArticle.articleId}">
                  <i class="icon-pencil"></i>
                                Edit article
                            </a>
              </div>
            </c:if>
          </div>

          <div class="span12" id="article_content" type="markdown">
            <textarea>${targetArticle.content}</textarea>
          </div>
        </div>
      </div>

      <div class="tab-pane fade" id="tab-article-discussion"></div>
    </div>
  </div>

</body>
</html>