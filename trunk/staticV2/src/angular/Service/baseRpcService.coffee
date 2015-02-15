angular.module("cdojV2").factory("BaseRpcService", [
  "$http", "ToastService"
  ($http, toast) ->
    showError = ->
      # Show error

    callback = (onSuccess, onError) ->
      return (data) ->
        if data.result == "success"
          onSuccess(data)
        else
          onError(data)
    serverError = ->
      return -> toast.error(msg("NetworkError"))

    getFinalAddress = (address) ->
      # We can support remote api server in the feature.
      return address

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