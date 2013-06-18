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
 User list page

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
    <script src="<s:url value="/scripts/cdoj/cdoj.user.js"/>"></script>
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
                <th style="width: 30px;" class="orderButton" field="id">Id</th>
                <th class="orderButton" field="userName">User name</th>
                <th class="orderButton" field="nickName">Nick name</th>
                <th class="orderButton" field="school">School</th>
                <th style="width: 160px;" class="orderButton" field="lastLogin">Last login</th>
                <th style="width: 60px;" class="orderButton" field="solved">Solved</th>
                <th style="width: 60px;" class="orderButton" field="tried">Tried</th>
            </tr>
            </thead>
            <tbody id="userList">
            </tbody>
        </table>

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

</body>
</html>