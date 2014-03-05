cdoj.directive("uiContestAdminSpan",
->
  restrict: "A"
  scope:
    contestId: "="
    isVisible: "="
  controller: [
    "$scope", "$http",
    ($scope, $http) ->
      $scope.editVisible = ->
        queryString = "/contest/operator/#{$scope.contestId}/isVisible/#{!$scope.isVisible}"
        $http.post(queryString).then (response)=>
          if response.data.result == "success"
            $scope.isVisible = !$scope.isVisible
    # TODO error
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
                  <a href="/contest/editor/{{contestId}}" target="_blank"
                     class="btn btn-default btn-sm" style="padding: 1px 5px;"><i class="fa fa-pencil"></i></a>
                </div>
              </div>
    """
)