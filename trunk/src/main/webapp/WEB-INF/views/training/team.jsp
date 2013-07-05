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
    <script src="http://d3js.org/d3.v3.js"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.training.team.js"/>"></script>
    <title>Team information</title>
</head>
<body>

<div class="subnav">
    <ul id="TabMenu" class="nav nav-tabs">
        <li class="active">
            <a href="#tab-team-overview" data-toggle="tab">Overview</a>
        </li>
        <li>
            <a href="#tab-team-history" data-toggle="tab">History</a>
        </li>
    </ul>
</div>

<div id="ratingContent" class="subnav-content">
    <div id="TabContent" class="tab-content">
        <div class="tab-pane fade active in" id="tab-team-overview">
            <div class="row">
                <div class="span12">
                    <div id="ratingChart">
                    </div>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="tab-team-history">
            <div class="row">
                <div class="span12">
                    <table id="teamHistory" class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 30px;">#</th>
                            <th>Contest</th>
                            <th style="width: 90px;">Rating</th>
                            <th style="width: 80px;">Volatility</th>
                        </tr>
                        </thead>
                        <tbody id="teamHistoryList">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>