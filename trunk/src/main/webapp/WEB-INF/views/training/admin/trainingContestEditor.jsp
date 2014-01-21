<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<head>
  <page:applyDecorator name="head" page="/WEB-INF/views/common/fileUploaderHeader.jsp"/>
  <script src="<s:url value="
  /scripts/cdoj/cdoj.admin.training.trainingContestEditor.js"/>"></script>
  <
  title > Training
  contest
  editor <
  /
  title >
  <
  /
  head >
      < body >
  < div
  class
  = "row"
  id = "trainingContestEditor" >
      < div
  class
  = "span10" >
      < form
  class
  = "form-horizontal" >
      < fieldset >
      < h3 >
      Edit
  training
  contest < span
  id = "trainingContestId" >${targetTrainingContest.trainingContestId} <
  /
  span >
  <
  /
  h3 >

  < div
  class
  = "row" >
      < div
  class
  = "span10" >
      < div
  class
  = "control-group" >
      < label
  class
  = "control-label" > Title <
  /
  label >

  < div
  class
  = "controls" >
      < input
  type = "text"
  name = "trainingContestDTO.title"
  maxlength = "50"
  value = "${targetTrainingContest.title}"
  id = "contestDTO_title"
  class
  = "span6"
  placeholder = "Enter title here" >
      <
  /
  div >
  <
  /
  div >
  <
  /
  div >

  < div
  class
  = "span10" >
      < div
  class
  = "control-group" >
      < label
  class
  = "control-label" > Is
  personal <
  /
  label >

  < div
  class
  = "controls" >
      < s
  :
  if test = "targetTrainingContest.isPersonal == true" >
      < label for= "trainingContestDTO.isPersonal-false" class
  = "radio inline" >
      < input
  type = "radio"
  name = "trainingContestDTO.isPersonal"
  id = "trainingContestDTO.isPersonal-false"
  value = "false" >
      No
      <
  /
  label >

  < label
  for= "trainingContestDTO.isPersonal-true" class
  = "radio inline" >
      < input
  type = "radio"
  name = "trainingContestDTO.isPersonal"
  id = "trainingContestDTO.isPersonal-true"
  value = "true"
  checked = "" >
      Yes
      <
  /
  label >
  <
  /
  s:if>
  <
  s:else
  >
  <
  label
  for= "trainingContestDTO.isPersonal-false" class
  = "radio inline" >
      < input
  type = "radio"
  name = "trainingContestDTO.isPersonal"
  id = "trainingContestDTO.isPersonal-false"
  value = "false"
  checked = "" >
      No
      <
  /
  label >

  < label
  for= "trainingContestDTO.isPersonal-true" class
  = "radio inline" >
      < input
  type = "radio"
  name = "trainingContestDTO.isPersonal"
  id = "trainingContestDTO.isPersonal-true"
  value = "true" >
      Yes
      <
  /
  label >
  <
  /
  s:else
  >
  </
  div >
  <
  /
  div >
  <
  /
  div >

  < div
  class
  = "span10" >
      < div
  class
  = "control-group" >
      < label
  class
  = "control-label" > Type <
  /
  label >
  < div
  class
  = "controls" >
      < s
  :
  iterator
  value = "Global.trainingContestTypeList"
  id = "id"
  status = "status" >
      < label
  for= "trainingContestDTO.type-<s:property value="#
  status.index
  "/>"
  class
  = "radio inline" >
      < input
  type = "radio"
  name = "trainingContestDTO.type"
  id = "trainingContestDTO.type-<s:property value="
  #
  status.index
  "/>"
  value = "<s:property value="
  #
  status.index
  "/>"
  < s
  :
  if test = "targetTrainingContest.type == #status.index" >
      checked = ""
      </
  s:if>
  >
  <
  s:property
  value = "#id.description" / >
      <
  /
  label >
  <
  /
  s:iterator >
  <
  /
  div >
  <
  /
  div >
  <
  /
  div >

  < div
  class
  = "span10" >
      < div
  class
  = "control-group" >
      < label
  class
  = "control-label" > Upload
  rank
  file <
  /
  label >

  < div
  class
  = "controls" >
      < div
  id = "fileUploader" > <
  /
  div >
  < span
  id = "fileUploaderAttention"
  class
  = "help-inline" > Please
  use
  xls. <
  /
  span >
  < p
  class
  = "help-block" > 修改了比赛类型之后请先submit一次 <
  /
  p >
  < p
  class
  = "help-block" > VJ和HDU上直接导出排名即可
  （
  注意导出的排名可能有中文空格
  ，
  需要批量替换掉
  ）</
  p >
  < p
  class
  = "help-block" > 扣分的话类型请选择adjust
  ，
  xls文档格式如下
  ：
  <
  table
  class
  = "table table-striped table-condensed" >
      < thead >
      < tr >
      < th > name <
  /
  th >
      < th > penalty <
  /
  th >
  <
  /
  tr >
  <
  /
  thead >
      < tbody >
      < tr >
      < td > 李昀 <
  /
  td >
      < td > 100 <
  /
  td >
  <
  /
  tr >
      < tr >
      < td > 何云鹏 <
  /
  td >
      < td > 200 <
  /
  td >
  <
  /
  tr >
  <
  /
  tbody >
  <
  /
  table >
  数字是扣分幅度
  ，
  负数的话就是加分
  。
  </
  p >
  <
  /
  div >
  <
  /
  div >
  <
  /
  div >

  < div
  class
  = "span10" >
      < div
  class
  = "control-group" >
      < label
  class
  = "control-label" > Rank
  list <
  /
  label >

  < div
  class
  = "controls" >
      < div
  class
  = "row" >
      < div
  class
  = "span7" >
      < h3 > Only
  show
  related
  user <
  /
  h3 >
  < table
  class
  = "table table-striped table-bordered" >
      < thead >
      < tr >
      < th
  style = "width: 30px;" > Rank <
  /
  th >
      < th > Name <
  /
  th >
      < th > User <
  /
  th >
  < th
  style = "width: 60px;" > Solve <
  /
  th >
  < th
  style = "width: 100px;" > Penalty / Score <
  /
  th >
  <
  /
  tr >
  <
  /
  thead >
  < tbody
  id = "rankList" >
      < s
  :
  iterator
  value = "targetTrainingContest.trainingStatusViewList"
  id = "trainingStatus" >
      < tr >
      < td > < s
  :
  property
  value = "#trainingStatus.rank" / > <
  /
  td >
      < td > < s
  :
  property
  value = "#trainingStatus.name" / > <
  /
  td >
      < td > < s
  :
  property
  value = "#trainingStatus.userName" / > <
  /
  td >
      < td > < s
  :
  property
  value = "#trainingStatus.solve" / > <
  /
  td >
      < td > < s
  :
  property
  value = "#trainingStatus.penalty" / > <
  /
  td >
  <
  /
  tr >
  <
  /
  s:iterator >
  <
  /
  tbody >
  <
  /
  table >
  <
  /
  div >
  <
  /
  div >
  <
  /
  div >
  <
  /
  div >
  <
  /
  div >
  <
  /
  div >

  < div
  class
  = "form-actions" >
      < s
  :
  submit
  name = "submit"
  cssClass = "btn btn-primary"
  value = "Submit"
  theme = "bootstrap" / >
      <
  /
  div >
  <
  /
  fieldset >
  <
  /
  form >
  <
  /
  div >
  <
  /
  div >

  <
  /
  body >
  <
  /
  html >