cdoj.controller("UserController", [
  "$scope", "$rootScope", "$http", "$element", "$compile"
  ($scope, $rootScope, $http, $element, $compile) ->
    $rootScope.hasLogin = false
    $rootScope.currentUser = 0
    $scope.userLoginDTO =
      userName: ""
      password: ""
    $scope.fieldInfo = []
    $scope.views =
      loginView: """
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            Sign in
          </a>
          <ul ui-dropdown-menu class="dropdown-menu cdoj-form-menu" style="width: 340px;">
            <li>
              <form>
                <div class="input-group form-group input-group-sm">
                  <span class="input-group-addon">
                    <i class="fa fa-user" style="width: 14px;"></i>
                  </span>
                  <input type="text"
                         ng-model="userLoginDTO.userName"
                         maxlength="24"
                         id="userName"
                         class="form-control"
                         ng-required="true"
                         ng-pattern="/^[a-zA-Z0-9_]{4,24}$/"
                         placeholder="Username"/>
                </div>
                <div class="input-group form-group input-group-sm">
                  <span class="input-group-addon">
                    <i class="fa fa-key" style="width: 14px;"></i>
                  </span>
                  <input type="password"
                         ng-model="userLoginDTO.password"
                         id="password"
                         ng-required="true"
                         class="form-control"
                         placeholder="Password"/>
                  <span class="input-group-btn">
                    <button type="submit"
                            class="btn btn-default"
                            ng-click="login()">
                      Login
                    </button>
                  </span>
                </div>
                <ui-validate-info value="fieldInfo" for="password"></ui-validate-info>
              </form>
            </li>
            <li role="presentation" class="divider"></li>
            <li>
              <a href="#" data-toggle="modal"
                 data-target="#cdoj-register-modal">
                <i class="fa fa-arrow-circle-right" style="padding-right: 6px;"></i>Register</a>
              <a href="#" data-toggle="modal"
                 data-target="#cdoj-activate-modal">
                <i class="fa fa-arrow-circle-right" style="padding-right: 6px;"></i>Forget password? </a>
            </li>
          </ul>
      """
      userView: """
            <div id="cdoj-user">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <img id="cdoj-user-avatar"
                     ui-avatar
                     email="currentUser.email"
                     src="/images/avatar/default.jpg"/>
              </a>
              <ul class="dropdown-menu"
                  role="menu"
                  aria-labelledby="user-menu">
                <li role="presentation"
                    class="dropdown-header text-center">
                  <span id="currentUser"
                        ng-bind="currentUser.userName">
                    </span>
                </li>
                <li role="presentation">
                  <a href="/user/center/{{currentUser.userName}}">
                    <i class="fa fa-home"></i>User center
                  </a>
                </li>
                <li role="presentation">
                  <a href="#" data-toggle="modal"
                     data-target="#cdoj-profile-edit-modal">
                    <i class="fa fa-wrench"></i>Edit profile
                  </a>
                </li>
                <li role="presentation" class="divider"></li>
                <li role="presentation">
                  <a href="#" id="cdoj-logout-button" ng-click="logout()">
                    <i class="fa fa-power-off"></i>Logout
                  </a>
                </li>
              </ul>
            </div>
      """
    $rootScope.$watch("hasLogin",
      ->
        if $rootScope.hasLogin
          view = $scope.views.userView
        else
          view = $scope.views.loginView
        # recompile content
        $element.html(view)
        $compile($element.contents())($scope)
    )
    $scope.login = ->
      password = CryptoJS.SHA1($scope.userLoginDTO.password).toString()
      $scope.userLoginDTO.password = password
      $http.post("/user/login", $scope.userLoginDTO).then (response)->
        data = response.data
        console.log data
        if data.result == "success"
          $rootScope.hasLogin = true
          $rootScope.currentUser =
            userName: data.userName
            email: data.email
            type: data.type
        else if data.result == "field_error"
          $scope.fieldInfo = data.field
        else
          alert data.error_msg
    $scope.logout = ->
      $http.post("/user/logout").then (response)->
        data = response.data
        if data.result == "success"
          $rootScope.hasLogin = false
          $rootScope.currentUser = 0
])
console.log CryptoJS.SHA1("wrongPassword").toString()