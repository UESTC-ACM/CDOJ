# Submit and submit...
initProblemPage = ->
  if $("#problem-show").length > 0
    $currentUser = $("#currentUser")
    @userLogin = if $currentUser.length != 0 then true else false
    problemId = $("#problem-title").attr("value")
    if @userLogin
      $("#submit-code").click =>
        info =
          codeContent: $("#code-content").val()
          languageId: $(":radio[name='language']:checked").attr('value')
          contestId: null
          problemId: problemId
        jsonPost("/status/submit", info, (data)=>
          if data.result == "error"
            alert data.error_msg
          else if data.result == "success"
            window.location.href = "/status/list"
        )
        return false