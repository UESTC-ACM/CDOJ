initUserActivate = ->
  $cdojActivationForm = $("#cdoj-activation-form")
  if $cdojActivationForm.length != 0
    $cdojActivationForm.find("#submit-button").click =>
      info = $cdojActivationForm.getFormData()
      jsonPost("/user/resetPassword"
        info
        (data) =>
          $cdojActivationForm.formValidate
            result: data,
            onSuccess: ->
              alert("Success!")
              window.location.href = "/"
      )
      return false