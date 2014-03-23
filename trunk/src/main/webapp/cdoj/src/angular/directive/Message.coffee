cdoj
.directive("messagelink",
->
  restrict: "EA"
  scope:
    message: "="
  replace: true
  controller: [
    "$scope", "$modal",
    ($scope, $modal)->
      $scope.readMessage = ->
        $scope.$broadcast("refresh")
        $modal.open(
          templateUrl: "template/modal/message-modal.html"
          controller: "MessageModalController"
          resolve:
            message: ->
              $scope.message
        )
  ]
  template: """
<a href="javascript:void(0);"
   ng-click="readMessage()"
   ng-bind="message.title"></a>
"""
)