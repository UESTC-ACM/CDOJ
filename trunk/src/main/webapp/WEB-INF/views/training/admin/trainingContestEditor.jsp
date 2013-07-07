<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="cdoj" uri="/WEB-INF/cdoj.tld" %>
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
 Summer training home page

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head
    <page:applyDecorator name="head" page="/WEB-INF/views/common/fileUploaderHeader.jsp"/>
    <script src="<s:url value="/scripts/cdoj/cdoj.admin.training.contestAdmin.js"/>"></script>
    <title>Summer training contest</title>
</head>
<body>
<div class="row" id="contestEditor">
    <div class="span10">
        <form class="form-horizontal">
            <fieldset>
                <h3>
                    Edit summery training contest <span id="trainingContestId">1</span>
                </h3>

                <div class="row">
                    <div class="span10">
                        <div class="control-group">
                            <label class="control-label">Title</label>

                            <div class="controls">
                                <input type="text"
                                       name="contestDTO.title"
                                       maxlength="50"
                                       value="${targetContest.title}"
                                       id="contestDTO_title"
                                       class="span6"
                                       placeholder="Enter title here">
                            </div>
                        </div>
                    </div>

                    <div class="span10">
                        <div class="control-group">
                            <label class="control-label">Is personal</label>
                            <div class="controls">
                                <label for="contestDTO.type-false" class="radio inline">
                                    <input type="radio"
                                           name="contestDTO.type"
                                           id="contestDTO.type-false"
                                           value="false"
                                           checked="">
                                    No
                                </label>

                                <label for="contestDTO.type-true" class="radio inline">
                                    <input type="radio"
                                           name="contestDTO.type"
                                           id="contestDTO.type-true"
                                           value="false">
                                    Yes
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">Upload rank file</label>
                        <div class="controls">
                            <div id="fileUploader"></div>
                            <span id="fileUploaderAttention" class="help-inline">Please use xls.</span>
                        </div>
                    </div>

                    <div class="span10">
                        <div class="control-group">
                            <label class="control-label">Rank list</label>

                            <div class="controls">
                                <div class="row">
                                    <div class="span6">
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                            <tr>
                                                <th style="width: 30px;">Rank</th>
                                                <th>User</th>
                                                <th style="width: 60px;">Solve</th>
                                                <th style="width: 100px;">Penalty/Score</th>
                                            </tr>
                                            </thead>
                                            <tbody id="rankList">
                                            <tr>
                                                <td>1</td>
                                                <td>UESTC_Gannicus</td>
                                                <td>8</td>
                                                <td>24:10:52</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="form-actions">
                    <s:submit name="submit"
                              cssClass="btn btn-primary"
                              value="Submit"
                              theme="bootstrap"/>
                </div>
            </fieldset>
        </form>
    </div>
</div>

</body>
</html>