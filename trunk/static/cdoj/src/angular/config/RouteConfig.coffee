cdoj
.config([
    "$routeProvider",
    ($routeProvider) ->
      $routeProvider.when("/",
        templateUrl: "template/index/index.html"
        controller: "IndexController"
      ).when("/status/list",
        templateUrl: "template/status/list.html"
        controller: "StatusListController"
      ).when("/user/list",
        templateUrl: "template/user/list.html"
        controller: "UserListController"
      ).when("/user/center/:userName/:tab",
        templateUrl: "template/user/center.html"
        controller: "UserCenterController"
      ).when("/user/center/:userName",
        templateUrl: "template/user/center.html"
        controller: "UserCenterController"
      ).when("/user/activate/:userName/:serialKey",
        templateUrl: "template/user/activation.html"
        controller: "PasswordResetController"
      ).when("/user/register",
        templateUrl: "template/user/register.html"
        controller: "UserRegisterController"
      ).when("/article/show/:articleId",
        templateUrl: "template/article/show.html"
        controller: "ArticleShowController"
      ).when("/article/editor/:userName/:action",
        templateUrl: "template/article/editor.html"
        controller: "ArticleEditorController"
      ).when("/admin/dashboard",
        templateUrl: "template/admin/dashboard.html"
        controller: "AdminDashboardController"
      ).when("/404/",
        templateUrl: "template/index/404.html"
      ).when("/error/:message",
        templateUrl: "template/index/error.html"
        controller: "ErrorController"
      ).otherwise(
        redirectTo: "/404/"
      )
  ])