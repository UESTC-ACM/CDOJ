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
  Date: 13-1-9
  Time: 下午1:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Problem</title>
</head>
<body>

<s:property value="page"/>

<!-- 题目分页和跳转 -->
<div class="row">
    <div class="span9">
        <div class="pagination pagination-centered">
            <ul>
                <li><a href="#">前一页</a></li>
                <li class="active">
                    <a href="#">1</a>
                </li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">后一页</a></li>
            </ul>
        </div>
    </div>
    <!-- 快速跳转题目 -->
    <form class="well form-horizontal pull-right">
        <input type="text" class="span2" placeholder="Problem ID...">
        <button type="submit" class="btn btn-primary">GO!</button>
    </form>
</div>

<!-- 题目列表 -->
<div class="row">
    <div class="span12">
        <table class="table table-striped table-bordered table-problemlist">
            <!-- 标题在cdoj.css中强制定义了居中 -->
            <thead>
            <tr>
                <th class="info-solved"><i class="icon-screenshot"></i></th>
                <th class="info-problem-id">#</th>
                <th>Title</th>
                <th class="info-problem-difficulty">节操</th>
                <th class="info-problem-ac">Accept</th>
                <th class="info-problem-ratio">Ratio</th>
            </tr>
            </thead>
            <!-- 参照第一行的CSS！有些列需要强制靠右 -->
            <tbody>
            <tr>
                <!-- icon-ok icon-remove 分别是勾勾和叉叉 -->
                <td><i class="icon-ok"></i></td>
                <td class="info-right">1</td>
                <td>
                    <!-- 题目名称与来源 -->
                    <span class="info-problem-source pull-left" data-original-title="来源：2013世界总决赛"><a href="./problem_single.html">A+B Problem</a></span>

                    <!-- 标签们！ -->
                    <span class="label pull-right tags">data structs</span>
                    <span class="label pull-right tags">dp</span>
                    <span class="label pull-right tags">binary search</span>
                </td>
                <!-- 难度 -->
                <td><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i></td>
                <!-- 通过数 -->
                <td class="info-right">100000</td>
                <!-- AC率 -->
                <td class="info-right">10.09%</td>
            </tr>

            <tr>
                <td></td>
                <td class="info-right">2</td>
                <td>
                    <!-- 题目名称与来源 -->
                    <span class="info-problem-source pull-left" data-original-title="来源：2013世界总决赛"><a href="./problem_single.html">A+B Problem</a></span>

                    <span class="label pull-right tags">implementation</span>
                </td>
                <td><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star-empty"></i></td>
                <td class="info-right">1</td>
                <td class="info-right">100.00%</td>
            </tr>

            <tr>
                <td><i class="icon-remove"></i></td>
                <td class="info-right">9999</td>
                <td>
                    <!-- 题目名称与来源 -->
                    <span class="info-problem-source pull-left" data-original-title="来源：2013世界总决赛"><a href="./problem_single.html">A+B Problem</a></span>

                    <span class="label pull-right tags">binary search</span>
                    <span class="label pull-right tags">data structures</span>
                    <span class="label pull-right tags">implementation</span>
                    <span class="label pull-right tags">sortings</span>
                </td>
                <td><i class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i></td>
                <td class="info-right">1000</td>
                <td class="info-right">0.00%</td>
            </tr>

            </tbody>

        </table>
    </div>
</div>

<!-- 题目分页和跳转 -->
<div class="row">
    <div class="span9">
        <div class="pagination pagination-centered">
            <ul>
                <li><a href="#">前一页</a></li>
                <li class="active">
                    <a href="#">1</a>
                </li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">后一页</a></li>
            </ul>
        </div>
    </div>
    <!-- 快速跳转题目 -->
    <form class="well form-horizontal pull-right">
        <input type="text" class="span2" placeholder="Problem ID...">
        <button type="submit" class="btn btn-primary">GO!</button>
    </form>
</div>

</body>
</html>