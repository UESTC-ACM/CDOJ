initLayout = ->
  $cdojNavbar = $("#cdoj-navbar")
  $cdojContainer = $("#cdoj-container")
  $cdojUser = $cdojNavbar.find("#cdoj-user")
  $cdojNavbarMenu = $cdojNavbar.find("#cdoj-navbar-menu")

  $mzry1992Header = $cdojContainer.find("#mzry1992-header")
  $mzry1992Container = $cdojContainer.find("#mzry1992-container")

  # Make sure #mzry1992-header is always on the top of screen and do not hidden anything of #mzry1992-container
  if $mzry1992Header.length != 0 && $mzry1992Container.length != 0
    $mzry1992Container.css("margin-top", $mzry1992Header.outerHeight())

  # Set current page
  currentUrl = window.location.pathname
  current_position = "home";
  for pos in ["problem", "contest", "status", "user", "article"]
    current_position = pos if currentUrl.startsWith("/" + pos + "/")
  $cdojNavbarMenu.find('#menu-item-' + current_position).addClass('cdoj-menu-selected');