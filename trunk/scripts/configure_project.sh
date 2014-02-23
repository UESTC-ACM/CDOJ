#!/bin/bash
#
# configure project
#
# parameters:
# total: 1
# $1 password for db

password=root

if [ $# -ge 1 ]; then
  password=$1
fi

sed -i "s/<db.password>root<\/db.password>/<db.password>${password}<\/db.password>/g" pom.xml
sed -i "s/db.password=root/db.password=${password}/g" src/main/resources/resources.properties
sed -i "s/db.password=root/db.password=${password}/g" src/test/resources/resources.properties

