cdoj
.directive("uiOnsiteUserFileUploader", [
    "$window"
    ($window) ->
      restrict: "E"
      replace: true
      link: ($scope, $element) ->
        $scope.label = "Load from CSV file"
        onsiteUserFileUploader = new qq.FineUploaderBasic(
          button: $($element)[0]
          request:
            endpoint: "/contest/uploadOnsiteUserFile"
            inputName: "uploadFile"
          validation:
            allowedExtensions: ["csv", "txt"],
            sizeLimit: 10 * 1000 * 1000 # 10 MB
          multiple: false
          callbacks:
            onSubmit: ->
              $scope.$apply(->
                $scope.label = "Uploading..."
              )
            onComplete: (id, fileName, data) ->
              if data.success == "true"
                $scope.$apply(->
                  $scope.$emit("onsiteUserFileUploader:complete", data.list)
                  $scope.label = "Load from CSV file"
                )
            onError: (id, fileName, errorReason) ->
              $window.alert errorReason
        )
      template: """
<button class="btn btn-danger">{{label}}</button>
"""
  ])