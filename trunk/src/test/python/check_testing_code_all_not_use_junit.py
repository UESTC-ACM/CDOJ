#!/usr/bin/env python2
# coding=utf-8
import unittest
import os
import stat
import test_util


class TestTestingCodeNotContainJUnit(test_util.TestCase):

    def __init__(self, file_name):
        unittest.TestCase.__init__(self, methodName='test')
        self.file_name = file_name

    def getName(self):
        return (
            'testTestingCodeAllNotUseJUnit#' +
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
            if 'import org.junit' in line or 'import org.testng.AssertJUnit' in line:
                self.fail('\x1b[1;31mfind junit import at line ' + str(current) + ' of ' + self.file_name + '\n\x1b[0;33m' +
                          self.file_name[self.file_name.rfind('/') + 1:] + '@' + str(current) + ': ' + line + '\x1b[m')


def addTestCases(suite, dir_name):
    for item in os.listdir(dir_name):
        sub_path = os.path.join(dir_name, item)
        mode = os.stat(sub_path)[stat.ST_MODE]
        if stat.S_ISDIR(mode):
            addTestCases(suite, sub_path)
        elif sub_path[-5:] == '.java':
            suite.addTest(TestTestingCodeNotContainJUnit(file_name=sub_path))

if __name__ == '__main__':
    suite = unittest.TestSuite()
    base_dir = os.getcwd()
    test_dir = base_dir + '/src/test/java'
    addTestCases(suite, test_dir)
    result = test_util.TestRunner().run(suite)
    quit(len(result.errors) + len(result.failures))
