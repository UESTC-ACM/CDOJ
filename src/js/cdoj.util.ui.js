/**
 * Blind dialog to a modal.
 */

(function ($) {
  'use strict';

  /**
   * Set button information.
   *
   * @param userOptions
   */
  $.fn.setButton = function (userOptions) {
    var options = mergeOptions({
          callback: function () {
          }
        },
        userOptions);

    $.each(this, function () {
      $(this).on('click', function () {
        options.callback();
        return false;
      });
    });

    return this;
  };

}(jQuery));
