module.exports = (grunt) ->

  grunt.initConfig
    pkg: grunt.file.readJSON "package.json"

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

  grunt.loadNpmTasks "grunt-contrib-watch"
  grunt.loadNpmTasks "grunt-contrib-less"
  grunt.loadNpmTasks "grunt-contrib-coffee"
  grunt.loadNpmTasks "grunt-contrib-concat"
  grunt.loadNpmTasks "grunt-yui-compressor"

  grunt.registerTask "js",[
    # Angular project
    "coffee:compileAngularProject"
    "min:minimizeAngularProject"

    "concat:concatAngularJs"
    "concat:concatMinimizeAngularJs"

    # Old files
    "coffee:compileOldProject"
    "min:minimizeOldProject"

    "concat:concatMinimizeOldJs"
    "concat:concatOldJs"

    # Dependencies files
    "concat:concatDependencies"
    "min:minimizeDependencies"

    "concat:concatMinimizedDependencies1"
    "concat:concatMinimizedDependencies2"
  ]