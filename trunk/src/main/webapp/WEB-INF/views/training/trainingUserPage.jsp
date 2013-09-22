<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <!--[if lte IE 8]>
    <script src="<s:url value="/scripts/r2d3.js"/>"></script>
    <![endif]-->
    <!--[if gte IE 9]><!-->
    <script src="<s:url value="/scripts/d3.js"/>"></script>
    <!--<![endif]-->

    <script src="<s:url value="/scripts/cdoj/cdoj.training.rating.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.chart.rating.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.training.user.js"/>"></script>
    <title>${targetTrainingUasr.name} history</title>
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
                    <div id="name" value="${targetTrainingUser.trainingUserId}">
                        <h1 style="text-align: center;">${targetTrainingUser.name}</h1>
                    </div>
                    <div class="row">
                        <div class="span3">
                            <table class="table table-condensed" id="trainingUserInfo">
                                <thead>
                                <tr>
                                    <td><h4 style="color: #ffffff;">Rating</h4></td>
                                    <td id="ratingSpan" value="${targetTrainingUser.rating}"></td>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td colspan="2">
                                        <span>${targetTrainingUser.userName}</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <span>${targetTrainingUser.member}</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Volatility</td>
                                    <td id="volatilitySpan" value="${targetTrainingUser.volatility}"></td>
                                </tr>
                                <tr>
                                    <td>Type</td>
                                    <td><span>${targetTrainingUser.typeName}</span></td>
                                </tr>
                                <tr>
                                    <td>Competitions</td>
                                    <td><a href="#" id="historyHref">${targetTrainingUser.competitions}</a></td>
                                </tr>
                                </tbody>
                            </table>
                            <div id="statusChart">
                            </div>
                        </div>
                        <div class="span9">
                            <div id="ratingChart">
                            </div>
                        </div>
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