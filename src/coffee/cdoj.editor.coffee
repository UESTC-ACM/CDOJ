# A markdown editor
# require jQuery and sugar
class Flandre
  constructor: (@options) ->
    @element = @options.element
    if @element == null || @element.length == 0
      return null
    this.createTextarea()

  setText: (el, content) ->
    content = content.replace(/</g, '&lt;')
    content = content.replace(/>/g, '&gt;')
    content = content.replace(/\n/g, '<br>')

    content = content.replace(/<br>\s/g, '<br>&nbsp;')
    content = content.replace(/\s\s\s/g, '&nbsp; &nbsp;')
    content = content.replace(/\s\s/g, '&nbsp; ')
    content = content.replace(/^ /, '&nbsp;')

    el.innerHTML = content

  createTextarea: ->
    oldText = @element[0].innerHTML
    @element.empty()
    this.setText(@element[0], oldText)
    @element.addClass("flandre tex2jax_ignore form-control").attr("contentEditable", "true")

$ = jQuery
# Build on element
$.fn.flandre = (@options) ->
  @options["element"] = $(this)
  flandre = new Flandre(@options)