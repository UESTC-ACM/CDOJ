angular.module("cdojV2").factory("LanguageConfig",
  ->
    languages =
      "en": {
        "name": "English"
        "messages": messages_en
      }
      "zh": {
        "name": "中文（简体）"
        "messages": messages_zh
      }
    return -> languages
)

angular.module("cdojV2").factory("Msg", [
  "SettingsService", "LanguageConfig"
  (settingsService, LanguageConfig) ->
    languages = LanguageConfig()
    settingKey = "lang"
    defaultLanguage = "en"

    _getMessage = (lang, message) ->
      language = languages[lang]
      result = language["messages"][message]
      # Use defaultLanguage when not found, and void infinite loop
      if !angular.isDefined(result) and lang != defaultLanguage
        result = _getMessage(defaultLanguage, message)
      return result

    return (message) ->
      lang = settingsService.get(settingKey)
      return _getMessage(lang, message)
])