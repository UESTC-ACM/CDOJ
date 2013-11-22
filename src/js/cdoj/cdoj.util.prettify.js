/**
 * Tools for use prettify.
 */

function prettify() {
  var pres = $('pre');
  $.each(pres,function() {
    if ($(this).attr('type') == 'no-prettify')
      return;
    $(this).addClass('prettyprint linenums');
  });
  //noinspection JSUnresolvedFunction,JSUnresolvedVariable
  if (window.prettyPrint && prettyPrint()) {

  }
}