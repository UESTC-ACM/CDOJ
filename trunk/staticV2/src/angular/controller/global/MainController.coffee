angular.module("cdojV2").controller("MainController", [
  "$scope", "$mdSidenav", "$mdDialog", "Msg", "$window"
  ($scope, $mdSidenav, $mdDialog, msg, $window) ->
    LEFT_SIDEBAR_ID = "left-sidenav"

    # Open left sidebar if it is closed.
    $scope.openMenu = ->
      if $mdSidenav(LEFT_SIDEBAR_ID).isOpen() == false
        $mdSidenav(LEFT_SIDEBAR_ID).open()
    $scope.openSettingsDialog = (ev) ->
      $mdDialog.show({
        controller: "SettingsDialogController"
        templateUrl: getTemplateUrl("dialog", "settingsDialog")
      }).then(
        -> $window.location.reload()
        -> # Do nothing.
      )
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