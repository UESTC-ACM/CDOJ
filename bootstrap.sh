#! /usr/bin/env bash

sudo apt-get update -y
sudo apt-get dist-upgrade -y
sudo apt-get install -y build-essential openjdk-8-jdk nodejs-legacy npm python2.7 g++-multilib
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install gradle

echo "Boostraping completed."
echo "please install mysql.sever maunally to setup password, then run mysql_secure_installation to configure it."
