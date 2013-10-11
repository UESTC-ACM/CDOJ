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

  $.fn.setSelector = function (userOptions) {
    var options = mergeOptions({
      activeClass: 'pure-menu-selected',
      disabledClass: 'pure-menu-disabled'
    }, userOptions);

    $.each(this, function() {
      var $selector = $(this);
      $.each($selector.find('button'), function() {
        console.log($(this));
      });
    });
  };

  $.fn.setTab = function (userOptions) {
    var options = mergeOptions({
      activeClass: 'pure-menu-selected',
      disabledClass: 'pure-menu-disabled'
    }, userOptions);

    $.each(this, function() {
      var tabs = [];
      var $tabPlane = $(this).find('ul');
      var currentTab = null;

      function clean(tab) {
        tab.link.removeClass(options.activeClass);
        $(tab.id).css('display', 'none');
      }
      function show(tab) {
        if (currentTab !== null) {
          clean(currentTab);
        }
        if (currentTab !== tab.id) {
          tab.link.addClass(options.activeClass);
          $(tab.id).css('display', 'block');
          currentTab = tab;
        }
      }

      $.each($tabPlane.find('li'), function() {
        var $tabLink = $(this).children('a');
        if ($($tabLink).length > 0) {
          var $tabId = $tabLink.attr('href');
          var value = {
            id: $tabId,
            link: $(this)
          };
          if ($(this).hasClass(options.disabledClass) === false) {
            tabs.add(value);
            $tabLink.on('click', function() {
              show(value);
            });
          }
          clean(value);
        }
      });
      if (tabs.length === 0) return;

      show(tabs[0]);
    });
  };

}(jQuery));
