cdoj.directive("uiStatus",
->
  restrict: "A"
  scope:
    status: "="
  controller: [
    "$scope", "$http"
    ($scope, $http) ->
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
                console.log $scope.status
          )
        , 500)
  ]
  template: """
{{status.returnType}}
    """
)