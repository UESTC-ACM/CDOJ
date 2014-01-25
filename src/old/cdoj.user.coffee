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

  #Set loginout button when user not logined
  if @user.userLogin
    $("#cdoj-profile-edit-button").click =>
      $profileEditForm = $("#cdoj-profile-edit-form")
      info = $profileEditForm.getFormData()
      delete info["newPassword"] if info.newPassword == ""
      delete info["newPasswordRepeat"] if info.newPasswordRepeat == ""
      jsonPost("/user/edit"
        info
        (data) =>
          $profileEditForm.formValidate
            result: data,
            onSuccess: ->
              window.location.reload()
      )
      return false
