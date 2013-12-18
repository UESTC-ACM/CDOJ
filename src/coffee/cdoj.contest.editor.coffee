initContestEditor = ->
  $editor = $("#contest-editor")
  if $editor.length > 0
    action = $("#contest-editor-title").attr("value")
    editorsId = ["description"]
    editors = []
    editorsId.each((value, id) ->
      editors.push(
        id: value
        editor: $("##{value}").flandre(
          picture:
            uploadUrl: "/picture/uploadPicture/contest/#{action}"
        )
      )
    )
    $('.datepicker').datepicker();