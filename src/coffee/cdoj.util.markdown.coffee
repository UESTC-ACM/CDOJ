$ = jQuery
# unescape html in pre tag
$.fn.unescapePre = ->
  this.each (id, el)=>
    $el = $(el)
    str = "<pre>#{$el[0].innerHTML}</pre>".unescapeHTML()
    $el.replaceWith($("<div>#{str}</div>"))

# render markdown content use marked
$.fn.markdown = ->
  this.each (id, el)=>
    $el = $(el)
    md = $el.html().replace('<textarea>','')
      .replace('</textarea>','')
      .replace('<TEXTAREA>','')
      .replace('</TEXTAREA>','')
      .trim()
    html = $(marked(md))
    $el.empty().append(html)
    $el.find("pre").unescapePre()
