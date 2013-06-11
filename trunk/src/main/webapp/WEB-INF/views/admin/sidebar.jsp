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
 Sidebar of all admin page.

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
    <title></title>
</head>
<body>
<div class="well" style="padding: 8px 0;">
    <ul class="nav nav-list">
        <li class="nav-header"><i class="icon-home"></i>Dashboard</li>
        <li><a href="#">OJ status</a></li>
        <li><a href="#">Backup</a></li>
        <li><a href="#">Help</a></li>

        <li class="divider"></li>

        <li class="nav-header"><i class="icon-user"></i>User</li>
        <li><s:a action="list" namespace="/admin/user">User list</s:a></li>

        <li class="divider"></li>

        <li class="nav-header"><i class="icon-file"></i>Problem</li>
        <li><s:a action="list" namespace="/admin/problem">Problem list</s:a></li>
        <li><s:a action="editor/" namespace="/admin/problem">Add problem</s:a></li>

        <li class="divider"></li>

        <li class="nav-header"><i class="icon-screenshot"></i>Contest</li>
        <li><s:a action="list" namespace="/admin/contest">Contest list</s:a></li>
        <li><s:a action="editor/" namespace="/admin/contest">Add contest</s:a></li>

        <li class="divider"></li>

        <li class="nav-header"><i class="icon-refresh"></i>Status</li>
        <li><s:a action="list" namespace="/admin/status">Status list</s:a></li>

    </ul>
</div>
</body>
</html>