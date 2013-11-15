$ = jQuery
# unescape html in pre tag
$.fn.prePrettify = ->
  this.each (id, el)=>
    $el = $(el)
    $el.addClass("prettyprint linenums") if $el.attr("type") != "no-prettify"