cdoj.controller("ListController", [
  "$scope", "$http",
  ($scope, $http) ->
    $scope.condition = 0
    $scope.list = []
    $scope.pageInfo =
      countPerPage: 20
      currentPage: 1
      displayDistance: 2
      totalPages: 1
    $scope.requestUrl = 0

    $scope.reset = ->
      _.each($scope.condition, (value, index) ->
        $scope.condition[index] = undefined
      )
      $scope.condition["currentPage"] = null

    $scope.$watch(
      "condition",
      () ->
        if $scope.requestUrl != 0
          $http.post($scope.requestUrl, $scope.condition).then (response) =>
            $scope.list = response.data.list
            $scope.pageInfo = response.data.pageInfo
      ,
      true
    )
])