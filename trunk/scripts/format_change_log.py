#! /usr/bin/env python2
# coding=utf8
import json

with open("change_log.log") as of:
    data = json.load(of)

bugs = []
enhancements = []

for issue in data:
    labels = issue['labels']
    for label in labels:
        if label['name'] == "bug":
            bugs.append(issue)
        elif label['name'] == "enhancement":
            enhancements.append(issue)
if bugs:
    print "* Bugs fixed"
    for issue in bugs:
        title = issue['title']
        print "  * %s" % (title)

if enhancements:
    print "* New features"
    for issue in enhancements:
        title = issue['title']
        print "  * %s" % (title)

