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
  Created by IntelliJ IDEA.
  User: mzry1992
  Date: 13-1-30
  Time: 下午2:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<s:url value="/scripts/cdoj.admin.problem.js"/>"></script>
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
                <th style="width: 60px;">Accept</th>
                <th style="width: 60px;">Submit</th>
            </tr>
            </thead>
            <tbody id="problemList">
            <tr>
                <td>1</td>
                <td>
                    <span class="info-problem-source pull-left" data-original-title="hehe"> <a href="./problem_single.html">A+B Problem</a></span>

                    <span class="label label-info pull-right tags">data structs</span>
                    <span class="label label-info pull-right tags">dp</span>
                    <span class="label label-info pull-right tags">binary search</span>
                </td>
                <td><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i></td>
                <td>10</td>
                <td>20</td>
            </tr>
            </tbody>
        </table>

        <div id="userSelector">
            <form action="well form-inline">
                <a href="#" class="btn" id="selectAllUser">Select all</a>
                <a href="#" class="btn" id="clearSelectedUser">Clear</a>
                <a href="#" class="pull-right btn btn-danger" id="deleteSelectedUser">Delete selected user</a>
            </form>
        </div>
    </div>

    <div class="tab-pane fade" id="tab-problem-search">
        <div id="userCondition">
            <form class="well form-inline">
                <div class="control-group">
                    <div class="input-prepend">
                        <span class="add-on">Form</span>
                        <s:textfield name="userCondition.startId"
                                     maxLength="6"
                                     cssClass="input-small"/>
                    </div>
                    <div class="input-prepend">
                        <span class="add-on">To</span>
                        <s:textfield name="userCondition.endId"
                                     maxLength="6"
                                     cssClass="input-small"/>
                    </div>
                </div>
                <div class="control-group">
                    <div class="input-prepend">
                        <span class="add-on">User name</span>
                        <s:textfield name="userCondition.userName"
                                     maxLength="24"
                                     cssClass="span4"/>
                    </div>
                    <div class="input-prepend">
                        <span class="add-on">Type</span>
                        <s:select name="userCondition.type"
                                  list="global.authenticationTypeList"
                                  listKey="ordinal()"
                                  listValue="description"
                                  cssClass="span4"/>
                    </div>
                </div>
                <div class="control-group">
                    <div class="input-prepend">
                        <span class="add-on">School</span>
                        <s:textfield name="userCondition.school"
                                     maxLength="50"
                                     cssClass="span4"/>
                    </div>
                    <div class="input-prepend">
                        <span class="add-on">Department</span>
                        <s:select name="userCondition.departmentId"
                                  list="global.departmentList"
                                  listKey="departmentId"
                                  listValue="name"
                                  cssClass="span4"/>
                    </div>
                </div>
                <s:submit name="search"
                          cssClass="btn btn-primary"
                          value="Search"
                          theme="bootstrap"/>
            </form>
        </div>
    </div>
</div>

</body>
</html>