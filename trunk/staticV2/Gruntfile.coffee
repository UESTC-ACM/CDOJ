module.exports = (grunt) ->

  grunt.initConfig
    pkg: grunt.file.readJSON "package.json"

    coffee:
      # All coffee script need compile in production
      cdojAngularFull:
        options:
          join: true
        files:
          "temp/angular/cdoj.angular.js": [
            "src/i18n/*.coffee"
            "src/util/*.coffee"
            "src/angular/*.coffee"
            "src/angular/**/*.coffee"
          ]

    coffeelint:
      # All coffee script should be here.
      coffee: [
        "src/i18n/*.coffee"
        "src/util/*.coffee"
        "src/angular/**/*.coffee"
        "src/angular/*.coffee"
        "src/test/**/*.coffee"
      ]
      options:
        "arrow_spacing":
          "level": "error"

    less:
      # CDOJ less files
      cdoj:
        files:
          "temp/css/cdoj.css": "src/less/cdoj.less"

    concat:
      # All dependencies scripts (full)
      cdojDepsFull:
        src: [
          "bower_components/angular/angular.js"
          "bower_components/angular-animate/angular-animate.js"
          "bower_components/angular-aria/angular-aria.js"
          "bower_components/angular-cookies/angular-cookies.js"
          "bower_components/angular-material/angular-material.js"
          "bower_components/angular-route/angular-route.js"
          "bower_components/underscore/underscore.js"
          "bower_components/underscore.string/lib/underscore.string.js"
          "bower_components/marked/lib/marked.js"
          "bower_components/google-code-prettify/src/prettify.js"
        ]
        dest: "temp/js/cdoj.deps.js"
      cdojDepsUnminimized:
        src: [
          "bower_components/marked/lib/marked.js"
          "bower_components/google-code-prettify/src/prettify.js"
        ]
        dest: "temp/js/unminimizedDeps.js"
      # All dependencies scripts (minimized)
      cdojDepsMinimized:
        src: [
          "bower_components/angular/angular.min.js"
          "bower_components/angular-animate/angular-animate.min.js"
          "bower_components/angular-aria/angular-aria.min.js"
          "bower_components/angular-cookies/angular-cookies.min.js"
          "bower_components/angular-material/angular-material.min.js"
          "bower_components/angular-route/angular-route.min.js"
          "temp/js/underscore.min.js"
          "bower_components/underscore.string/dist/underscore.string.min.js"
          "temp/js/unminimizedDeps.min.js"
        ]
        dest: "temp/js/cdoj.deps.min.js"
      # CDOJ css file
      cdojCssFull:
        src: [
          "bower_components/angular-material/angular-material.css"
          "temp/css/cdoj.css"
        ]
        dest: "dist/css/cdoj.css"
      # CDOJ js file (full)
      cdojJsFull:
        src: [
          "temp/js/cdoj.deps.js"
          "temp/angular/cdoj.angular.js"
        ]
        dest: "dist/js/cdoj.js"
      # CDOJ js file (minimized)
      cdojJsMinimized:
        src: [
          "temp/js/cdoj.deps.min.js"
          "temp/angular/cdoj.angular.min.js"
        ]
        dest: "dist/js/cdoj.min.js"

    cssmin:
      minimizeCss:
        src: "dist/css/cdoj.css"
        dest: "dist/css/cdoj.min.css"

    min:
      minimizeUnderscore:
        src: "bower_components/underscore/underscore.js"
        dest: "temp/js/underscore.min.js"
      minimizeUnminimizedDeps:
        src: "temp/js/unminimizedDeps.js"
        dest: "temp/js/unminimizedDeps.min.js"
      minimizeCdojAngualr:
        src: "temp/angular/cdoj.angular.js"
        dest: "temp/angular/cdoj.angular.min.js"

    karma:
      unit:
        configFile: "cdoj.conf.js"

  grunt.loadNpmTasks "grunt-contrib-watch"
  grunt.loadNpmTasks "grunt-contrib-less"
  grunt.loadNpmTasks "grunt-contrib-coffee"
  grunt.loadNpmTasks "grunt-contrib-concat"
  grunt.loadNpmTasks "grunt-yui-compressor"
  grunt.loadNpmTasks "grunt-coffeelint"
  grunt.loadNpmTasks "grunt-karma"

  grunt.registerTask "compileFull", [
    "coffeelint:coffee"

    "less:cdoj"
    "concat:cdojCssFull"

    "coffee:cdojAngularFull"

    "concat:cdojDepsFull"
    "concat:cdojJsFull"
  ]
  grunt.registerTask "minifyResult", [
    "cssmin:minimizeCss"

    "concat:cdojDepsUnminimized"
    "min:minimizeUnminimizedDeps"
    "min:minimizeUnderscore"
    "concat:cdojDepsMinimized"

    "min:minimizeCdojAngualr"
    "concat:cdojJsMinimized"
  ]
  grunt.registerTask "default", [
    "build"
  ]
  grunt.registerTask "build", [
    "compileFull",
    "minifyResult"
  ]
  grunt.registerTask "test", [
    "build"
    "karma:unit"
  ]