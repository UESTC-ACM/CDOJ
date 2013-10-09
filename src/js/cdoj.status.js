/**
 * All function used in problem list page.
 */

(function($) {
  'use strict';

  $.fn.statusListModule = function() {

    var $list = $(this);

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
      if (returnTypeId === 0)
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
      if (timeCost !== '')
        html.append($('<span>' + timeCost + ' ms</span>'));
      return html;
    }

    function getMemoryCost(memoryCost) {
      var html = $('<td></td>');
      if (memoryCost !== '')
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

    function refreshList(condition) {
      if (condition === undefined)
        condition = currentCondition;
      jsonPost('/status/search', condition, function (data) {
        if (data.result == "error") {
          alert(data.error_msg);
          return;
        }

        //pagination
        $list.find('#pageInfo').empty();
        $list.find('#pageInfo').append(data.pageInfo);
        $list.find('#pageInfo').find('a').click(function (e) {
          if ($(this).attr('href') === null)
            return false;
          currentCondition.currentPage = $(this).attr('href');
          refreshList(currentCondition);
          return false;
        });

        var statusList = data.statusList;
        var tbody = $list.find('#statusList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list

        $.each(statusList, function (index, value) {
          tbody.append(getHTML(value));
        });

        //blindCodeHref();
        //blindCompileInfo();

        // format time style
        $list.find('.cdoj-time').formatTimeStyle();
      });
    }

    var currentCondition = {
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

    refreshList(currentCondition);
    setInterval(refreshList, 3000);

    ///////////////////////////////////////////////////////////////////////////
    function changeOrder(field) {
      if (currentCondition.orderFields == field)
        currentCondition.orderAsc = (currentCondition.orderAsc === "true" ? "false" : "true");
      else {
        currentCondition.orderFields = field;
        currentCondition.orderAsc = "false";
      }
      refreshList(currentCondition);
    }

    $.each($list.find('.orderButton'), function(){
      var field = $(this).attr('field');
      $(this).setButton({
        callback: function(){
          changeOrder(field);
        }
      });
    });

    refreshList(currentCondition);

    var $searchForm = $list.find('#search-group');
    var $conditionForm = $searchForm.find('#condition');

    function trigger() {
      if ($conditionForm.hasClass('open')) {
        $conditionForm.removeClass('open');

      } else {
        $conditionForm.addClass('open');
      }
    }

    $conditionForm.hover(function() {
      $conditionForm.addClass('hover');
    }, function() {
      $conditionForm.removeClass('hover');
    });

    $(document).click(function() {
      if ($conditionForm.hasClass('open') && !$conditionForm.hasClass('hover')) {
        trigger();
        return false;
      }
    });

    $searchForm.find('#advanced').setButton({
      callback: function() {
        if (!$conditionForm.hasClass('open')) {
          trigger();
        }
      }
    });

    $conditionForm.find('#search-button').setButton({
      callback: function () {
        currentCondition = $conditionForm.getFormData();
        currentCondition.currentPage = 1;
        refreshList(currentCondition);
        trigger();
      }
    });

    $conditionForm.find('#reset-button').setButton({
      callback: function () {
        $conditionForm.resetFormData();
      }
    });

    $searchForm.find('#search').setButton({
      callback: function () {
        currentCondition = {
          "currentPage": 1,
          "keyword": $searchForm.find('#search-keyword').val()
        };
        refreshList(currentCondition);
      }
    });
  };

}(jQuery));

$(document).ready(function () {
  $('#status-list').statusListModule();
});
