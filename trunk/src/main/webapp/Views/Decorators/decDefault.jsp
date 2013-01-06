<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
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

<html lang="en">
<head>
    <link rel="icon" type="image/png" href="<s:url value="/Images/logo/favicon128.png"/>">
    <meta http-equiv=Content-Type content="text/html;charset=utf-8">
    <!-- 要用到的CSS -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<s:url value="/Styles/bootstrap.css"/>" rel="stylesheet">
    <link href="<s:url value="/Styles/prettify.css"/>" rel="stylesheet">
    <!-- 我的自定义CSS -->
    <link href="../Styles/cdoj.css" rel="stylesheet">
    <!-- 要用到的JS -->
    <script src="<s:url value="/Scripts/jquery.min.js"/>"></script>
    <script src="<s:url value="/Scripts/bootstrap.js"/>"></script>
    <script src="<s:url value="/Scripts/prettify.js"/>"></script>
    <script type="text/javascript"
            src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
    <!-- 如果需要开启内网模式的话请解压MathJax.zip到plugin目录下然后将上面替换成这个
         <script type="text/javascript" src="/Plugins/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
         -->
    <script src="<s:url value="/Plugins/ckeditor/ckeditor.js"/>"></script>
    <script src="<s:url value="/Plugins/edit_area/edit_area_full.js"/>"></script>
    <script src="<s:url value="/Scripts/cdoj.js"/>"></script>

    <!-- 网站标题，暂时先这样 -->
    <title><decorator:title/> - UESTC Online Judge</title>

</head>

<body>
<!--
        Navbar
        @TODO:
        决定导航上的项目有哪些
        分隔符的颜色需要微调
        Navbar都要用
        -->
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <!-- 留白。。。 -->
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a><!-- 留白。。。 -->

            <!-- 导航部分 -->
            <ul class="nav">
                <!-- 当前页面class为active -->
                <li class="active">
                    <a href="./index.html#">CDOJ</a>
                </li>
                <li class="divider-vertical"></li>

                <li>
                    <a href="./problem.html">Problems</a>
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
                    <a href="./ranklist.html">Ranklist</a>
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

            <!-- 用户信息菜单，自行取用 -->
            <!--
                       用户信息菜单，登陆后
                       -->
            <ul class="nav pull-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-user"></i>
                        ${user.userName}
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
                            <a href="./logout.html">
                                <i class="icon-off"></i>
                                Logout
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- 用户相关的菜单 -->

            <!--
                       用户信息菜单，没有登录的时候
                       -->
            <ul class="nav pull-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-pencil"></i>
                        Login
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- 登陆按钮 -->
                        <li>
                            <a href="./login.html">
                                <i class="icon-ok-circle"></i>
                                Login
                            </a>
                        </li>

                        <!-- 注册按钮 -->
                        <li>
                            <a href="./register.html">
                                <i class="icon-plus-sign"></i>
                                Register
                            </a>
                        </li>

                    </ul>
                </li>
            </ul>
            <!-- 用户相关的菜单 -->

        </div>
    </div>
</div>
<!-- navbar -->

<!-- 正文 -->
<div id="wrap">
    <div class="container">

        <decorator:body/>

    </div>
</div>

<!-- Footer -->
<div id="footer">
    <div class="container">
        <div class="row">
            <div class="span3 pull-left">
                <a id="logo-banner" href="./index.html"></a>
            </div>
            <div class="span9">
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
            </div>
        </div>
    </div>
</div>

</body>
</html>
