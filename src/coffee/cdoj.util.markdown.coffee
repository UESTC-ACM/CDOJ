markdown = (content) ->
  return marked(content)

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
