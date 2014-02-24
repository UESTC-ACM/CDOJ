#! /bin/bash
#
# set up travis data base
#

sed -i "s/<db.user>root<\/db.user>/<db.user>travis<\/db.user>/g" pom.xml
sed -i "s/<db.password>root<\/db.password>/<db.password><\/db.password>/g" pom.xml
sed -i "s/db.user=root/db.user=travis/g" src/test/resources/resources.properties
sed -i "s/db.password=root/db.password=/g" src/test/resources/resources.properties

