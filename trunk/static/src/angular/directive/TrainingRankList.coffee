cdoj
.directive("uiTrainingRankList"
  ->
    restrict: "E"
    scope:
      rankList: "="
      type: "="
      platformType: "="
    controller: ["$scope",
      ($scope) ->
    ]
    replace: true
    templateUrl: "template/training/trainingRankList.html"
)