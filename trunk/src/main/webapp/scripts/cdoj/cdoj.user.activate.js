/**
 * Javascript for account activation page
 */

!function ($) {

  $(function () {

    $("#accountActivationSubmit").setButton({
      callback: function() {
        var info = $('#accountActivationForm').getFormData();
        console.log(info);
        jsonPost('/user/resetPassword', info, function(data) {
          console.log(data);
          $("#accountActivationForm").formValidate({
            result: data,
            onSuccess: function() {
              alert('Success');
              window.location = '/';
            }
          });
        });
      }
    });

  })
}(window.jQuery);