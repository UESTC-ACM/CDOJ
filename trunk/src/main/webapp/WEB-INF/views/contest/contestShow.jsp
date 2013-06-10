<%--
  ~ cdoj, UESTC ACMICPC Online Judge
  ~
  ~ Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
  ~ mzry1992 <@link muziriyun@gmail.com>
  ~
  ~ This program is free software; you can redistribute it and/or
  ~ modify it under the terms of the GNU General Public License
  ~ as published by the Free Software Foundation; either version 2
  ~ of the License, or (at your option) any later version.
  ~
  ~ This program is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with this program; if not, write to the Free Software
  ~ Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  --%>

<%--
 Contest page

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
    <title>Contest</title>
</head>
<body>
<ul id="TabMenu" class="nav nav-tabs">
    <li class="active">
        <a href="#tab-contest-summary" data-toggle="tab">Summary</a>
    </li>
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Problems<b class="caret"></b></a>
        <ul class="dropdown-menu">
            <s:iterator value="contestProblems" id="problem">
                <li>
                    <a href="#tab-contest-problem-<s:property value="#problem.order"/>" data-toggle="tab">
                        <s:property value="#problem.order"/> - <s:property value="#problem.title"/>
                    </a>
                </li>
            </s:iterator>
        </ul>
    </li>
    <li>
        <a href="#tab-contest-submit" data-toggle="tab">Submit</a>
    </li>
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Clarification<b class="caret"></b></a>
        <ul class="dropdown-menu">
            <li class="disabled">
                <a href="#tab-contest-clarification-request" data-toggle="tab">Request Clarification</a>
            </li>
            <li class="disabled">
                <a href="#tab-contest-clarification-view" data-toggle="tab">View Clarifications</a>
            </li>
        </ul>
    </li>
    <li>
        <a href="#tab-contest-status" data-toggle="tab">Status</a>
    </li>
    <li>
        <a href="#tab-contest-rank" data-toggle="tab">Rank</a>
    </li>
</ul>


<div id="TabContent" class="tab-content">
    <div class="tab-pane fade active in" id="tab-contest-summary">

    </div>

    <s:iterator value="contestProblems" id="problem">
        <div class="tab-pane fade" id="tab-contest-problem-<s:property value="#problem.order"/>">
            <!--TODO add passed/tried flag before problem title-->
            <s:property value="#problem.order"/> - <s:property value="#problem.title"/>
        </div>
    </s:iterator>

    <div class="tab-pane fade" id="tab-contest-submit">
    </div>

    <div class="tab-pane fade" id="tab-contest-clarification-request">
    </div>

    <div class="tab-pane fade" id="tab-contest-clarification-view">
    </div>

    <div class="tab-pane fade" id="tab-contest-status">
    </div>

    <div class="tab-pane fade" id="tab-contest-rank">
    </div>

</div>

</body>
</html>