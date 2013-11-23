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
<script src="<s:url value="/scripts/cdoj/cdoj.contest.js"/>"></script>
<title>Contest</title>
</head>
<body>

  <ul id="TabMenu" class="nav nav-pills">
    <li class="active"><a href="#tab-contest-list"
      data-toggle="tab">Contest list</a></li>
    <li><a href="#tab-contest-search" data-toggle="tab">Search</a></li>
  </ul>

  <div id="TabContent" class="tab-content">
    <div class="tab-pane fade active in" id="tab-contest-list">

      <div class="pull-right span6" style="clear: both;">
        <table class="table table-bordered">
          <tr>
            <td class="contest-state-running">Running</td>
            <td class="contest-state-ended">Ended</td>
            <td class="contest-state-pending">Pending</td>
          </tr>
        </table>
      </div>

      <div style="clear: both;"></div>

      <div id="pageInfo"></div>

      <table class="table table-bordered">
        <thead>
          <tr>
            <th style="width: 30px;" class="orderButton" field="id">Id</th>
            <th class="orderButton" field="title">Title</th>
            <th style="width: 70px;" class="orderButton" field="type">Type</th>
            <th style="width: 160px;" class="orderButton" field="time">Start
              time</th>
            <th style="width: 100px;" class="orderButton" field="length">Length</th>
          </tr>
        </thead>
        <tbody id="contestList">
        </tbody>
      </table>
    </div>

    <div class="tab-pane fade" id="tab-contest-search">
      <div id="contestCondition">
        <form class="form-horizontal">
          <div class="control-group">
            <label class="control-label" for="contestCondition.startId">Contest
              ID</label>

            <div class="controls">
              <div class="input-prepend inline">
                <span class="add-on">Form</span>
                <s:textfield name="contestCondition.startId"
                  maxLength="6" cssClass="input-small" />
              </div>
              <div class="input-prepend">
                <span class="add-on">To</span>
                <s:textfield name="contestCondition.endId" maxLength="6"
                  cssClass="input-small" />
              </div>
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="contestCondition.title">Title</label>

            <div class="controls">
              <s:textfield name="contestCondition.title" maxLength="100"
                cssClass="span6" />
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="contestCondition.keyword">Keyword</label>

            <div class="controls">
              <s:textfield name="contestCondition.keyword"
                maxLength="100" cssClass="span6" />
            </div>
          </div>

          <div class="control-group">
            <label class="control-label" for="contestCondition.type">Type</label>

            <div class="controls">
              <s:select name="contestCondition.type"
                list="global.contestTypeList" listKey="ordinal()"
                listValue="description" cssClass="span6" />
            </div>
          </div>

          <div class="form-actions">
            <s:submit name="search" cssClass="btn btn-primary"
              value="Search" theme="bootstrap" />
            <s:submit name="reset" cssClass="btn btn-danger"
              value="Reset" theme="bootstrap" />
          </div>
        </form>
      </div>
    </div>
  </div>

</body>
</html>