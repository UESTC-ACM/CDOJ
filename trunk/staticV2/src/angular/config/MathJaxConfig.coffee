angular.module("cdojV2").run(->
  MathJax.Hub.Config(
    tex2jax:
      inlineMath: [
        [ '$', '$' ],
        [ '\\[', '\\]' ]
      ]
  )
)