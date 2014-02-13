<%--
User list page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>User</title>
</head>
<body>
<div id="user-list"
     ng-controller="ListController"
     ng-init="condition={
        currentPage: null,
        startId: undefined,
        endId: undefined,
        userName: undefined,
        nickName: undefined,
        type: undefined,
        school: undefined,
        departmentId: undefined,
        orderFields: 'solved,tried,id',
        orderAsc: 'false,false,true'
     };
     requestUrl='/user/search';
     authenticationTypeList = [

     ];">
  <div class="row">
    <div class="col-md-12">
      <div ui-page-info
           page-info="pageInfo"
           condition="condition"
           id="page-info">
      </div>
      <div id="advance-search">
        <a href="#" id="advanced" data-toggle="dropdown"><i
            class="fa fa-caret-square-o-down"></i></a>
        <ul ui-dropdown-menu class="dropdown-menu cdoj-form-menu" role="menu"
            aria-labelledby="advance-menu">
          <li role="presentation" id="condition">
            <form class="form">
              <fieldset>
                <legend>User ID</legend>
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="startId">Form</label>
                      <input type="number"
                             ng-model="condition.startId"
                             min="1"
                             id="startId"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="endId">To</label>
                      <input type="number"
                             ng-model="condition.endId"
                             min="1"
                             id="endId"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                </div>
              </fieldset>
              <fieldset>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label for="userName">User name</label>
                      <input type="text"
                             ng-model="condition.userName"
                             maxlength="24"
                             id="userName"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <label for="nickName">Nick name</label>
                      <input type="text"
                             ng-model="condition.nickName"
                             maxlength="24"
                             id="nickName"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <label for="type">Type</label>
                      <select ng-model="condition.type"
                              ng-options="type.authenticationTypeId as type.description for type in $root.authenticationTypeList"
                              id="type"
                              ng-required="true"
                              class="form-control input-sm">
                        <option value="">All</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <label for="keyword">Keyword</label>
                      <input type="text"
                             ng-model="condition.keyword"
                             maxlength="100"
                             id="keyword"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <label for="school">School</label>
                      <input type="text"
                             ng-model="condition.school"
                             maxlength="100"
                             id="school"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <label for="type">Department</label>
                      <select ng-model="condition.departmentId"
                              ng-options="department.departmentId as department.name for department in $root.departmentList"
                              id="departmentId"
                              ng-required="true"
                              class="form-control input-sm">
                        <option value="">All</option>
                      </select>
                    </div>
                  </div>
                </div>
              </fieldset>
              <p class="pull-right">
                <button type="button" class="btn btn-danger btn-sm"
                        ng-click="reset()">Reset
                </button>
              </p>
            </form>
        </ul>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-lg-6" ng-repeat="user in list">
      <div class="panel panel-default">
        <div class="panel-body">
          <div class="media">
            <a class="pull-left" href="#">
              <img id="cdoj-users-avatar"
                   ui-avatar
                   email="user.email"/>
            </a>

            <div class="media-body">
              <h4 class="media-heading">
                <a href="/user/center/{{user.userName}}">
                  <span ng-bind="user.nickName"></span>
                  <small ng-bind="user.userName"></small>
                </a>
              </h4>
              <span>
                <i class="fa fa-map-marker"></i>
                <span ng-bind="user.school"></span>
              </span>
              <br/>
              <span>
                <a href="/status/list?userName={{user.userName}}">
                  <span ng-bind="user.solved"></span>/<span ng-bind="user.tried"></span>
                </a>
              </span>
              <ui-user-admin-span user="user"></ui-user-admin-span>
            </div>
          </div>
        </div>
        <div class="panel-footer"
             style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">
          Motto: <span ng-bind="user.motto"></span></div>
      </div>
    </div>
  </div>
</div>

