cdoj.controller("MessageModalController", [
  "$scope", "$rootScope", "$http", "$modalInstance", "message", "$window"
  ($scope, $rootScope, $http, $modalInstance, message, $window) ->
    $scope.message = message
    $scope.message.content = "Loading..."
    $http.get("/message/fetch/#{$scope.message.messageId}").success((data) ->
      if data.result == "success"
        if $scope.message.isOpened == false
          $rootScope.$broadcast("all:refresh")
        _.extend($scope.message, data.message)
      else
        $window.alert data.error_msg
    ).error(->
      $window.alert "Network error."
    )
    $scope.$on("$routeChangeStart", ->
      $modalInstance.dismiss()
    )
    $scope.printMessage = ->
      printWindow = $window.open("", "print")
      printWindow.document.write("<html><head><title>print</title>")
      printWindow.document.write("</head><body >")

      content = angular.copy($scope.message.content)
      content = marked(content)
      $content = $("<div></div>").append(content)

      MathJax.Hub.Queue(["Typeset", MathJax.Hub, $content[0]])
      console.log $content.find("pre")
      $content.find("pre").each (id, el) ->
        $el = $(el)
        if $el.attr("type") != "no-prettify"
          text = prettyPrintOne($el[0].innerText.escapeHTML())
          $el.empty().append(text)

      printWindow.document.write($content[0].innerHTML)
      printWindow.document.write("</body></html>")

      printWindow.print()
      printWindow.close()

])