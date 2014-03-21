#!/usr/bin/env python2
# coding=utf-8
import unittest
import os
import stat
import test_util
from test_util import findWholeWordInRealCode

daos = []


class TestControllersNotContainDaos(test_util.TestCase):

    def __init__(self, file_name):
        unittest.TestCase.__init__(self, methodName='test')
        self.file_name = file_name

    def getName(self):
        return (
            'testControllerNotContainDAOs#' +
            self.file_name[self.file_name.rfind('/') + 1: -5]
        )

    def test(self):
        f = open(self.file_name, 'r')

        current = 0
        comment = False
        for line in f:
            if line[-1] == '\n':
                line = line[:-1]
            if '//' in line:
                line = line[: line.find('//')]
            current = current + 1
            if '/*' in line and '*/' in line:
                continue
            if '/*' in line:
                comment = True
            elif '*/' in line:
                comment = False
            if comment:
                continue
            if 'implements' not in line and 'import' not in line:
                for entity in daos:
                    if findWholeWordInRealCode(entity, line):
                        self.fail('\x1b[1;31mfind DB DAO \x1b[0;32m' + entity + '\x1b[m reference at line ' + str(current) + ' of ' + self.file_name +
                                  '\n\x1b[0;33m' + self.file_name[self.file_name.find('/db/dao/'):] + '@' + str(current) + ': ' + line + '\x1b[m')


def addTestCases(suite, dir_name):
    for item in os.listdir(dir_name):
        sub_path = os.path.join(dir_name, item)
        mode = os.stat(sub_path)[stat.ST_MODE]
        if stat.S_ISDIR(mode):
            addTestCases(suite, sub_path)
        elif sub_path[-5:] == '.java':
            suite.addTest(TestControllersNotContainDaos(file_name=sub_path))


def initDaos(dir_name):
    for item in os.listdir(dir_name):
        sub_path = os.path.join(dir_name, item)
        mode = os.stat(sub_path)[stat.ST_MODE]
        if stat.S_ISDIR(mode):
            initDaos(sub_path)
        elif sub_path[-5:] == '.java':
            dao = sub_path[sub_path.rfind('/') + 1: -5]
            daos.append(dao)

if __name__ == '__main__':
    suite = unittest.TestSuite()
    base_dir = os.getcwd()
    dao_dir = base_dir + '/src/main/java/cn/edu/uestc/acmicpc/db/dao/'
    initDaos(dao_dir)
    controller_dir = base_dir + \
        '/src/main/java/cn/edu/uestc/acmicpc/web/oj/controller/'
    addTestCases(suite, controller_dir)
    result = test_util.TestRunner().run(suite)
    quit(len(result.errors) + len(result.failures))
