#!/bin/bash
#
# configure project
#
# parameters:
# total: 11
# $1 original file
# $2 destination file
# $3 database name
# $4 user for db
# $5 password for db
# $6 static resources path
# $7 images path
# $8 data path
# $9 upload path
# $10 judge work path
# $11 staticV2 path

if [ $# -ne 11 ]; then
  exit 1
fi

cp $1 $2
echo "db.url=jdbc:mysql://localhost:3306/$3?useUnicode=true&characterEncoding=UTF-8" >> $2
echo "db.username=$4" >> $2
echo "db.password=$5" >> $2
echo "staticResources.path=$6" | sed 's/\\/\\\\/g' >> $2
echo "images.path=$7" | sed 's/\\/\\\\/g' >> $2
echo "data.path=$8" | sed 's/\\/\\\\/g' >> $2
echo "upload.path=$9" | sed 's/\\/\\\\/g' >> $2
echo "judge.workPath=${10}" | sed 's/\\/\\\\/g' >> $2
echo "staticV2.path=${11}" | sed 's/\\/\\\\/g' >> $2
