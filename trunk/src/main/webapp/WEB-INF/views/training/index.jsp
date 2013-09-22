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
    <script src="<s:url value="/scripts/cdoj/cdoj.training.index.js"/>"></script>
    <title>Training</title>
</head>
<body>

<div class="subnav">
    <ul id="TabMenu" class="nav nav-tabs">
        <li class="active">
            <a href="#tab-team-rating" data-toggle="tab">Team rating</a>
        </li>
        <li>
            <a href="#tab-personal-rating" data-toggle="tab">Personal rating</a>
        </li>
        <li>
            <a href="#tab-trainingContest-list" data-toggle="tab">Contest list</a>
        </li>
        <li>
            <a href="#tab-member-register" data-toggle="tab">Register</a>
        </li>
    </ul>
</div>

<div id="ratingContent" class="subnav-content">
    <div id="TabContent" class="tab-content">
        <div class="tab-pane fade active in" id="tab-team-rating">
            <div class="row">
                <div class="span12">
                    <div id="teamChart">
                    </div>
                </div>
                <div class="span12">
                    <table id="teamListTable" class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 30px;">#</th>
                            <th>Team name</th>
                            <th>Member</th>
                            <th style="width: 80px;">Rating</th>
                            <th style="width: 70px;">Volatility</th>
                            <th style="width: 40px;">Comp</th>
                        </tr>
                        </thead>
                        <tbody id="teamList">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="tab-personal-rating">
            <div class="row">
                <div class="span12">
                    <div id="personalChart">
                    </div>
                </div>
                <div class="span12">
                    <table id="personalListTable" class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 30px;">#</th>
                            <th>Team name</th>
                            <th>Member</th>
                            <th style="width: 80px;">Rating</th>
                            <th style="width: 70px;">Volatility</th>
                            <th style="width: 40px;">Comp</th>
                        </tr>
                        </thead>
                        <tbody id="personalList">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="tab-trainingContest-list">
            <div class="row">
                <div class="span12">
                    <table id="trainingContestListTable" class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 30px;">#</th>
                            <th>Title</th>
                            <th style="width: 80px;">Type</th>
                        </tr>
                        </thead>
                        <tbody id="trainingContestList">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="tab-member-register">
            <div class="row" id="trainingUserRegister">
                <div class="span12">
                    <s:if test="currentUser == null">
                        <h1>Please login first.</h1>
                    </s:if>
                    <s:else>
                        <form class="form-horizontal">
                            <fieldset>

                                <div class="control-group">
                                    <label class="control-label" for="account">Account</label>
                                    <div class="controls">
                                        <input type="text" maxlength="24" value="<s:property value="currentUser.userName"/>" id="account" class="span4" readonly="true">
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="trainingUserDTO_name">Name</label>
                                    <div class="controls">
                                        <input type="text" name="trainingUserDTO.name" maxlength="24" value="" id="trainingUserDTO_name" class="span4"/>
                                        <p class="help-block">组队赛名字需以UESTC_开头，其它学校请以自己学校名字开头，参考UESTC_HeavensFeel</p>
                                        <p class="help-block">个人赛请用自己的真实姓名</p>
                                        <p class="help-block">注意今后除了特殊通知的比赛，别的比赛中自己注册的用户的<strong>昵称</strong>均要和你注册的名称相同</p>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="trainingUserDTO_member">Member</label>
                                    <div class="controls">
                                        <input type="text" name="trainingUserDTO.member" maxlength="30" value="" id="trainingUserDTO_member" class="span4"/>
                                        <p class="help-block">如果是个人排名只需填入你的姓名，如果是组队赛请填写队伍三人的姓名。</p>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label">Register as</label>
                                    <div class="controls">

                                        <label for="trainingUserDTO.type-0" class="radio inline">
                                            <input type="radio" name="trainingUserDTO.type" id="trainingUserDTO.type-0" value="0" checked="">
                                            Personal
                                        </label>

                                        <label for="trainingUserDTO.type-1" class="radio inline">
                                            <input type="radio" name="trainingUserDTO.type" id="trainingUserDTO.type-1" value="1">
                                            Team
                                        </label>

                                    </div>
                                </div>

                                <div class="form-actions">
                                    <button type="submit" id="registerTrainingUserButton" class="btn btn-primary">Register</button>
                                </div>
                            </fieldset>
                        </form>
                    </s:else>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>