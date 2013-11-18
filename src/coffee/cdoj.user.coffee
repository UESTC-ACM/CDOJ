getCurrentUser = ->
  $currentUser = $("#currentUser")
  @userLogin = if $currentUser.length != 0 then true else false
  #Set user information and bind user avatar on page
  if @userLogin
    @currentUser = $currentUser[0].innerHTML.trim()
    @currentUserType = $currentUser.attr("type")
    return {
      userLogin: true
      currentUser: @currentUser
      currentUserType: @currentUserType
    }
  else
    return userLogin: false

initUser = ->
  @user = getCurrentUser()
  #Set user information and bind user avatar on page
  if @user.userLogin
    $userAvatar = $("#cdoj-user-avatar")
    $userAvatar.setAvatar
      image: "http://www.acm.uestc.edu.cn/images/akari_small.jpg"
      size: $userAvatar.width() if $userAvatar.width()

  #Set login && register && activate button when user not logined
  if @user.userLogin == false
    $("#cdoj-login-button").click =>
      $loginForm = $("#cdoj-login-form")
      info = $loginForm.getFormData()
      jsonPost("/user/login"
        info
        (data) =>
          $loginForm.formValidate
            result: data,
            onSuccess: ->
              window.location.reload()
      )
      return false
    $("#cdoj-register-button").click =>
      $registerForm = $("#cdoj-register-form")
      info = $registerForm.getFormData()
      jsonPost("/user/register"
        info
        (data) =>
          $registerForm.formValidate
            result: data,
            onSuccess: ->
              window.location.reload()
      )
      return false
    $("#cdoj-activate-button").click =>
      $activateForm = $("#cdoj-activate-form")
      info = $activateForm.getFormData()
      $.post("/user/sendSerialKey/" + info.userName
        (data) =>
          if data.result == "success"
            alert "We send you an Email with the url to reset your password right now, please check your mail box."
            $("#cdoj-activate-modal").modal("hide");
          else if data.result == "failed"
            alert "Unknown error occurred."
          else
            alert data.error_msg;
      )
      return false

  #Set loginout button when user not logined
  if @user.userLogin
    $("#cdoj-logout-button").click =>
      $.post("/user/logout"
        (data) =>
          if data.result == "success"
            window.location.reload()
      )
      return false
