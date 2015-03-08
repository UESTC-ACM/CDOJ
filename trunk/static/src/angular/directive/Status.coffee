cdoj
.directive("uiStatus",
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
        if $scope.status.resultId == 7
          if $scope.alwaysShowHref
            $scope.showHref = true
          else if (
              $rootScope.hasLogin &&
              (
                  $rootScope.currentUser.type == 1 ||
                  $rootScope.currentUser.userName == $scope.status.userName
              )
          )
            $scope.showHref = true
          else
            $scope.showHref = false
        else
          $scope.showHref = false
      checkShowHref()
      $scope.$on("currentUser:updated", ->
        checkShowHref()
      )
      $scope.showCompileInfo = ->
        statusId = $scope.status.statusId
        $modal.open(
          templateUrl: "template/modal/compile-info-modal.html"
          controller: "CompileInfoModalController"
          size: "lg"
          resolve:
            statusId: ->
              statusId
        )
      removeStatusTimer = ->
        clearInterval(refreshStatusTimer)
      refreshStatus = ->
        condition =
          currentPage: null
          startId: $scope.status.statusId
          endId: $scope.status.statusId
          userName: undefined
          problemId: undefined
          languageId: undefined
          contestId: $scope.status.contestId
        if angular.isUndefined condition.contestId
          condition.contestId = -1
        $http.post("/status/search", condition).success((data) ->
          if data.result == "success" and data.list.length == 1
            $scope.status = data.list[0]
            checkShowHref()
            if [0, 16, 17, 18].none($scope.status.resultId)
              removeStatusTimer()
        ).error(->
          $window.alert "Network error."
        )
      if [0, 16, 17, 18].some($scope.status.resultId)
        refreshStatusTimer = setInterval(refreshStatus, 1000)
      $scope.$on("$locationChangeStart", ->
        removeStatusTimer()
      )
  ]
  template: """
<a href="javascript:void(0);"
   ng-show="showHref"
   ng-click="showCompileInfo()">{{status.result}}</a>
<span ng-hide="showHref">{{status.result}}</span>
    """
)

