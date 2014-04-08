###
angular-bootstrap-switch
@version v0.2.1 - 2013-12-31
@author Francesco Pontillo (francescopontillo@gmail.com)
@link https://github.com/frapontillo/angular-bootstrap-switch
@license Apache License 2.0
###
"use strict"

# Source: common/module.js
angular.module "frapontillo.bootstrap-switch", []

# Source: dist/.temp/directives/bsSwitch.js
angular.module("frapontillo.bootstrap-switch").directive "bsSwitch", [
  "$timeout"
  ($timeout) ->
    restrict: "EA"
    require: "ngModel"
    template: "<input>"
    replace: true
    scope:
      switchToggle: "&"
      switchActive: "@"
      switchSize: "@"
      switchOn: "@"
      switchOff: "@"
      switchOnLabel: "@"
      switchOffLabel: "@"
      switchLabel: "@"
      switchIcon: "@"
      switchAnimate: "@"

    link: (scope, tElement, attrs, controller) ->
      element = $(tElement)
      listenToModel = ->
        controller.$formatters.push (newValue) ->
          if angular.isDefined newValue
            $timeout ->
              element.bootstrapSwitch "setState", newValue or false, true

        scope.$watch "switchActive", (newValue) ->
          active = newValue is true or newValue is "true" or not newValue
          element.bootstrapSwitch "setDisabled", not active

        scope.$watch "switchOnLabel", (newValue) ->
          element.bootstrapSwitch "setOnLabel", newValue or "Yes"

        scope.$watch "switchOffLabel", (newValue) ->
          element.bootstrapSwitch "setOffLabel", newValue or "No"

        scope.$watch "switchOn", (newValue) ->
          attrs.dataOn = newValue
          element.bootstrapSwitch "setOnClass", newValue or ""

        scope.$watch "switchOff", (newValue) ->
          attrs.dataOff = newValue
          element.bootstrapSwitch "setOffClass", newValue or ""

        scope.$watch "switchAnimate", (newValue) ->
          element.bootstrapSwitch "setAnimated", scope.$eval(newValue or "true")

        scope.$watch "switchSize", (newValue) ->
          element.bootstrapSwitch "setSizeClass", scope.getSizeClass(newValue)

        scope.$watch "switchLabel", (newValue) ->
          element.bootstrapSwitch "setTextLabel", newValue

        scope.$watch "switchIcon", (newValue) ->
          element.bootstrapSwitch "setTextIcon", newValue

      listenToView = ->
        element.on "switch-change", (e, data) ->
          scope.$apply ->
            controller.$setViewValue data.value
            scope.switchToggle()

      scope.getSizeClass = ->
        (if attrs.switchSize then "switch-" + attrs.switchSize else "")

      listenToModel()
      listenToView()
      element.bootstrapSwitch()
      $timeout ->
        element.bootstrapSwitch(
          "setState"
          controller.$modelValue or false
          true
        )

      scope.$on "$destroy", ->
        element.bootstrapSwitch "destroy"
]