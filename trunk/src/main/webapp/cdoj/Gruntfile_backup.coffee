module.exports = (grunt) ->

  grunt.initConfig
    pkg: grunt.file.readJSON "package.json"

    less:
      compile:
        files:
          "dist/css/cdoj.less.css": "src/less/cdoj.less"

    coffee:
      compileAngularProject:
        options:
          join: true
        files:
          "temp/angular/cdoj.angular.coffee.js": [
            "src/angular/app.coffee"
            "src/angular/controller/*.coffee"
          ]
      compileJQueryProject:
        options:
          join: true
        files:
          "temp/jquery/cdoj.jquery.coffee.js": [
            "src/jquery/cdoj.util.*.coffee"
          ]

    concat:
      concatAngularJs:
        src: [
          "src/angular/angular.js"
          "src/angular/angular-sanitize.js"
          "src/angular/angular-elastic.js"
          "src/angular/ui-bootstrap-tpls-0.10.0.js"
          "temp/angular/cdoj.angular.coffee.js"
        ]
        dest: "dist/js/cdoj.angular.js"
      concatMinimizeAngularJs:
        src: [
          "src/angular/angular.min.js"
          "src/angular/angular-sanitize.min.js"
          "src/angular/angular-elastic.min.js"
          "src/angular/ui-bootstrap-tpls-0.10.0.min.js"
          "temp/angular/cdoj.angular.coffee.min.js"
        ]
        dest: "dist/js/cdoj.angular.min.js"
      concatJQueryJs:
        src: [
          "temp/jquery/cdoj.jquery.coffee.js"
        ]
        dest: "dist/js/cdoj.jquery.js"
      concatMinimizeJQueryJs:
        src: [
          "temp/jquery/cdoj.jquery.coffee.min.js"
        ]
        dest: "dist/js/cdoj.jquery.min.js"
      concatDependencies:
        src: [
          "src/js/jquery-2.0.3.js"
          "src/js/sugar-full.development.js"
          "src/js/string.js"
          "src/js/underscore.js"
          "src/js/prettify.js"
          "src/js/marked.js"
          "src/js/bootstrap.js"
          "src/js/md5.js"
          "src/js/sha1.js"
          "src/js/bootstrap-datetimepicker.js"
        ]
        dest: "temp/dependencies/cdoj.dependencies.js"
      concatMinimizedDependencies1:
        src: [
          "temp/dependencies/cdoj.dependencies.js"
          "src/js/fineuploader-4.0.3.min.js"
        ]
        dest: "dist/js/cdoj.dependencies.js"
      concatMinimizedDependencies2:
        src: [
          "temp/dependencies/cdoj.dependencies.min.js"
          "src/js/fineuploader-4.0.3.min.js"
        ]
        dest: "dist/js/cdoj.dependencies.min.js"
      concatCss:
        src: [
          "src/css/bootstrap.css"
          "src/css/datetimepicker.css"
          "dist/css/cdoj.less.css"
        ]
        dest: "dist/css/cdoj.css"

    min:
      minimizeAngularProject:
        src: [
          "temp/angular/cdoj.angular.coffee.js"
        ]
        dest: "temp/angular/cdoj.angular.coffee.min.js"
      minimizeJQueryProject:
        src: [
          "temp/jquery/cdoj.jquery.coffee.js"
        ]
        dest: "temp/jquery/cdoj.jquery.coffee.min.js"
      minimizeDependencies:
        src: [
          "temp/dependencies/cdoj.dependencies.js"
        ]
        dest: "temp/dependencies/cdoj.dependencies.min.js"
    cssmin:
      minimizeCss:
        src: "dist/css/cdoj.css"
        dest: "dist/css/cdoj.min.css"

    watch:
      files: [
        "src/*/*.*"
      ]
      tasks: [
        "first"
      ]

  grunt.loadNpmTasks "grunt-contrib-watch"
  grunt.loadNpmTasks "grunt-contrib-less"
  grunt.loadNpmTasks "grunt-contrib-coffee"
  grunt.loadNpmTasks "grunt-contrib-concat"
  grunt.loadNpmTasks "grunt-yui-compressor"

  grunt.registerTask "first", [
    # Angular project
    "coffee:compileAngularProject"
    "concat:concatAngularJs"

    # JQuery files
    "coffee:compileJQueryProject"
    "concat:concatJQueryJs"

    # Dependencies files
    "concat:concatDependencies"
    "concat:concatMinimizedDependencies1"

    # Css
    "less:compile"
    "concat:concatCss"
  ]
  grunt.registerTask "second", [
    "cssmin:minimizeCss"

    "min:minimizeDependencies"
    "concat:concatMinimizedDependencies2"

    "min:minimizeAngularProject"
    "concat:concatMinimizeAngularJs"

    "min:minimizeJQueryProject"
    "concat:concatMinimizeJQueryJs"
  ]
  grunt.registerTask "default", [
    "first",
    "second"
  ]