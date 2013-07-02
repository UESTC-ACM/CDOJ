<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="cdoj" uri="/WEB-INF/cdoj.tld" %>
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
 Admin problem editor page

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <page:applyDecorator name="head" page="/WEB-INF/views/common/fileUploaderHeader.jsp"/>
    <script src="<s:url value="/plugins/epiceditor/js/epiceditor.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.util.picture.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.admin.articleEditor.js"/>"></script>
    <title>Article</title>
</head>
<body>
<div class="row" id="articleEditor">

    <div class="span10">
        <h3>
            Edit article <span id="articleId">${targetArticle.articleId}</span>
        </h3>
        <div class="control-group">
            <div class="controls">
                <input type="text"
                       name="articleDTO.title"
                       maxlength="50"
                       value="${targetArticle.title}"
                       id="articleDTO_title"
                       class="span10"
                       placeholder="Enter title here">
            </div>
        </div>

    </div>

    <div class="span10">
        <div id="articleDTO_content" class="textarea-content textarea-large">${targetArticle.content}</div>
    </div>

    <div class="span10">
        <h2>Author</h2>
        <div class="control-group">
            <div class="controls">
                <input type="text"
                       name="articleDTO.author"
                       maxlength="50"
                       value="${targetArticle.author}"
                       id="articleDTO_author"
                       class="span10"
                        placeholder="Enter author here">
            </div>
        </div>
    </div>

    <div class="span10">
        <input type="submit" id="submit" name="submit" value="Submit" class="btn btn-primary">
    </div>
</div>

<page:applyDecorator name="body" page="/WEB-INF/views/admin/pictureModal.jsp"/>

</body>
</html>