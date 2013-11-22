initProblemDataEditor = ->
  $editor = $("#problem-data-editor")
  if $editor.length > 0
    problemId = $editor.find("#problem-data-editor-title").attr("value")

    $editor.find("#submit").click =>
      $form = $editor.find("#problem-data-form")
      info = $form.getFormData()
      info["problemId"] = problemId
      jsonPost("/problem/updateProblemData"
        info
        (data) =>
          $form.formValidate
            result: data,
            onSuccess: ->
              window.location.reload()
      )
      return false

    $dataUploadButton = $editor.find("#problem-data-uploader")
    dataUploader = new qq.FineUploaderBasic(
      button: $dataUploadButton[0]
      request:
        endpoint: "/problem/uploadProblemDataFile/#{problemId}"
        inputName: "uploadFile"
      validation:
        allowedExtensions: ["zip"],
        sizeLimit: 100 * 1024 * 1024 # 100 MB
      multiple: false
      callbacks:
        onComplete: (id, fileName, data) ->
          if data.success == "true"
            template = """
              <div class="alert alert-success">
                Total data: #{data.total}
              </div>
              """
          else
            template = """
              <div class="alert alert-danger">
                #{data.error}
              </div>
              """
          $editor.find("#uploader-info").empty().append(template)
    )
