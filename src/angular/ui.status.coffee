cdoj.directive("uiStatus",
->
  restrict: "A"
  scope:
    status: "="
  controller: [
    "$scope", "$rootScope", "$http", "$modal"
    ($scope, $rootScope, $http, $modal) ->
      $scope.showHref = false
      $rootScope.$watch("hasLogin + status",
      ->
        if $scope.status.returnTypeId == 7
          if $rootScope.hasLogin && ($rootScope.currentUser.type == 1 || $rootScope.currentUser.userName == $scope.status.userName)
            $scope.showHref = true
          else
            $scope.showHref = false
        else
          $scope.showHref = false
      , true)
      $scope.showCompileInfo = ->
        statusId = $scope.status.statusId
        $modal.open(
          templateUrl: "compileInfoModal.html"
          controller: "CompileInfoModalController"
          resolve:
            statusId: -> statusId
        )
      if [0, 16, 17, 18].some($scope.status.returnTypeId)
        timmer = setInterval(->
          condition =
            currentPage: null
            startId: $scope.status.statusId
            endId: $scope.status.statusId
            userName: undefined
            problemId: undefined
            languageId: undefined
            contestId: undefined
            result: 'OJ_ALL'
            orderFields: 'statusId'
            orderAsc: 'false'
          $http.post("/status/search", condition).then(
            (response)->
              data = response.data
              if data.result == "success" and data.list.length == 1
                $scope.status = data.list[0]
                if [0, 16, 17, 18].none($scope.status.returnTypeId)
                  clearInterval(timmer)
          )
        , 500)
  ]
  template: """
<a href="#" ng-show="showHref" ng-click="showCompileInfo()">{{status.returnType}}</a>
<span ng-hide="showHref">{{status.returnType}}</span>
    """
)
cdoj.controller("CompileInfoModalController", [
  "$scope", "$http", "$modalInstance", "statusId"
  ($scope, $http, $modalInstance, statusId)->
    $scope.compileInfo="Loading..."
    $http.post("/status/info/#{statusId}").then(
      (response) ->
        data = response.data
        compileInfo = ""
        if data.result == "success"
          compileInfo = data.compileInfo
        else
          compileInfo = data.error_msg
        $scope.compileInfo = compileInfo
    )
])

