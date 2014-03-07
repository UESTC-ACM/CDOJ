# Textarea cursor tools
# From http://www.jquery4u.com/snippets/6-jquery-cursor-functions/
$ = jQuery
$.fn.getCursorPosition = ->
  if this.lengh == 0 then return -1
  return $(this).getSelectionStart()

$.fn.setCursorPosition = (position) ->
  if this.lengh == 0 then return this
  return $(this).setSelection(position, position)

$.fn.getSelection = ->
  if this.lengh == 0 then return -1
  s = $(this).getSelectionStart()
  e = $(this).getSelectionEnd()
  return this[0].value.substring(s,e)

$.fn.getSelectionStart = ->
  if this.lengh == 0 then return -1
  input = this[0]

  pos = input.value.length
  if input.createTextRange
    r = document.selection.createRange().duplicate()
    r.moveEnd('character', input.value.length)
    if r.text == ''
      pos = input.value.length
    pos = input.value.lastIndexOf(r.text)
  else if typeof(input.selectionStart) != "undefined"
    pos = input.selectionStart
  return pos

$.fn.getSelectionEnd = ->
  if this.lengh == 0 then return -1
  input = this[0]

  pos = input.value.length
  if input.createTextRange
    r = document.selection.createRange().duplicate()
    r.moveStart('character', -input.value.length)
    if (r.text == '')
      pos = input.value.length
    pos = input.value.lastIndexOf(r.text)
  else if(typeof(input.selectionEnd)!="undefined")
    pos = input.selectionEnd
  return pos

$.fn.setSelection = (selectionStart, selectionEnd) ->
  if this.lengh == 0 then return this
  input = this[0]

  if input.createTextRange
    range = input.createTextRange()
    range.collapse(true)
    range.moveEnd('character', selectionEnd)
    range.moveStart('character', selectionStart)
    range.select()
  else if input.setSelectionRange
    input.focus()
    input.setSelectionRange(selectionStart, selectionEnd)
  return this

# Angular!
cdoj.directive("uiFlandre",
->
  restrict: "A"
  scope:
    content: "=ngModel"
    uploadUrl: "@"
  controller: [
    "$scope", "$element", "$window"
    ($scope, $element, $window) ->
      $scope.mode = "edit"
      $scope.previewContent = ""
      $editor = $element.find(".flandre-editor")

      # Preview button
      $scope.togglePreview = ->
        if $scope.mode == "edit"
          # preview
          $scope.previewContent = $scope.content
          $scope.mode = "preview"
        else
          # edit
          $scope.mode = "edit"

      # Upload picture
      pictureUploader = new qq.FineUploaderBasic(
        button: $($element).find(".flandre-picture-uploader")[0]
        request:
          endpoint: $scope.uploadUrl
          inputName: "uploadFile"
        validation:
          allowedExtensions: ["jpeg", "jpg", "gif", "png"],
          sizeLimit: 10 * 1000 * 1000 # 10 MB
        multiple: false
        callbacks:
          onComplete: (id, fileName, data) ->
            console.log data
            if data.success == "true"
              value = "![title](#{data.uploadedFileUrl})"
              position = $editor.getCursorPosition()
              oldText = $editor.val()
              oldText = oldText.insert(value, position)
              $scope.$apply(->
                $scope.content = oldText
              )
              # ![title](...)
              #   ^^^^^
              $editor.setSelection(position + 2, position + 7)
            else
              $window.alert data.error_msg
      )
  ]
  template: """
      <div class="panel panel-default">
        <div class="panel-heading flandre-heading">
          <div class="btn-toolbar" role="toolbar">
            <div class="btn-group">
              <button type="button" class="btn btn-default btn-sm"
                      ng-click="togglePreview()"
                      ng-class="{active: mode == 'preview'}">Preview</button>
            </div>
            <div class="btn-group flandre-tools">
              <span class="btn btn-default btn-sm"><i class="fa fa-smile-o"></i></span>
              <span class="btn btn-default btn-sm flandre-picture-uploader"><i class="fa fa-picture-o"></i></span>
            </div>
          </div>
        </div>
        <textarea class="tex2jax_ignore form-control flandre-editor"
                  msd-elastic
                  ng-class="{'flandre-show': mode == 'edit'}"
                  ng-model="content"></textarea>
        <div class="flandre-preview" ng-class="{'flandre-show': mode == 'preview'}">
          <markdown content="previewContent"></markdown>
        </div>
      </div>
      """
  replace: true
)