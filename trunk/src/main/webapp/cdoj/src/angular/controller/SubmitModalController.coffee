cdoj
.controller("SubmitModalController", [
    "$scope", "$rootScope", "$window", "$http", "$modalInstance", "submitDTO", "title"
    ($scope, $rootScope, $window, $http, $modalInstance, submitDTO, title)->
      $scope.submitDTO = submitDTO
      $scope.title = title

      $scope.submit = ->
        submitDTO = angular.copy($scope.submitDTO)
        if angular.isUndefined submitDTO.codeContent then return
        if $rootScope.hasLogin == false
          $window.alert "Please login first!"
          $modalInstance.dismiss("close")
        else
          $http.post("/status/submit", submitDTO).then (response)->
            data = response.data
            if data.result == "success"
              $modalInstance.close("success")
            else if data.result == "field_error"
              $scope.fieldInfo = data.field
            else
              $window.alert data.error_msg
      $scope.close = ->
        $modalInstance.dismiss("close")
  ])