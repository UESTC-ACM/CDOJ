angular.module("cdojV2").controller("MainController", [
  "$scope", "$mdSidenav", "Msg"
  ($scope, $mdSidenav, msg) ->
    LEFT_SIDEBAR_ID = "left-sidenav"

    # Open left sidebar if it is closed.
    $scope.openMenu = ->
      if $mdSidenav(LEFT_SIDEBAR_ID).isOpen() == false
        $mdSidenav(LEFT_SIDEBAR_ID).open()
    $scope.pageTitle = "CDOJ"

    # Page title
    setPageTitle = (title, subTitle) ->
      if subTitle == ""
        $scope.pageTitle = msg(title)
      else
        $scope.pageTitle = "#{msg(title)}: #{subTitle}"
    setPageTitle("Home", "")
    $scope.$on("pageTitleChangeEvent", (e, title, subTitle) ->
      setPageTitle(title, subTitle)
    )
])