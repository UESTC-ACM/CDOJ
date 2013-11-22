# A markdown editor
# require bootstrap3, jQuery, sugar, mathjax and marked
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
    oldText = @element[0].innerHTML
    @element.empty()
    template = """
      <div class="panel panel-default">
        <div class="panel-heading" id="flandre-heading">
          <div class="btn-toolbar" role="toolbar">
            <div class="btn-group">
              <button type="button" class="btn btn-default btn-sm" id="tool-preview">Preview</button>
            </div>
            <div class="btn-group flandre-tools">
              <button type="button" class="btn btn-default btn-sm" id="tool-emotion"><i class="fa fa-meh-o"></i></button>
              <button type="button" class="btn btn-default btn-sm" id="tool-picture"><i class="fa fa-picture-o"></i></button>
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
    emotionDialog = "emotion-dialog-#{@element.attr("id")}"
    toolEmotion.popover(
      placement: "bottom"
      html: true
      container: "body"
      title: """
        <ul class="nav nav-pills">
          <li class="active"><a href="#emotion-brd" data-toggle="tab">BRD</a></li>
        </ul>
        """
      content: """
        <div id="#{emotionDialog}" style="width: auto;">
          <div class="tab-content">
            <div class="tab-pane active" id="emotion-brd">
              #{emotionTable("/plugins/cdoj/img/emotion/brd", "gif", 40)}
            </div>
          </div>
        </div>
        """
    )
    toolEmotion.on("shown.bs.popover", ->
      $("##{emotionDialog}").find("td").click (e) =>
        $el = $(e.currentTarget)
        value = $el.attr("value")
        editor.insertAfterCursor(value, 0)
        toolEmotion.popover("toggle")
    )

    toolPicture = @element.find("#tool-picture")
    pictureDialog = "picture-dialog-#{@element.attr("id")}"
    toolPicture.popover(
      placement: "bottom"
      html: true
      container: "body"
      content: """
        <div id="#{pictureDialog}">
          <div class="btn btn-primary" id="picture-upload-button">Upload picture</div>
        </div>
        """
    )
    toolPicture.on("shown.bs.popover", ->
      $button = $("picture-upload-button");
      uploader = new qq.FineUploaderBasic(
        button: $button[0]
        request:
          endpoint: options.picture.uploadUrl
      )
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