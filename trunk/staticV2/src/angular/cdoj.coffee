getTemplateUrl = (path, file) ->
  "templateV2/#{path}/#{file}.html"

cdojV2 = angular.module('cdojV2', [
  "ngMaterial"
  "ngRoute"
])
