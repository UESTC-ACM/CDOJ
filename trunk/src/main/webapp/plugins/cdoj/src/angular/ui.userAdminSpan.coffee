cdoj.directive("uiUserAdminSpan",
->
  restrict: "E"
  scope:
    user: "="
  controller: [
    "$scope", "$http", "$modal", "$window"
    ($scope, $http, $modal, $window)->
      $scope.showEditor = ->
        userName = angular.copy($scope.user.userName)
        $http.get("/user/profile/#{userName}").then (response)->
          data = response.data
          if data.result == "success"
            $modal.open(
              templateUrl: "userAdminModalContent.html"
              controller: [
                "$scope", "$http", "$modalInstance"
                ($scope, $http, $modalInstance)->
                  $scope.userEditDTO = data.user
                  $scope.fieldInfo = []
                  $scope.edit = ->
                    userEditDTO = angular.copy($scope.userEditDTO)

                    userEditDTO.newPassword = undefined if userEditDTO.newPassword == ""
                    userEditDTO.newPasswordRepeat = undefined if userEditDTO.newPasswordRepeat == ""
                    if userEditDTO.newPassword == undefined && userEditDTO.newPasswordRepeat == undefined
                      userEditDTO = _.omit(userEditDTO, "newPassword")
                      userEditDTO = _.omit(userEditDTO, "newPassowrdRepeat")
                    else
                      newPassword = CryptoJS.SHA1(userEditDTO.newPassword).toString()
                      userEditDTO.newPassword = newPassword
                      newPasswordRepeat = CryptoJS.SHA1(userEditDTO.newPasswordRepeat).toString()
                      userEditDTO.newPasswordRepeat = newPasswordRepeat

                    console.log userEditDTO
                    $http.post("/user/adminEdit", userEditDTO).then (response)->
                      data = response.data
                      if data.result == "success"
                        $modalInstance.close()
                      else if data.result == "field_error"
                        $scope.fieldInfo = data.field
                      else
                        $window.alert data.error_msg
                  $scope.close = ->
                    $modalInstance.dismiss("close")
              ]
            )
          else
            $window.alert data.error_msg
  ]
  template: """
<div class="btn-toolbar" role="toolbar" style="position: absolute; top: 12px; right: 30px;"
     ng-show="$root.isAdmin">
  <div class="btn-group">
    <button type="button" class="btn btn-default btn-sm" ng-click="showEditor()">
      <i class="fa fa-pencil"></i>
    </button>
  </div>
</div>
"""
)