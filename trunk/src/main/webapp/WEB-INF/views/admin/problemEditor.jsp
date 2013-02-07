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
  Date: 13-2-3
  Time: 下午5:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<s:url value="/plugins/epiceditor/js/epiceditor.js"/>"></script>
    <script src="<s:url value="/scripts/cdoj.admin.problemEditor.js"/>"></script>
    <title>Problem</title>
</head>
<body>
<div class="row" id="problemEditor">

    <div class="span10">
        <h3 id="editorFlag" value="${editorFlag}">
            <s:if test='editorFlag=="new"'>
                Add new problem
            </s:if>
            <s:else>
                Edit problem <span id="problemId">${targetProblem.problemId}</span>
            </s:else>
        </h3>
        <div class="control-group">
            <div class="controls">
                <input type="text"
                       name="problemDTO.title"
                       maxlength="50"
                       value="${targetProblem.title}"
                       id="problemDTO_title"
                       class="span10"
                       placeholder="Enter title here">
            </div>
        </div>
    </div>

    <div class="span10">
        <div id="problemDTO_description" class="textarea-content textarea-large">${targetProblem.description}</div>
    </div>

    <div class="span10">
        <h2>Input</h2>
        <div id="problemDTO_input" class="textarea-content textarea-mini">${targetProblem.input}</div>
    </div>

    <div class="span10">
        <h2>Output</h2>
        <div id="problemDTO_output" class="textarea-content textarea-mini">${targetProblem.output}</div>
    </div>

    <div class="span10">
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
                    <div id="problemDTO_sampleInput" class="textarea-content textarea-big">${targetProblem.sampleInput}</div>
                </td>
                <td>
                    <div id="problemDTO_sampleOutput" class="textarea-content textarea-big">${targetProblem.sampleOutput}</div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="span10">
        <h2>Hint</h2>
        <div id="problemDTO_hint" class="textarea-content textarea-mini">${targetProblem.hint}</div>
    </div>

    <div class="span10">
        <h2>Source</h2>
        <div class="control-group ">
            <div class="controls">
                <input type="text"
                       name="problemDTO.source"
                       maxlength="100"
                       value="${targetProblem.source}"
                       id="problemDTO_source"
                       class="span10"
                       placeholder="Enter source here">
            </div>
        </div>
    </div>

    <div class="span10">
        <input type="submit" id="submit" name="submit" value="Submit" class="btn btn-primary">
    </div>
</div>

</body>
</html>