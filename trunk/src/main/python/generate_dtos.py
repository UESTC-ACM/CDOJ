#!/usr/bin/env python2
# coding=utf-8

import os
import sys
import stat
import getopt
import json

def parseOpt(argv):
  help_info = "generate_dtos.py -i <input> -o <output>"
  input_dir = ""
  output_dir = ""
  try:
    opts, args = getopt.getopt(argv, "hi:o:", ["input=", "output="])
  except getopt.GetoptError:
    print help_info
    sys.exit(1)
  for opt, arg in opts:
    if opt == "-h":
      print help_info
      sys.exit(0)
    elif opt in ("-i", "--input"):
      input_dir = arg
    elif opt in ("-o", "--output"):
      output_dir = arg
  return input_dir, output_dir

def scanfInputDirectory(dir_name):
  input_files = []
  for item in os.listdir(dir_name):
    sub_path = os.path.join(dir_name, item)
    mode = os.stat(sub_path)[stat.ST_MODE]
    if stat.S_ISDIR(mode):
      input_files.extend(scanfInputDirectory(sub_path))
    elif sub_path[-5:] == '.json':
      input_files.append(sub_path)
  return input_files

fetchName = lambda name: name[:1].upper() + name[1:]

def writeProperties(indent, out, fields, builder):
  for i in range(len(fields)):
    out.write(indent * ' ')
    out.write("private " + fields[i]["type"] + " " + fields[i]["name"])
    if "default" in fields[i]:
      val = fields[i]["default"]
      if type(val) is str:
        out.write(" = \"" + fields[i]["default"] + "\"")
      elif type(val) is bool:
        out.write(" = " + str(fields[i]["default"]).lower())
      else:
        out.write(" = " + str(fields[i]["default"]))
    out.write(";\n")

  for i in range(len(fields)):
    out.write("\n")
    out.write(indent * ' ')
    out.write("public " + fields[i]["type"] +  " get" + fetchName(fields[i]["name"]) + "() {\n")
    out.write((indent + 2) * ' ')
    out.write("return " + fields[i]["name"] + ";\n")
    out.write(indent * ' ')
    out.write("}\n\n")
    out.write(indent * ' ')
    if builder == True:
      out.write("public Builder set" + fetchName(fields[i]["name"]) + "(" + fields[i]["type"] + " " + fields[i]["name"] +  ") {\n")
    else:
      out.write("public void set" + fetchName(fields[i]["name"]) + "(" + fields[i]["type"] + " " + fields[i]["name"] +  ") {\n")
    out.write((indent + 2) * ' ')
    out.write("this." + fields[i]["name"] + " = " + fields[i]["name"] + ";\n")
    if builder == True:
      out.write((indent + 2) * ' ')
      out.write("return this;\n")
    out.write(indent * ' ')
    out.write("}\n")

def generateDto(input_file, output_dir):
  data = json.load(open(input_file))
  entity = data["entity"]
  fields = data["fields"]
  package = data["package"]

  # Get output file name
  package_dir = package.replace(".", "/")
  # Create directors if needed
  output_dir = output_dir + package_dir + "/"
  if not os.path.exists(output_dir):
    os.makedirs(output_dir)
  class_name = input_file[input_file.rfind("/") + 1: -5]
  output_file = output_dir + class_name + ".java"

  # Create file
  out = open(output_file, "w")

  # imports
  out.write("""package {0};

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.{1};

import java.sql.Timestamp;
import java.util.Map;
""".format(package, entity))

  # Class definition
  out.write("""
public class {0} implements BaseDTO<{1}> {{

  public {0}() {{
  }}

""".format(class_name, entity))

  # Constructor
  out.write("  public {0}(".format(class_name))
  for i in xrange(len(fields)):
    field = fields[i]
    if i > 0:
      out.write(", ")
    out.write("{0} {1}".format(field["type"], field["name"]))
  out.write(") {\n")
  for field in fields:
    out.write("    this.{0} = {0};\n".format(field["name"]))
  out.write("  }\n\n")

  writeProperties(2, out, fields, False)

  # Equals
  out.write("""
  @Override
  public boolean equals(Object o) {{
    if (this == o) {{
      return true;
    }}
    if (o == null || getClass() != o.getClass()) {{
      return false;
    }}

    {0} that = ({0}) o;

""".format(class_name))
  for field in fields:
    out.write("    if ({0} != null ? !{0}.equals(that.{0}) : that.{0} != null) {{\n".format(field["name"]))
    out.write("      return false;\n    }\n")
  out.write("\n    return true;\n  }\n")

  # HashCode
  out.write("""
  @Override
  public int hashCode() {
    int result = """)
  for i in xrange(len(fields)):
    field = fields[i]
    if i > 0:
      out.write("    result = 31 * result + (")
    out.write("{0} != null ? {0}.hashCode() : 0".format(field["name"]))
    if i > 0:
      out.write(")")
    out.write(";\n")
  out.write("    return result;\n  }\n")

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
    out.write(fields[i]["name"])
  out.write(");\n")
  out.write(4 * ' ')
  out.write("}\n\n")

  out.write(4 * ' ')
  out.write("@Override\n")
  out.write(4 * ' ')
  out.write("public " + class_name + " build(Map<String, Object> properties) {\n")
  for i in range(0, len(fields)):
    out.write(6 * ' ')
    out.write(fields[i]["name"] + " = (" + fields[i]["type"] + ") properties.get(\"" + fields[i]["name"] + "\");\n")
  out.write(6 * ' ')
  out.write("return build();\n")
  out.write(4 * ' ')
  out.write("}\n\n")

  writeProperties(4, out, fields, True)
  out.write(2 * ' ')
  out.write("}\n")
  out.write("}\n")

  out.close()

if __name__ == "__main__":
  input_dir, output_dir = parseOpt(sys.argv[1:])

  base_dir = os.getcwd() + "/"
  input_dir = base_dir + input_dir + "/"
  output_dir = base_dir + output_dir + "/"

  # Scanf input directory and get input files
  input_files = scanfInputDirectory(input_dir)

  # Generate output
  for input_file in input_files:
    generateDto(input_file, output_dir)
