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
  Created by IntelliJ IDEA.
  User: mzry1992
  Date: 13-7-11
  Time: 下午9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="cdoj" uri="/WEB-INF/cdoj.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${targetTrainingContest.title}</title>
</head>
<body>

<div class="row">
    <div class="span12">
        <h1>${targetTrainingContest.title}</h1>
    </div>
    <div class="span12">

        <div class="pull-right span6" style="clear: both;">
            <table class="table table-bordered">
                <tr>
                    <td class="firstac">First accept</td>
                    <td class="ac">Accept</td>
                    <td class="fail">Failed</td>
                    <td class="pending">Pending</td>
                </tr>
            </table>
        </div>

        <div style="clear: both;">
        </div>

        <div>
            <table class="table table-bordered">
                <thead id="rankListHead">
                <tr>
                    <th style="width: 20px;">Rk</th>
                    <th style="width: 150px;">User</th>
                    <th style="width: 30px;">Slv.</th>
                    <th style="width: 40px;">Time</th>

                    <s:iterator value="targetTrainingContestRankList.problemSummary" id="problem">
                        <th class="problemSummaryInfo" value="<s:property value="#problem.order"/>">
                            <s:property value="#problem.order"/>
                            <br/>
                            <span class="problemSolved"><s:property value="#problem.solved"/></span> /
                            <span class="problemTried"><s:property value="#problem.tried"/></span>
                        </th>
                    </s:iterator>
                </tr>
                </thead>
                <tbody id="rankList">
                <s:iterator value="targetTrainingContestRankList.trainingUserRankSummaryList" id="summary">
                    <tr>
                        <td><s:property value="#summary.rank"/></td>
                        <td><a href="/training/user/show/<s:property value="#summary.userId"/>"><s:property
                                value="#summary.nickName"/></a></td>
                        <td><s:property value="#summary.solved"/></td>
                        <td><s:property value="#summary.penalty"/></td>
                        <s:iterator value="#summary.trainingProblemSummaryInfoList" id="problem">
                            <s:if test="#problem.solved == true">
                                <s:if test="#problem.firstSolved == true">
                                    <td class="firstac"><s:property value="#problem.tried"/>/<s:property
                                            value="#problem.solutionTime"/></td>
                                </s:if>
                                <s:else>
                                    <td class="ac"><s:property value="#problem.tried"/>/<s:property
                                            value="#problem.solutionTime"/></td>
                                </s:else>
                            </s:if>
                            <s:else>
                                <s:if test="#problem.tried > 0">
                                    <td class="fail"><s:property value="#problem.tried"/></td>
                                </s:if>
                                <s:else>
                                    <td></td>
                                </s:else>
                            </s:else>
                        </s:iterator>
                    </tr>
                </s:iterator>
                </tbody>
            </table>
        </div>

    </div>
</div>
</body>
</html>