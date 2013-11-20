#!/bin/bash
PRE_COMMIT_ERRRO="\x1b[1;31mpre-commit error!\x1b[m"

#echo -e "\x1b[0;33mCheck: java style check\x1b[m"
#mvn checkstyle:checkstyle
#if [ $? -ne 0 ]; then
  #echo -e "\x1b[1;31mJava style is not correctly, please check.\x1b[m"
  #exit 1
#fi

echo -e "\x1b[0;33mCheck: mysql connection.\x1b[m"
mysql -uroot -proot -e exit 2> /dev/null
if [ $? -ne 0 ]; then
  echo -e "\x1b[1;31mMysql service is not opened, please open mysql service.\x1b[m"
  exit 1
fi

echo -e "\x1b[0;33mCheck: dtos not contain entities\x1b[m"
./src/test/python/check_dtos_not_contain_entities.py
if [ $? -ne 0 ]; then
  echo -e $PRE_COMMIT_ERRRO
  exit 1
fi

echo -e "\x1b[0;33mCheck: no entity used in DTO\x1b[m"
./src/test/python/check_controller_not_contain_daos.py
if [ $? -ne 0 ]; then
  echo -e $PRE_COMMIT_ERRRO
  exit 1
fi

echo -e "\x1b[0;33mCheck: no junit import in testing codes\x1b[m"
./src/test/python/check_testing_code_all_not_use_junit.py
if [ $? -ne 0 ]; then
  echo -e $PRE_COMMIT_ERRRO
  exit 1
fi

echo -e "\x1b[0;33mrun unit tests\x1b[m"
mvn test
if [ $? -ne 0 ]; then
  echo -e $PRE_COMMIT_ERRRO
  exit 1
fi

echo -e "\x1b[0;33mrun integration tests\x1b[m"
mvn failsafe:integration-test
if [ $? -ne 0 ]; then
  echo -e $PRE_COMMIT_ERRRO
  exit 1
fi

echo -e "\x1b[1;32mpre-commit successfully, you can use git commit to commit your code\x1b[m"
exit 0

