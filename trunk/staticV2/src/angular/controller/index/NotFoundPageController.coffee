angular.module("cdojV2").controller("NotFoundPageController", [
  "$rootScope", "$scope", "Msg", "$mdDialog", "$location"
  ($rootScope, $scope, msg, $mdDialog, $location) ->
    $rootScope.$broadcast("pageChangeEvent", "404")
    $rootScope.$broadcast("pageTitleChangeEvent", "404", "")
    
    $scope.count = 404
    $scope.errorLabel = msg("404Error")
    
    alert = $mdDialog.alert({
      title: msg("YouWin")
      content: msg("YouAreSoBoring")
      ok: msg("BackToHomePage")
    })
        
    $scope.click = ->
      $scope.count--
      if $scope.count == 0
        $mdDialog.show(alert).then(
          -> $location.path("/home")
        )
])