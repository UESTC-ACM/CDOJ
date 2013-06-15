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
 Problem statement

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
    <script src="<s:url value="/scripts/cdoj/cdoj.problem.problemShow.js"/>"></script>
    <script src="<s:url value="/plugins/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"/>"></script>
    <title>${targetProblem.title}</title>
</head>
<body>

<div class="subnav">
    <ul id="TabMenu" class="nav nav-tabs">
        <li class="active">
            <a href="#tab-problem-show" data-toggle="tab">Problem</a>
        </li>
        <li>
            <a href="#tab-problem-submit" data-toggle="tab">Submit</a>
        </li>
        <li>
            <a href="#tab-problem-status" data-toggle="tab">Status</a>
        </li>
        <li class="disabled">
            <a href="#tab-problem-discus" data-toggle="tab">Discus</a>
        </li>
    </ul>
</div>

<div id="problemContent" class="subnav-content">
    <div id="TabContent" class="tab-content">
        <div class="tab-pane fade active in" id="tab-problem-show">
            <div class="row" id="problem">
                <div class="span12" id="problem_title" value="${targetProblem.problemId}">
                    <h1 class="pull-left">${targetProblem.title}</h1>
                    <s:if test="targetProblem.isSpj == true">
                        <span class="label label-important tags" style="margin: 12px 0 0 8px;">SPJ</span>
                    </s:if>

                    <s:if test="currentUser.type == 1">
                        <div class="pull-right" style="margin: 10px 0;">
                            <s:a action="editor/%{targetProblem.problemId}" namespace="/admin/problem">
                                <i class="icon-pencil"></i>
                                Edit problem statement
                            </s:a>
                            <br/>
                            <s:a action="data/%{targetProblem.problemId}" namespace="/admin/problem">
                                <i class="icon-cog"></i>
                                Edit problem data
                            </s:a>
                        </div>
                    </s:if>

                </div>

                <div class="span12">
                    <dl class="dl-horizontal">
                        <dt>Time limit</dt>
                        <dd><span>${targetProblem.javaTimeLimit} / ${targetProblem.timeLimit} ms (Java / others)</span>
                        </dd>

                        <dt>Memory limit</dt>
                        <dd>
                            <span>${targetProblem.javaMemoryLimit} / ${targetProblem.memoryLimit} kb (Java / others)</span>
                        </dd>

                        <dt>Total accepted</dt>
                        <dd><span>${targetProblem.solved}</span></dd>

                        <dt>Total submissions</dt>
                        <dd><span>${targetProblem.tried}</span></dd>

                    </dl>
                </div>

                <div class="span12" id="problem_description" type="markdown">
                    <textarea>${targetProblem.description}</textarea>
                </div>
                <div class="span12">
                    <h2>Input</h2>

                    <div id="problem_input" type="markdown">
                        <textarea>${targetProblem.input}</textarea>
                    </div>
                </div>
                <div class="span12">
                    <h2>Output</h2>

                    <div id="problem_output" type="markdown">
                        <textarea>${targetProblem.output}</textarea>
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
                                    <cdoj:format value="${targetProblem.sampleInput}"/>
                                </div>
                            </td>
                            <td>
                                <div class="sample" type="no-prettify">
                                    <cdoj:format value="${targetProblem.sampleOutput}"/>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <s:if test="targetProblem.hint != ''">
                    <div class="span12">
                        <h2>Hint</h2>

                        <div class="" id="problem_hint" type="markdown">
                            <textarea>${targetProblem.hint}</textarea>
                        </div>
                    </div>
                </s:if>
                <s:if test="targetProblem.source != ''">
                    <div class="span12">
                        <h2>Source</h2>

                        <div class="" id="problem_source">
                                ${targetProblem.source}
                        </div>
                    </div>
                </s:if>

            </div>
        </div>

        <div class="tab-pane fade" id="tab-problem-submit">
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
                <a href="#" id="submitCode" class="pull-right btn btn-primary">Submit</a>
            </div>
        </div>

        <div class="tab-pane fade" id="tab-problem-status">
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
        <div class="tab-pane fade" id="tab-problem-discus">
        </div>
    </div>
</div>

<page:applyDecorator name="body" page="/WEB-INF/views/status/modal.jsp"/>

</body>
</html>