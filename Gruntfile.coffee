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
            "src/angular/global.coffee"
            "src/angular/*.coffee"
          ]
      compileOldProject:
        options:
          join: true
        files:
          "temp/old/cdoj.old.coffee.js": [
            "src/old/cdoj.global.coffee"
            "src/old/cdoj.util.*.coffee"
            "src/old/cdoj.editor.coffee"
            "src/old/cdoj.class.*.coffee"
            "src/old/cdoj.layout.coffee"
            "src/old/cdoj.*.coffee"
            "src/old/cdoj.coffee"
          ]

    concat:
      concatAngularJs:
        src: [
          "src/angular/angular.js"
          "temp/angular/cdoj.angular.coffee.js"
        ]
        dest: "dist/js/cdoj.angular.js"
      concatMinimizeAngularJs:
        src: [
          "src/angular/angular.min.js"
          "temp/angular/cdoj.angular.coffee.min.js"
        ]
        dest: "dist/js/cdoj.angular.min.js"
      concatOldJs:
        src: [
          "temp/old/cdoj.old.coffee.js"
        ]
        dest: "dist/js/cdoj.old.js"
      concatMinimizeOldJs:
        src: [
          "temp/old/cdoj.old.coffee.min.js"
        ]
        dest: "dist/js/cdoj.old.min.js"
      concatDependencies:
        src: [
          "src/js/jquery-2.0.3.js"
          "src/js/sugar-full.development.js"
          "src/js/underscore.js"
          "src/js/prettify.js"
          "src/js/marked.js"
          "src/js/bootstrap.js"
          "src/js/md5.js"
          "src/js/jquery.elastic.source.js"
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
      minimizeOldProject:
        src: [
          "temp/old/cdoj.old.coffee.js"
        ]
        dest: "temp/old/cdoj.old.coffee.min.js"
      minimizeDependencies:
        src: [
          "temp/dependencies/cdoj.dependencies.js"
        ]
        dest: "temp/dependencies/cdoj.dependencies.min.js"

    cssmin:
      minimizeCss:
        src: "dist/css/cdoj.css"
        dest: "dist/css/cdoj.min.css"

    watch: {
      files: [
        "src/*/*.*"
      ]
      tasks: [
        "first"
      ]
    }

  grunt.loadNpmTasks "grunt-contrib-watch"
  grunt.loadNpmTasks "grunt-contrib-less"
  grunt.loadNpmTasks "grunt-contrib-coffee"
  grunt.loadNpmTasks "grunt-contrib-concat"
  grunt.loadNpmTasks "grunt-yui-compressor"

  grunt.registerTask "first", [
    # Angular project
    "coffee:compileAngularProject"
    "concat:concatAngularJs"

    # Old files
    "coffee:compileOldProject"
    "concat:concatOldJs"

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

    "min:minimizeOldProject"
    "concat:concatMinimizeOldJs"
  ]
  grunt.registerTask "default", [
    "first",
    "second"
  ]