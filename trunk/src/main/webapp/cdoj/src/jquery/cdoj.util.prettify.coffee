$ = jQuery
$.fn.prettify = ->
  this.find("pre").each (id, el)=>
    $el = $(el)
    if $el.attr("type") != "no-prettify"
      text = prettyPrintOne($el[0].innerText.escapeHTML())
      $el.empty().append(text)