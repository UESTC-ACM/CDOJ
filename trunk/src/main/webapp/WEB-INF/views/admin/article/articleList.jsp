<%--
 Admin problem list page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<script src="<c:url value="/scripts/cdoj/cdoj.admin.article.js"/>"></script>
<title>Article</title>
</head>
<body>

  <ul id="TabMenu" class="nav nav-pills">
    <li class="active"><a href="#tab-article-list"
      data-toggle="tab">Article list</a></li>
    <li><a href="#tab-article-search" data-toggle="tab">Search</a></li>
  </ul>

  <div id="TabContent" class="tab-content">
    <div class="tab-pane fade active in" id="tab-article-list">
      <div id="pageInfo"></div>

      <table class="table table-bordered">
        <thead>
          <tr>
            <th style="width: 30px;" class="orderButton" field="id">Id</th>
            <th class="orderButton" field="title">Title</th>
            <th style="width: 140px;" class="orderButton" field="time">Last
              updated</th>
            <th style="width: 200px;">Owner</th>
          </tr>
        </thead>
        <tbody id="articleList">
        </tbody>
      </table>
    </div>

    <div class="tab-pane fade" id="tab-article-search">
      <div id="articleCondition">
        <form class="form-horizontal">
          <div class="control-group">
            <label class="control-label" for="startId">Article ID</label>

            <div class="controls">
              <div class="input-prepend inline">
                <span class="add-on">Form</span> <input type="text"
                  name="startId" maxlength="6" value=""
                  id="startId" class="input-small">
              </div>
              <div class="input-prepend">
                <span class="add-on">To</span> <input type="text"
                  name="endId" maxlength="6" value=""
                  id="endId" class="input-small">
              </div>
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="title">Title</label>
            <div class="controls">
              <input type="text" name="title"
                maxlength="100" value="" id="title"
                class="span6">
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="keyword">Keyword</label>
            <div class="controls">
              <input type="text" name="keyword"
                maxlength="100" value="" id="keyword"
                class="span6">
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Is Visible</label>
            <div class="controls">
              <label class="radio inline"> <input type="radio"
                name="isVisible" value="all" checked="">
                All
              </label> <label class="radio inline"> <input type="radio"
                name="isVisible" value="true">
                Yes
              </label> <label class="radio inline"> <input type="radio"
                name="isVisible" value="false">
                No
              </label>
            </div>
          </div>
          <div class="form-actions">
            <input type="submit" id="search" name="search"
              value="Search" class="btn btn-primary"> <input
              type="submit" id="reset" name="reset" value="Reset"
              class="btn btn-danger">
          </div>
        </form>
      </div>
    </div>
  </div>

</body>
</html>