#!/usr/bin/env python
import sys
import getopt

def fetchName(name):
  return name[:1].upper() + name[1:]

def writeProperties(indent, out, fields, types, builder):
  for i in range(len(fields)):
    out.write(indent * ' ')
    out.write("private " + types[i] + " " + fields[i] + ";\n")
  for i in range(len(fields)):
    out.write("\n")
    out.write(indent * ' ')
    out.write("public " + types[i] +  " get" + fetchName(fields[i]) + "() {\n")
    out.write((indent + 2) * ' ')
    out.write("return " + fields[i] + ";\n")
    out.write(indent * ' ')
    out.write("}\n\n")
    out.write(indent * ' ')
    if builder == True:
      out.write("public Builder set" + fetchName(fields[i]) + "(" + types[i] + " " + fields[i] +  ") {\n")
    else:
      out.write("public void set" + fetchName(fields[i]) + "(" + types[i] + " " + fields[i] +  ") {\n")
    out.write((indent + 2) * ' ')
    out.write("this." + fields[i] + " = " + fields[i] + ";\n")
    if builder == True:
      out.write((indent + 2) * ' ')
      out.write("return this;\n")
    out.write(indent * ' ')
    out.write("}\n")

def main(argv):
  help_info = 'gen_dto.py -e <entity> -p <package> -c <class> -f <fields> -t <types>'
  fields = []
  types = []
  class_name = ''
  package = ''
  entity = ''
  try:
    opts, args = getopt.getopt(argv, "he:p:c:f:t:", ["entity=", "package=", "class=", "fields=", "types="])
  except getopt.GetoptError:
    print help_info
    sys.exit(1)
  for opt, arg in opts:
    if opt == '-h':
      print help_info
      sys.exit(0)
    elif opt in ("-c", "--class"):
      class_name = arg
    elif opt in ("-f", "--fields"):
      fields = arg.split(",")
    elif opt in ("-t", "--types"):
      types = arg.split(",")
    elif opt in("-p", "--package"):
      package = arg
    elif opt in("-e", "--entity"):
      entity = arg
  
  if len(fields) != len(types):
    print "the number of fields should be equals to the number of types"
    print "number of fields:", len(fields)
    print "number of types:", len(types)
    sys.exit(1);
  if package == '' or class_name == '' or entity == '':
    print "package, class name and entity should not be empty"
    sys.exit(1)
  print "entity: ", entity
  print "class: ", class_name
  print "package: ", package
  print "fields: ", fields
  print "types: ", types

  file_name = class_name + ".java"
  out = open(file_name, 'w')

  print "begin write dto source..."

  #package file header
  out.write("package cn.edu.uestc.acmicpc.db.dto.impl." + package + ";\n\n")
  
  #imports
  out.write("import java.util.*;\n")
  out.write("import java.sql.*;\n")
  out.write("\n")
  out.write("import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;\n")
  out.write("import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;\n")
  out.write("import cn.edu.uestc.acmicpc.db.entity." + fetchName(entity) + ";\n")
  out.write("import cn.edu.uestc.acmicpc.util.annotation.Fields;\n\n")

  #class
  out.write("@Fields({ ")
  for i in range(0, len(fields)):
    if i > 0:
      out.write(", ")
    field = fields[i]
    out.write("\"" + field + "\"")
  out.write(" })\n")
  out.write("public class " + class_name + " implements BaseDTO<" + entity + "> {\n\n")
  out.write(2 * ' ')
  out.write("public " + class_name + "() {\n")
  out.write(2 * ' ')
  out.write("}\n\n")
  out.write(2 * ' ')
  out.write("private " + class_name + "(")
  for i in range(0, len(fields)):
      if i > 0:
        out.write(", ")
      out.write(types[i] + " " + fields[i])
  out.write(") {\n")
  for i in range(0, len(fields)):
    out.write(4 * ' ')
    out.write("this." + fields[i] + " = " + fields[i] + ";\n")
  out.write(2 * ' ')
  out.write("}\n\n")

  writeProperties(2, out, fields, types, False)

  out.write("\n")
  out.write(2 * ' ')
  out.write("public static Builder builder() {\n")
  out.write(4 * ' ')
  out.write("return new Builder();\n")
  out.write(2 * ' ')
  out.write("}\n\n")

  #builder
  out.write(2 * ' ')
  out.write("public static class Builder implements BaseBuilder<" + class_name + "> {\n\n")
  out.write(4 * ' ')
  out.write("private Builder() {\n")
  out.write(4 * ' ')
  out.write("}\n\n")
  out.write(4 * ' ')
  out.write("@Override\n")
  out.write(4 * ' ')
  out.write("public " + class_name + " build() {\n")
  out.write(6 * ' ')
  out.write("return new " + class_name + "(")
  for i in range(0, len(fields)):
    if i > 0:
      out.write(", ")
    out.write(fields[i])
  out.write(");\n")
  out.write(4 * ' ')
  out.write("}\n\n")

  out.write(4 * ' ')
  out.write("@Override\n")
  out.write(4 * ' ')
  out.write("public " + class_name + " build(Map<String, Object> properties) {\n")
  for i in range(0, len(fields)):
    out.write(6 * ' ')
    out.write(fields[i] + " = (" + types[i] + ") properties.get(\"" + fields[i] + "\");\n")
  out.write(6 * ' ')
  out.write("return build();\n\n")
  out.write(4 * ' ')
  out.write("}\n\n")

  writeProperties(4, out, fields, types, True)
  out.write(2 * ' ')
  out.write("}\n")
  out.write("}\n")

  print "write dto source completed"

if __name__ == "__main__":
  main(sys.argv[1:])

