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
    <script src="<s:url value="/scripts/marked.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.util.markdown.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.util.submit.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.contest.contestShow.js"/>"></script>
    <script src="<s:url value="/plugins/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"/>"></script>
    <title>Contest</title>
</head>
<body>

<div id="contestStatus hero-unit" class="currentContestId" value="<s:property value="targetContest.contestId"/>">
    <h1 id="contestRunningState" value="<s:property value="targetContest.status"/>">
        <s:if test="targetContest.status == 'Pending'">
            Pending
        </s:if>
        <s:elseif test="targetContest.status == 'Ended'">
            Ended
        </s:elseif>
        <s:else>
            Running
        <span class="pull-right" id="timeLeft"
              value="<s:property value="targetContest.timeLeft"/>"
              totTime="<s:property value="targetContest.length"/>"
              type="milliseconds"
              timeStyle="length">
        </span>
        </s:else>
    </h1>
</div>

<s:if test="targetContest.status == 'Ended'">
    <div class="progress progress-success progress-striped">
        <div class="bar" style="width: 100%;"></div>
    </div>
</s:if>
<s:elseif test="targetContest.status == 'Running'">
    <div id="timeLeftProgressF" class="progress progress-striped active">
        <div class="bar" id="timeLeftProgress" style="width: 0;"></div>
    </div>
</s:elseif>

<div class="subnav">
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
                            <s:property value="#problem.order"/> - ${problem.title}
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
</div>

<div id="contestContent" class="subnav-content">
<div id="TabContent" class="tab-content">
<div class="tab-pane fade active in" id="tab-contest-summary">

    <div class="row span9 offset1">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th style="width: 30px;" class="orderButton" field="id">Id</th>
                <th class="orderButton" field="title">Title</th>
                <th style="width: 140px;" class="orderButton" field="solved">Status</th>
            </tr>
            </thead>
            <tbody id="contestSummary">
            <s:iterator value="contestProblems" id="problem">
                <tr>
                    <td><s:property value="#problem.order"/></td>
                    <td>
                        <s:if test="#problem.isSpj == true">
                            <span class="label label-important tags pull-right">SPJ</span>
                        </s:if>
                        <a class="pull-left problemHref" href="#" target="<s:property value="#problem.order"/>">
                                ${problem.title}
                        </a>
                    </td>

                    <td class="problemSummaryInfo" value="<s:property value="#problem.order"/>">
                        <span class="problemSolved"><s:property value="#problem.solved"/></span> /
                        <span class="problemTried"><s:property value="#problem.tried"/></span>
                    </td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
    </div>
</div>

<s:iterator value="contestProblems" id="problem">
    <div class="tab-pane fade" id="tab-contest-problem-<s:property value="#problem.order"/>">
        <!--TODO add passed/tried flag before problem title-->
        <div class="row problem">
            <div class="span12" id="problem_title">
                <h1 class="pull-left"><s:property value="#problem.order"/> - ${problem.title}</h1>
                <s:if test="targetProblem.isSpj == true">
                    <span class="label label-important tags" style="margin: 12px 0 0 8px;">SPJ</span>
                </s:if>
            </div>

            <div class="span12">
                <dl class="dl-horizontal">
                    <dt>Time limit</dt>
                    <dd><span>${problem.javaTimeLimit} / ${problem.timeLimit} ms (Java / others)</span></dd>

                    <dt>Memory limit</dt>
                    <dd><span>${problem.javaMemoryLimit} / ${problem.memoryLimit} kb (Java / others)</span></dd>

                    <dt>Total accepted</dt>
                    <dd><span>${problem.solved}</span></dd>

                    <dt>Total submissions</dt>
                    <dd><span>${problem.tried}</span></dd>

                </dl>
            </div>

            <div class="span12" id="problem_description" type="markdown">
                <textarea>${problem.description}</textarea>
            </div>
            <div class="span12">
                <h2>Input</h2>

                <div id="problem_input" type="markdown">
                    <textarea>${problem.input}</textarea>
                </div>
            </div>
            <div class="span12">
                <h2>Output</h2>

                <div id="problem_output" type="markdown">
                    <textarea>${problem.output}</textarea>
                </div>
            </div>
            <div class="span12">
                <h2>Sample input and output</h2>
                <table class="table table-sample table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>Sample Input</th>
                        <th>Sample Output</th>
                    </tr>
                    </thead>
                    <tbody class="font-code">
                    <tr>
                        <td>
                            <div class="sample" type="no-prettify">
                                <cdoj:format value="${problem.sampleInput}"/>
                            </div>
                        </td>
                        <td>
                            <div class="sample" type="no-prettify">
                                <cdoj:format value="${problem.sampleOutput}"/>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <s:if test="#problem.hint != ''">
                <div class="span12">
                    <h2>Hint</h2>

                    <div class="" id="problem_hint" type="markdown">
                        <textarea>${problem.hint}</textarea>
                    </div>
                </div>
            </s:if>
            <s:if test="#problem.source != ''">
                <div class="span12">
                    <h2>Source</h2>

                    <div class="" id="problem_source">
                            ${problem.source}
                    </div>
                </div>
            </s:if>
        </div>
    </div>
