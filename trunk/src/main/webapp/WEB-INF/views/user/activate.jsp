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
<div class="row">
  <div class="col-md-12">
    <h1>Reset your password</h1>
  </div>
  <div class="col-md-12">
    <form class="form-horizontal" id="cdoj-activation-form">
      <fieldset>
        <div class="form-group">
          <label class="control-label col-sm-4 col-md-3"
                 for="userName">User Name</label>

          <div class="col-sm-8 col-md-6">
            <input type="text" name="userName" maxlength="24"
                   value="<c:out value="${userName}"/>" id="userName"
                   class="form-control input-sm" readonly="readonly"/>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-sm-4 col-md-3"
                 for="serialKey">Serial key</label>

          <div class="col-sm-8 col-md-6">
            <input type="text" name="serialKey" maxlength="48"
                   value="<c:out value="${serialKey}"/>" id="serialKey"
                   class="form-control input-sm" readonly="readonly">
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-sm-4 col-md-3"
                 for="password">Password</label>

          <div class="col-sm-8 col-md-6">
            <input type="password" name="password" maxlength="20"
                   id="password" class="form-control input-sm"/>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-sm-4 col-md-3"
                 for="passwordRepeat">Repeat your password</label>

          <div class="col-sm-8 col-md-6">
            <input type="password" name="passwordRepeat"
                   maxlength="20" id="passwordRepeat"
                   class="form-control input-sm"/>
          </div>
        </div>
        <div class="col-sm-8 col-md-6 col-sm-offset-4 col-md-offset-3">
          <button type="submit" id="submit-button" class="btn btn-primary pull-right">Submit
          </button>
        </div>
      </fieldset>
    </form>
  </div>
</div>
</body>
</html>