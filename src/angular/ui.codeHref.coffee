cdoj.directive("uiCodeHref",
->
  restrict: "A"
  scope:
    status: "="
  controller: [
    "$scope", "$rootScope", "$http"
    ($scope, $rootScope, $http) ->
      $scope.showHref = false
      $rootScope.$watch("hasLogin",
      ->
        if $rootScope.hasLogin && ($rootScope.currentUser.type == 1 || $rootScope.currentUser.userName == $scope.status.userName)
          $scope.showHref = true
        else
          $scope.showHref = false
      )
      $scope.showCode = ->
        statusId = $scope.status.statusId
        $http.post("/status/info/#{statusId}").then(
          (response) ->
            data = response.data
            compileInfo = ""
            if data.result == "success"
              compileInfo = data.compileInfo
            else
              compileInfo = data.error_msg
            code = ""
            if data.result == "success"
              code = data.code
            else
              code = data.error_msg
            code = code.escapeHTML();
            $modal = $("#code-modal")
            $modal.find(".modal-body").empty().append("<pre>#{code}</pre>")
            $modal.find(".modal-body").prettify()
            $modal.modal("toggle")
        )
  ]
  template: """
<a href="#" ng-show="showHref" ng-click="showCode()">{{status.length}} B</a>
<span ng-hide="showHref">{{status.length}} B</span>
    """
)