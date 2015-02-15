angular.module("cdojV2").factory("Msg", [
  "$cookies"
  ($cookies) ->
    messages =
      "en": messages_en
      "zh": messages_zh

    lang = $cookies.lang
    if lang == undefined
      lang = "en"

    return (message) ->
      messages[lang][message] || messages["en"][message]
])