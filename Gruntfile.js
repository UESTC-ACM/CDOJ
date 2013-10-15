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
    coffee: {
      compile: {
        files: {
          'dist/js/cdoj.js': 'src/coffee/cdoj.coffee'
        }
      }
    },
    watch: {
      files: ['src/*/*.*'],
      tasks: ['less']
    }
  });

  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-coffee');

  grunt.registerTask('default', ['less', 'coffee']);

};
