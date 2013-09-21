/**
 * Javascript for user admin page.
 */

/**
 * current search condition
 */
var currentCondition;

/**
 * current user list
 */
var userList;

function getUserName(email, userName) {
  var html = $('<td style="text-align: left;">' +
      '<img id="usersAvatar" email="' + email + '"/>' +
      '<a href="/user/center/' + userName + '">' + userName + '</a>' +
      '</td>');
  return html;
}

function getNickName(nickName, type) {
  var html = $('<td>' + nickName + '</td>');
  if (type != 0)
    html.addClass('userName-type' + type);
  return html;
}
/**
 * refresh the user list
 * @param condition
 */
function refreshUserList(condition) {
  jsonPost('/user/search', condition, function (data) {
    if (data.result == "error") {
      alert(data["error_msg"]);
      return;
    }

    //pagination
    $('#pageInfo').empty();
    $('#pageInfo').append(data.pageInfo);
    $('#pageInfo').find('a').click(function (e) {
      if ($(this).attr('href') == null)
        return false;
      currentCondition.currentPage = $(this).attr("href");
      refreshUserList(currentCondition);
      return false;
    });

    userList = data.userList;
    var tbody = $('#userList');
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

function changeOrder(field) {
  if (currentCondition["userCondition.orderFields"] == field)
    currentCondition["userCondition.orderAsc"] = (currentCondition["userCondition.orderAsc"] == "true" ? "false" : "true");
  else {
    currentCondition["userCondition.orderFields"] = field;
    currentCondition["userCondition.orderAsc"] = "false";
  }
  refreshUserList(currentCondition);
}

$(document).ready(function () {

  $('#departmentId').prepend('<option value="-1">All</option>');
  $('#departmentId').attr("value", -1);
  $('#type').prepend('<option value="-1">All</option>');
  $('#type').attr("value", -1);

  currentCondition = {
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

  $('input#search').setButton({
    callback: function () {
      currentCondition = $('#userCondition').getFormData();
      currentCondition.currentPage = 1;
      refreshUserList(currentCondition);
      $('#TabMenu a:first').tab('show');
    }
  });

  $('input#reset').setButton({
    callback: function () {
      $('#userCondition').resetFormData();
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

  refreshUserList(currentCondition);
});