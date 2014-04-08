#!/bin/bash
#
# configure project
#
# parameters:
# total: 8
# $1 original file
# $2 destination file
# $3 user for db
# $4 password for db
# $5 static resources path
# $6 images path
# $7 data path
# $8 upload path

if [ $# -ne 8 ]; then
  exit 1
fi

cp $1 $2
echo "db.username=$3" >> $2
echo "db.password=$4" >> $2
echo "staticResources.path=$5" >> $2
echo "images.path=$6" >> $2
echo "data.path=$7" >> $2
echo "upload.path=$8" >> $2
