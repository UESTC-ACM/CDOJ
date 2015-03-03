###
  Menu in material design
###
angular.module("cdojV2").directive("cdojMenu", ->
  restrict: "E"
  template: """<div layout="column" class="cdoj-menu" ng-transclude></div>"""
  replace: true
  transclude: true
).directive("cdojMenuItem", [
  "$mdInkRipple", "$mdTheming", "$mdAria"
  ($mdInkRipple, $mdTheming, $mdAria) ->
    isAnchor = (attr) ->
      angular.isDefined(attr.href) || angular.isDefined(attr.ngHref)

    getTemplate = (element, attr) ->
     if isAnchor(attr)
      '<a class="cdoj-menu-button" ng-transclude></a>'
     else
      '<button class="cdoj-menu-button" ng-transclude></button>'

    postLink = (scope, element, attr) ->
      node = element[0]
      $mdTheming(element)
      $mdInkRipple.attachButtonBehavior(scope, element)

      elementHasText = node.textContent.trim()
      if !elementHasText
        $mdAria.expect(element, 'aria-label')

      # For anchor elements, we have to set tabindex manually when the
      # element is disabled
      if isAnchor(attr) && angular.isDefined(attr.ngDisabled)
        scope.$watch(attr.ngDisabled, (isDisabled) ->
          element.attr('tabindex', isDisabled ? -1 : 0)
        )

    return {
      restrict: "E"
      replace: true
      transclude: true
      template: getTemplate
      link: postLink
    }
])