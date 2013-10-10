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
		<div id="article">
			<div class="pure-g mzry1992-header">
				<div class="pure-u-4-5" id="article_title"
					value="${targetArticle.articleId}">
					<h1>${targetArticle.title}</h1>
				</div>
				<div class="pure-u-1-5">
					<c:if test="${currentUser.type == 1}">
					<a href="/admin/article/editor/${targetArticle.articleId}">
						<i class="icon-pencil"></i>
						Edit article
					</a>
					</c:if>
				</div>
			</div>

			<div class="pure-g mzry1992-content">
				<div class="pure-u-1" id="article_content" type="markdown">
					<textarea>${targetArticle.content}</textarea>
				</div>
			</div>
		</div>

	</body>
</html>
