cdoj
.factory("Error", [
    "$location"
    ($location) ->
      error: (message) ->
        $location.path("/error/" + message.escapeURL())
  ])