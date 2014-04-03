cdoj
.controller("ContestEditorController", [
    "$scope", "$rootScope", "$http", "$window", "contest"
    ($scope, $rootScope, $http, $window, contest) ->
      # Administrator only
      $scope.$emit(
        "permission:setPermission"
        $rootScope.AuthenticationType.ADMIN
      )
      $window.scrollTo(0, 0)

      $scope.contest = contest
      $scope.problemList = []
      $scope.fieldInfo = []
      $scope.action = contest.action

      if $scope.action != "new"
        $scope.title = "Edit contest " + $scope.action
        $scope.problemList = _.map(contest.problemList, (val) ->
          problemId: val.problemId
          title: val.title
        )
      else
        $scope.title = "New contest"

      $scope.$watch("problemList", () ->
        $scope.contest.problemList = _.map($scope.problemList, (val) ->
          return val.problemId
        ).join(",")
      , true)

      $scope.updateProblemTitle = (problem) ->
        if isNaN(parseInt(problem.problemId))
          problem.title = "Invalid problem id!"
        else
          $http.get(
            "/problem/query/" + problem.problemId + "/title"
          ).success (data) ->
            if data.result == "success"
              if data.list.length == 1
                problem.title = data.list[0]
              else
                problem.title = "No such problem!"
            else
              problem.title = data.error_msg

      $scope.addProblem = ->
        problemId = ""
        if $scope.problemList.length > 0
          lastId = parseInt($scope.problemList.last().problemId)
          if !isNaN(lastId)
            problemId = lastId + 1
        problem =
          problemId: problemId
          title: ""
        $scope.updateProblemTitle(problem)
        $scope.problemList.add(problem)

      $scope.removeProblem = (index) ->
        $scope.problemList.splice(index, 1)

      $scope.fieldInfo = []

      $scope.submit = ->
        contestEditDTO = angular.copy($scope.contest)
        if contestEditDTO.type == $rootScope.ContestType.PRIVATE
          if (
              angular.isUndefined(contestEditDTO.password) ||
              angular.isUndefined(contestEditDTO.passwordRepeat)
          )
            $window.scrollTo(0, 0)
            return
        else if contestEditDTO.type == $rootScope.ContestType.INHERIT
          if angular.isUndefined(contestEditDTO.parentId)
            $window.scrollTo(0, 0)
            return

        contestEditDTO.time = Date.create(contestEditDTO.time).getTime()
        password = CryptoJS.SHA1(contestEditDTO.password).toString()
        contestEditDTO.password = password
        passwordRepeat = CryptoJS.SHA1(contestEditDTO.passwordRepeat).toString()
        contestEditDTO.passwordRepeat = passwordRepeat
        $http.post("/contest/edit", contestEditDTO).success((data) ->
          if data.result == "success"
            $window.location.href = "#/contest/show/#{data.contestId}"
          else if data.result == "field_error"
            $scope.fieldInfo = data.field
            $window.scrollTo(0, 0)
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )

      $scope.searchContest = (keyword) ->
        contestCondition =
          keyword: keyword
        $http.post("/contest/search", contestCondition).then((response) ->
          data = response.data
          if data.result == "success"
            return data.list
        )

      $scope.onsiteUsers = []
      $scope.$on("onsiteUserFileUploader:complete", (e, list) ->
        $scope.onsiteUsers = list
      )
      if $scope.contest.type == $rootScope.ContestType.ONSITE
        $http.get(
            "/contest/fetchAllOnsiteUsers/" + $scope.contest.contestId
        ).success((data) ->
          if data.result == "success"
            $scope.onsiteUsers = _.map(
              data.list
              (user) ->
                userName: user.userName
                password: "Encrypted password"
                teamName: user.nickName
                members: user.name
            )
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
      $scope.updateOnsiteUsers = ->
        userList = _.map(
          $scope.onsiteUsers
          (user) ->
            userName: user.userName
            password: CryptoJS.SHA1(user.password).toString()
            nickName: user.teamName
            name: user.members
        )
        $http.post(
          "/contest/updateOnsiteUser"
          userList: userList
          contestId: $scope.contest.contestId
        ).success((data) ->
          if data.result == "success"
            $window.alert "Update onsite users information successful!"
          else
            $window.alert data.error_msg
        ).error(->
          $window.alert "Network error."
        )
      $scope.onsiteUserFileHelp = """
You should upload user config file in csv format.

You need to specify the corresponding attribute in the header row, see
the example below for more details.

(Use `,` as the separators and `"` as the quote character)

```
"id", "userName", "password", "teamName", "members"
"team001", "12th_team001", "123456789", "UESTC_Dage", "大哥, 大哥, 大哥"
```

Please check the list carefully after you upload it, and click the update
button below to commit changes.
"""
  ])