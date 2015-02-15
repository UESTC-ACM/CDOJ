###
  Dropdown in material design
###
angular.module("cdojV2").factory("DropdownService", [
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
  "$scope", "$mdComponentRegistry", "$q", "$attrs", "$document", "$animate",
  "$timeout"
  ($scope, $mdComponentRegistry, $q, $attrs, $document, $animate, $timeout) ->
    self = this
    promise = $q.when(true)
    $scope.isOpen = false

    setDropdownMenuPosition = ->
      dropdownMenu = self.dropdownMenu
      dropdown = self.dropdown

      top = dropdown.prop("offsetHeight")
      right = 0

      dropdownMenu.css(
        position: "absolute"
        top: top
        right: right
      )

    updateIsOpen = (isOpen) ->
      setDropdownMenuPosition()
      $scope.isOpen = isOpen
      $document[if isOpen then "on" else "off"]("click", self.close)
      $animate[if isOpen then "removeClass" else "addClass"](
        self.dropdownMenu, "cdoj-dropdown-menu-closed"
      )

    self.$toggleOpen = (isOpen) ->
      if $scope.isOpen == isOpen
        return $q.when(true)
      else
        # Fix it
        updateIsOpen(isOpen)
        return $q.when(true)

    self.isOpen = -> false
    self.open = -> self.$toggleOpen(true)
    self.close = -> self.$toggleOpen(false)
    self.toggle = -> self.$toggleOpen(!$scope.isOpen)

    self.destroy = $mdComponentRegistry.register(self, $attrs.mdComponentId)
]).directive("cdojDropdown", ->
  restrict: "E"
  controller: "DropdownController"
  link: ($scope, $element, $attr, $ctrl) ->
    $ctrl.dropdown = $element
    $element.css(
      position: "relative"
    )
).directive("cdojDropdownToggle", ->
  restrict: "A"
  require: "^cdojDropdown"
  link: ($scope, $element, $attr, $ctrl) ->
    $ctrl.dropdownToggle = $element
    $element.on("click", (event) ->
      event.stopPropagation()
      $ctrl.open()
    )
).directive("cdojDropdownMenu", ->
  restrict: "E"
  require: "^cdojDropdown"
  transclude: true
  replace: true
  template: """
    <md-card class="cdoj-dropdown-menu cdoj-dropdown-menu-closed"
         ng-transclude>
    </md-card>
  """
  link: ($scope, $element, $attr, $ctrl) ->
    $ctrl.dropdownMenu = $element
)