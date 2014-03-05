cdoj.directive("uiRejudgeButton",
->
  restrict: "A"
  scope:
    condition: "="
  controller: [
    "$scope", "$http", "$window"
    ($scope, $http, $window) ->
      $scope.rejudge = ->
        $http.post("/status/count", $scope.condition).then(
          (response)->
            data = response.data
            if data.result == "success"
              if confirm("Rejudge all #{data.count} records")
                $http.post("/status/rejudge", $scope.condition).then(
                  (response)->
                    data = response.data
                    if data.result == "success"
                      $window.alert "Done!"
                    else
                      $window.alert data.error_msg
                )
            else
              $window.alert data.error_msg
        )
  ]
  template: """
    <button type="button" class="btn btn-danger btn-sm" ng-click="rejudge()">Rejudge</button>
    """
)