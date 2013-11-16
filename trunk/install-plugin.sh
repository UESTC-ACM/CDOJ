#!/bin/bash

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

mzry_git_submodule
