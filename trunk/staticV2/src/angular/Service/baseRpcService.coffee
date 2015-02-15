angular.module("cdojV2").factory("BaseRpcService", [
  "$http", "$mdToast", "Msg"
  ($http, $mdToast, msg) ->
    showError = ->
      # Show error

    callback = (onSuccess, onError) ->
      return (data) ->
        if data.result == "success"
          onSuccess(data)
        else
          onError(data)
    serverError = ->
      return ->
        message = msg("NetworkError")
        toast = $mdToast.simple()
          .content(message)
          .action(msg("OK"))
          .hideDelay(0)
          .highlightAction(false)
          .position("top right")
        $mdToast.show(toast)

    getFinalAddress = (address) ->
      # We can support remote api server in the feature.
      return "http://acm.uestc.edu.cn" + address

    _get = (address, onSuccess, onError) ->
      $http.get(getFinalAddress(address))
        .success(callback(onSuccess, onError))
        .error(serverError())
    _post = (address, content, onSuccess, onError) ->
      $http.post(getFinalAddress(address), content)
        .success(callback(onSuccess, onError))
        .error(serverError())

    return {
      get: (address, onSuccess, onError) ->
        _get(address, onSuccess, onError)
      post: (address, content, onSuccess, onError) ->
        _post(address, content, onSuccess, onError)
    }
])