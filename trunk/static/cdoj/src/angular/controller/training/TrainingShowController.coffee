cdoj
.controller("TrainingShowController", [
    "$scope", "$http", "$rootScope", "$window", "trainingDto", "$modal"
    ($scope, $http, $rootScope, $window, trainingDto, $modal) ->
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.NOOP
      )
      $window.scrollTo(0, 0)

      $scope.trainingDto = trainingDto

      $scope.addNewMember = ->
        $modal.open(
          templateUrl: "template/modal/training-member-editor-modal.html"
          controller: "TrainingMemberEditorController"
          resolve:
            action: -> "new"
            trainingUserDto: ->
              trainingId: trainingDto.trainingId
              trainingUserName: ""
              type: 0
        )
  ])