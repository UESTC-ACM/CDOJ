cdoj.directive("uiProblemAdminSpan",
->
  restrict: "A"
  scope:
    problemId: "="
    isVisible: "="
    type: "="
  controller: [
    "$scope", "$http", "$window"
    ($scope, $http, $window) ->
      $scope.editVisible = ->
        queryString = "/problem/operator/" +
          $scope.problemId + "/isVisible/" + (!$scope.isVisible)
        $http.post(queryString).success((data) ->
          if data.result == "success"
            $scope.isVisible = !$scope.isVisible
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
      $scope.editType = ->
        changeTo = "INTERNAL"
        if $scope.type == "INTERNAL"
          changeTo = "NORMAL"
        queryString = "/problem/operator/" +
          $scope.problemId + "/type/" + changeTo;
        $http.post(queryString).success((data) ->
          if data.result == "success"
            $scope.type = changeTo
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
  ]
  template: """
<div class="btn-toolbar" role="toolbar">
  <div class="btn-group">
    <button type="button"
            class="btn btn-default btn-sm"
            ng-click="editType()"
            style="padding: 1px 5px;">
      <i class="fa"
         ng-class="{
          'fa-lock': type == 'INTERNAL',
          'fa-unlock': type == 'NORMAL'
         }"></i>
    </button>
    <button type="button"
            class="btn btn-default btn-sm"
            ng-click="editVisible()"
            style="padding: 1px 5px;">
      <i class="fa"
         ng-class="{
          'fa-eye': isVisible == true,
          'fa-eye-slash': isVisible == false
         }"></i>
    </button>
    <a href="#/problem/editor/{{problemId}}"
       class="btn btn-default btn-sm"
       style="padding: 1px 5px;">
      <i class="fa fa-pencil"></i>
    </a>
  </div>
</div>
    """
)