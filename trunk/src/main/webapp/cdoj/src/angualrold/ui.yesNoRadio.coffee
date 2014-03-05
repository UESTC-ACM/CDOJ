cdoj.directive("uiYesNoRadio",
->
  restrict: "E"
  scope:
    ngModel: "="
  controller: [
    "$scope"
    ($scope) ->
      $scope.options = [
        key: "All"
        value: undefined
      ,
        key: "Yes"
        value: true
      ,
        key: "No"
        value: false
      ]
      $scope.select = (value)->
        $scope.ngModel = value
  ]
  replace: true
  template: """
<div class="btn-group pull-right" data-toggle="buttons">
  <button type="button" class="btn  btn-info btn-sm" ng-class="{active: option.value == ngModel}"
          ng-repeat="option in options"
          ng-click="select(option.value)"
          ng-bind="option.key">
  </button>
</div>
"""
)