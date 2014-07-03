cdoj
.controller("TrainingMemberEditorController", [
    "$scope", "$http", "$modalInstance", "action"
    ($scope, $http, $modalInstance, action) ->

      $scope.action = action
      console.log action
      if $scope.action == "new"
        $scope.title = "Add new member"
      else
        $scope.title = "Edit"

      $scope.dismiss = ->
        $modalInstance.dismiss("close")
  ])