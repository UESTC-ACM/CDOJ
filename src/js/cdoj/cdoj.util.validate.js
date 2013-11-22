/**
 * Validate form data (base on server return result)
 */

(function($) {
  'use strict';

  /**
   * Set dialog information.
   *
   * @param userOptions
   */
  $.fn.formValidate = function(userOptions) {
    var options = mergeOptions({
          result: {'result': 'success'},
          onSuccess: function(){},
          onFail: function(){}
        },
        userOptions);

    var result = options.result;

    $.each(this, function() {
      var self = $(this);

      if (result.result === null) {
        alert('Unknown exception');
        return false;
      } else if (result.result == 'success') {
        options.onSuccess();
        return true;
      } else if (result.result == 'error') {
        alert(result.error_msg);
        return false;
      } else if (result.result == 'field_error') {
        // Clear existing errors on submit
        self.find('div.error').removeClass('error');
        self.find('span.s2_help_inline').remove();
        self.find('div.s2_validation_errors').remove();

        $.each(result.field, function(index, value) {
          var element = self.find(':input[name="' + value.field + '"]'), controlGroup, controls;
          if (element && element.length > 0) {

            // select first element
            element = $(element[0]);
            controlGroup = element.closest('div.control-group');
            controlGroup.addClass('error');
            controls = controlGroup.find('div.controls');
            if (controls) {
              controls.append('<span class="help-inline s2_help_inline">' + value.defaultMessage + '</span>');
            }
          }
        });

        options.onFail();
        return false;
      } else {
        return false;
      }
    });
  };

}(jQuery));
