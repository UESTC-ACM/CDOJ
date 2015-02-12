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
      messages =
        "en": messages_en
        "zh": messages_zh

      lang = $cookies.lang
      if lang == undefined
        lang = "zh"

      $rootScope.getMessage = (message) ->
        messages[lang][message] || messages["en"][message]
])
