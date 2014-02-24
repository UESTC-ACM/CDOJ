# Global angular module
cdoj = angular.module("cdoj", ["ngSanitize", "monospaced.elastic", "ui.bootstrap"])
cdoj.run([
  "$rootScope", "$http"
  ($rootScope, $http)->
    $http.get("/globalData").then (response)->
      data = response.data
      if data.result == "error"
        alert(data.error_msg)
      else
        _.extend($rootScope, data)
])