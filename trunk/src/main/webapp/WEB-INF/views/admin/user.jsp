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
  Date: 13-1-27
  Time: 下午2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<s:url value="/scripts/cdoj.admin.user.js"/>"></script>
    <title>User</title>
</head>
<body>


<ul id="TabMenu" class="nav nav-pills">
    <li class="active">
        <a href="#tab-user-list" data-toggle="tab">User list</a>
    </li>
    <li><a href="#tab-user-search" data-toggle="tab">Search</a></li>
</ul>

<div id="TabContent" class="tab-content">
    <div class="tab-pane fade active in" id="tab-user-list">
        <div id="pageInfo">
        </div>

        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th style="width: 14px;"><i class="icon-th-list"/></th>
                <th style="width: 30px;">Id</th>
                <th>User name</th>
                <th>Nick name</th>
                <th>Email</th>
                <th style="width: 100px;">Type</th>
                <th>Last login</th>
                <th style="width: 14px;"></th>
            </tr>
            </thead>
            <tbody id="userList">
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

    <div class="tab-pane fade" id="tab-user-search">
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
                        <s:textfield name="userCondition.type"
                                     maxLength="1"
                                     cssClass="input-mini"/>
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

<!-- User edit Modal -->
<div id="userEditModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="userEditModal" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="userEditModalLabel"></h3>
    </div>
    <div class="modal-body">
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
        <a href="#" class="btn btn-primary">Update</a>
    </div>
</div>

</body>
</html>