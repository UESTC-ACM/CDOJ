cdoj.modeul("cdojV2").factory("DropdownService", [
  "$mdComponentRegistry", "$q"
  ($mdComponentRegistry, $q) ->
    return (handle) ->
      errorMsg = "Dropdown '#{handle}' is not available!"
      instance = $mdComponentRegistry.get(handle)
      if !instance
        $mdComponentRegistry.notFoundError(handle)

      return {
        isOpen: -> instance && instance.isOpen()
        toggle: -> if instance then instance.toggle() else $q.reject(errorMsg)
        open: -> if instance then instance.open() else $q.reject(errorMsg)
        close: -> if instance then instance.close() else $q.reject(errorMsg)
      }
]).controller("DropdownController", [
  "$scope", "$mdComponentRegistry", "$q", "$attrs"
  ($scope, $mdComponentRegistry, $q, $attrs) ->
    self = this

    promise = $q.when(true)
    self.$toggleOpen = (isOpen) ->
      if $scope.isOpen == isOpen
        return $q.when(true)
      else
        deferred = $q.defer()
        return deferred.promise()

    self.isOpen = -> false
    self.open -> self.$toggleOpen(true)
    self.close -> self.$toggleOpen(false)
    self.toggle -> self.$toggleOpen(!$scope.isOpen)

    self.destroy = $mdComponentRegistry.register(self, $attrs.mdComponentId)

]).directive("cdojDropdown", ->
  restrict: "E"
  scope:
    isOpen: "="
  controller: "DropdownController"
  link = ($scope, $element, $attr, $ctrl) ->
    $ctrl.dropdown = $element
    if !angular.isDefined($scope.isOpen)
      $scope.isOpen = false
).directive("cdojDropdownToggle", ->
  restrict: "A"
  require: "^cdojDropdown"
  link = ($scope, $element, $attr, $ctrl) ->
    $ctrl.dropdownToggle = $element
).directive("cdojDropdownMenu", ->
  restrict: "E"
  require: "^cdojDropdown"
  link = ($scope, $element, $attr, $ctrl) ->
    $ctrl.dropdownMenu = $element
)