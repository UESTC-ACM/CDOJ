cdoj
.controller("IndexController", [
    "$scope", "$rootScope"
    ($scope, $rootScope)->
      $rootScope.title = "Home"
      articleCondition = angular.copy($rootScope.articleCondition)
      articleCondition.type = $rootScope.ArticleType.NOTICE
      articleCondition.orderFields = "order"
      articleCondition.orderAsc = "true"
      $scope.articleCondition = articleCondition
  ])