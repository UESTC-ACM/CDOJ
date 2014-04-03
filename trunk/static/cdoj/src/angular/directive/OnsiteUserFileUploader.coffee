cdoj
.directive("uiOnsiteUserFileUploader", [
    "$window"
    ($window) ->
      restrict: "E"
      replace: true
      link: ($scope, $element) ->
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
            onComplete: (id, fileName, data) ->
              if data.success == "true"
                $scope.$emit("onsiteUserFileUploader:complete", data.list)
            onError: (id, fileName, errorReason) ->
              $window.alert errorReason
        )
      template: """
<button class="btn btn-danger">Load from CSV file</button>
"""
  ])