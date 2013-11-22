# A markdown editor
# require bootstrap3, jQuery, sugar, mathjax, fine-uploader and marked
class Flandre
  constructor: (options) ->
    @options = options
    @element = @options.element
    if @element == null || @element.length == 0
      return null
    @createTextarea()
    @toolbar()

  # Escape html
  escape: (content) ->
    #content = content.replace(/</g, '&lt;')
    #content = content.replace(/>/g, '&gt;')
    #content = content.replace(/\n/g, '<br>')
    #content = content.replace(/<br>\s/g, '<br>&nbsp;')
    #content = content.replace(/\s\s\s/g, '&nbsp; &nbsp;')
    #content = content.replace(/\s\s/g, '&nbsp; ')
    #content = content.replace(/^ /, '&nbsp;')
    return content

  createTextarea: ->
    oldText = @element[0].innerHTML.trim()
    @element.empty()
    template = """
      <div class="panel panel-default">
        <div class="panel-heading" id="flandre-heading">
          <div class="btn-toolbar" role="toolbar">
            <div class="btn-group">
              <button type="button" class="btn btn-default btn-sm" id="tool-preview">Preview</button>
            </div>
            <div class="btn-group flandre-tools">
              <a class="btn btn-default btn-sm" id="tool-emotion"><i class="fa fa-meh-o"></i></a>
              <a class="btn btn-default btn-sm" id="tool-picture"><i class="fa fa-picture-o"></i></a>
            </div>
          </div>
        </div>
        <textarea class="tex2jax_ignore form-control" id="flandre-editor">#{@escape(oldText)}</textarea>
        <div id="flandre-preview"></div>
      </div>
      """
    @element.append(template)

  toolbar: ->
    editor = @element.find("#flandre-editor")
    preview = @element.find("#flandre-preview")
    options = @options

    editor.elastic()

    toolPreview = @element.find("#tool-preview")
    toolPreview.click (e) =>
      $el = $(e.currentTarget)
      isActive = $el.hasClass("active")
      editor.css("display", "none")
      preview.css("display", "none")
      if isActive
        editor.css("display", "block")
        @element.find(".flandre-tools").css("display", "block")
      else
        text = editor.val().escapeHTML();

        preview.empty().append(text)
        preview.markdown()
        preview.prettify()
        preview.mathjax()

        preview.css("display", "block")
        @element.find(".flandre-tools").css("display", "none")

      $el.button("toggle")

    toolEmotion = @element.find("#tool-emotion")
    emotionModalId = "emotion-modal-#{@element.attr("id")}"
    emotionModalTemplate = """
      <div class="modal fade" id="#{emotionModalId}" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
              <h4 class="modal-title">
                <ul class="nav nav-pills">
                  <li class="active"><a href="#emotion-brd" data-toggle="tab">BRD</a></li>
                </ul>
              </h4>
            </div>
            <div class="modal-body">
              <div id="emotion-dialog" style="width: auto;" value="false">
                <div class="tab-content">
                  <div class="tab-pane active" id="emotion-brd">
                    #{emotionTable("/plugins/cdoj/img/emotion/brd", "gif", 40)}
                  </div>
                </div>
              </div>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
      """
    $(document.body).append(emotionModalTemplate)
    emotionModal = $("##{emotionModalId}")
    toolEmotion.click =>
      emotionModal.modal("show")
    emotionModal.on("shown.bs.modal", ->
      emotionModal.find("#emotion-dialog").find("td").on("click", (e) =>
        $el = $(e.currentTarget)
        value = $el.attr("value")
        editor.insertAfterCursor(value, 0)
        emotionModal.modal("hide")
      )
    )

    toolPicture = @element.find("#tool-picture")
    pictureUploader = new qq.FineUploaderBasic(
      button: toolPicture[0]
      request:
        endpoint: options.picture.uploadUrl
        inputName: "uploadFile"
      validation:
        allowedExtensions: ["jpeg", "jpg", "gif", "png"],
        sizeLimit: 10 * 1024 * 1024 # 10 MB
      multiple: false
      callbacks:
        onComplete: (id, fileName, data) ->
          if data.success == "true"
            value = "![#{data.uploadedFile}](#{data.uploadedFileUrl})"
            editor.insertAfterCursor(value, 0)
          else
            # TODO
            console.log(data)
    )

  getText: ->
    return @element.find("#flandre-editor").val()

$ = jQuery
# Build on element
$.fn.flandre = (options) ->
  if options != undefined
    options["element"] = $(this)
  else
    options = element: $(this)
  flandre = new Flandre(options)
  return flandre