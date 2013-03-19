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
    <script src="<s:url value="/scripts/cdoj/cdoj.admin.user.js"/>"></script>
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
                <th style="width: 14px;"><i class="icon-th-list"></i></th>
                <th style="width: 30px;">Id</th>
                <th>User name</th>
                <th>Nick name</th>
                <th>Email</th>
                <th style="width: 90px;">Type</th>
                <th>Last login</th>
                <th style="width: 14px;"></th>
            </tr>
            </thead>
            <tbody id="userList">
            </tbody>
        </table>

        <div id="userSelector">
            <%--suppress HtmlUnknownTarget --%>
            <form action="well form-inline">
                <a href="#" class="btn" id="selectAllUser">Select all</a>
                <a href="#" class="btn" id="clearSelectedUser">Clear</a>
                <a href="#" class="pull-right btn btn-danger" id="deleteSelectedUser">Delete selected user</a>
            </form>
        </div>
    </div>

    <div class="tab-pane fade" id="tab-user-search">
        <div id="userCondition">
            <form class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="userCondition.startId">User ID</label>

                    <div class="controls">
                        <div class="input-prepend inline">
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
                </div>
                <div class="control-group">
                    <label class="control-label" for="userCondition.userName">User name</label>

                    <div class="controls">
                        <s:textfield name="userCondition.userName"
                                     maxLength="24"
                                     cssClass="span6"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="userCondition.type">Type</label>

                    <div class="controls">
                        <s:select name="userCondition.type"
                                  list="global.authenticationTypeList"
                                  listKey="ordinal()"
                                  listValue="description"
                                  cssClass="span6"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="userCondition.school">School</label>

                    <div class="controls">
                        <s:textfield name="userCondition.school"
                                     maxLength="50"
                                     cssClass="span6"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="userCondition.departmentId">Department</label>

                    <div class="controls">
                        <s:select name="userCondition.departmentId"
                                  list="global.departmentList"
                                  listKey="departmentId"
                                  listValue="name"
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
                </div>
            </form>
        </div>
    </div>
</div>

<!-- User edit Modal -->
<div id="userEditModal" class="modal hide fade modal-large" tabindex="-1" role="dialog"
     aria-labelledby="userEditModal" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="userEditModalLabel"></h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal">
            <fieldset>
                <s:textfield name="userDTO.userId"
                             maxLength="20"
                             cssClass="span4"
                             label="User ID"
                             readonly="true"
                             theme="bootstrap"/>
                <s:textfield name="userDTO.nickName"
                             maxLength="20"
                             cssClass="span4"
                             label="Nick name"
                             readonly="true"
                             theme="bootstrap"/>
                <s:textfield name="userDTO.email"
                             maxLength="100"
                             cssClass="span4"
                             label="Email"
                             readonly="true"
                             theme="bootstrap"/>
                <s:textfield name="userDTO.school"
                             maxLength="50"
                             cssClass="span4"
                             value="UESTC"
                             label="School"
                             theme="bootstrap"/>
                <s:select name="userDTO.departmentId"
                          list="global.departmentList"
                          listKey="departmentId"
                          listValue="name"
                          cssClass="span4"
                          label="Department"
                          theme="bootstrap"/>
                <s:textfield name="userDTO.studentId"
                             maxLength="20"
                             cssClass="span4"
                             label="Student ID"
                             theme="bootstrap"/>
                <s:select name="userDTO.type"
                          list="global.authenticationTypeList"
                          listKey="ordinal()"
                          listValue="description"
                          cssClass="span4"
                          label="Type"
                          theme="bootstrap"/>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
        <a href="#" class="btn btn-primary">Update</a>
    </div>
</div>

</body>
</html>