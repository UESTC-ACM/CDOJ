cdoj.directive("uiRejudgeButton",
->
  restrict: "A"
  controller: [
    "$scope", "$rootScope", "$http", "$window"
    ($scope, $rootScope, $http, $window) ->
      $scope.rejudge = ->
        $http.post("/status/count", $rootScope.statusCondition).then (response)->
          data = response.data
          if data.result == "success"
            if confirm("Rejudge all #{data.count} records")
              $http.post("/status/rejudge", $rootScope.condition).then (response)->
                data = response.data
                if data.result == "success"
                  $window.alert "Done!"
                else
                  $window.alert data.error_msg
          else
            $window.alert data.error_msg
  ]
  template: """
          <a href="javascript:void(0);"
             ng-show="$root.isAdmin"
             class="btn btn-danger btn-xs"
             ng-click="rejudge()">Rejudge</a>
"""
)