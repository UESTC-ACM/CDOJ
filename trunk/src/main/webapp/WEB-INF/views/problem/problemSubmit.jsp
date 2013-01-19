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
  Date: 13-1-19
  Time: 下午3:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Submit</title>
</head>
<body>

    <div class="problem">
        <div class="row">
            <!-- 题目标题 -->
            <div class="span12">
                <h2>Problem 9999. One million ways to make love</h2>
            </div>

            <s:url action="problem/" namespace="/problemset" id="problemUrl"/>
            <!-- 导航链接 -->
            <div class="span12">
                <ul class="nav nav-pills">
                    <li><a href="${problemUrl}${problemId}">Problem</a></li>
                    <li class="active">
                        <a href="#">Submit</a>
                    </li>
                    <li><a href="${problemUrl}${problemId}/status">Status</a></li>
                    <li><a href="${problemUrl}${problemId}/discuss">Discuss</a></li>
                </ul>
            </div>
        </div>

        <div class="row">
            <!-- 语言选择 -->
            <div class="span12">

            </div>

            <!-- 编辑框 -->
                <div class="span12">
                    <textarea id="sourceCodeEditor">
                    </textarea>
                    <script>
                        editAreaLoader.init({
                            id:"sourceCodeEditor", start_highlight:true, syntax:"cpp", allow_toggle:false, toolbar:"syntax_selection", syntax_selection_allow:"c,cpp,java", show_line_colors:true
                        });
                    </script>
                </div>

            <!-- 提交 -->
                <div class="span12">
                    <div class="btn btn-success">Submit</div>
                </div>

        </div>
    </div>
</body>
</html>