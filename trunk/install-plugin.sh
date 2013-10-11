#!/bin/bash

INSTALLER="src/main/webapp/plugins/install.sh"

function mzry_exist {
  if which $1 1>/dev/null 2>&1; then
    return 0
  else
    return 1
  fi
}
function mzry_error {
  echo -e "\033[41mERROR\033[0m "$1
}
function mzry_info {
  echo -e "\033[42mINFO\033[0m "$1
}
function mzry_git_submodule {
  cd ..
  mzry_info "Initialize sub module"
  git submodule init
  mzry_info "Update sub module"
  git submodule update --recursive
  cd trunk
}

if mzry_exist npm; then
  npm_version=$(npm --version)
  mzry_info "npm version \033[41m"$npm_version"\033[0m"
  mzry_git_submodule
  source $INSTALLER
else
  mzry_error "Please install node.js[http://nodejs.org/download/] first."
fi