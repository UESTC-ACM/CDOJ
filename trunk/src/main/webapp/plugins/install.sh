#!/bin/bash

DEPS=("grunt" "bower")
DEPS_COMMAND=("grunt grunt-cli" "bower")

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

cd src/main/webapp/plugins/
prepare
grunt_install bootstrap
rm -rf bootstrap/bower_components
grunt_install pure
grunt_install cdoj
grunt_install jquery
grunt_install jqueryui
cd ../../../../
