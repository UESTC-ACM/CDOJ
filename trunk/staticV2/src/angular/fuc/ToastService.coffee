angular.module("cdojV2").factory("ToastService", [
  "$mdToast", "Msg"
  ($mdToast, msg) ->
    _error = (message) ->
      toast = $mdToast.simple()
        .content(message)
        .action(msg("OK"))
        .hideDelay(0)
        .highlightAction(false)
        .position("top right")
      $mdToast.show(toast)

    return {
      error: _error
    }
])