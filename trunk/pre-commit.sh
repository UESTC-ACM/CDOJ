#!/bin/bash
PRE_COMMIT_ERRRO='pre-commit error!'
echo "Check: no entity used in DTO"
./src/test/python/check_dtos_not_contain_entities.py
if [ $? -ne 0 ]; then
  echo $PRE_COMMIT_ERRRO
  exit 1
fi
echo "run integration tests"
mvn integration-test
if [ $? -ne 0 ]; then
  echo $PRE_COMMIT_ERRRO
  exit 1
fi
echo 'pre-commit successfully, you can use git commit to commit your code'
exit 0

