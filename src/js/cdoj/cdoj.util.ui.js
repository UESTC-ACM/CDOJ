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

  $.fn.setButtonRadio = function (userOptions) {
    var options = mergeOptions({
          callback: function () {
          }
        },
        userOptions);

    $.each(this, function () {
      var $this = $(this);
      $this.find('button').addClass('middle');
      $this.find('button:first-child').removeClass('middle').addClass('left');
      $this.find('button:last-child').removeClass('middle').addClass('right');
      $this.find('.active').addClass('red');

      $this.find('button').on('click', function() {
        $this.find('.active').removeClass('active');
        $(this).addClass('active');
        $this.find('.red').removeClass('red');
        $(this).addClass('red');
      });
    });

    return this;
  };

}(jQuery));
