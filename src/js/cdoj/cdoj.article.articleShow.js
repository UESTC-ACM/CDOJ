/**
 * All function used in article page.
 */

$(document).ready(function () {
  var currentArticle = $('#article_title').attr('value');

  markdown();

  $.each($('.sample'), function() {
    var md = $(this).html().replace('<textarea>', '').replace(
            '</textarea>', '').replace('<TEXTAREA>', '').replace('</TEXTAREA>',
            '').replace('\r', '<br/>');
    $(this).empty().append(md);
  });

  // make code pretty
  prettify();

  MathJax.Hub.Config({
    tex2jax : {
      inlineMath : [ [ '$', '$' ], [ '\\[', '\\]' ] ]
    }
  });
  MathJax.Hub.Queue([ 'Typeset', MathJax.Hub ]);
});
