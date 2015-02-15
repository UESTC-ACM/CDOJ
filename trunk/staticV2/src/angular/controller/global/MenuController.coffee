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

    createMenuItem("Home", "home", "Go to home page", "#/")
    createMenuItem(
      "Problems", "puzzle", "Go to problem list", "#/problem/list")
    createMenuItem(
      "Contests", "trophy", "Go to contest list", "#/contest/list")
    createMenuItem(
      "Status", "timetable", "Go to status list", "#/status/list")
    createMenuItem(
      "Users", "account-multiple", "Go to user list", "#/user/list")

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