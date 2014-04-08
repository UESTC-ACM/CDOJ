module.exports = (grunt) ->

  grunt.initConfig
    pkg: grunt.file.readJSON "package.json"

    watch:
      files: [
        "src/*/*.*"
        "src/*/*/*.*"
      ]
      tasks: [
        "compileFull"
      ]

    coffee:
      cdojAngularFull:
        options:
          join: true
        files:
          "temp/angular/cdoj.angular.js": [
            "src/angular/global.coffee"
            "src/angular/cdoj.coffee"
            "src/angular/config/*.coffee"
            "src/angular/run/*.coffee"
            "src/angular/factory/*.coffee"
            "src/angular/controller/*/*.coffee"
            "src/angular/controller/*.coffee"
            "src/angular/directive/*.coffee"
          ]

    coffeelint:
      coffee: [
        "src/angular/*.coffee"
        "src/angular/**/*.coffee"
      ]
      options:
        "arrow_spacing":
          "level": "error"
    less:
      cdoj:
        files:
          "temp/css/cdoj.css": "src/less/cdoj.less"

    concat:
      cdojFull:
        src: [
          "bower_components/bootstrap/dist/css/bootstrap.css"
          "bower_components/bootstrap-switch/build/css/bootstrap3/bootstrap-switch.css"
          "bower_components/smalot-bootstrap-datetimepicker/css/bootstrap-datetimepicker.css"
          "temp/css/cdoj.css"
        ]
        dest: "dist/css/cdoj.css"
      cdojJsFull:
        src: [
          "bower_components/angular/angular.js"
          "bower_components/angular-bootstrap/ui-bootstrap-tpls.js"
          "bower_components/angular-route/angular-route.js"
          "bower_components/angular-cookies/angular-cookies.js"
          "bower_components/angular-sanitize/angular-sanitize.js"
          "bower_components/angular-bindonce/bindonce.js"
          "bower_components/angular-elastic/elastic.js"
          "bower_components/jquery/dist/jquery.js"
          "bower_components/bootstrap/dist/js/bootstrap.js"
          "bower_components/bootstrap-switch/build/js/bootstrap-switch.js"
          "bower_components/jquery-ui/ui/jquery-ui.js"
          "bower_components/smalot-bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"
          "bower_components/fine-uploader/_build/fineuploader.js"
          "bower_components/CryptoJS/src/core.js"
          "bower_components/CryptoJS/src/md5.js"
          "bower_components/CryptoJS/src/sha1.js"
          "bower_components/sugar/release/sugar-full.development.js"
          "bower_components/underscore/underscore.js"
          "bower_components/underscore.string/lib/underscore.string.js"
          "bower_components/google-code-prettify/src/prettify.js"
          "bower_components/marked/lib/marked.js"
          "temp/angular/cdoj.angular.js"
        ]
        dest: "dist/js/cdoj.js"
      cdojJsMinimized:
        src: [
          "bower_components/angular/angular.min.js"
          "bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js"
          "bower_components/angular-route/angular-route.min.js"
          "bower_components/angular-cookies/angular-cookies.min.js"
          "bower_components/angular-sanitize/angular-sanitize.min.js"
          "bower_components/angular-bindonce/bindonce.min.js"
          "bower_components/jquery/dist/jquery.min.js"
          "bower_components/bootstrap/dist/js/bootstrap.min.js"
          "bower_components/bootstrap-switch/build/js/bootstrap-switch.min.js"
          "bower_components/jquery-ui/ui/minified/jquery-ui.min.js"
          "bower_components/smalot-bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"
          "bower_components/fine-uploader/_build/fineuploader.min.js"
          "bower_components/sugar/release/sugar-full.min.js"
          "temp/js/underscore.min.js"
          "bower_components/underscore.string/dist/underscore.string.min.js"
          "temp/js/not-minimized.min.js"
        ]
        dest: "dist/js/cdoj.min.js"
      cdojJsNotMinimized:
        src: [
          "bower_components/angular-elastic/elastic.js"
          "bower_components/CryptoJS/src/core.js"
          "bower_components/CryptoJS/src/md5.js"
          "bower_components/CryptoJS/src/sha1.js"
          "bower_components/google-code-prettify/src/prettify.js"
          "bower_components/marked/lib/marked.js"
          "temp/angular/cdoj.angular.js"
        ]
        dest: "temp/js/not-minimized.js"

    cssmin:
      minimizeCss:
        src: "dist/css/cdoj.css"
        dest: "dist/css/cdoj.min.css"

    min:
      minimizeUnderscore:
        src: "bower_components/underscore/underscore.js"
        dest: "temp/js/underscore.min.js"
      minimizeScripts:
        src: "temp/js/not-minimized.js"
        dest: "temp/js/not-minimized.min.js"

  grunt.loadNpmTasks "grunt-contrib-watch"
  grunt.loadNpmTasks "grunt-contrib-less"
  grunt.loadNpmTasks "grunt-contrib-coffee"
  grunt.loadNpmTasks "grunt-contrib-concat"
  grunt.loadNpmTasks "grunt-yui-compressor"
  grunt.loadNpmTasks "grunt-coffeelint"

  grunt.registerTask "compileFull", [
    "coffeelint:coffee"

    "less:cdoj"
    "concat:cdojFull"

    "coffee:cdojAngularFull"
    "concat:cdojJsFull"
  ]
  grunt.registerTask "minifyResult", [
    "cssmin:minimizeCss"

    "concat:cdojJsNotMinimized"
    "min:minimizeUnderscore"
    "min:minimizeScripts"
    "concat:cdojJsMinimized"
  ]
  grunt.registerTask "default", [
    "compileFull",
    "minifyResult"
  ]
  grunt.registerTask "build", [
    "compileFull",
    "minifyResult"
  ]