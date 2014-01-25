cdoj.directive("uiStatus",
->
  restrict: "A"
  scope:
    status: "="
  controller: [
    "$scope", "$rootScope", "$http"
    ($scope, $rootScope, $http) ->
      $scope.showHref = false
      $rootScope.$watch("hasLogin",
      ->
        if $scope.status.returnTypeId == 7
          if $rootScope.hasLogin && ($rootScope.currentUser.type == 1 || $rootScope.currentUser.userName == $scope.status.userName)
            $scope.showHref = true
          else
            $scope.showHref = false
        else
          $scope.showHref = false
      )
      $scope.showCompileInfo = ->
        statusId = $scope.status.statusId
        $http.post("/status/info/#{statusId}").then(
          (response) ->
            data = response.data
            compileInfo = ""
            if data.result == "success"
              compileInfo = data.compileInfo
            else
              compileInfo = data.error_msg
            $modal = $("#compile-info-modal")
            $modal.find(".modal-body").empty().append("<pre>#{compileInfo}</pre>")
            $modal.find(".modal-body").prettify()
            $modal.modal("toggle")
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