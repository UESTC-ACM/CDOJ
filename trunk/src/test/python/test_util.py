#!/usr/bin/env python2
# coding=utf-8
import unittest
import sys
import re


def findWholeWordRegex(w):
    return re.compile(r'\b({0})\b'.format(w)).search


def findWholeWordInRealCode(word, s):
    s = s.partition('//')[0]
    s = s.replace('\\"', '')
    s = re.sub('".*?"', '', s)
    return findWholeWordRegex(word)(s)


class TestCase(unittest.TestCase):

    def getName(self):
        pass


class TestRunner:

    def __init__(self, stream=sys.stderr):
        self.stream = stream

    def write(self, message):
        self.stream.write(message)

    def run(self, test):
        result = TestResult(self)
        test(result)
        # result.printErrors()
        run = result.testsRun
        return result


class TestResult(unittest.TestResult):

    def __init__(self, runner):
        unittest.TestResult.__init__(self)
        self.runner = runner

    def startTest(self, test):
        pass

    def addSuccess(self, test):
        self.runner.write("\x1b[0;32mPASSED: \x1b[m" + test.getName() + "\n")

    def addError(self, test, err):
        self.runner.write("\x1b[1;31mERROR: \x1b[m" + test.getName() + "\n")
        self.runner.write(str(err[1]) + '\n')

    def addFailure(self, test, err):
        self.runner.write("\x1b[1;31mFAILED: \x1b[m" + test.getName() + "\n")
        self.runner.write(str(err[1]) + '\n')

    def printErrors(self):
        self.printErrorList('Error', self.errors)
        self.printErrorList('Failure', self.failures)

    def printErrorList(self, flavor, errors):
        pass
