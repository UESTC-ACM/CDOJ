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
    # Use Default internal method until overridden by directive postLink
    self.$toggleOpen = -> $q.when($scope.isOpen)
    self.isOpen = -> !$scope.isOpen
    self.open -> self.$toggleOpen(true)
    self.close -> self.$toggleOpen(false)
    self.toggle -> self.$toggleOpen(!$scope.isOpen)
    
    self.destroy = $mdComponentRegistry.register(self, $attrs.mdComponentId)
]).directive("cdojDropdownToggle", ->
  restrict: "E"
  controller: "DropdownController"

)