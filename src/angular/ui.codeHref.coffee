cdoj.directive("uiCodeHref",
->
  restrict: "A"
  scope:
    status: "="
  controller: [
    "$scope", "$rootScope", "$http", "$modal"
    ($scope, $rootScope, $http, $modal) ->
      $scope.showHref = false
      $rootScope.$watch("hasLogin",
      ->
        if $rootScope.hasLogin && ($rootScope.currentUser.type == 1 || $rootScope.currentUser.userName == $scope.status.userName)
          $scope.showHref = true
        else
          $scope.showHref = false
      )
      $scope.showCode = ->
        statusId = $scope.status.statusId
        $modal.open(
          templateUrl: "codeModal.html"
          controller: "CodeModalController"
          resolve:
            statusId: -> statusId
        )
  ]
  template: """
<a href="#" ng-show="showHref" ng-click="showCode()">{{status.length}} B</a>
<span ng-hide="showHref">{{status.length}} B</span>
    """
)
cdoj.controller("CodeModalController", [
  "$scope", "$http", "$modalInstance", "statusId"
  ($scope, $http, $modalInstance, statusId)->
    $scope.code="Loading..."
    $http.post("/status/info/#{statusId}").then(
      (response) ->
        data = response.data
        compileInfo = ""
        if data.result == "success"
          compileInfo = data.compileInfo
        else
          compileInfo = data.error_msg
        code = ""
        if data.result == "success"
          code = data.code
        else
          code = data.error_msg
        $scope.code = code;
    )
])
cdoj.directive("uiCode",
->
  restrict: "E"
  scope:
    code: "="
  controller: [
    "$scope"
    ($scope, $element)->
      $scope.prettifiedCode = ""
      $scope.$watch("code", ->
        $scope.prettifiedCode = prettyPrintOne($scope.code.trim().escapeHTML())
      )
  ]
  template: """
<pre ng-bind-html="prettifiedCode"></pre>
"""
  replace: true
)