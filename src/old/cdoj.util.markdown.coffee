$ = jQuery
# unescape html in pre tag
$.fn.unescapePre = ->
  this.each (id, el)=>
    $el = $(el)
    str = "<pre>#{$el[0].innerHTML}</pre>".unescapeHTML()
    $el.replaceWith($("<div>#{str}</div>"))

# unescape html in code tag
$.fn.unescapeCode = ->
  this.each (id, el)=>
    $el = $(el)
    str = "<code>#{$el[0].innerHTML.unescapeHTML()}</code>"
    $el.replaceWith(str)

$.fn.renderTable = ->
  this.each (id, el)=>
    $el = $(el)
    $el.addClass("table").addClass("table-bordered").addClass("table-condensed").addClass("cdoj-markdown-table")

$.fn.renderImage = ->
  this.each (id, el)=>
    $el = $(el)
    # $el.addClass("img-thumbnail").addClass("cdoj-markdown-img")

# render markdown content use marked
$.fn.markdown = ->
  this.each (id, el)=>
    $el = $(el)
    md = $el.html().trim().replace('<textarea>','')
      .replace('</textarea>','')
      .replace('<TEXTAREA>','')
      .replace('</TEXTAREA>','')
      .trim()
    html = $(marked(md))
    $el.empty().append(html)
    $el.find("code").unescapeCode()
    $el.find("pre").unescapePre()
    $el.find("table").renderTable()
    $el.find("img").renderImage()
