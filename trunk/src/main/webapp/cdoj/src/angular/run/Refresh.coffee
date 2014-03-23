cdoj
.run([
    "$rootScope"
    ($rootScope)->
      $rootScope.$on("all:refresh", ->
        $rootScope.$broadcast("data:refresh")
        $rootScope.$broadcast("globalData:refresh")
      )

      # Fetch the data at the begining
      $rootScope.$broadcast("all:refresh")
  ])
