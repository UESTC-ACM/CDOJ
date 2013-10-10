#!/bin/bash

DEPS=("grunt")

function prepare {
  for dep in ${DEPS[@]} 
  do
    mzry_info "Install \033[41m"$dep"\033[0m"
    npm install -g dep
  done
}

function install_cdoj {
  cd cdoj
  npm install
  npm grunt
  cd ..
}

prepare
install_cdoj
