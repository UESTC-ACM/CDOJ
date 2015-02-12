getTemplateUrl = (path, file) ->
  "templateV2/#{path}/#{file}.html"

cdojV2 = angular.module('cdojV2', [
  "ngMaterial"
  "ngRoute"
  "ngCookies"
])

cdojV2
.run([
    "$rootScope", "$http", "$cookies"
    ($rootScope, $http, $cookies) ->
      supportedLanguages = ["en"]

      lang = $cookies.lang
      if lang == undefined || !(lang in supportedLanguages)
        lang = "en"

      messages = {}
      $rootScope.getMessage = (message) -> messages[message]

      $http.get("/i18n/#{lang}.json").success((data) ->
        messages = data
      )
])
