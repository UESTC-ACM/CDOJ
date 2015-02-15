angular.module("cdojV2").factory("BaseRpcService", [
  "$http"
  ($http) ->
    showError = ->
      # Show error

    callback = (onSuccess, onError) ->
      return (data) ->
        if data.result == "success"
          onSuccess(data)
        else
          onError(data)
    serverError = (onError) ->
      onError(error_msg: "Unable load data from server")

    _get = (address, onSuccess, onError) ->
      $http.get(address)
        .success(callback(onSuccess, onError))
        .error(serverError(onError))
    _post = (address, content, onSuccess, onError) ->
      $http.post(address, content)
        .success(callback(onSuccess, onError))
        .error(serverError(onError))

    return {
      get: (address, onSuccess, onError) ->
        _get(address, onSuccess, onError)
      post: (address, content, onSuccess, onError) ->
        _post(address, content, onSuccess, onError)
    }
])