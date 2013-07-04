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
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="tab-team-history">
            <div class="row">
                <div class="span12">
                    <table id="personalListTable" class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 30px;">#</th>
                            <th>Contest</th>
                            <th style="width: 90px;">Rating</th>
                            <th style="width: 80px;">Volatility</th>
                        </tr>
                        </thead>
                        <tbody id="personalList">
                        <tr>
                            <td>1</td>
                            <td>World final 2013</td>
                            <td style="text-align: right;">
                                <span class="rating-red label-rating">2500</span>
                                <span class="label label-success pull-right label-diff">+1200</span>
                            </td>
                            <td>322(+0)</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>World final 2012</td>
                            <td style="text-align: right;">
                                <span class="rating-blue label-rating">1300</span>
                                <span class="label label-important pull-right label-diff">-500</span>
                            </td>
                            <td>322(+0)</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>World final 2011</td>
                            <td style="text-align: right;">
                                <span class="label label-success pull-right label-diff">+700</span>
                                <span class="rating-yellow label-rating">1800</span>
                            </td>
                            <td>322(+0)</td>
                        </tr>
                        <tr>
                            <td>4</td>
                            <td>World final 2010</td>
                            <td style="text-align: right;">
                                <span class="rating-green label-rating">1100</span>
                                <span class="label label-success pull-right label-diff">+300</span>
                            </td>
                            <td>322(+0)</td>
                        </tr>
                        <tr>
                            <td>5</td>
                            <td>World final 2009</td>
                            <td style="text-align: right;">
                                <span class="rating-green label-rating">800</span>
                                <span class="label label-info pull-right label-diff">INIT</span>
                            </td>
                            <td>322(+322)</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>