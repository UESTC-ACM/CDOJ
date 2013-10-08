/**
 * Javascript for all page.
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

    $("#activateModal").setDialog({
      callback: function() {
        var info=$("#activateModal").find(".form-horizontal").getFormData();
        //noinspection JSUnresolvedFunction
        $.post('/user/sendSerialKey/' + info.userName, function(data) {
          if (data.result == 'success') {
            alert('We send you an Email with the url to reset your password right now, please check your mail box.');
            $('#activateModal').modal('hide');
          } else if (data.result = 'failed')
            alert('Unknown error occurred.');
          else
            alert(data['error_msg']);
        });
      },
      blindEnterKey: true
    });

    $("#registerModal").setDialog({
      callback: function() {
        var info=$("#registerModal").find(".form-horizontal").getFormData();
        jsonPost('/user/register', info, function(data) {
          $("#registerModal").find(".form-horizontal").formValidate({
            result: data,
            onSuccess: function() {
              $("#registerModal").modal('hide');
              window.location.reload();
            }
          });
        });
      }
    });

    $("#loginModal").setDialog({
      callback: function() {
        var info=$("#loginModal").find(".form-horizontal").getFormData();
        //info = {"userName": null, "password": "password"};

        jsonPost('/user/login', info, function(data) {
          console.log(data);
          $("#loginModal").find(".form-horizontal").formValidate({
            result: data,
            onSuccess: function() {
              $("#loginModal").modal('hide');
              window.location.reload();
            }
          });
        });
      },
      blindEnterKey: true
    });

    $("#logoutButton").setButton({
      callback: function() {
        //noinspection JSUnresolvedFunction
        $.post('/user/logout', function(data) {
          if (data["result"] == "success")
            window.location.reload();
        });
      }
    });

  })
}(window.jQuery);
