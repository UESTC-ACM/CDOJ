cdoj.directive("uiPageInfo",
->
  restrict: "A"
  scope:
    pageInfo: "="
    condition: "="
  controller: [
    "$scope",
    ($scope) ->
      $scope.pageList = []
      $scope.$watch("pageInfo", ()->
        if $scope.pageInfo.totalPages > 1
          beginPages = Math.max(1, $scope.pageInfo.currentPage - $scope.pageInfo.displayDistance)
          endPages = Math.min($scope.pageInfo.totalPages, beginPages + $scope.pageInfo.displayDistance * 2)
          beginPages = Math.max(1, endPages - $scope.pageInfo.displayDistance * 2)
          $scope.pageList = _.range(beginPages, endPages + 1).map(
            (num) ->
              page: num
              active: num == $scope.pageInfo.currentPage
          )
      )
      $scope.jump = (target)->
        $scope.condition.currentPage = target
  ]
  template: """
      <ul class="pagination pagination-sm">
        <li><a href="#" ng-click="jump(1)"><i class="fa fa-arrow-left"></i></a></li>
        <li ng-repeat="page in pageList"
            ng-class="{active: page.active}">
          <a href="#" ng-click="jump(page.page)">{{page.page}}</a>
        </li>
        <li><a href="#" ng-click="jump(pageInfo.totalPages)"><i class="fa fa-arrow-right"></i></a></li>
      </ul>
    """
)