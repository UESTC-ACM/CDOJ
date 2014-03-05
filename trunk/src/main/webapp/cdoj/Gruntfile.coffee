module.exports = (grunt) ->

  grunt.initConfig
    pkg: grunt.file.readJSON "package.json"

    watch:
      files: [
        "src/*/*.*"
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
            "src/angular/controller/*.coffee"
          ]

    less:
      cdoj:
        files:
          "temp/css/cdoj.css": "src/less/cdoj.less"

    concat:
      cdojFull:
        src: [
          "bower_components/bootstrap/dist/css/bootstrap.css"
          "temp/css/cdoj.css"
        ]
        dest: "dist/css/cdoj.css"
      cdojMinimize:
        src: [
          "bower_components/bootstrap/dist/css/bootstrap.min.css"
          "temp/css/cdoj.min.css"
        ]
        dest: "dist/css/cdoj.min.css"

    cssmin:
      cdojMinimize:
        src: "temp/css/cdoj.css"
        dest: "temp/css/cdoj.min.css"

    min:
      cdojMinimize:
        src: [
          "temp/angular/cdoj.angular.js"
        ]
        dest: "temp/angular/cdoj.angular.min.js"

  grunt.loadNpmTasks "grunt-contrib-watch"
  grunt.loadNpmTasks "grunt-contrib-less"
  grunt.loadNpmTasks "grunt-contrib-coffee"
  grunt.loadNpmTasks "grunt-contrib-concat"
  grunt.loadNpmTasks "grunt-yui-compressor"

  grunt.registerTask "compileFull", [
    "less:cdoj"
    "concat:cdojFull"

    "coffee:cdojAngularFull"
  ]
  grunt.registerTask "minifyResult", [
    "cssmin:cdojMinimize"
    "concat:cdojMinimize"

    "min:cdojMinimize"
  ]
  grunt.registerTask "default", [
    "compileFull",
    "minifyResult"
  ]