angular.module("cdojV2").factory("SettingsService", [
  "$cookies"
  ($cookies) ->
    defaultSettings =
      lang: "en"

    currentSettings = angular.copy(defaultSettings)
    # Load from cookies
    _.each(currentSettings, (value, key) ->
      cookieValue = $cookies[key]
      if angular.isDefined(cookieValue)
        currentSettings[key] = cookieValue
    )

    _get = (attr) ->
      return angular.copy(currentSettings[attr])

    _getAll = ->
      return angular.copy(currentSettings)

    _getDefaultSettings = ->
      return angular.copy(defaultSettings)

    _set = (attr, value) ->
      currentSettings[attr] = value
      $cookies[attr] = value

    _setAll = (settings) ->
      _.each(settings, (value, key) ->
        _set(key, value)
      )

    return {
      get: _get
      getAll: _getAll
      getDefaultSettings: _getDefaultSettings
      set: _set
      setAll: _setAll
    }
])