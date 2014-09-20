cdoj.controller("CompileInfoModalController", [
  "$scope", "$http", "$modalInstance", "statusId"
  ($scope, $http, $modalInstance, statusId) ->
    $scope.compileInfo = "Loading..."
    $http.post("/status/info/#{statusId}").success((data) ->
      compileInfo = ""
      if data.result == "success"
        compileInfo = data.compileInfo
      else
        compileInfo = data.error_msg
      $scope.compileInfo = compileInfo
    ).error(->
      $scope.compileInfo = "Network error."
    )
    $scope.$on("$routeChangeStart", ->
      $modalInstance.dismiss()
    )
])