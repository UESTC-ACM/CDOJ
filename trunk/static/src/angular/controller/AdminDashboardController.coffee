cdoj
.controller("AdminDashboardController", [
    "$scope", "$rootScope", "$window", "$http", "$timeout"
    ($scope, $rootScope, $window, $http, $timeout) ->
      # Administrator only
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.ADMIN
      )
      $window.scrollTo(0, 0)

      # Initilize article condition
      articleCondition = angular.copy($rootScope.articleCondition)
      articleCondition.type = $rootScope.ArticleType.NOTICE
      articleCondition.orderFields = "order"
      articleCondition.orderAsc = "true"

      $scope.list = []
      originalOrder = ""
      refresh = ->
        $http.post("/article/search", articleCondition).success((data) ->
          if data.result == "success"
            $scope.list = data.list
            originalOrder = _.map($scope.list, (val) ->
              val.articleId).join(",")
            $scope.pageInfo = data.pageInfo
          else
            $window.alert data.error_msg
        ).error(->
          $timeout(refresh, 500)
        )
      $timeout(refresh, 0)

      $scope.listChanged = false

      # Sortable event
      $scope.sortableOptions =
        stop: ->
          newOrder = _.map($scope.list, (val) ->
            val.articleId).join(",")
          $scope.listChanged = !angular.equals(newOrder, originalOrder)

      # Save notice order changes
      $scope.save = ->
        newOrder = _.map($scope.list, (val) ->
          val.articleId).join(",")
        $http.post(
          "/article/changeNoticeOrder"
          order: newOrder
        ).success((data) ->
          if data.result == "success"
            $window.alert "Done"
            refresh()
            $scope.listChanged = false
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
  ])