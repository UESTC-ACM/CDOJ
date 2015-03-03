describe("Controller: MainController", ->
  beforeEach(module("cdojV2"))

  beforeEach(inject((_$controller_, _$rootScope_) ->
    @scope = _$rootScope_.$new()
    @mainController = _$controller_("MainController", $scope: @scope)
  ))

  it("Should inject correctly", ->
    expect(@scope.openMenu).toBeDefined()
  )
)