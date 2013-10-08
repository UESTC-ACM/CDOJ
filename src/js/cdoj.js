/**
 * Javascript for all page.
 */

var currentUser;

$(function () {
	currentUser = $('#currentUser')[0].innerHTML;
	if (currentUser !== undefined) {
    $('#userAvatar').setAvatar({
      image: 'http://www.acm.uestc.edu.cn/images/akari_small.jpg',
			size: 120
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
});
