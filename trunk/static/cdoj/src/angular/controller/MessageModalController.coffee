cdoj.controller("MessageModalController", [
  "$scope", "$rootScope", "$http", "$modalInstance", "message", "$window"
  ($scope, $rootScope, $http, $modalInstance, message, $window)->
    $scope.message = message
    $scope.message.content = "Loading..."
    $http.get("/message/fetch/#{$scope.message.messageId}").success((data)->
      if data.result == "success"
        if $scope.message.isOpened == false
          $rootScope.$broadcast("all:refresh")
        _.extend($scope.message, data.message)
      else
        $window.alert data.error_msg
    ).error(->
      $window.alert "Network error."
    )
    $scope.$on("$routeChangeStart", ->
      $modalInstance.dismiss()
    )
])