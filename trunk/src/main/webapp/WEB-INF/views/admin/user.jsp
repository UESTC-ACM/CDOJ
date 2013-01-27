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

<table class="table table-striped table-bordered">
    <thead>
        <tr>
            <th>User name</th>
            <th>Nick name</th>
            <th>Email</th>
            <th>School</th>
            <th>Department</th>
            <th>Student ID</th>
            <th>Type</th>
            <th>Last login</th>
        </tr>
    </thead>
    <tbody id="userList">
        <tr>
            <td>He he</td>
            <td>aaa</td>
            <td>He he</td>
            <td>aaa</td>
        </tr>
        <tr>
            <td>He he</td>
            <td>aaa</td>
            <td>He he</td>
            <td>aaa</td>
        </tr>
    </tbody>
</table>
</body>
</html>