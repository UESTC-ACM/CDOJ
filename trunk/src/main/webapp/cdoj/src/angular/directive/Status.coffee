cdoj
.directive("uiStatus",
  ->
    restrict: "A"
    scope:
      status: "="
    controller: [
      "$scope", "$rootScope", "$http", "$modal"
      ($scope, $rootScope, $http, $modal) ->
        $scope.showHref = false
        checkShowHref = ->
          if $scope.status.returnTypeId == 7
            if $rootScope.hasLogin && ($rootScope.currentUser.type == 1 || $rootScope.currentUser.userName == $scope.status.userName)
              $scope.showHref = true
            else
              $scope.showHref = false
          else
            $scope.showHref = false
        $rootScope.$watch("hasLogin",
        ->
          checkShowHref()
        )
        $scope.showCompileInfo = ->
          statusId = $scope.status.statusId
          $modal.open(
            templateUrl: "template/modal/compile-info-modal.html"
            controller: "CompileInfoModalController"
            resolve:
              statusId: ->
                statusId
          )
        if [0, 16, 17, 18].some($scope.status.returnTypeId)
          timmer = setInterval(->
            condition =
              currentPage: null
              startId: $scope.status.statusId
              endId: $scope.status.statusId
            $http.post("/status/search", condition).then(
              (response)->
                data = response.data
                if data.result == "success" and data.list.length == 1
                  $scope.status = data.list[0]
                  checkShowHref()
                  if [0, 16, 17, 18].none($scope.status.returnTypeId)
                    clearInterval(timmer)
            )
          , 500)
    ]
    template: """
<a href="javascript:void(0);" ng-show="showHref" ng-click="showCompileInfo()">{{status.returnType}}</a>
<span ng-hide="showHref">{{status.returnType}}</span>
    """
  )

