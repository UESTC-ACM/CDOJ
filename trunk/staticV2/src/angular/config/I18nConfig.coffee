angular.module("cdojV2").run([
  "$rootScope", "$http", "$cookies"
  ($r, $http, $cookies) ->
    messages =
      "en": messages_en
      "zh": messages_zh

    lang = $cookies.lang
    if lang == undefined
      lang = "zh"

    $r.getMessage = (message) ->
      messages[lang][message] || messages["en"][message]
])