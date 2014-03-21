cdoj
.controller("ListController", [
    "$scope", "$rootScope", "$http", "$window", "$routeParams"
    ($scope, $rootScope, $http, $window, $routeParams) ->
      $scope.pageInfo =
        countPerPage: 20
        currentPage: 1
        displayDistance: 2
        totalPages: 1
      $scope.itemsPerPage = 20
      $scope.showPages = 10

      $scope.$on("refreshList", (e, callback)->
        $scope.refresh(callback)
      )
      $scope.$on("refreshList:" + $scope.name, (e, callback)->
        $scope.refresh(callback)
      )
      _.each $scope.condition, (val, key)->
        if angular.isDefined $routeParams[key]
          if not isNaN(parseInt($routeParams[key]))
            $scope.condition[key] = parseInt($routeParams[key])
          else
            $scope.condition[key] = $routeParams[key]
      $scope.refresh = (callback)->
        if $scope.requestUrl != 0
          condition = angular.copy($scope.condition)
          $http.post($scope.requestUrl, condition).then (response) =>
            data = response.data
            if data.result == "success"
              $scope.$$nextSibling.list = response.data.list
              $scope.pageInfo = data.pageInfo
              $scope.itemsPerPage = $scope.pageInfo.countPerPage
              if angular.isFunction callback
                callback(response.data)
            else
              $window.alert data.error_msg
      $scope.$watch("condition", ->
        $scope.refresh()
      , true
      )
  ])