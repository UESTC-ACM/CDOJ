/**
 * Javascript for user admin page.
 */

/**
 * current search condition
 */
var currentCondition;

/**
 * refresh the user list
 * @param condition
 */
function refreshUserList(condition) {
  jsonPost('/admin/user/search', condition, function (data) {
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
      refreshUserList(currentCondition);
      return false;
    });

    var userList = data.userList;
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
          '<td class="cdoj-time">' + value.lastLogin + '</td>' +
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

/**
 * show dialog to edit user defined in userList[index]
 *
 * @param index
 * @return always return false to stop url jump event.
 */
function editUserDialog(index) {
  var user = userList[index];
  var userDTO = {
    "userId": user.userId,
    "nickName": user.nickName,
    "email": user.email,
    "school": user.school,
    "departmentId": user.departmentId,
    "studentId": user.studentId,
    "type": user.type
  };
  $('#userEditModal').setFormData(userDTO);
  var dialog = $('#userEditModal');
  dialog.find('#userEditModalLabel').empty();
  dialog.find('#userEditModalLabel').append(user.userName);
  dialog.modal();
  return false;
}

function changeOrder(field) {
  if (currentCondition["orderFields"] == field)
    currentCondition["orderAsc"] = (currentCondition["orderAsc"] == "true" ? "false" : "true");
  else {
    currentCondition["orderFields"] = field;
    currentCondition["orderAsc"] = "false";
  }
  refreshUserList(currentCondition);
}

$(document).ready(function () {

  $('#userCondition').find('#departmentId').prepend('<option value="-1">All</option>');
  $('#userCondition').find('departmentId').attr("value", -1);
  $('#userCondition').find('#type').prepend('<option value="-1">All</option>');
  $('#userCondition').find('type').attr("value", -1);

  currentCondition = {
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

  $('#userEditModal').setDialog({
    callback: function () {
      var info = $('#userEditModal').getFormData();
      //console.log(info);
      jsonPost('/admin/user/edit', info, function (data) {
        $("#userEditModal").find(".form-horizontal").formValidate({
          result: data,
          onSuccess: function () {
            $("#userEditModal").modal('hide');
            refreshUserList(currentCondition);
          }
        });
      });
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