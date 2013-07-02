<%--
  ~ cdoj, UESTC ACMICPC Online Judge
  ~
  ~ Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
  ~ mzry1992 <@link muziriyun@gmail.com>
  ~
  ~ This program is free software; you can redistribute it and/or
  ~ modify it under the terms of the GNU General Public License
  ~ as published by the Free Software Foundation; either version 2
  ~ of the License, or (at your option) any later version.
  ~
  ~ This program is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with this program; if not, write to the Free Software
  ~ Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  --%>

<%--
 Problem statement

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="cdoj" uri="/WEB-INF/cdoj.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<s:url value="/scripts/marked.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.util.markdown.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.article.articleShow.js"/>"></script>
    <script src="<s:url value="/plugins/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"/>"></script>
    <title>${targetArticle.title}</title>
</head>
<body>

<div class="subnav">
    <ul id="TabMenu" class="nav nav-tabs">
        <li class="active">
            <a href="#tab-article-show" data-toggle="tab">Article</a>
        </li>
        <li class="disabled">
            <a href="#tab-article-discus" data-toggle="tab">Discus</a>
        </li>
    </ul>
</div>

<div id="articleContent" class="subnav-content">
    <div id="TabContent" class="tab-content">
        <div class="tab-pane fade active in" id="tab-article-show">
            <div class="row" id="article">
                <div class="span12" id="article_title" value="${targetArticle.articleId}">
                    <h1 class="pull-left">${targetArticle.title}</h1>

                    <s:if test="currentUser.type == 1">
                        <div class="pull-right" style="margin: 10px 0;">
                            <s:a action="editor/%{targetArticle.articleId}" namespace="/admin/article">
                                <i class="icon-pencil"></i>
                                Edit article
                            </s:a>
                        </div>
                    </s:if>
                </div>

                <div class="span12" id="article_content" type="markdown">
                    <textarea>${targetArticle.content}</textarea>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="tab-article-discus">
        </div>
    </div>
</div>

</body>
</html>