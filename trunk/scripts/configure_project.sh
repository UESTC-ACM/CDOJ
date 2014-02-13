#!/bin/bash
#
# configure project
#
# parameters:
# total: 2
# $1 password for db
# $2 host name

password=root
hostname=http:\\/\\/127.0.0.1:8080

if [ $# -ge 1 ]; then
  password=$1
fi

if [ $# -ge 2 ]; then
  hostname=$2
fi

sed -i "s/<db.password>root<\/db.password>/<db.password>${password}<\/db.password>/g" pom.xml
sed -i "s/db.password=root/db.password=${password}/g" src/main/resources/resources.properties
sed -i "s/db.password=root/db.password=${password}/g" src/test/resources/resources.properties
sed "s/http:\/\/127.0.0.1:8080/${hostname}/g" src/main/resources/settings.xml > settings.xml
mv settings.xml src/main/resources/settings.xml

