#!/bin/bash

DEPS=("grunt" "bower" "coffee")
DEPS_COMMAND=("grunt grunt-cli" "bower" "coffee-script")

function prepare {
  local i
  for (( i = 0 ; i < ${#DEPS[@]} ; i++ )) do
    dep=${DEPS[$i]}
	command=${DEPS_COMMAND[$i]}
	if mzry_exist $dep; then
	  mzry_info "Skip install "$dep
	else
	  mzry_info "Install "$dep
	  sudo npm install -g $command
	fi
  done
}

function grunt_install {
  mzry_info "Configuration \033[41m"$1"\033[0m"
  cd $1 
  bower install
  npm install
  if [ $2 == "build" ]; then
		grunt build
  else
		grunt
	fi
  cd ..
}

function check_file {
	if [ ! -f $1]; then
		mzry_error $1" not exists"
	fi
}

cd src/main/webapp/plugins/
prepare
grunt_install bootstrap
rm -rf bootstrap/bower_components
grunt_install cdoj
grunt_install jquery

#check
check_file "bootstrap/dist/css/bootstrap.css"
check_file "Font-Awesome/css/font-awesome.min.css"
check_file "Font-Awesome/css/font-awesome-ie7.min.css"
check_file "cdoj/dist/css/cdoj.css"

check_file "jquery/dist/jquery.js"
check_file "sugar/release/sugar-full.development.js"
check_file "underscore/underscore.js"
check_file "bootstrap/bootstrap.js"
check_file "marked/lib/marked.js"
check_file "MathJax/MathJax.js"
check_file "cdoj/dist/js/cdoj.js"

cd ../../../../
