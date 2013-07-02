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
 Admin problem list page

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
    <script src="<s:url value="/scripts/cdoj/cdoj.admin.article.js"/>"></script>
    <title>Article</title>
</head>
<body>

<ul id="TabMenu" class="nav nav-pills">
    <li class="active">
        <a href="#tab-article-list" data-toggle="tab">Article list</a>
    </li>
    <li><a href="#tab-article-search" data-toggle="tab">Search</a></li>
</ul>

<div id="TabContent" class="tab-content">
    <div class="tab-pane fade active in" id="tab-article-list">
        <div id="pageInfo">
        </div>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th style="width: 30px;" class="orderButton" field="id">Id</th>
                <th class="orderButton" field="title">Title</th>
                <th style="width: 140px;" class="orderButton" field="time">Last updated</th>
                <th style="width: 200px;">Owner</th>
            </tr>
            </thead>
            <tbody id="articleList">
            </tbody>
        </table>
    </div>

    <div class="tab-pane fade" id="tab-article-search">
        <div id="articleCondition">
            <form class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="articleCondition.startId">Article ID</label>

                    <div class="controls">
                        <div class="input-prepend inline">
                            <span class="add-on">Form</span>
                            <s:textfield name="articleCondition.startId"
                                         maxLength="6"
                                         cssClass="input-small"/>
                        </div>
                        <div class="input-prepend">
                            <span class="add-on">To</span>
                            <s:textfield name="articleCondition.endId"
                                         maxLength="6"
                                         cssClass="input-small"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="articleCondition.title">Title</label>

                    <div class="controls">
                        <s:textfield name="articleCondition.title"
                                     maxLength="100"
                                     cssClass="span6"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="articleCondition.keyword">Keyword</label>

                    <div class="controls">
                        <s:textfield name="articleCondition.keyword"
                                     maxLength="100"
                                     cssClass="span6"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">Is Visible</label>

                    <div class="controls">
                        <label class="radio inline">
                            <input type="radio" name="articleCondition.isVisible" value="all" checked="">
                            All
                        </label>
                        <label class="radio inline">
                            <input type="radio" name="articleCondition.isVisible" value="true">
                            Yes
                        </label>
                        <label class="radio inline">
                            <input type="radio" name="articleCondition.isVisible" value="false">
                            No
                        </label>
                    </div>
                </div>
                <div class="form-actions">
                    <s:submit name="search"
                              cssClass="btn btn-primary"
                              value="Search"
                              theme="bootstrap"/>
                    <s:submit name="reset"
                              cssClass="btn btn-danger"
                              value="Reset"
                              theme="bootstrap"/>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>