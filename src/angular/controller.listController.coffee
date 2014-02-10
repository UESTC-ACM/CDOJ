cdoj.controller("ListController", [
  "$scope", "$rootScope", "$http",
  ($scope, $rootScope, $http) ->
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
    $scope.refresh = ->
      if $scope.requestUrl != 0
        condition = angular.copy($scope.condition)
        $http.post($scope.requestUrl, condition).then (response) =>
          $scope.list = response.data.list
          $scope.pageInfo = response.data.pageInfo
    $rootScope.$watch("currentUser", ->
      $scope.refresh()
    , true)
    $scope.$watch("condition", ->
      $scope.refresh()
    , true
    )
])