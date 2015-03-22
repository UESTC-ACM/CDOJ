angular.module("cdojV2").controller("MenuController", [
  "Msg", "$scope", "$location"
  (msg, $scope, $location) ->
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

    createMenuItem("Home", "home", "Go to home page", "/")
    createMenuItem(
      "Problems", "puzzle", "Go to problem list", "/problem/list")
    createMenuItem(
      "Contests", "trophy", "Go to contest list", "/contest/list")
    createMenuItem(
      "Status", "timetable", "Go to status list", "/status/list")
    createMenuItem(
      "Users", "account-multiple", "Go to user list", "/user/list")

    # Change page location when use in header tabs
    $scope.switchTab = (url, current) ->
      # Disable current tab manually
      console.log(url, current)
      if !current
        $location.path(url)
      
    # Highlight current page for sidenav bar
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