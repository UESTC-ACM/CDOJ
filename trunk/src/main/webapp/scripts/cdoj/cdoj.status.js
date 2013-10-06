/**
 * All function used in status list page.
 */

/**
 * current search condition
 */
var currentCondition;

function getStatusId(statusId) {
  var html = $('<td>' + statusId + '</td>');
  return html;
}

function getUserName(userName) {
  var html = $('<td><a href="/user/center/' + userName + '">' + userName + '</a></td>');
  return html;
}

function getProblemId(problemId) {
  var html = $('<td><a href="/problem/show/'+problemId+'">' + problemId + '</td>');
  return html;
}

function getJudgeResponse(returnType, returnTypeId, statusId, userName) {
  var html = $('<td></td>');
  if (returnTypeId == 0)
    html.addClass('status-querying');
  else if (returnTypeId == 1)
    html.addClass('status-accept');
  else
    html.addClass('status-error');
  if (returnTypeId == 7 && (currentUserType == 1 || userName == currentUser))
    html.append($('<a id="compileInfo" href="#" statusId="' + statusId +'">' + returnType + '</a>'));
  else
    html.append(returnType);
  return html;
}

function getCodeUrl(length, statusId, userName) {
  var html = $('<span style="margin-right: 8px;"></span>');
  if (currentUserType == 1 || userName == currentUser)
    html.append($('<a id="codeHref" href="#" statusId="' + statusId + '">' + length + ' B</a>'));
  else
    html.append(length + ' B');
  return html;
}

function getLength(length, language, statusId, userName) {
  var html = $('<td style="text-align: right;"></td>');
  html.append(getCodeUrl(length, statusId, userName));
  html.append($('<span class="label label-success" style="width: 30px; text-align: center;">' + language + '</span>'));
  return html;
}

function getTimeCost(timeCost) {
  var html = $('<td></td>');
  if (timeCost != '')
    html.append($('<span>' + timeCost + ' ms</span>'));
  return html;
}

function getMemoryCost(memoryCost) {
  var html = $('<td></td>');
  if (memoryCost != '')
    html.append($('<span>' + memoryCost + ' KB</span>'));
  return html;
}

function getTime(time) {
  var html = $('<td class="cdoj-time" type="milliseconds">' + time + '</td>');
  return html;
}

function getHTML(value) {
  var html = $('<tr></tr>');
  html.append(getStatusId(value.statusId));
  html.append(getUserName(value.userName));
  html.append(getProblemId(value.problemId));
  html.append(getJudgeResponse(value.returnType, value.returnTypeId, value.statusId, value.userName));
  html.append(getLength(value.length, value.language, value.statusId, value.userName));
  html.append(getTimeCost(value.timeCost));
  html.append(getMemoryCost(value.memoryCost));
  html.append(getTime(value.time));
  return html;
}

function blindCodeHref() {
  $('#codeHref').live('click', function () {
    var id = $(this).attr('statusId');
    $.post('/status/info/' + id, function (data) {
      if (data.result == 'error')
        alert(data['error_msg']);
      else {
        console.log(data);
        var codeModal = $('#codeModal');
        var codeLabel = $('#codeModalLabel');
        var codeViewer = $('#codeViewer');
        codeLabel.empty();
        codeLabel.append('Code');
        codeViewer.empty();

        var str = data['code'];
        str = '<pre class="prettyprint linenums">' + str + '</pre>'
        codeViewer.append(str);

        var mult = 0.95;
        if (Sys.windows || Sys.safari)
          mult = 0.65;

        codeViewer.css('max-height', Math.min(600, $(window).height() * mult));
        prettyPrint();
        codeModal.modal();
      }
    });
    return false;
  });
}


function blindCompileInfo() {
  $('#compileInfo').live('click', function () {
    var id = $(this).attr('statusId');
    $.post('/status/info/' + id, function (data) {
      if (data.result == 'error')
        alert(data['error_msg']);
      else {
        var compileInfoModal = $('#compileInfoModal');
        var compileInfoModalLabel = $('#compileInfoModalLabel');
        var compileInfoViewer = $('#compileInfoViewer');
        compileInfoModalLabel.empty();
        compileInfoModalLabel.append('Compilation Error Information');
        compileInfoViewer.empty();
        compileInfoViewer.removeClass('linenums');
        compileInfoViewer.append(data['compileInfo']);
        prettyPrint();
        compileInfoModal.modal();
      }
    });
    return false;
  });
}

function refreshStatusList(condition) {
  if (condition == null)
    condition = currentCondition;
  jsonPost('/status/search', condition, function (data) {
    if (data.result == "error") {
      alert(data['error_msg']);
      return;
    }

    //pagination
    $('#pageInfo').empty();
    $('#pageInfo').append(data.pageInfo);
    $('#pageInfo').find('a').click(function (e) {
      if ($(this).attr('href') == null)
        return false;
      currentCondition.currentPage = $(this).attr('href');
      refreshStatusList(currentCondition);
      return false;
    });

    var statusList = data.statusList;
    var tbody = $('#statusList');
    // remove old user list
    tbody.find('tr').remove();
    // put user list

    $.each(statusList, function (index, value) {
      tbody.append(getHTML(value));
    });

    blindCodeHref();
    blindCompileInfo();

    // format time style
    $('.cdoj-time').formatTimeStyle();
  });
}

function changeOrder(field) {
  if (currentCondition["orderFields"] == field)
    currentCondition["orderAsc"] = (currentCondition["orderAsc"] == "true" ? "false" : "true");
  else {
    currentCondition["orderFields"] = field;
    currentCondition["orderAsc"] = "true";
  }
  refreshStatusList(currentCondition);
}

$(document).ready(function () {
  currentCondition = {
    "currentPage": null,
    "startId": undefined,
    "endId": undefined,
    "userName": undefined,
    "problemId": undefined,
    "languageId": undefined,
    "contestId": undefined,
    "result": undefined,
    "orderFields": undefined,
    "orderAsc": undefined
  };

  $('input#search').setButton({
    callback: function () {
      currentCondition = $('#statusCondition').getFormData();
      if (currentCondition["userName"] == '')
        currentCondition["userName"] = undefined;
      currentCondition.currentPage = 1;
      refreshStatusList(currentCondition);
      $('#TabMenu a:first').tab('show');
    }
  });

  $('input#reset').setButton({
    callback: function () {
      $('#statusCondition').resetFormData();
    }
  });

  $.each($('.orderButton'), function(){
    var field = $(this).attr('field');
    $(this).setButton({
      callback: function(){
        changeOrder(field);
      }
    });
  });

  refreshStatusList(currentCondition);

  setInterval(refreshStatusList, 3000);
});
