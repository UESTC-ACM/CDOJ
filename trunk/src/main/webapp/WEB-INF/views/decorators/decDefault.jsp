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
  Date: 13-1-6
  Time: 上午11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" type="image/png" href="<s:url value="/images/logo/favicon128.png"/>">
    <meta http-equiv=Content-Type content="text/html;charset=utf-8">
    <!-- 要用到的CSS -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<s:url value="/styles/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<s:url value="/styles/prettify.css"/>" rel="stylesheet">
    <!-- 我的自定义CSS -->
    <link href="<s:url value="/styles/cdoj.css"/>" rel="stylesheet">
    <!-- 要用到的JS -->
    <script src="<s:url value="/scripts/jquery.min.js"/>"></script>
    <script src="<s:url value="/scripts/bootstrap.min.js"/>"></script>
    <script src="<s:url value="/scripts/prettify.js"/>"></script>
    <script type="text/javascript"
            src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
    <!-- 如果需要开启内网模式的话请解压MathJax.zip到plugin目录下然后将上面替换成这个
         <script type="text/javascript" src="/Plugins/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
         -->
    <script src="<s:url value="/plugins/ckeditor/ckeditor.js"/>"></script>
    <script src="<s:url value="/plugins/edit_area/edit_area_full.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj.js"/>"></script>

    <decorator:head/>

    <!-- 网站标题，暂时先这样 -->
    <title><decorator:title/> - UESTC Online Judge</title>

</head>

<body>
<script>
    $(document).on("click", ".alert", function(e) {
        bootbox.alert("Hello world!", function() {
            console.log("Alert Callback");
        });
    });
</script>
<s:div cssClass="navbar navbar-fixed-top">
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
                            <i class="icon-pencil"></i>
                            Login
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <s:a action="login" namespace="/user">
                                    <i class="icon-ok-circle"></i>
                                    Login
                                </s:a>
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
                            <i class="icon-user"></i>
                                <s:property value="#session.userName"/>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
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
                                <s:a action="logout" namespace="/user">
                                    <i class="icon-off"></i>
                                    Logout
                                </s:a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <!-- 用户相关的菜单 -->
            </s:else>

        </s:div>
    </s:div>
</s:div>

<!-- Register Modal -->
<div id="registerModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="registerModal" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="registerModalLabel">Register</h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal">
            <fieldset>`
                <s:textfield name="userDTO.userName"
                             maxLength="24"
                             cssClass="span4"
                             label="User Name"
                             theme="bootstrap"/>
                <s:password name="userDTO.password"
                            maxLength="20"
                            cssClass="span4"
                            label="Password"
                            theme="bootstrap"/>
                <s:password name="userDTO.passwordRepeat"
                            maxLength="20"
                            cssClass="span4"
                            label="Repeat your password"
                            theme="bootstrap"/>
                <s:textfield name="userDTO.nickName"
                             maxLength="20"
                             cssClass="span4"
                             label="Nick name"
                             theme="bootstrap"/>
                <s:textfield name="userDTO.email"
                             maxLength="100"
                             cssClass="span4"
                             label="Email"
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
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Close</a>
        <a href="#" class="btn btn-primary">Register</a>
    </div>
</div>

<s:div id="wrap">
    <s:div cssClass="mzry1992">
        <s:div cssClass="container">
            <decorator:body/>
        </s:div>
    </s:div>
</s:div>

<s:div id="footer">
    <s:div cssClass="container">
        <s:div cssClass="row">
            <s:div cssClass="span3 pull-left">
                <s:a id="logo-banner" action="index" namespace="/"/>
            </s:div>
            <s:div cssClass="span9">
                <p class="muted credit">
                    UESTC Online Judge
                </p>

                <p class="muted credit">
                    Gnomovision version 69, Copyright (C) 2012 lyhypacm, mzry1992
                    <br/>
                    Gnomovision comes with ABSOLUTELY NO WARRANTY
                    <br/>
                    This is free software, and you are welcome to redistribute it under <a
                        href="http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt">certain conditions</a>
                </p>
            </s:div>
        </s:div>
    </s:div>
</s:div>

</body>
</html>
