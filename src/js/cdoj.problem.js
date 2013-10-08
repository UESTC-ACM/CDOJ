/**
 * All function used in problem list page.
 */

(function($) {
  'use strict';

  $.fn.problemListModule = function() {
    var $problemList = $(this);

    function getTitle(problemId, title, source, isVisible) {
      var html = '';
      if (isVisible === false)
        html += '<i class = "icon-eye-close pull-left tags"/>';

      html += '<a class="pull-left" href="/problem/show/' + problemId + '" title="'+source+'">' + title + '</a></span>';
      return html;
    }

    function getTags(tags, isSpj) {
      var html = '<span class="label label-primary pull-right tags">' + 'tag' + '</span>';
      if (isSpj === true)
        html += '<span class="label label-danger tags pull-right">SPJ</span>';
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
      if (condition === undefined)
        condition = currentCondition;
      jsonPost('/problem/search', condition, function (data) {

        if (data.result == "error") {
          alert(data.error_msg);
          return;
        }

        //pagination
        $problemList.find('#pageInfo').empty();
        $problemList.find('#pageInfo').append(data.pageInfo);
        $problemList.find('#pageInfo').find('a').click(function (e) {
          if ($(this).attr('href') === null)
            return false;
          currentCondition.currentPage = $(this).attr('href');
          refreshProblemList(currentCondition);
          return false;
        });

        var problemList = data.problemList;

        var tbody = $problemList.find('#problemList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(problemList, function (index, value) {
          var html = $('<tr></tr>');
          html.append('<td>' + value.problemId + '</td>');
          html.append('<td>' + getTitle(value.problemId, value.title, value.source, value.isVisible) + getTags(value.tags, value.isSpj) + '</td>');
          html.append('<td>' + getDifficulty(value.difficulty) + '</td>');
          var solved = $('<td><i class="icon-user"/> x' + value.solved + '</td>');
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
      if (currentCondition.orderFields == field)
        currentCondition.orderAsc = (currentCondition.orderAsc === "true" ? "false" : "true");
      else {
        currentCondition.orderFields = field;
        currentCondition.orderAsc = "false";
      }
      refreshProblemList(currentCondition);
    }

    var currentCondition = {
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


    $.each($problemList.find('.orderButton'), function(){
      var field = $(this).attr('field');
      $(this).setButton({
        callback: function(){
          changeOrder(field);
        }
      });
    });

    refreshProblemList(currentCondition);

    var $searchForm = $problemList.find('#search-group');
    $searchForm.find('#advanced').setButton({
      callback: function() {
        alert('fuck');
      }
    });
  };

}(jQuery));

$(document).ready(function () {
  $('#problem-list').problemListModule();
});
