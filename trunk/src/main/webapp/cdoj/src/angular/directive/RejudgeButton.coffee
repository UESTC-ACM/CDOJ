cdoj.directive("uiRejudgeButton",
->
  restrict: "A"
  scope:
    condition: "="
  controller: [
    "$scope", "$rootScope", "$http", "$window"
    ($scope, $rootScope, $http, $window) ->
      $scope.rejudge = ->
        $http.post("/status/rejudgeStatusCount", $scope.condition).success((data)->
          if data.result == "success"
            if confirm("Rejudge all #{data.count} records")
              $http.post("/status/rejudge", $scope.condition).success((data)->
                if data.result == "success"
                  $window.alert "Done!"
                else
                  $window.alert data.error_msg
              ).error(->
                $window.alert "Network error."
              )
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
  ]
  template: """
          <a href="javascript:void(0);"
             ng-show="$root.isAdmin"
             class="btn btn-danger btn-xs"
             ng-click="rejudge()">Rejudge</a>
"""
)