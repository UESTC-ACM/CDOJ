#!/usr/bin/env python
#coding=utf-8
import unittest
import sys
import os
import stat

entities = []

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

class TestDtosNotContainEntities(unittest.TestCase):

  def __init__(self, file_name):
    unittest.TestCase.__init__(self, methodName = 'test')
    self.file_name = file_name

  def getDescription(self):
    return 'testDtosNotContainEntities_' + self.file_name[self.file_name.rfind('/') : -5]

  def test(self):
    f = open(self.file_name, 'r')

    current = 0
    comment = False
    for line in f:
      if line[-1] == '\n':
        line = line[:-1]
      if '//' in line:
        line = line[ : line.find('//')]
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
        for entity in entities:
          if findWholeWord(entity, line):
            self.fail('\x1b[1;31mfind DB entity \x1b[0;32m' + entity + '\x1b[m reference at line ' + str(current) + ' of ' + self.file_name + '\n\x1b[0;33m' + self.file_name[self.file_name.find('/dto/impl/') : ] + '@' + str(current) + ': ' + line + '\x1b[m')

def addTestCases(suite, dir_name):
  for item in os.listdir(dir_name):
    sub_path = os.path.join(dir_name, item)
    mode = os.stat(sub_path)[stat.ST_MODE]
    if stat.S_ISDIR(mode):
      addTestCases(suite, sub_path)
    elif sub_path[-5 : ] == '.java':
      suite.addTest(TestDtosNotContainEntities(file_name = sub_path))

def initEntities(dir_name):
  for item in os.listdir(dir_name):
    sub_path = os.path.join(dir_name, item)
    mode = os.stat(sub_path)[stat.ST_MODE]
    if stat.S_ISDIR(mode):
      initEntities(sub_path)
    elif sub_path[-5 : ] == '.java':
      entity = sub_path[sub_path.rfind('/') + 1 : -5]
      entities.append(entity)

if __name__ == '__main__':
  suite = unittest.TestSuite()
  base_dir =  os.getcwd()
  base_dir = base_dir[:base_dir.rfind('/cdoj/trunk') + 11]
  entity_dir = base_dir + '/src/main/java/cn/edu/uestc/acmicpc/db/entity/'
  initEntities(entity_dir)
  dto_dir = base_dir + '/src/main/java/cn/edu/uestc/acmicpc/db/dto/impl/'
  addTestCases(suite, dto_dir)
  result = unittest.TextTestRunner(verbosity = 2).run(suite)
  quit(len(result.errors) + len(result.failures))

