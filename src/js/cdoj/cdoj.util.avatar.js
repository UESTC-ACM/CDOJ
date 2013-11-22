/**
 * Blind setAvatar function to nodes.
 */

(function($) {
  'use strict';

  /**
   * Set selected nodes into valid avatar images.
   *
   * @param userOptions
   */
  $.fn.setAvatar = function(userOptions) {
    var options = mergeOptions({
          method: 'append',
          size: 40,
          image: 'http://www.acm.uestc.edu.cn/images/akari.jpg',
          rating: 'pg'
        },
        userOptions);

    $.each(this, function() {
      var self = $(this);
      var selfId = self.attr('id');
      if (self.attr('size') !== undefined)
        options.size = self.attr('size');
      var result = $.gravatar(self.attr('email'), options)
        .attr('id', selfId)
        .addClass(self.attr('class'));
      self.replaceWith(result);
    });

    return this;
  };

}(jQuery));
