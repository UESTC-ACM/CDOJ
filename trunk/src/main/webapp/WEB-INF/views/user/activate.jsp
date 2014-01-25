<%--
 Account activate page.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Account activation</title>
</head>
<body>
<div class="row"
     ng-controller="PasswordResetController">
  <div class="col-md-12">
    <h1>Reset your password</h1>
  </div>
  <div class="col-md-12">
    <form class="form-horizontal">
      <fieldset>
        <div class="form-group">
          <label class="control-label col-sm-4 col-md-3"
                 for="userName">User Name</label>

          <div class="col-sm-8 col-md-6">
            <input type="text"
                   ng-model="userActivateDTO.userName"
                   maxlength="24"
                   value="<c:out value="${userName}"/>"
                   id="userName"
                   class="form-control input-sm"
                   readonly="readonly"/>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-sm-4 col-md-3"
                 for="serialKey">Serial key</label>

          <div class="col-sm-8 col-md-6">
            <input type="text"
                   ng-model="userActivateDTO.serialKey"
                   maxlength="48"
                   value="<c:out value="${serialKey}"/>"
                   id="serialKey"
                   class="form-control input-sm"
                   readonly="readonly">
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-sm-4 col-md-3"
                 for="password">Password</label>

          <div class="col-sm-8 col-md-6">
            <input type="password"
                   ng-model="userActivateDTO.password"
                   id="password"
                   ng-required="true"
                   class="form-control input-sm"/>
            <ui-validate-info value="fieldInfo" for="password"></ui-validate-info>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-sm-4 col-md-3"
                 for="passwordRepeat">Repeat your password</label>

          <div class="col-sm-8 col-md-6">
            <input type="password"
                   ng-model="userActivateDTO.passwordRepeat"
                   id="passwordRepeat"
                   ng-required="true"
                   equals="{{userActivateDTO.password}}"
                   class="form-control input-sm"/>
            <ui-validate-info value="fieldInfo" for="passwordRepeat"></ui-validate-info>
          </div>
        </div>
        <div class="col-sm-8 col-md-6 col-sm-offset-4 col-md-offset-3">
          <button type="submit"
                  class="btn btn-primary pull-right"
                  ng-click="submit()">Submit
          </button>
        </div>
      </fieldset>
    </form>
  </div>
</div>
</body>
</html>