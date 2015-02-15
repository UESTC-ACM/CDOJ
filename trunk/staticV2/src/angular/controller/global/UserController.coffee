angular.module("cdojV2").controller("UserController", [
  "$scope", "Msg"
  ($scope, msg) ->
    $scope.signInText = msg("SignIn")
    $scope.needLogin = true
])