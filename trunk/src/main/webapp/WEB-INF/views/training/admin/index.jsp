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
 Summer training home page

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<s:url value="/scripts/cdoj/cdoj.training.rating.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.admin.training.js"/>"></script>
    <title>Summer training</title>
</head>
<body>

<div>
    <ul id="TabMenu" class="nav nav-tabs">
        <li class="active">
            <a href="#tab-contest-manage" data-toggle="tab">Contest manage</a>
        </li>
        <li>
            <a href="#tab-training-user" data-toggle="tab">Training users</a>
        </li>
    </ul>
</div>

<div id="ratingContent" class="subnav-content">
    <div id="TabContent" class="tab-content">

        <div class="tab-pane fade active in" id="tab-contest-manage">
            <div class="row">
                <div class="span10">
                    <table id="contestListTable" class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 30px;">#</th>
                            <th>Contest name</th>
                        </tr>
                        </thead>
                        <tbody id="contestList">
                        <tr><td>1</td><td>2013</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="tab-training-user">
            <div class="row">
                <div class="span10">
                    <div id="pageInfo">
                    </div>

                    <table id="trainingUserListTable" class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 30px;" class="orderButton" field="id">#</th>
                            <th class="orderButton" field="name">Name</th>
                            <th class="orderButton" field="userByUserId">User</th>
                            <th style="width: 80px;" class="orderButton" field="rating">Rating</th>
                            <th style="width: 70px;" class="orderButton" field="volatility">Volatility</th>
                            <th style="width: 40px;" class="orderButton" field="competitions">Comp</th>
                            <th style="width: 60px;" class="orderButton" field="type">Type</th>
                            <th style="width: 120px;" class="orderButton" field="allow">Allow</th>
                        </tr>
                        </thead>
                        <tbody id="trainingUserList">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>