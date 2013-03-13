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
 Problem statement

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<s:url value="/scripts/marked.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.util.markdown.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj/cdoj.problemShow.js"/>"></script>
    <title>${targetProblem.title}</title>
</head>
<body>
<div class="row" id="problem">
    <div class="span12" id="problem_title">
        <h1 class="pull-left">${targetProblem.title}</h1>
        <span class="label label-important tags" style="margin: 12px 0 0 8px;">SPJ</span>

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
            <dd><span>${targetProblem.javaTimeLimit} / ${targetProblem.timeLimit} ms (Java / others)</span></dd>

            <dt>Memory limit</dt>
            <dd><span>${targetProblem.javaMemoryLimit} / ${targetProblem.memoryLimit} kb (Java / others)</span></dd>

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
</body>
</html>