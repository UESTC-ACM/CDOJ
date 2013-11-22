$(document).ready(function () {
  var url = window.location.pathname;
  var $cdoj_menu = $('.cdoj-menu');
  var current_position = 'home';
  ['problem', 'contest', 'status', 'user', 'article'].each(function(now) {
    if (url.startsWith('/' + now + '/') || url.startsWith('/admin/' + now + '/'))
      current_position = now;
  });
  $cdoj_menu.find('#menu-item-' + current_position).addClass('cdoj-menu-selected');
});