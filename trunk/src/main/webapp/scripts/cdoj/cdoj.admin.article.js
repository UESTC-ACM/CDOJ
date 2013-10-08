/**
 * All function used in article admin list page.
 */

/**
 * current search condition
 */
var currentCondition;

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
      var icon = $('#visibleState[articleId="' + id + '"]');
      if (value == false) {
        icon.removeClass(visibleClass);
        icon.addClass(unVisibleClass);
      } else {
        icon.removeClass(unVisibleClass);
        icon.addClass(visibleClass);
      }
      icon.live('click', function() {
        editVisible(id, !value)
      });
    }
  });
}

function getTitle(articleId, title, isVisible, clicked, author) {
  var html = $('<td></td>');
  var titleInfo = '';
  titleInfo += '<i id="visibleState" isVisible="' + isVisible + '"articleId="'
      + articleId + '" class="';
  if (isVisible == true)
    titleInfo += visibleClass;
  else
    titleInfo += unVisibleClass;
  titleInfo += ' pull-left tags"/>';

  titleInfo += '<a class="pull-left" href="/admin/article/editor/' + articleId
      + '" title="Edit article">' + title + '</a></span>';
  titleInfo += '<span class="pull-left muted" style="margin-left: 12px"> By '
      + author + '</span>';

  titleInfo += '<span class="pull-right label label-success">' + clicked
      + '</span>';
  html.append(titleInfo);

  return html;
}

function blindVisibleEdit() {
  $('#visibleState').live('click', function() {
    var id = $(this).attr('articleId');
    var visible = ($(this).attr('isVisible') == 'true') ? true : false;
    editVisible(id, !visible);
  });
}

function refreshArticleList(condition) {
  jsonPost('/admin/article/search', condition, function(data) {

    if (data.result == "error") {
      alert(data.error_msg);
      return;
    }

    // pagination
    $('#pageInfo').empty();
    $('#pageInfo').append(data.pageInfo);
    $('#pageInfo').find('a').click(function(e) {
      if ($(this).attr('href') == null)
        return false;
      currentCondition.currentPage = $(this).attr("href");
      refreshProblemList(currentCondition);
      return false;
    });

    var articleList = data.articleList;
    var tbody = $('#articleList');
    // remove old user list
    tbody.find('tr').remove();
    // put user list
    $.each(articleList, function(index, value) {
      var html = $('<tr></tr>');

      html.append('<td>' + value.articleId + '</td>');
      html.append(getTitle(value.articleId, value.title, value.isVisible,
          value.clicked, value.author));
      html.append('<td class="cdoj-time" type="milliseconds">' + value.time + '</td>');
      html.append('<td style="text-align: left;"><img id="usersAvatar" email="'
          + value.ownerEmail + '"/>' + value.ownerName + '</td>');
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

function changeOrder(field) {
  if (currentCondition["orderFields"] == field)
    currentCondition["orderAsc"] = (currentCondition["orderAsc"] == "true" ? "false"
        : "true");
  else {
    currentCondition["orderFields"] = field;
    currentCondition["orderAsc"] = "false";
  }
  refreshArticleList(currentCondition);
}

$(document).ready(function() {
  currentCondition = {
    "currentPage" : null,
    "startId" : undefined,
    "endId" : undefined,
    "title" : undefined,
    "isVisible" : undefined,
    "keyword" : undefined,
    "orderFields" : undefined,
    "orderAsc" : undefined
  }

  $('input#search').setButton({
    callback : function() {
      currentCondition = $('#articleCondition').getFormData();
      currentCondition.currentPage = 1;
      refreshArticleList(currentCondition);
      $('#TabMenu a:first').tab('show');
    }
  });

  $('input#reset').setButton({
    callback : function() {
      $('#articleCondition').resetFormData();
    }
  });

  $.each($('.orderButton'), function() {
    var field = $(this).attr('field');
    $(this).setButton({
      callback : function() {
        changeOrder(field);
      }
    });
  });

  refreshArticleList(currentCondition);
});