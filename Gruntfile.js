module.exports = function(grunt) {

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    less: {
      compile: {
        files: {
          'dist/css/cdoj.css': 'src/less/cdoj.less'
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
      options: {
        join: true
      },
      compile: {
        files: {
          'dist/js/cdoj.coffee.js': [
            'src/coffee/cdoj.util.*.coffee',
            'src/coffee/cdoj.user.coffee',
            'src/coffee/cdoj.problem.list.coffee',
            'src/coffee/cdoj.coffee'
          ]
        }
      }
    },
    concat: {
      dist: {
        src: ['src/js/md5.js', 'dist/js/cdoj.coffee.js'],
        dest: 'dist/js/cdoj.js'
      }
    },
    watch: {
      files: [
        'src/less/*.less',
        'src/coffee/*.coffee'
      ],
      tasks: ['less', 'coffee', 'concat']
    }
  });

  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-coffee');
  grunt.loadNpmTasks('grunt-contrib-concat');

  grunt.registerTask('default', ['less', 'coffee', 'concat']);

};
