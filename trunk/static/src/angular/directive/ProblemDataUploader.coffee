cdoj
.directive("uiProblemDataUploader",
->
  restrict: "E"
  scope:
    uploadUrl: "@"
  controller: [
    "$scope", "$element"
    ($scope, $element) ->
      $scope.hint = "Upload"
      $scope.hasError = false
      dataUploader = new qq.FineUploaderBasic(
        button: $element[0]
        request:
          endpoint: $scope.uploadUrl
          inputName: "uploadFile"
        validation:
          allowedExtensions: ["zip"],
          sizeLimit: 300 * 1024 * 1024 # 300 MB
        multiple: false
        callbacks:
          onComplete: (id, fileName, data) ->
            if data.success == "true"
              $scope.$apply(->
                $scope.hint = "Total data: #{data.total}"
                $scope.hasError = false
              )
            else
              $scope.$apply(->
                $scope.hint = data.error
                $scope.hasError = true
              )
          onProgress: (id, fileName, uploadedBytes, totalBytes) ->
            $scope.$apply(->
              percentage = Math.round(uploadedBytes / totalBytes * 100)
              $scope.hint = "#{percentage} %"
            )
          onError: (id, fileName, errorReason) ->
            $scope.hasError = true
            $scope.hint = errorReason
      )
  ]
  template: """
                <span class="btn"
                    ng-class="{'btn-success': hasError == false,
'btn-danger': hasError == true}">
                  <i class="fa fa-upload"></i><span ng-bind="hint"></span>
                </span>
"""
  replace: true
)