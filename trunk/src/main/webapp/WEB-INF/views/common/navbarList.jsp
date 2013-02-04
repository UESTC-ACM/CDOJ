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
  Date: 13-1-26
  Time: 下午11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<!-- TODO ul missing? -->
<li class="divider-vertical"></li>

<li>
    <a href="<s:url action="index" namespace="/"/>">CDOJ</a>
</li>
<li class="divider-vertical"></li>

<li>
    <a href="<s:url action="page/1" namespace="/problemset"/>">Problems</a>
</li>
<li class="divider-vertical"></li>

<li>
    <%--suppress HtmlUnknownTarget --%>
    <a href="./contest.html">Contests</a>
</li>
<li class="divider-vertical"></li>

<li>
    <%--suppress HtmlUnknownTarget --%>
    <a href="./status.html">Status</a>
</li>
<li class="divider-vertical"></li>

<li>
    <%--suppress HtmlUnknownTarget --%>
    <a href="./users.html">Users</a>
</li>
<li class="divider-vertical"></li>

<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Links<b class="caret"></b></a>
    <ul class="dropdown-menu">
        <!-- 固定链接 -->
        <li><a href="#">BBS</a></li>
        <li><a href="#">Wiki</a></li>
        <li><a href="http://www.mzry1992.com/blog/">mzry1992.com</a></li>

        <!-- Click这里就交给何老师了 -->
        <li class="divider"></li>
        <li class="nav-header">Click</li>
        <li><a href="#">F.A.Q</a></li>
        <li><a href="#">Download</a></li>
        <li><a href="#">Step-by-Step</a></li>
        <li><a href="#">Team Honors</a></li>

    </ul>
</li>
<li class="divider-vertical"></li>
</body>
</html>