<script type="text/ng-template" id="userAdminModalContent.html">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">&times;</button>
    <h4 class="modal-title">Edit</h4>
  </div>
  <div class="modal-body">
    <form class="form-horizontal">
      <fieldset>
        <div class="form-group">
          <label class="control-label col-sm-4 "
                 for="userName">User Name</label>
          <div class="col-sm-8">
            <input type="text"
                   ng-model="userEditDTO.userName"
                   maxlength="24"
                   id="userName"
                   ng-required="true"
                   ng-pattern="/^[a-zA-Z0-9_]{4,24}$/"
                   readonly
                   class="form-control input-sm"/>
            <ui-validate-info value="fieldInfo" for="userName"></ui-validate-info>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-sm-4 "
                 for="newPassword">New password</label>
          <div class="col-sm-8">
            <input type="password"
                   ng-model="userEditDTO.newPassword"
                   ng-required="false"
                   id="newPassword"
                   ng-minlength="6"
                   ng-maxlength="24"
                   class="form-control input-sm"/>
            <ui-validate-info value="fieldInfo" for="newPassword"></ui-validate-info>
          </div>
        </div>
        <div class="form-group ">
          <label class="control-label col-sm-4 "
                 for="newPasswordRepeat">Repeat new password</label>
          <div class="col-sm-8">
            <input type="password"
                   ng-model="userEditDTO.newPasswordRepeat"
                   ng-required="false"
                   id="newPasswordRepeat"
                   ng-minlength="6"
                   ng-maxlength="24"
                   equals="{{userEditDTO.newPassword}}"
                   class="form-control input-sm"/>
            <ui-validate-info value="fieldInfo" for="newPasswordRepeat"></ui-validate-info>
          </div>
        </div>
        <div class="form-group ">
          <label class="control-label col-sm-4 "
                 for="nickName">Nick name</label>
          <div class="col-sm-8">
            <input type="text"
                   ng-model="userEditDTO.nickName"
                   maxlength="20"
                   id="nickName"
                   ng-required="true"
                   ng-maxlength="20"
                   ng-minlength="2"
                   class="form-control input-sm"/>
            <ui-validate-info value="fieldInfo" for="nickName"></ui-validate-info>
          </div>
        </div>
        <div class="form-group ">
          <label class="control-label col-sm-4 " for="email">Email</label>
          <div class="col-sm-8">
            <input type="email"
                   ng-model="userEditDTO.email"
                   id="email"
                   ng-required="true"
                   class="form-control input-sm"/>
            <ui-validate-info value="fieldInfo" for="email"></ui-validate-info>
            <spanp class="help-block">
              Your email will be used for <a href="http://en.gravatar.com/">Gravatar</a>
              service.
            </spanp>
          </div>
        </div>
        <div class="form-group ">
          <label class="control-label col-sm-4 " for="school">School</label>
          <div class="col-sm-8">
            <input type="text"
                   ng-model="userEditDTO.school"
                   id="school"
                   ng-required="true"
                   ng-maxlength="100"
                   ng-minlength="1"
                   class="form-control input-sm"/>
            <ui-validate-info value="fieldInfo" for="school"></ui-validate-info>
          </div>
        </div>
        <div class="form-group ">
          <label class="control-label col-sm-4 "
                 for="departmentId">Department</label>
          <div class="col-sm-8">
            <select ng-model="userEditDTO.departmentId"
                    ng-options="department.departmentId as department.name for department in $root.departmentList"
                    id="departmentId"
                    ng-required="true"
                    class="form-control input-sm">
            </select>
            <ui-validate-info value="fieldInfo" for="departmentId"></ui-validate-info>
          </div>
        </div>
        <div class="form-group ">
          <label class="control-label col-sm-4 "
                 for="departmentId">Type</label>
          <div class="col-sm-8">
            <select ng-model="userEditDTO.type"
                    ng-options="type.authenticationTypeId as type.description for type in $root.authenticationTypeList"
                    id="type"
                    ng-required="true"
                    class="form-control input-sm">
            </select>
            <ui-validate-info value="fieldInfo" for="type"></ui-validate-info>
          </div>
        </div>
        <div class="form-group ">
          <label class="control-label col-sm-4 "
                 for="studentId">Student ID</label>
          <div class="col-sm-8">
            <input type="text"
                   ng-model="userEditDTO.studentId"
                   id="studentId"
                   ng-required="true"
                   ng-maxlength="20"
                   ng-minlength="1"
                   class="form-control input-sm"/>
            <ui-validate-info value="fieldInfo" for="studentId"></ui-validate-info>
          </div>
        </div>
        <div class="form-group ">
          <label class="control-label col-sm-4 " for="motto">Motto</label>
          <div class="col-sm-8">
            <textarea class="form-control"
                      rows="3"
                      ng-model="userEditDTO.motto"
                      id="motto"></textarea>
            <ui-validate-info value="fieldInfo" for="motto"></ui-validate-info>
          </div>
        </div>
      </fieldset>
    </form>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default"
            ng-click="close()">Close
    </button>
    <button type="button" class="btn btn-primary"
            ng-click="edit()">Edit
    </button>
  </div>
</script>
</body>
</html>
