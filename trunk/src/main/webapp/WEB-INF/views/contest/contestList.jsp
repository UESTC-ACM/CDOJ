<%--
 Contest list page

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Contest</title>
</head>
<body>
  <div id="contest-list">
    <div class="row" id="mzry1992-header">
      <div class="col-md-12">
        <div id="page-info">
        </div>
        <div id="search-group">
          <input type="text" name="search-keyword" maxlength="24"
            value="" id="search-keyword" class="pull-left form-control" />
          <button id="search" class="btn btn-success">
            <i class="fa fa-search"></i>
          </button>

          <a href="#" id="advanced"><i
            class="fa fa-caret-square-o-down"></i></a>
          <div id="condition">
            <form class="form">
              <fieldset>
                <legend>Contest Id</legend>
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="startId">Form</label> <input
                        type="text" name="startId" maxlength="6"
                        value="" id="startId"
                        class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="endId">To</label> <input type="text"
                        name="endId" maxlength="6" value="" id="endId"
                        class="form-control input-sm" />
                    </div>
                  </div>
                </div>
              </fieldset>

              <fieldset>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label for="title">Title</label> <input
                        type="text" name="title" maxlength="100"
                        value="" id="title"
                        class="form-control input-sm" />
                    </div>
                  </div>
                </div>
              </fieldset>
              <c:if
                test="${sessionScope.currentUser != null && sessionScope.currentUser.type == 1}">
                <fieldset>
                  <legend>Is Visible</legend>
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="radio-inline"> <input
                          type="radio" name="isVisible" value="all"
                          checked="" /> All
                        </label> <label class="radio-inline"> <input
                          type="radio" name="isVisible" value="true" />
                          Yes
                        </label> <label class="radio-inline"> <input
                          type="radio" name="isVisible" value="false" />
                          No
                        </label>
                      </div>
                    </div>
                  </div>
                </fieldset>
              </c:if>
              <p class="pull-right">
                <button type="submit" class="btn btn-primary btn-sm"
                  id="search-button">Search</button>
                <button type="button" class="btn btn-danger btn-sm"
                  id="reset-button">Reset</button>
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>

    <div class="row" id="mzry1992-container">
      <c:if
        test="${sessionScope.currentUser != null && sessionScope.currentUser.type == 1}">
        <div class="col-md-12" id="contest-admin-operation">
          <div class="panel panel-danger">
            <div class="panel-body">
              <a href="/contest/editor/new" class="btn btn-success">
                <i class="fa fa-plus"></i>Add new contest
              </a>
            </div>
          </div>
        </div>
      </c:if>
      <div id="list-container"></div>
    </div>
  </div>
</body>
</html>