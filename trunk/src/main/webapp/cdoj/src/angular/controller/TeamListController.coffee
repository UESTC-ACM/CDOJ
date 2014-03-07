cdoj
.controller("TeamListController", [
    "$scope", "$rootScope", "$http"
    ($scope, $rootScope, $http)->
      $scope.hideMemberPanel = true
      $scope.toggleMemberPanel = ->
        $scope.hideMemberPanel = not $scope.hideMemberPanel
])