/**
 * Javascript for all page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */

!function ($) {

  $(function () {

    // make code pretty
    prettify();

    // get avatars
    $('#userAvatar').setAvatar({
      image: 'http://www.acm.uestc.edu.cn/images/akari_small.jpg'
    });

    // format time style
    $('.cdoj-time').formatTimeStyle();

    var mult = 0.95;
    if (Sys.windows || Sys.safari)
      mult = 0.65;

    $('#registerModal').find('.modal-body').css('max-height', Math.min(450, $(window).height() * mult));

    $("#registerModal").setDialog({
      callback: function() {
        info=$("#registerModal").find(".form-horizontal").getFormData();
        $.post('/user/register', info, function(data) {
          $("#registerModal").find(".form-horizontal").checkValidate({
            result: data,
            onSuccess: function(e) {
              $("#registerModal").modal('hide');
              window.location.reload();
            }
          });
        });
      }
    });

    $("#loginModal").setDialog({
      callback: function() {
        info=$("#loginModal").find(".form-horizontal").getFormData();
        $.post('/user/login', info, function(data) {
          console.log(data);
          $("#loginModal").find(".form-horizontal").checkValidate({
            result: data,
            onSuccess: function(e) {
              $("#loginModal").modal('hide');
              //window.location.reload();
            }
          });
        });
      },
      blindEnterKey: true
    });

    $("#activateModal").setDialog({
      callback: function() {
        info=$("#activateModal").find(".form-horizontal").getFormData();
        $.post('/user/sendSerialKey/' + info.userName, function(data) {
          if (data.result == 'ok') {
            alert('We send you an Email with the url to reset your password right now, please check your mail box.');
            $('#activateModal').modal('hide');
          } else
            alert(data.err_msg);
        });
      },
      blindEnterKey: true
    });

    $("#logoutButton").setButton({
      callback: function() {
        $.post('/user/logout',function(data) {
          if (data["result"] == "ok")
            window.location.reload();
        });
      }
    });

  })
}(window.jQuery)

