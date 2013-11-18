initProblemEditor = ->
  $editor = $("#problem-editor")
  if $editor.length > 0
    editorsId = ["description", "input", "output", "sampleInput", "sampleOutput", "hint"]
    editorsId.each((value, id) ->
      $("##{value}").flandre(foo: "bar")
    )