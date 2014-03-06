cdoj.controller("CompileInfoModalController", [
  "$scope", "$http", "$modalInstance", "statusId"
  ($scope, $http, $modalInstance, statusId)->
    $scope.compileInfo = "Loading..."
    $http.post("/status/info/#{statusId}").then(
      (response) ->
        data = response.data
        compileInfo = ""
        if data.result == "success"
          compileInfo = data.compileInfo
        else
          compileInfo = data.error_msg
        $scope.compileInfo = compileInfo
    )
])