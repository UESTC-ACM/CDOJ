cdoj
.directive("uiLoadingNotification",
->
  restrict: "E"
  controller: [
    "$scope", "$rootScope", "$window"
    ($scope, $rootScope, $window) ->
      $scope.show = false
      $scope.marginTopValue = (($window.innerHeight - 100) / 2) + "px"
      $rootScope.$on(
        "loading:on"
        ->
          $scope.show = true
      )
      $rootScope.$on(
        "loading:off"
        ->
          $scope.show = false
      )
  ]
  template: """
<div>
<div ng-if="show"
     style="position: fixed;
            top: 0;
            left: 0;
            z-index: 999998;
            width: 100%;
            height: 100%;
            background: #000;
            filter: alpha(opacity=60);
            opacity: 0.6;">
</div>
<div ng-if="show"
     style="position: fixed;
            top: 0;
            left: 0;
            z-index: 999999;
            width: 100%;
            height: 100%;">
  <div style="margin-left: auto;
              margin-right: auto;
              width: 200px;
              height: 100px;
              background: #FFF;"
      ng-style="{'margin-top': marginTopValue}">
    <div class="text-center"
         style="padding-top: 36px;
                font-size: 24px;
                color: #F00;
                font-weight: bold;">
      LOADING
    </div>
  </div>
</div>
</div>
"""
)