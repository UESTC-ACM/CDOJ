module.exports = function(grunt) {

  grunt.initConfig({
    pkg: grunt.file.readJSON("package.json"),
    // Less compile task
    less: {
      compile: {
        files: {
          "dist/css/cdoj.less.css": "src/less/cdoj.less"
        }
      }
    },
    // Coffee compile task
    coffee: {
      options: {
        join: true
      },
      old: {
        files: {
          "dist/js/cdoj.coffee.old.js": [
            "src/coffee/cdoj.global.coffee",
            "src/coffee/cdoj.util.*.coffee",
            "src/coffee/cdoj.editor.coffee",
            "src/coffee/cdoj.class.*.coffee",
            "src/coffee/cdoj.layout.coffee",
            "src/coffee/cdoj.*.coffee",
            "src/coffee/cdoj.coffee"
          ]
        }
      },
      angular: {
        files: {
          "dist/js/cdoj.coffee.angular.js": [
            "src/angular/global.coffee",
            "src/angular/*.coffee"
          ]
        }
      }
    },
    concat: {
      others: {
        src: [
          "src/js/jquery-2.0.3.js",
          "src/js/sugar-full.development.js",
          "src/js/underscore.js",
          "src/js/prettify.js",
          "src/js/marked.js",
          "src/js/bootstrap.js",
          "src/js/md5.js",
          "src/js/jquery.elastic.source.js",
          "src/js/bootstrap-datetimepicker.js"
        ],
        dest: "dist/js/others.js"
      },
      minied: {
        src: [
          //"src/js/angular.min.js",
          "src/js/fineuploader-4.0.3.min.js"
        ],
        dest: "dist/js/minied.js"
      },
      js: {
        src: [
          "dist/js/others.js",
          "dist/js/minied.js",
          "dist/js/cdoj.coffee.js"
        ],
        dest: "dist/js/cdoj.js"
      },
      jsMin: {
        src: [
          "dist/js/others.min.js",
          "dist/js/minied.js",
          "dist/js/cdoj.coffee.min.js"
        ],
        dest: "dist/js/cdoj.min.js"
      },
      css: {
        src: ["src/css/bootstrap.css", "src/css/datetimepicker.css", "dist/css/cdoj.less.css"],
        dest: "dist/css/cdoj.css"
      }
    },
    min: {
      others: {
        src: "dist/js/others.js",
        dest: "dist/js/others.min.js"
      },
      js: {
        src: "dist/js/cdoj.coffee.js",
        dest: "dist/js/cdoj.coffee.min.js"
      }
    },
    cssmin: {
      css: {
        src: "dist/css/cdoj.css",
        dest: "dist/css/cdoj.min.css"
      }
    },
    watch: {
      files: [
        "src/less/*.less",
        "src/coffee/*.coffee"
      ],
      tasks: [
        "less:compile",
        "concat:css",

        "coffee",
        "concat:others",
        "concat:js"
      ]
    }
  });

  grunt.loadNpmTasks("grunt-contrib-watch");
  grunt.loadNpmTasks("grunt-contrib-less");
  grunt.loadNpmTasks("grunt-contrib-coffee");
  grunt.loadNpmTasks("grunt-contrib-concat");
  grunt.loadNpmTasks("grunt-yui-compressor");

  grunt.registerTask("default", [
    "less:compile",
    "concat:css",
    "cssmin",

    "coffee",
    "concat:others",
    "concat:minied",
    "concat:js",

    "min:others",
    "min:js",
    "concat:jsMin"
  ]);

};
