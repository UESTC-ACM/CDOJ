<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="cdoj" uri="/WEB-INF/cdoj.tld" %>
<%--
  ~ /*
  ~  * cdoj, UESTC ACMICPC Online Judge
  ~  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
  ~  * 	mzry1992 <@link muziriyun@gmail.com>
  ~  *
  ~  * This program is free software; you can redistribute it and/or
  ~  * modify it under the terms of the GNU General Public License
  ~  * as published by the Free Software Foundation; either version 2
  ~  * of the License, or (at your option) any later version.
  ~  *
  ~  * This program is distributed in the hope that it will be useful,
  ~  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~  * GNU General Public License for more details.
  ~  *
  ~  * You should have received a copy of the GNU General Public License
  ~  * along with this program; if not, write to the Free Software
  ~  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  ~  */
  --%>

<%--
 Admin problem list page

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<s:url value="/scripts/cdoj/cdoj.old.admin.problem.js"/>"></script>
    <title>Problem</title>
</head>
<body>

<ul id="TabMenu" class="nav nav-pills">
    <li class="active">
        <a href="#tab-problem-list" data-toggle="tab">Problem list</a>
    </li>
    <li><a href="#tab-problem-search" data-toggle="tab">Search</a></li>
</ul>

<div id="TabContent" class="tab-content">
    <div class="tab-pane fade active in" id="tab-problem-list">
        <div id="pageInfo">
        </div>

        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th style="width: 30px;">Id</th>
                <th>Title</th>
                <th style="width: 70px;">Difficulty</th>
                <th style="width: 14px;"></th>
            </tr>
            </thead>
            <tbody id="problemList">
            </tbody>
        </table>
    </div>

    <div class="tab-pane fade" id="tab-problem-search">
        <div id="problemCondition">
            <form class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="problemCondition.startId">Problem ID</label>

                    <div class="controls">
                        <div class="input-prepend inline">
                            <span class="add-on">Form</span>
                            <s:textfield name="problemCondition.startId"
                                         maxLength="6"
                                         cssClass="input-small"/>
                        </div>
                        <div class="input-prepend">
                            <span class="add-on">To</span>
                            <s:textfield name="problemCondition.endId"
                                         maxLength="6"
                                         cssClass="input-small"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="problemCondition.startDifficulty">Difficulty</label>

                    <div class="controls">
                        <div class="input-prepend inline">
                            <span class="add-on">Form</span>
                            <s:textfield name="problemCondition.startDifficulty"
                                         maxLength="6"
                                         cssClass="input-small"/>
                        </div>
                        <div class="input-prepend">
                            <span class="add-on">To</span>
                            <s:textfield name="problemCondition.endDifficulty"
                                         maxLength="6"
                                         cssClass="input-small"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="problemCondition.title">Title</label>

                    <div class="controls">
                        <s:textfield name="problemCondition.title"
                                     maxLength="100"
                                     cssClass="span6"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="problemCondition.keyword">Keyword</label>

                    <div class="controls">
                        <s:textfield name="problemCondition.keyword"
                                     maxLength="100"
                                     cssClass="span6"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="problemCondition.source">Source</label>

                    <div class="controls">
                        <s:textfield name="problemCondition.source"
                                     maxLength="100"
                                     cssClass="span6"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">Is SPJ</label>

                    <div class="controls">
                        <label class="radio inline">
                            <input type="radio" name="problemCondition.isSpj" value="all" checked="">
                            All
                        </label>
                        <label class="radio inline">
                            <input type="radio" name="problemCondition.isSpj" value="true">
                            Yes
                        </label>
                        <label class="radio inline">
                            <input type="radio" name="problemCondition.isSpj" value="false">
                            No
                        </label>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">Is Visible</label>

                    <div class="controls">
                        <label class="radio inline">
                            <input type="radio" name="problemCondition.isVisible" value="all" checked="">
                            All
                        </label>
                        <label class="radio inline">
                            <input type="radio" name="problemCondition.isVisible" value="true">
                            Yes
                        </label>
                        <label class="radio inline">
                            <input type="radio" name="problemCondition.isVisible" value="false">
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