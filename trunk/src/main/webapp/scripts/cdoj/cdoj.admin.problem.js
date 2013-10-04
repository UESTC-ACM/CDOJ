/**
 * All function used in problem admin list page.
 */

/**
 * current search condition
 */
var currentCondition;

var visibleClass = 'icon-eye-open';
var unVisibleClass = 'icon-eye-close';

function getQueryString(field, id, value) {
  var queryString = '/admin/problem/operator';
  queryString += '/' + id;
  queryString += '/' + field;
  queryString += '/' + value;
  return queryString;
}

function editVisible(id, value) {
  var queryString = getQueryString('isVisible', id, value);
  $.post(queryString, function (data) {
    console.log(data);
    if (data.result == "success") {
      var icon = $('#visibleState[problemId="' + id + '"]');
      if (value == false) {
        icon.removeClass(visibleClass);
        icon.addClass(unVisibleClass);
      }
      else {
        icon.removeClass(unVisibleClass);
        icon.addClass(visibleClass);
      }
      icon.live('click', function () {
        editVisible(id, !value)
      });
    }
  });
}

function getTitle(problemId, title, source, isSpj, isVisible, tags) {
  var html = $('<td></td>');
  var titleInfo = '';
  titleInfo += '<i id="visibleState" isVisible="' + isVisible + '"problemId="' + problemId + '" class="';
  if (isVisible == true)
    titleInfo += visibleClass;
  else
    titleInfo += unVisibleClass;
  titleInfo += ' pull-left tags"/>';

  if (isSpj == true)
    titleInfo += '<span class="label label-important tags pull-right">SPJ</span>';

  titleInfo += '<a class="pull-left" href="/admin/problem/editor/' + problemId + '" title="Edit problem">'
      + title + '</a></span>';

  titleInfo += getTags(tags);

  html.append(titleInfo);

  return html;
}

function getTags(tags) {
  var html = '';
  return html;
  /*
  TODO(mzry1992)
  $.each(tags, function (index, value) {
    html += '<span class="label label-info pull-right tags">' + value + '</span>';
  });
  return html;*/
}

function getDifficulty(difficulty) {
  difficulty = Math.max(1, Math.min(difficulty, 5));
  var html = '';
  for (var i = 1; i <= difficulty; i++)
    html += '<i class="difficulty-level icon-star pull-left" style="margin: 0px;" value="' + i + '"></i>';
  for (var i = difficulty + 1; i <= 5; i++)
    html += '<i class="difficulty-level icon-star-empty pull-left" style="margin: 0px;" value="' + i + '"></i>';
  return html;
}

function blindVisibleEdit() {
  $('#visibleState').live('click', function () {
    var id = $(this).attr('problemId');
    var visible = ($(this).attr('isVisible') == 'true') ? true : false;
    editVisible(id, !visible);
  });
}

function blindDifficultSpan() {
  $('i.difficulty-level').live('click', function () {
    var target = $(this);
    var problemId = target.parent().attr('problemId');
    var value = target.attr('value');
    var queryString = getQueryString('difficulty', problemId, value);
    $.post(queryString, function (data) {
      if (data.result == "success") {
        var parentNode = target.parent();
        parentNode.empty();
        parentNode.append(getDifficulty(value));
        blindDifficultSpan();
      }
    });
  });
}

function refreshProblemList(condition) {
  jsonPost('/admin/problem/search', condition, function (data) {

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
      currentCondition.currentPage = $(this).attr("href");
      refreshProblemList(currentCondition);
      return false;
    });

    var problemList = data.problemList;
    var tbody = $('#problemList');
    // remove old user list
    tbody.find('tr').remove();
    // put user list
    $.each(problemList, function (index, value) {
      var html = $('<tr></tr>');

      html.append('<td>' + value.problemId + '</td>');
      html.append(getTitle(value.problemId, value.title, value.source, value.isSpj, value.isVisible, value.tags));
      html.append('<td class="difficult-span" problemId="' + value.problemId + '">' + getDifficulty(value.difficulty) + '</td>');
      html.append('<td><a href="/admin/problem/data/' + value.problemId + '" title="Edit data"><i class="icon-cog"></i></a></td>');
      tbody.append(html);
    });

    blindVisibleEdit();
    blindDifficultSpan();
  });
}

function changeOrder(field) {
  if (currentCondition["orderFields"] == field)
    currentCondition["orderAsc"] = (currentCondition["orderAsc"] == "true" ? "false" : "true");
  else {
    currentCondition["orderFields"] = field;
    currentCondition["orderAsc"] = "false";
  }
  refreshProblemList(currentCondition);
}

$(document).ready(function () {
  currentCondition = {
    "currentPage": null,
    "startId": undefined,
    "endId": undefined,
    "title": undefined,
    "source": undefined,
    "isVisible": undefined,
    "isSpj": undefined,
    "startDifficulty": undefined,
    "endDifficulty": undefined,
    "keyword": undefined,
    "orderFields": undefined,
    "orderAsc": undefined
  };

  $('input#search').setButton({
    callback: function () {
      currentCondition = $('#problemCondition').getFormData();
      currentCondition.currentPage = 1;
      refreshProblemList(currentCondition);
      $('#TabMenu a:first').tab('show');
    }
  });

  $('input#reset').setButton({
    callback: function () {
      $('#problemCondition').resetFormData();
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

  refreshProblemList(currentCondition);
});