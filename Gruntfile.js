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
    watch: {
      files: ['<%= jshint.files %>'],
      tasks: ['less']
    }
  });

  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-less');

  grunt.registerTask('default', ['less']);

};
