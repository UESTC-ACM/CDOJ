describe("MainController", ->
  beforeEach(module('cdojV2'));

  beforeEach(inject((_$controller_, $injector) ->
   #The injector unwraps the underscores (_) from around the parameter names when matching
   $controller = _$controller_
   mdSidenav = $injector
  ))

  describe("Click open menu button", ->
    it("Do nothing when sidenav is already opened", ->
      $scope = {}
      controller = $controller("PasswordController", { $scope: $scope })
      mdSidenav = $injector("$mdSidenav")
      mdSidenav.open()
      $scope.openMenu()

      expect(mdSidenav.isOpen()).toBe(true)
    )

    it("Open sidenav when it is closed", ->
      $scope = {}
      controller = $controller("PasswordController", { $scope: $scope })
      mdSidenav = $injector("$mdSidenav")
      mdSidenav.close()
      $scope.openMenu()

      expect(mdSidenav.isOpen()).toBe(true)
    )
  )
)