/**
 * Blind dialog to a modal.
 */

(function ($) {
  'use strict';

  /**
   * Set dialog information.
   *
   * @param userOptions
   */
  $.fn.setDialog = function (userOptions) {
    var options = mergeOptions({
          callback: function () {
          },
          button: $('a.btn-primary'),
          blindEnterKey: false
        },
        userOptions);

    $.each(this, function () {
      var self = $(this);
      self.on('show', function () {    // wire up the button
        options.button.on('click', function () {
          options.callback();
          return false;
        });
        if (options.blindEnterKey) {
          self.find('form').on('keydown', function () {
            if (event.keyCode == 13) {
              options.callback();
              return false;
            }
          });
        }
      });
      self.on('hide', function () {    // remove the event listeners when the dialog is dismissed
        options.button.off('click');
      });
    });

    return this;
  };

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
      $(this).on("click", function () {
        options.callback();
        return false;
      });
    });

    return this;
  };

}(jQuery));