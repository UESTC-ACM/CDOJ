cdoj
.controller("IndexController", [
    "$scope", "$rootScope", "$window"
    ($scope, $rootScope, $window)->
      $scope.$emit("permission:setPermission", $rootScope.AuthenticationType.NOOP)
      $window.scrollTo(0, 0)

      $rootScope.title = "Home"
      articleCondition = angular.copy($rootScope.articleCondition)
      articleCondition.type = $rootScope.ArticleType.NOTICE
      articleCondition.orderFields = "order"
      articleCondition.orderAsc = "true"
      $scope.articleCondition = articleCondition
  ])