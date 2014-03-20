cdoj
.controller("AdminDashboardController", [
    "$scope", "$rootScope", "$window", "$http"
    ($scope, $rootScope, $window, $http)->
      if $rootScope.hasLogin == false || $rootScope.currentUser.type != $rootScope.AuthenticationType.ADMIN
        $window.alert("Permission denied!")
        $window.history.back()

      articleCondition = angular.copy($rootScope.articleCondition)
      articleCondition.type = $rootScope.ArticleType.NOTICE
      articleCondition.orderFields = "order"
      articleCondition.orderAsc = "true"

      $scope.list = []
      originalOrder = ""
      refresh = ->
        $http.post("/article/search", articleCondition).then (response) =>
          data = response.data
          if data.result == "success"
            $scope.list = data.list
            originalOrder = _.map($scope.list, (val)-> val.articleId).join(",")
            $scope.pageInfo = data.pageInfo
          else
            $window.alert data.error_msg
      refresh()

      $scope.listChanged = false
      $scope.sortableOptions =
        stop: (e, ui)->
          newOrder = _.map($scope.list, (val)-> val.articleId).join(",")
          $scope.listChanged = !angular.equals(newOrder, originalOrder)
      $scope.save = ->
        newOrder = _.map($scope.list, (val)-> val.articleId).join(",")
        $http.post("/article/changeNoticeOrder", order: newOrder).then (response)->
          data = response.data
          if data.result == "success"
            $window.alert "Done"
            refresh()
            $scope.listChanged = false
          else
            $window.alert data.error_msg
  ])