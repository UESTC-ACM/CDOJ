cdoj
.controller("CodeModalController", [
    "$scope", "$http", "$modalInstance", "statusId"
    ($scope, $http, $modalInstance, statusId) ->
      $scope.code = "Loading..."
      $http.post("/status/info/#{statusId}").success((data) ->
        compileInfo = ""
        if data.result == "success"
          compileInfo = data.compileInfo
        else
          compileInfo = data.error_msg
        code = ""
        if data.result == "success"
          code = data.code
        else
          code = data.error_msg
        $scope.code = code
      ).error(->
        $scope.code = "Network error."
      )
      $scope.$on("$routeChangeStart", ->
        $modalInstance.dismiss()
      )
  ])
