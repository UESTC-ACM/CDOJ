# A markdown editor
# require bootstrap3, jQuery, sugar, mathjax and marked
class Flandre
  constructor: (@options) ->
    @element = @options.element
    if @element == null || @element.length == 0
      return null
    @createTextarea()
    @toolbar()

  # Escape html
  escape: (content) ->
    content = content.replace(/</g, '&lt;')
    content = content.replace(/>/g, '&gt;')
    content = content.replace(/\n/g, '<br>')
    content = content.replace(/<br>\s/g, '<br>&nbsp;')
    content = content.replace(/\s\s\s/g, '&nbsp; &nbsp;')
    content = content.replace(/\s\s/g, '&nbsp; ')
    content = content.replace(/^ /, '&nbsp;')
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
            <div class="btn-group">
              <button type="button" class="btn btn-default btn-sm" id="tool-picture"><i class="icon-picture"></i></button>
            </div>
          </div>
        </div>
        <div class="tex2jax_ignore" contenteditable="true" id="flandre-editor">#{@escape(oldText)}</div>
        <div id="flandre-preview"></div>
      </div>
      """
    @element.append(template)

  toolbar: ->
    editor = @element.find("#flandre-editor")
    preview = @element.find("#flandre-preview")
    toolPreview = @element.find("#tool-preview")
    toolPreview.click (e) =>
      $el = $(e.currentTarget)
      isActive = $el.hasClass("active")
      editor.css("display", "none")
      preview.css("display", "none")
      if isActive
        editor.css("display", "block")
      else
        text = editor[0].innerText.escapeHTML()

        preview.empty().append(text)
        preview.markdown()
        preview.prettify()
        preview.mathjax()

        preview.css("display", "block")
      $el.button("toggle")
    toolPicture = @element.find("#tool-picture")

  getText: ->
    return @element.find("#flandre-editor")[0].innerText

$ = jQuery
# Build on element
$.fn.flandre = (options) ->
  if options != undefined
    options["element"] = $(this)
  else
    options = element: $(this)
  flandre = new Flandre(options)
  return flandre