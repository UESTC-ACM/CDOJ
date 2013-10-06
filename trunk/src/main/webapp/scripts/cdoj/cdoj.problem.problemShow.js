/**
 * All function used in problem page.
 */

function getStatusId(statusId) {
  var html = $('<td>' + statusId + '</td>');
  return html;
}

function getUserName(userName) {
  var html = $('<td><a href="/user/center/' + userName + '">' + userName + '</a></td>');
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
    html.append($('<a id="compileInfo" href="#" statusId="' + statusId + '">' + returnType + '</a>'));
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

function getProblemId(problemId) {
  var html = $('<td>' + problemId + '</td>');
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
        var codeModal = $('#codeModal');
        var codeLabel = $('#codeModalLabel');
        var codeViewer = $('#codeViewer');
        codeLabel.empty();
        codeLabel.append('Code');
        codeViewer.empty();

        var str = data['code'];
        str = '<pre class="prettyprint linenums">' + js.lang.String.encodeHtml(str) + '</pre>'
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
        compileInfoViewer.append(js.lang.String.encodeHtml(data['compileInfo']));
        prettyPrint();
        compileInfoModal.modal();
      }
    });
    return false;
  });
}

var currentCondition;

function refreshStatusList(condition) {
  if (condition == null)
    condition = currentCondition;
  jsonPost('/status/search', condition, function (data) {
    if (data.result == "error") {
      //alert(data.error_msg);
      clearInterval(statusTimer);
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

var statusTimer;
var currentProblem;

$(document).ready(function () {

  currentProblem = $('#problem_title').attr('value');

  markdown();

  $.each($('.sample'), function () {
    var md = $(this).html()
        .replace('<textarea>', '')
        .replace('</textarea>', '')
        .replace('<TEXTAREA>', '')
        .replace('</TEXTAREA>', '')
        .replace('\r', '<br/>');
    $(this).empty().append(md);
  });

  // make code pretty
  prettify();

  // make sample input and output have the same height
  var height = 0;
  $.each($('.sample'), function () {
    height = Math.max(height, $(this).height());
  });
  $.each($('.sample'), function () {
    $(this).css('height', height);
  });

  MathJax.Hub.Config({
    tex2jax: {inlineMath: [
      ['$', '$'],
      ['\\[', '\\]']
    ]}
  });
  MathJax.Hub.Queue(['Typeset', MathJax.Hub]);

  //Blind Submit
  $('#submitCode').click(function () {
    var info = {
      'codeContent': null,
      'languageId': null,
      'contestId': null,
      'problemId': currentProblem
    };
    //Get code content
    info['codeContent'] = $('#codeContent').val();
    //Get language Id
    info['languageId'] = $('#languageSelector').find('.active').attr('value');

    console.log(info);
    jsonPost('/status/submit', info, function (data) {
      if (data.result == 'error')
        alert(data['error_msg']);
      else if (data.result == 'success') {
        $('#TabMenu').find('a[href="#tab-problem-status"]').tab('show');
        //TODO change it like PC^2
      } else {
        alert('Please login first!');
        $("#loginModal").modal();
      }
    });
  });

  currentCondition = {
    "currentPage": null,
    "startId": undefined,
    "endId": undefined,
    "userName": undefined,
    "problemId": currentProblem,
    "languageId": undefined,
    "contestId": undefined,
    "result": undefined,
    "orderFields": undefined,
    "orderAsc": undefined
  };

  $.each($('.orderButton'), function () {
    var field = $(this).attr('field');
    $(this).setButton({
      callback: function () {
        changeOrder(field);
      }
    });
  });

  //Only refresh status at status tab
  $('#TabMenu').find('a[href="#tab-problem-status"]').on('show', function () {
    refreshStatusList(currentCondition);
    statusTimer = setInterval(refreshStatusList, 3000);
  });
  $('#TabMenu').find('a[href!="#tab-problem-status"]').on('show', function () {
    if (statusTimer)
      clearInterval(statusTimer);
  });

});
