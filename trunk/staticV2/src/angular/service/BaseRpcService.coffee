angular.module("cdojV2").factory("BaseRpcService", [
  "$http", "ToastService", "$q", "Msg"
  ($http, toast, $q, msg) ->
    showError = (error_msg) ->
      toast.error(error_msg)

    callback = (deferred) ->
      return (data) ->
        if data.result == "success"
          deferred.resolve(data)
        else
          showError(data.error_msg)
          deferred.reject(data)
    serverError = (deferred) ->
      return ->
        error_msg = msg("NetworkError")
        showError(error_msg)
        deferred.reject(error_msg)

    getFinalAddress = (address) ->
      # We can support remote api server in the feature.
      return address

    _get = (address) ->
      deferred = $q.defer()
      $http.get(getFinalAddress(address))
        .success(callback(deferred))
        .error(serverError(deferred))
      return deferred.promise
    _post = (address, content, onSuccess, onError) ->
      deferred = $q.defer()
      $http.post(getFinalAddress(address), content)
        .success(callback(deferred))
        .error(serverError(deferred))
      return deferred.promise

    return {
      get: (address) ->
        _get(address)
      post: (address, content) ->
        _post(address, content)
    }
])