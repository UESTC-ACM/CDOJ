angular.module("cdojV2").controller("MenuController", [
  "Msg", "$scope"
  (msg, $scope) ->
    $scope.pages = []
    createMenuItem = (title, icon, label, href) ->
      page =
        title: title
        displayedText: msg(title)
        icon: icon
        label: label
        href: href
        current: false
      $scope.pages.push(page)

    createMenuItem("Home", "action:home", "Go to home page", "#/")
    createMenuItem(
      "Problems", "action:assignment", "Go to problem list", "#/problem/list")
    createMenuItem(
      "Contests", "action:extension", "Go to contest list", "#/contest/list")
    createMenuItem(
      "Status", "action:info", "Go to status list", "#/status/list")
    createMenuItem(
      "Users", "social:people", "Go to user list", "#/user/list")

    setCurrentPage = (where) ->
      _.each($scope.pages, (data) ->
        if data.title == where
          data.current = true
        else
          data.current = false
      )
    setCurrentPage("Home")
    $scope.$on("pageChangeEvent", (e, where) -> setCurrentPage(where))
])