cdoj
.directive(
  "team",
  ->
    restrict: "E"
    replace: true
    templateUrl: "template/team/team.html"
    scope:
      user: "="
      team: "="
    controller: [
      "$scope", "$rootScope", "$http", "$modal"
      ($scope, $rootScope, $http, $modal) ->
        $scope.editPermission = false
        if $rootScope.hasLogin
          if (
              $rootScope.currentUser.userId == $scope.user.userId ||
              $rootScope.isAdmin
          )
            $scope.editPermission = true
        $scope.allow = $scope.team.allow
        $scope.$watch("allow", (val) ->
          $scope.team.allow = val
          team = $scope.team
          $http.get(
            "/team/changeAllowState/" +
              $scope.user.userId + "/" + team.teamId + "/" + team.allow
          ).success(->
            $http.get(
              "/team/typeAHeadItem/" + $scope.team.teamName
            ).success((data) ->
              if data.result == "success"
                $scope.team = data.team
              else
                $window.alert data.error_msg
            ).error(->
              $window.alert "Network error."
            )
          ).error(->
            $window.alert "Network error."
          )
        )
        $scope.showTeamEditor = (team) ->
          $modal.open(
            templateUrl: "template/modal/team-editor-modal.html"
            controller: "TeamEditorModalController"
            resolve:
              team: ->
                team
          )
    ]
)