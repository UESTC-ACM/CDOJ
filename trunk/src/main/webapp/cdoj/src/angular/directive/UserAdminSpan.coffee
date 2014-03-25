cdoj
.directive("uiUserAdminSpan",
->
  restrict: "E"
  scope:
    user: "="
  controller: [
    "$scope", "$http", "$modal", "$window", "UserProfile",
    ($scope, $http, $modal, $window, $userProfile)->
      $scope.showEditor = ->
        $userProfile.setProfile $scope.user.userName
        $modal.open(
          templateUrl: "template/modal/user-admin-modal.html"
          controller: "UserAdminModalController"
        )
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