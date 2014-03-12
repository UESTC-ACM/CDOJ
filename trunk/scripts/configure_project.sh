#!/bin/bash
#
# configure project
#
# parameters:
# total: 4
# $1 original file
# $2 destination file
# $3 user for db
# $4 password for db

if [ $# -ne 4 ]; then
  exit 1
fi

cp $1 $2
echo "db.username=$3" >> $2
echo "db.password=$4" >> $2
