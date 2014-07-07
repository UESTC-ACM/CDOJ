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
      _trainingUserCriteria = angular.copy($rootScope.trainingUserCriteria)
      _trainingUserCriteria.trainingId = trainingDto.trainingId
      $scope.trainingUserCriteria = _trainingUserCriteria

      $scope.editTrainingUser = (trainingUserId) ->
        $modal.open(
          templateUrl: "template/modal/training-member-editor-modal.html"
          controller: "TrainingMemberEditorController"
          size: "lg"
          resolve:
            action: ->
              "edit"
            trainingUserDto: ["$q", "$http", "Error"
              ($q, $http, $Error) ->
                deferred = $q.defer()
                $http.get(
                  "/training/trainingUserData/#{trainingUserId}"
                ).success((data) ->
                  if data.result == "success"
                    deferred.resolve(data.trainingUserDto)
                  else
                    $Error.error(data.error_msg)
                ).error(->
                  $Error.error("Network error!")
                )
                return deferred.promise
            ]
        )

      $scope.addNewMember = ->
        $modal.open(
          templateUrl: "template/modal/training-member-editor-modal.html"
          controller: "TrainingMemberEditorController"
          size: "lg"
          resolve:
            action: ->
              "new"
            trainingUserDto: ->
              trainingId: trainingDto.trainingId
              trainingUserName: ""
              type: 0
        )
  ])