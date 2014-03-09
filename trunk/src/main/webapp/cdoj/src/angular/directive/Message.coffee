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
          $modal.open(
            templateUrl: "template/modal/message-modal.html"
            controller: "MessageModalController"
            resolve:
              message: -> $scope.message
          )
          console.log "fuck"
    ]
    template: """
<a href="javascript:void(0);"
   ng-click="readMessage()"
   ng-bind="message.title"></a>
"""
  )