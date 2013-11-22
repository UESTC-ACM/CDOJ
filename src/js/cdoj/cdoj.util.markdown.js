/**
 * Blind marked to jQuery node element.
 */

(function($) {
  'use strict';

  $.fn.markdown = function() {
    console.log(this.innerHTML.toString());
  };

}(jQuery));

function markdown() {
  $.each($('[type="markdown"]'),function() {
    //IE7 and IE8's html() method will delete all line breakers EXCEPT in <textarea>
    var md = $(this).html()
        .replace('<textarea>','')
        .replace('</textarea>','')
        .replace('<TEXTAREA>','')
        .replace('</TEXTAREA>','');

    //IE7 and IE8 not support .trim() method.
    md = js.lang.String.trim(md);

    //noinspection JSUnresolvedFunction
    var markedHTML = $(marked(md));

    $(this).empty().append(markedHTML);

    $.each($(this).find('pre'), function(){
      //unescape all characters in <pre></pre>
      var str = $(this)[0].innerHTML;
      str = '<pre>' + str + '</pre>';
      str = js.lang.String.decodeHtml(str);
      $(this).replaceWith($('<div class="markdown-temp"></div>'));
      var nowDiv = $('.markdown-temp');
      $(nowDiv).append(str);
      $(nowDiv).removeClass('markdown-temp');
    });
  });
}