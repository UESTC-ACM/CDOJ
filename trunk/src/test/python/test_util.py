#!/usr/bin/env python2
#coding=utf-8
import unittest
import sys

def isLetterOrDigit(c):
  return (c >= 'a' and c <= 'z') or (c >= 'A' and c <= 'Z') or (c >= '0' and c <= '9')

def findWholeWord(word, s):
  line = ''
  in_string = False
  for i in range(0, len(s)):
    if s[i] == '"':
      in_string = (in_string == False)
    elif s[i] == '\\' and s[i + 1] == '"':
      in_string = False
    elif in_string == False:
      line = line + s[i]
  index = 0;
  while (index < len(line)):
    newIndex = line[index : ].find(word)
    if newIndex < index:
      break
    left = False
    right = False
    if newIndex == index or isLetterOrDigit(line[newIndex - 1]) == False:
      left = True
    if newIndex + len(word) == len(line) or isLetterOrDigit(line[newIndex + len(word)]) == False:
      right = True
    if left and right:
      return True
    index = newIndex + len(word)
  return False

class TestCase(unittest.TestCase):
  def getName(self):
    pass

class TestRunner:

  def __init__(self, stream = sys.stderr):
    self.stream = stream

  def write(self, message):
    self.stream.write(message)

  def run(self, test):
    result = TestResult(self)
    test(result)
    #result.printErrors()
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

