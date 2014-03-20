cdoj
.controller("ContestListController", [
    "$scope", "$rootScope", "$window", "$modal"
    ($scope, $rootScope, $window, $modal)->
      $scope.showAddContestModal = ->
        if $rootScope.isAdmin == false then return
        $modal.open(
          templateUrl: "template/modal/add-contest-modal.html"
          controller: "AddContestModalController"
        )
  ])
cdoj
.controller("AddContestModalController", [
    "$scope", "$rootScope", "$modalInstance", "$window"
    ($scope, $rootScope, $modalInstance, $window)->
      $scope.$on("$routeChangeStart", ->
        $modalInstance.close()
      )
      $scope.$on("contestUploader:complete", (e, contestId)->
        $window.location.href = "/#/contest/show/#{contestId}"
      )
  ])
cdoj
.directive("uiContestUploader", [
    "$window"
    ($window) ->
      restrict: "E"
      replace: true
      link: ($scope, $element)->
        contestUploader = new qq.FineUploaderBasic(
          button: $($element)[0]
          request:
            endpoint: "/contest/createContestByArchiveFile"
            inputName: "uploadFile"
          validation:
            allowedExtensions: ["zip"],
            sizeLimit: 100 * 1000 * 1000 # 100 MB
          multiple: false
          callbacks:
            onComplete: (id, fileName, data) ->
              console.log data
              if data.success == "true"
                $scope.$emit("contestUploader:complete", data.contestId)
              else
                $window.alert data.error_msg
        )
      template: """
<button class="btn btn-danger">Upload zip file</button>
"""
  ])