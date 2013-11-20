$ = jQuery

$.fn.mathjax = ->
  MathJax.Hub.Config(
    tex2jax :
      inlineMath : [ [ '$', '$' ], [ '\\[', '\\]' ] ]
  )
  this.each (id, el)=>
    MathJax.Hub.Queue(["Typeset", MathJax.Hub, el]);