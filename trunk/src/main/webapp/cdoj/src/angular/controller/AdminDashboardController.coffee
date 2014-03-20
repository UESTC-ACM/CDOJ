cdoj
.controller("AdminDashboardController", [
    "$scope", "$rootScope", "$window"
    ($scope, $rootScope, $window)->
      if $rootScope.hasLogin == false || $rootScope.currentUser.type != $rootScope.AuthenticationType.ADMIN
        $window.alert("Permission denied!")
        $window.history.back()

      articleCondition = angular.copy($rootScope.articleCondition)
      articleCondition.type = $rootScope.ArticleType.NOTICE
      $scope.articleCondition = articleCondition
  ])