$ = jQuery
# unescape html in pre tag
$.fn.prePrettify = ->
  this.each (id, el)=>
    $el = $(el)
    $el.addClass("prettyprint linenums") if $el.attr("type") != "no-prettify"

$.fn.prettify = ->
  this.find("pre").each (id, el)=>
    text = prettyPrintOne($(el)[0].innerText.escapeHTML())
    $(el).empty().append(text)