cdojV2
.controller("MenuController", [
    "$scope"
    ($scope) ->
      $scope.pages = []
      createMenuItem = (title, icon, label, href) ->
        page =
          title: title
          icon: icon
          label: label
          href: href
        $scope.pages.push(page)

      createMenuItem("Home", "action:home", "Go to home page", "#/")
      createMenuItem(
        "Problems", "action:assignment", "Go to problem list", "#/problem/list")
      createMenuItem(
        "Contests", "action:extension", "Go to contest list", "#/contest/list")
      createMenuItem(
        "Status", "action:info", "Go to status list", "#/status/list")
      createMenuItem(
        "User", "social:people", "Go to user list", "#/user/list")
  ])