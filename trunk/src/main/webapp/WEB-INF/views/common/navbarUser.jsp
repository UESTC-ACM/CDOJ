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
  Time: 下午11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<s:if test="currentUser == null">
    <ul class="nav pull-right">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <i class="icon-white icon-pencil"></i>
                Login
                <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
                <li>
                    <!-- Button to trigger modal -->
                        <%--suppress HtmlUnknownTarget --%>
                    <a href="#loginModal" role="button" data-toggle="modal">
                        <i class="icon-ok-circle"></i>
                        Login
                    </a>
                </li>

                <li>
                    <!-- Button to trigger modal -->
                        <%--suppress HtmlUnknownTarget --%>
                    <a href="#registerModal" role="button" data-toggle="modal">
                        <i class="icon-plus-sign"></i>
                        Register
                    </a>
                </li>

            </ul>
        </li>
    </ul>
</s:if>
<s:else>
    <ul class="nav pull-right">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <!-- TODO src missing? -->
                <img id="avatar" email="<s:property value="currentUser.email"/>"/>
                <s:property value="currentUser.userName"/>
                <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
                <s:if test="currentUser.type == 1">
                    <li>
                        <s:a action="index" namespace="/admin">
                            <i class="icon-lock"></i>
                            Admin
                        </s:a>
                    </li>
                </s:if>
                <!-- 用户中心的入口 -->
                <li>
                        <%--suppress HtmlUnknownTarget --%>
                    <a href="./usercenter.html">
                        <i class="icon-home"></i>
                        User center
                    </a>
                </li>

                <!-- 未读消息 -->
                <li>
                        <%--suppress HtmlUnknownTarget --%>
                    <a href="./message.html">
                        <i class="icon-envelope"></i>
                        Message
                        <span class="badge badge-success">2</span>
                    </a>
                </li>

                <!-- 收藏夹 -->
                <li>
                        <%--suppress HtmlUnknownTarget --%>
                    <a href="./bookmark.html">
                        <i class="icon-folder-open"></i>
                        Bookmark
                    </a>
                </li>

                <!-- 登出 -->
                <li>
                    <a href="#" id="logoutButton">
                        <i class="icon-off"></i>
                        Logout
                    </a>
                </li>
            </ul>
        </li>
    </ul>
    <!-- 用户相关的菜单 -->
</s:else>
</body>
</html>