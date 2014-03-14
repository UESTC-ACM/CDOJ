cdoj
.directive("uiValidateInfo",
  ->
    restrict: "E"
    scope:
      value: "="
      for: "@"
    link: ($scope)->
      $scope.isInvalid = false
      $scope.message = ""
      $scope.$watch("value",
      ->
        v = _.findWhere($scope.value, {field: $scope.for})
        if v != undefined
          $scope.message = v.defaultMessage
          $scope.isInvalid = true
      )
    template: """
      <span class="help-block" ng-show="isInvalid" ng-bind="message"></span>
    """
  )