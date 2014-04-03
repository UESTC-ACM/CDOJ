cdoj
.config([
    "$httpProvider",
    ($httpProvider) ->
      #initialize get if not there
      if (!$httpProvider.defaults.headers.get)
        $httpProvider.defaults.headers.get = {}
      #disable IE ajax request caching
      $httpProvider.defaults.headers.get["Cache-Control"] = "no-cache"
      $httpProvider.defaults.headers.get["Pragma"] = "no-cache"
  ])