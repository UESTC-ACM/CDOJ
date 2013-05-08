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
 User menu on navbar

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

                <li>
                    <a href="#activateModal" role="button" data-toggle="modal">
                        <i class="icon-refresh"></i>
                        Forget password
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
                <img id="userAvatar" email="<s:property value="currentUser.email"/>" type="avatar"/>
                <span id="currentUser" type="<s:property value="currentUser.type"/>">
                    <s:property value="currentUser.userName"/>
                </span>
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
                <li>
                    <s:a action="center/%{currentUser.userName}" namespace="/user">
                        <i class="icon-home"></i>
                        User center
                    </s:a>
                </li>
                <li>
                    <a href="#">
                        <i class="icon-envelope"></i>
                        Message
                        <span class="badge badge-success">2</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="icon-folder-open"></i>
                        Bookmark
                    </a>
                </li>
                <li>
                    <a href="#" id="logoutButton">
                        <i class="icon-off"></i>
                        Logout
                    </a>
                </li>
            </ul>
        </li>
    </ul>
</s:else>
</body>
</html>