</s:iterator>

<div class="tab-pane fade" id="tab-contest-submit">
    <form class="form-horizontal">
        <textarea class="contest-submit-area" id="codeContent"></textarea>
    </form>

    <div class="contest-submit-action">
        <div id="language-selector" class="pull-left" style="margin-right: 20px;">
            <div class="btn-group" data-toggle="buttons-radio" id="languageSelector">
                <s:iterator value="global.languageList">
                    <button class="btn btn-info <s:if test="languageId == 2">active</s:if>"
                            value="${languageId}">${name}</button>
                </s:iterator>
            </div>
        </div>
        <div id="problem-selector" class="pull-left">
            <select id="problemId" class="span4">
                <s:iterator value="contestProblems" id="problem" status="status">
                    <option value="<s:property value="#problem.problemId"/>">
                        <s:property value="#problem.order"/> - ${problem.title}
                    </option>
                </s:iterator>
            </select>
        </div>

        <a href="#" id="submitCode" class="pull-right btn btn-primary">Submit</a>
    </div>
</div>

<div class="tab-pane fade" id="tab-contest-clarification-request">
</div>

<div class="tab-pane fade" id="tab-contest-clarification-view">
</div>

<div class="tab-pane fade" id="tab-contest-status">
    <div id="pageInfo">
    </div>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th style="width: 60px;" class="orderButton" field="id">Id</th>
            <th>User</th>
            <th style="width: 60px;" class="orderButton" field="problemByProblemId">Problem</th>
            <th style="width: 260px;" class="orderButton" field="result">Judge's Response</th>
            <th style="width: 100px;" class="orderButton" field="length">Length</th>
            <th style="width: 70px;" class="orderButton" field="timeCost">Time</th>
            <th style="width: 80px;" class="orderButton" field="memoryCost">Memory</th>
            <th style="width: 140px;" class="orderButton" field="time">Submit Time</th>
        </tr>
        </thead>
        <tbody id="statusList">
        </tbody>
    </table>
</div>

<div class="tab-pane fade" id="tab-contest-rank">

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
                <th class="orderButton" field="id">Rk</th>
                <th style="width: 200px;">User</th>
                <th class="orderButton" field="problemByProblemId">Slv.</th>
                <th class="orderButton" field="result">Time</th>

                <s:iterator value="contestProblems" id="problem">
                    <th class="problemSummaryInfo" value="<s:property value="#problem.order"/>">
                        <a class="problemHref" href="#" target="<s:property value="#problem.order"/>">
                            <s:property value="#problem.order"/>
                        </a>
                        <br/>
                        <span class="problemSolved"><s:property value="#problem.solved"/></span> /
                        <span class="problemTried"><s:property value="#problem.tried"/></span>
                    </th>
                </s:iterator>
            </tr>
            </thead>
            <tbody id="rankList">
            </tbody>
        </table>
    </div>

</div>

</div>
</div>

<page:applyDecorator name="body" page="/WEB-INF/views/status/modal.jsp"/>
</body>
</html>