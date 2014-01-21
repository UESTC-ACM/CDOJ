# render all markdown content on page
render = ->
  # render all markdown page
  $("[type=\"markdown\"]").markdown()

  # render all math equations
  MathJax.Hub.Config(
    tex2jax :
      inlineMath : [ [ '$', '$' ], [ '\\[', '\\]' ] ]
  )
  MathJax.Hub.Queue([ 'Typeset', MathJax.Hub ])

  # prettify
  $(document.body).prettify()