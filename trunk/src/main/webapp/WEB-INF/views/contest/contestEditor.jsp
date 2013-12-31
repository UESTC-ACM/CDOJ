<%--
  Admin contest editor page

  @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<c:if test="${action eq 'new'}">
  <title>New contest</title>
</c:if>
<c:if test="${action eq 'edit'}">
  <title>Edit contest - contest${targetContest.contestId}</title>
</c:if>
</head>
<body>

  <div id="contest-editor">
      <div class="row">
        <c:if test="${action eq 'new'}">
          <div class="col-md-12" id="contest-editor-title" value="new">
            <h1>New contest</h1>
          </div>
        </c:if>
        <c:if test="${action eq 'edit'}">
          <div class="col-md-12" id="contest-editor-title"
            value="${targetContest.contestId}">
            <h1>Edit contest ${targetContest.contestId}</h1>
          </div>
        </c:if>
        <div class="form-group">
          <div class="col-sm-12">
            <input type="text" name="title" maxlength="50"
              value="${targetContest.title}" id="title"
              class="form-control" placeholder="Enter title here" />
          </div>
        </div>

        <div class="col-md-12">
          <form class="form-horizontal">
            <div class="form-group">
              <label for="type" class="col-sm-2 control-label">
                Type</label>
              <div class="col-sm-10">
                <div class="btn-group" data-toggle="buttons">
                  <c:forEach var="contestType"
                    items="${contestTypeList}" varStatus="status">
                    <label
                      class="btn btn-default <c:if test="${targetContest.type == status.index}">
                                               active
                                               </c:if>">
                      <input type="radio" name="type"
                      value="<c:out value="${status.index}"/>"
                      <c:if test="${targetContest.type == status.index}">
                                               checked="checked"
                                               </c:if> />
                      <c:out value="${contestType.description}" />
                    </label>
                  </c:forEach>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label for="time" class="col-sm-2 control-label">
                Begin time</label>
              <div class="col-sm-10">
                <div class="form-inline">
                  <div class="form-group">
                    <input type="text" value="<fmt:formatDate value="${targetContest.time}"
                    type="date" pattern="yyyy-MM-dd" />"
                      data-date-format="yyyy-mm-dd"
                      class="datepicker form-control"
                      style="width: 105px;" />
                  </div>
                  <div class="form-group">
                    <input type="text" value="<fmt:formatDate value="${targetContest.time}"
                    type="date" pattern="HH" />"
                      class="form-control inline" style="width: 45px;" maxlength="2"/>:
                  </div>
                  <div class="form-group">
                    <input type="text" value="<fmt:formatDate value="${targetContest.time}"
                    type="date" pattern="mm" />" class="form-control"
                      style="width: 45px;" maxlength="2"/>:
                  </div>
                  <div class="form-group">
                    <input type="text" value="00" class="form-control"
                      style="width: 45px;" readonly maxlength="2"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label for="length" class="col-sm-2 control-label">
                Length</label>
              <div class="col-sm-10">
                <div class="form-inline">
                  <div class="form-group">
                    <input type="text" value="<c:out value="${targetContest.lengthDays}"/>"
                      class="form-control"
                      style="width: 105px;" />
                  </div> days
                  <div class="form-group">
                    <input type="text" value="<c:out value="${targetContest.lengthHours}"/>"
                      class="form-control inline" style="width: 45px;" maxlength="2"/>:
                  </div>
                  <div class="form-group">
                    <input type="text" value="<c:out value="${targetContest.lengthMinutes}"/>" 
                    class="form-control"
                      style="width: 45px;" maxlength="2"/>:
                  </div>
                  <div class="form-group">
                    <input type="text" value="00" class="form-control"
                      style="width: 45px;" readonly maxlength="2"/>
                  </div>
                </div>
              </div>
            </div>
          </form>
          <div class="col-md-12">
            <div id="description">
              <c:out value="${targetContest.description}" escapeXml="true" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <%--
  <div>
    <div class="span10">
      <form class="form-horizontal">
        <fieldset>

          <div class="span10">
            <div class="control-group">
              <label class="control-label">Problem list</label>

              <div class="controls">
                <div class="row">
                  <div class="span6">
                    <table class="table table-striped table-bordered">
                      <thead>
                        <tr>
                          <th style="width: 14px;"><a href="#"
                            id="add_problem"><i class="icon-plus"></i></a></th>
                          <th style="width: 60px;">Id</th>
                          <th>Title</th>
                          <th style="width: 70px;">Difficulty</th>
                        </tr>
                      </thead>
                      <tbody id="problemList"
                        init="${targetContest.problemListString}">
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
    </div>

    <div class="form-actions">
      <s:submit name="submit" cssClass="btn btn-primary" value="Submit"
        theme="bootstrap" />
    </div>
    </fieldset>
    </form>
  </div>
  </div>  --%>
</body>
</html>