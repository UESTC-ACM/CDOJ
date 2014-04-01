#!/bin/bash
#
# configure project
#
# parameters:
# total: 5
# $1 original file
# $2 destination file
# $3 user for db
# $4 password for db
# $5 static resources path

if [ $# -ne 5 ]; then
  exit 1
fi

cp $1 $2
echo "db.username=$3" >> $2
echo "db.password=$4" >> $2
echo "staticResources.path=$5" >> $2
