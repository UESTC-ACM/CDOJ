/**
 * All function used in problem list page.
 */

(function($) {
  'use strict';

  $.fn.userListModule = function() {

    var $list = $(this);
    if ($list.length === 0) return;

    function getUserName(email, userName) {
      var html = $('<td>' +
          '<img id="usersAvatar" email="' + email + '"/>' +
          '<span><a href="/user/center/' + userName + '">' + userName + '</a></span>' +
          '</td>');
      return html;
    }

    function getNickName(nickName, type) {
      var html = $('<td>' + nickName + '</td>');
      if (type !== 0)
        html.addClass('userName-type' + type);
      return html;
    }
    /**
     * refresh the user list
     * @param condition
     */
    function refreshList(condition) {
      jsonPost('/user/search', condition, function (data) {
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
          currentCondition.currentPage = $(this).attr("href");
          refreshList(currentCondition);
          return false;
        });

        var userList = data.userList;
        var tbody = $list.find('#userList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(userList, function (index, value) {
          var html = $('<tr></tr>');
          html.append('<td>' + value.userId + '</td>');
          html.append(getUserName(value.email, value.userName));
          html.append(getNickName(value.nickName, value.type));
          html.append('<td>' + value.school + '</td>');
          html.append('<td class="cdoj-time" type="milliseconds">' + value.lastLogin+ '</td>');
          html.append('<td>' + value.solved + '</td>');
          html.append('<td>' + value.tried + '</td>');
          tbody.append(html);
        });

        // get userList avatars
        $('img#usersAvatar').setAvatar({
          size: 37,
          image: 'http://www.acm.uestc.edu.cn/images/akari_small.jpg'
        });

        // format time style
        $('.cdoj-time').formatTimeStyle();

      });
    }

    $list.find('#departmentId').prepend('<option value="-1">All</option>');
    $list.find('#departmentId').attr("value", -1);
    $list.find('#type').prepend('<option value="-1">All</option>');
    $list.find('#type').attr("value", -1);

    var currentCondition = {
      "currentPage": null,
      "startId": undefined,
      "endId": undefined,
      "userName": undefined,
      "type": undefined,
      "school": undefined,
      "departmentId": undefined,
      "orderFields": "solved,tried,id",
      "orderAsc": "false,false,true"
    };


    refreshList(currentCondition);

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

    this.refreshList = refreshList;
    return this;
  };

}(jQuery));

var userList;
$(document).ready(function () {
  userList = $('#user-list').userListModule();
});