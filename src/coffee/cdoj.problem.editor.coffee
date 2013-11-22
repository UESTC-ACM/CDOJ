initProblemEditor = ->
  $editor = $("#problem-editor")
  if $editor.length > 0
    action = $("#problem-editor-title").attr("value")
    editorsId = ["description", "input", "output", "hint"]
    editors = []
    editorsId.each((value, id) ->
      editors.push(
        id: value
        editor: $("##{value}").flandre(
          picture:
            uploadUrl: "/picture/uploadPicture/problem/#{action}"
        )
      )
    )
    $("#submit").click =>
      info =
        sampleInput: $("textarea#sample-input").val()
        sampleOutput: $("textarea#sample-output").val()
        title: $("#title").val()
        source: $("#source").val()
      editors.each((value, id) ->
        info[value.id] = value.editor.getText()
      )
      if action == "new"
        info["action"] = "new"
      else
        info["action"] = "edit"
        info["problemId"] = action
      jsonPost("/problem/edit", info, (data) ->
        if data.result == "success"
          window.location.href = "/problem/show/#{data.problemId}"
        else if data.result == "field_error"
          # must be empty title
          alert("Please enter a valid title!")
        else
          alert(data.error_msg)
      )
      return false
