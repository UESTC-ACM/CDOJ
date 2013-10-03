/**
 * All function used in problem list page.
 */


/**
 * current search condition
 */
var currentCondition;

function getTitle(problemId, title, source, isSpj, isVisible) {
  var html = '';
  if (isVisible == false)
    html += '<i class = "icon-eye-close pull-left tags"/>';
  if (isSpj == true)
    html += '<span class="label label-important tags pull-right">SPJ</span>';

  html += '<a class="pull-left" href="/problem/show/' + problemId + '" title="'+source+'">'
      + title + '</a></span>';
  return html;
}

function getTags(tags) {
  var html = '';
  return html;
  //TODO(mzry1992)
  //$.each(tags, function (index, value) {
  //  html += '<span class="label label-info pull-right tags">' + value + '</span>';
  //});
  //return html;
}

function getDifficulty(difficulty) {
  difficulty = Math.max(1, Math.min(difficulty, 5));
  var html = '';
  for (var i = 1; i <= difficulty; i++)
    html += '<i class="difficulty-level icon-star pull-left" style="margin: 2px 0 0 0;" value="' + i + '"></i>';
  return html;
}

function refreshProblemList(condition) {
  jsonPost('/problem/search', condition, function (data) {

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

    var problemList = data['problemList'];

    var tbody = $('#problemList');
    // remove old user list
    tbody.find('tr').remove();
    // put user list
    $.each(problemList, function (index, value) {
      var html = $('<tr></tr>');
      html.append('<td>' + value.problemId + '</td>');
      html.append('<td>' + getTitle(value.problemId, value.title, value.source, value.isSpj, value.isVisible) + getTags(value.tags) + '</td>');
      html.append('<td>' + getDifficulty(value.difficulty) + '</td>');
      var solved = $('<td><i class="icon-user"/>' + value.solved + '</td>');
      if (value.status == 1)
        solved.addClass('problem-state-accept');
      else if (value.status == 2)
        solved.addClass('problem-state-error');
      html.append(solved);
      tbody.append(html);
    });

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
