$(document).ready(function () {
  var url = window.location.pathname;
  var $cdoj_menu = $('#cdoj-menu');
  var current_position = 'home';
  ['problem', 'contest', 'status', 'user'].each(function(now) {
    if (url.startsWith('/' + now + '/'))
      current_position = now;
  });
  $cdoj_menu.find('#menu-item-' + current_position).addClass('pure-menu-selected');
});