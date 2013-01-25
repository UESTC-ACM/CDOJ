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
  Date: 13-1-25
  Time: 下午11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<s:div cssClass="navbar navbar-fixed-top navbar-inverse">
    <s:div cssClass="navbar-inner">
        <s:div cssClass="container">
            <ul class="nav">
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
                    <a href="./contest.html">Contests</a>
                </li>
                <li class="divider-vertical"></li>

                <li>
                    <a href="./statu.html">Status</a>
                </li>
                <li class="divider-vertical"></li>

                <li>
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

            </ul>
            <!-- 导航部分 -->
            <!-- 快速搜索框 -->
            <form class="navbar-search pull-right" action="">
                <input type="text" class="search-query span2" placeholder="Search">
            </form>

            <s:if test="#session.userName == null">
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
                                <a href="#loginModal" role="button" data-toggle="modal">
                                    <i class="icon-ok-circle"></i>
                                    Login
                                </a>
                            </li>

                            <li>
                                <!-- Button to trigger modal -->
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
                            <i class="icon-white icon-user"></i>
                            <s:property value="#session.userName"/>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <s:a action="index" namespace="/admin">
                                    <i class="icon-lock"></i>
                                    Admin
                                </s:a>
                            </li>
                            <!-- 用户中心的入口 -->
                            <li>
                                <a href="./usercenter.html">
                                    <i class="icon-home"></i>
                                    User center
                                </a>
                            </li>

                            <!-- 未读消息 -->
                            <li>
                                <a href="./message.html">
                                    <i class="icon-envelope"></i>
                                    Message
                                    <span class="badge badge-success">2</span>
                                </a>
                            </li>

                            <!-- 收藏夹 -->
                            <li>
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

        </s:div>
    </s:div>
</s:div>
</body>
</html>