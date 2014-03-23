cdoj.directive("uiContestAdminSpan",
->
  restrict: "A"
  scope:
    contestId: "="
    isVisible: "="
  controller: [
    "$scope", "$http", "$window"
    ($scope, $http, $window) ->
      $scope.editVisible = ->
        queryString = "/contest/operator/#{$scope.contestId}/isVisible/#{!$scope.isVisible}"
        $http.post(queryString).success((data)->
          if data.result == "success"
            $scope.isVisible = !$scope.isVisible
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
  ]
  template: """
              <div class="btn-toolbar" role="toolbar">
                <div class="btn-group">
                  <button type="button" class="btn btn-default btn-sm" ng-click="editVisible()" style="padding: 1px 5px;">
                    <i class="fa" ng-class="{
                      'fa-eye': isVisible == true,
                      'fa-eye-slash': isVisible == false
                    }"></i>
                  </button>
                  <a href="#/contest/editor/{{contestId}}"
                     class="btn btn-default btn-sm" style="padding: 1px 5px;"><i class="fa fa-pencil"></i></a>
                </div>
              </div>
    """
)