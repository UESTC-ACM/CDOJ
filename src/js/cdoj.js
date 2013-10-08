/**
 * Javascript for all page.
 */

var currentUser;

$(document).ready(function () {
  var $currentUser = $('#currentUser');
  if ($currentUser.length !== 0) {
    currentUser = $currentUser[0].innerHTML;
    $('#userAvatar').setAvatar({
      image: 'http://www.acm.uestc.edu.cn/images/akari_small.jpg',
      size: $('#userAvatar').width()
    });
  }

  $('#cdoj-login-button').setButton({
    callback: function() {
      var info=$('#cdoj-login-form').getFormData();

      jsonPost('/user/login', info, function(data) {
        $('#cdoj-login-form').formValidate({
          result: data,
          onSuccess: function() {
            window.location.reload();
          }
        });
      });
    }
  });

  $("#logoutButton").setButton({
    callback: function() {
      //noinspection JSUnresolvedFunction
      $.post('/user/logout', function(data) {
        if (data.result == "success")
          window.location.reload();
      });
    }
  });
});
