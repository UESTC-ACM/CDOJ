/**
 * float header
 */

$(document).ready(function () {
  if (!Sys.ie678) {
    var $nav = $('.mzry1992-header'), $content = $('.mzry1992-content');
    var navHeight = $nav.outerHeight();
    $content.css('margin-top', navHeight);
  }
});
