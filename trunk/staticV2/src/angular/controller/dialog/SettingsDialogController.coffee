angular.module("cdojV2").controller("SettingsDialogController", [
  "$scope", "SettingsService", "Msg", "LanguageConfig", "$mdDialog"
  ($scope, settingsService, msg, LanguageConfig, $mdDialog) ->
    $scope.title = msg("Settings")
    $scope.resetLabel = msg("Reset")
    $scope.cancelLabel = msg("Cancel")
    $scope.saveLabel = msg("Save")
    $scope.languageLabel = msg("Language")

    $scope.settings = settingsService.getAll()
    # Language
    $scope.languages = LanguageConfig()

    # Action
    $scope.reset = ->
      $scope.settings = settingsService.getDefaultSettings()
    $scope.cancel = ->
      $scope.settings = settingsService.getAll()
      $mdDialog.cancel();
    $scope.save = ->
      oldSettings = settingsService.getAll()
      if angular.equals($scope.settings, oldSettings)
        # Value not changed
        $mdDialog.cancel()
      else
        settingsService.setAll($scope.settings)
        $mdDialog.hide($scope.settings)
])