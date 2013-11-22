/**
 * Javascript for user admin page.
 */

(function($) {
  'use strict';

  $.fn.userAdminListModule = function() {
    var $list = $(this);
    if ($list.length === 0) return;

    var userList;

    function refreshList(condition) {
      if (condition === undefined)
        condition = currentCondition;

      jsonPost('/admin/user/search', condition, function (data) {
        if (data.result == "error") {
          alert(data.error_msg);
          return;
        }

        console.log(data);

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

        userList = data.userList;
        var tbody = $('#userList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(userList, function (index, value) {
          var html = '<tr>' +
              '<td>' + value.userId + '</td>' +
              '<td style="text-align: left;"><img id="usersAvatar" email="' + value.email + '"/>' + value.userName + '</td>' +
              '<td>' + value.nickName + '</td>' +
              '<td>' + value.email + '</td>' +
              '<td>' + value.typeName + '</td>' +
              '<td class="cdoj-time" type="milliseconds">' + value.lastLogin + '</td>' +
              '<td><a href="#" onclick="return editUserDialog(' + index + ')"><i class="icon-pencil"/></a></td>' +
              '</tr>';
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

    var currentCondition = {
      "currentPage": null,
      "startId": undefined,
      "endId": undefined,
      "userName": undefined,
      "type": undefined,
      "school": undefined,
      "departmentId": undefined,
      "orderFields": "id",
      "orderAsc": true
    };

    $list.find('#departmentId').prepend('<option value="-1">All</option>');
    $list.find('#departmentId').attr("value", -1);
    $list.find('#type').prepend('<option value="-1">All</option>');
    $list.find('#type').attr("value", -1);

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
  $('#user-admin-list').userAdminListModule();
});
