/**
 * All function used in article admin list page.
 */

(function($) {
  'use strict';

  $.fn.articleAdminListModule = function() {
    var $list = $(this);
    if ($list.length === 0) return;

    var visibleClass = 'icon-eye-open';
    var unVisibleClass = 'icon-eye-close';

    function getQueryString(field, id, value) {
      var queryString = '/admin/article/operator';
      queryString += '/' + id;
      queryString += '/' + field;
      queryString += '/' + value;
      return queryString;
    }

    function editVisible(id, value) {
      var queryString = getQueryString('isVisible', id, value);
      $.post(queryString, function(data) {
        if (data.result == "success") {
          var icon = $('.visibleState[articleId="' + id + '"]');
          if (value === false) {
            icon.removeClass(visibleClass);
            icon.addClass(unVisibleClass);
          } else {
            icon.removeClass(unVisibleClass);
            icon.addClass(visibleClass);
          }
          icon.on('click', function() {
            editVisible(id, !value);
          });
        }
      });
    }

    function getTitle(articleId, title, isVisible, clicked, author) {
      var html = $('<td></td>');
      var titleInfo = '';
      titleInfo += '<i isVisible="' + isVisible + '"articleId="' + articleId + '" class="visibleState ';
      if (isVisible === true)
        titleInfo += visibleClass;
      else
        titleInfo += unVisibleClass;
      titleInfo += ' pull-left tags"/>';

      titleInfo += '<a class="pull-left" href="/admin/article/editor/' + articleId + '" title="Edit article">' + title + '</a></span>';
      titleInfo += '<span class="pull-left muted" style="margin-left: 12px"> By ' + author + '</span>';

      titleInfo += '<span class="pull-right label label-success">' + clicked + '</span>';
      html.append(titleInfo);

      return html;
    }

    function blindVisibleEdit() {
      $list.find('.visibleState').on('click', function() {
        var id = $(this).attr('articleId');
        var visible = ($(this).attr('isVisible') == 'true') ? true : false;
        editVisible(id, !visible);
      });
    }

    function refreshList(condition) {
      if (condition === undefined)
        condition = currentCondition;
      console.log(condition);

      jsonPost('/admin/article/search', condition, function(data) {

        if (data.result == "error") {
          alert(data.error_msg);
          return;
        }

        // pagination
        $list.find('#pageInfo').empty();
        $list.find('#pageInfo').append(data.pageInfo);
        $list.find('#pageInfo').find('a').click(function(e) {
          if ($(this).attr('href') === null)
            return false;
          currentCondition.currentPage = $(this).attr("href");
          refreshList(currentCondition);
          return false;
        });

        var articleList = data.articleList;
        var tbody = $list.find('#articleList');
        console.log(tbody, articleList);
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(articleList, function(index, value) {
          var html = $('<tr></tr>');

          html.append('<td>' + value.articleId + '</td>');
          html.append(getTitle(value.articleId, value.title, value.isVisible, value.clicked, value.author));
          html.append('<td class="cdoj-time" type="milliseconds">' + value.time + '</td>');
          html.append('<td style="text-align: left;"><img id="usersAvatar" email="' + value.ownerEmail + '"/>' + value.ownerName + '</td>');
          tbody.append(html);
        });

        blindVisibleEdit();

        // get userList avatars
        $('img#usersAvatar').setAvatar({
          size : 37,
          image : 'http://www.acm.uestc.edu.cn/images/akari_small.jpg'
        });

        // format time style
        $('.cdoj-time').formatTimeStyle();
      });
    }

    var currentCondition = {
      "currentPage" : null,
      "startId" : undefined,
      "endId" : undefined,
      "title" : undefined,
      "isVisible" : undefined,
      "keyword" : undefined,
      "orderFields" : undefined,
      "orderAsc" : undefined
    };

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
  $('#article-admin-list').articleAdminListModule();
});