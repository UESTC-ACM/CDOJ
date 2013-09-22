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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<s:url value="/scripts/cdoj/cdoj.admin.status.js"/>"></script>
    <title>Status</title>
</head>
<body>

<ul id="TabMenu" class="nav nav-pills">
    <li class="active">
        <a href="#tab-status-list" data-toggle="tab">Status</a>
    </li>
    <li><a href="#tab-status-search" data-toggle="tab">Search</a></li>
</ul>

<div id="TabContent" class="tab-content">
    <div class="tab-pane fade active in" id="tab-status-list">
        <div id="pageInfo">
        </div>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th style="width: 60px;" class="orderButton" field="id">Id</th>
                <th>User</th>
                <th style="width: 60px;" class="orderButton" field="problemByProblemId">Problem</th>
                <th style="width: 60px;" class="orderButton" field="contestByContestId">Contest</th>
                <th style="width: 260px;" class="orderButton" field="result">Judge's Response</th>
                <th style="width: 140px;" class="orderButton" field="time">Submit Time</th>
            </tr>
            </thead>
            <tbody id="statusList">
            </tbody>
        </table>
    </div>

    <div class="tab-pane fade" id="tab-status-search">
        <div id="statusCondition">
            <form class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="statusCondition.startId">Status ID</label>

                    <div class="controls">
                        <div class="input-prepend inline">
                            <span class="add-on">Form</span>
                            <s:textfield name="statusCondition.startId"
                                         maxLength="6"
                                         cssClass="input-small"/>
                        </div>
                        <div class="input-prepend">
                            <span class="add-on">To</span>
                            <s:textfield name="statusCondition.endId"
                                         maxLength="6"
                                         cssClass="input-small"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="statusCondition.userName">User Name</label>

                    <div class="controls">
                        <s:textfield name="statusCondition.userName"
                                     maxLength="100"
                                     cssClass="span6"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="statusCondition.problemId">Problem ID</label>

                    <div class="controls">
                        <s:textfield name="statusCondition.problemId"
                                     maxLength="100"
                                     cssClass="span6"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="statusCondition.problemId">Contest ID</label>

                    <div class="controls">
                        <s:textfield name="statusCondition.contestId"
                                     maxLength="100"
                                     cssClass="span6"/>
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
                    <s:submit name="rejudge"
                              cssClass="btn btn-info pull-right"
                              value="Rejudge"
                              theme="bootstrap"/>
                </div>
            </form>
        </div>
    </div>

</div>

<page:applyDecorator name="body" page="/WEB-INF/views/status/modal.jsp"/>

</body>
</html>