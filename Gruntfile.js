module.exports = function(grunt) {

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    less: {
      compile: {
        files: {
          'dist/css/cdoj.css': 'src/css/cdoj.less'
        }
      },
      yuicompress: {
        files: {
          'dist/css/cdoj-min.css': 'dist/css/cdoj.css'
        },options: {
          yuicompress: true
        }
      }
    },
    concat: {
      options: {
        separator: ';'
      },
      dist: {
        src: [
          'src/js/cdoj.util.*.js',
          'src/js/jquery.gravatar.js',
          'src/js/md5.js',
          'src/js/cdoj.problem.js',
          'src/js/cdoj.status.js',
          'src/js/cdoj.user.js',
          'src/js/cdoj.admin.*.js',
          'src/js/*.js'
        ],
        dest: 'dist/js/cdoj.js'}},
    uglify: {
      options: {
        banner: '/*! CDOJ <%= grunt.template.today("dd-mm-yyyy") %> */\n'
      },
      dist: {
        files: {
          'dist/js/cdoj.min.js': ['<%= concat.dist.dest %>']
        }
      }
    },
    jshint: {
      files: ['Gruntfile.js', 'src/js/*.js'],
      options: {
        // options here to override JSHint defaults
        globals: {
          jQuery: true,
          console: true,
          module: true,
          document: true
        }
      }
    },
    watch: {
      files: ['<%= jshint.files %>'],
      tasks: ['jshint', 'concat', 'uglify', 'less']
    }
  });

  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-qunit');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-less');

  grunt.registerTask('test', ['jshint']);

  grunt.registerTask('default', ['jshint', 'concat', 'uglify', 'less']);

};
