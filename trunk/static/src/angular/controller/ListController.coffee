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

      $scope.$on("currentUser:changed", ->
        $scope.refresh()
      )
      $scope.$on("list:refresh", (e, callback) ->
        $scope.refresh(callback)
      )
      refreshTrigger = "list:refresh:" + $scope.name
      $scope.$on(refreshTrigger, (e, callback) ->
        $scope.refresh(callback)
      )
      $scope.$on("list:reset", ->
        $scope.reset()
      )
      resetTrigger = "list:reset:" + $scope.name
      $scope.$on(resetTrigger, ->
        $scope.reset()
      )

      _.each $scope.condition, (val, key) ->
        if angular.isDefined $routeParams[key]
          if not isNaN(parseInt($routeParams[key]))
            $scope.condition[key] = parseInt($routeParams[key])
          else
            $scope.condition[key] = $routeParams[key]
      $scope.refresh = (callback) ->
        if $scope.requestUrl != 0
          condition = angular.copy($scope.condition)
          $http.post($scope.requestUrl, condition).success((data) ->
            if data.result == "success"
              $scope.$$nextSibling.list = data.list
              $scope.pageInfo = data.pageInfo
              $scope.itemsPerPage = $scope.pageInfo.countPerPage
              if angular.isFunction callback
                callback(data)
          )
      $scope.reset = ->
        _.each($scope.condition, (value, index) ->
          if index == "orderFields" || index == "orderAsc" then return
          $scope.condition[index] = undefined
        )
        $scope.condition["currentPage"] = null
      $scope.$watch("condition", (newVal, oldVal) ->
        if newVal.currentPage == oldVal.currentPage
          $scope.condition.currentPage = 1
        $scope.refresh()
      , true
      )
  ])