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
 Summer training home page

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Summer training</title>
</head>
<body>

<div class="subnav">
    <ul id="TabMenu" class="nav nav-tabs">
        <li class="active">
            <a href="#tab-team-rating" data-toggle="tab">Team rating</a>
        </li>
        <li>
            <a href="#tab-personal-rating" data-toggle="tab">Personal rating</a>
        </li>
    </ul>
</div>

<div id="ratingContent" class="subnav-content">
    <div id="TabContent" class="tab-content">
        <div class="tab-pane fade active in" id="tab-team-rating">
            <div class="row">
                <div class="span12">
                    <table id="teamListTable" class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 30px;">#</th>
                            <th>Team name</th>
                            <th>Rating</th>
                            <th>Volatility</th>
                            <th>Competitions</th>
                        </tr>
                        </thead>
                        <tbody id="teamList">
                        <tr><td>1</td><td><s:a namespace="/training" action="team/1">lyhypacm</s:a></td><td class="rating-red">2500</td><td>322</td><td>1</td></tr>
                        <tr><td>3</td><td>lyhypacm</td><td class="rating-yellow">1800</td><td>322</td><td>1</td></tr>
                        <tr><td>4</td><td>lyhypacm</td><td class="rating-blue">1300</td><td>322</td><td>1</td></tr>
                        <tr><td>5</td><td>lyhypacm</td><td class="rating-green">1100</td><td>322</td><td>1</td></tr>
                        <tr><td>6</td><td>lyhypacm</td><td class="rating-gray">800</td><td>322</td><td>1</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="tab-personal-rating">
            <div class="row">
                <div class="span12">
                    <table id="personalListTable" class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 30px;">#</th>
                            <th>Team name</th>
                            <th>Rating</th>
                            <th>Volatility</th>
                            <th>Competitions</th>
                        </tr>
                        </thead>
                        <tbody id="personalList">
                        <tr><td>1</td><td>lyhypacm</td><td class="rating-red">2500</td><td>322</td><td>1</td></tr>
                        <tr><td>3</td><td>lyhypacm</td><td class="rating-yellow">1800</td><td>322</td><td>1</td></tr>
                        <tr><td>4</td><td>lyhypacm</td><td class="rating-blue">1300</td><td>322</td><td>1</td></tr>
                        <tr><td>5</td><td>lyhypacm</td><td class="rating-green">1100</td><td>322</td><td>1</td></tr>
                        <tr><td>6</td><td>lyhypacm</td><td class="rating-gray">800</td><td>322</td><td>1</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>