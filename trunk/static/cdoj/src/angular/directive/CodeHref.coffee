cdoj
.directive("uiCodeHref",
->
  restrict: "A"
  scope:
    status: "="
    alwaysShowHref: "@"
  controller: [
    "$scope", "$rootScope", "$http", "$modal"
    ($scope, $rootScope, $http, $modal) ->
      $scope.showHref = false
      checkShowHref = ->
        if $scope.alwaysShowHref
          $scope.showHref = true
        else if (
            $rootScope.hasLogin && (
                $rootScope.currentUser.type == 1 ||
                $rootScope.currentUser.userName == $scope.status.userName
            )
        )
          $scope.showHref = true
        else
          $scope.showHref = false
      checkShowHref()
      $scope.$on("currentUser:changed", ->
        checkShowHref()
      )
      $scope.showCode = ->
        statusId = $scope.status.statusId
        $modal.open(
          templateUrl: "template/modal/code-modal.html"
          controller: "CodeModalController"
          resolve:
            statusId: ->
              statusId
        )
  ]
  template: """
<a href="javascript:void(0);"
   ng-show="showHref"
   ng-click="showCode()">{{status.length}} B</a>
<span ng-hide="showHref">{{status.length}} B</span>
    """
)
