initArticleEditor = ->
  $editor = $("#article-editor")
  if $editor.length > 0
    action = $("#article-editor-title").attr("value")
    editorsId = ["content"]
    editors = []
    editorsId.each((value, id) ->
      editors.push(
        id: value
        editor: $("##{value}").flandre(
          picture:
            uploadUrl: "/picture/uploadPicture/article/#{action}"
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
        info["articleId"] = action
      jsonPost("/article/edit", info, (data) ->
        if data.result == "success"
          window.location.href = "/article/show/#{data.articleId}"
        else if data.result == "field_error"
          # must be empty title
          alert("Please enter a valid title!")
        else
          alert(data.error_msg)
      )
      return false
