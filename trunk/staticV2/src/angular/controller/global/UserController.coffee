angular.module("cdojV2").controller("UserController", [
  "$scope", "msg"
  ($scope, msg) ->
    $scope.signInText = msg("SignIn")
    $scope.needLogin = true
])