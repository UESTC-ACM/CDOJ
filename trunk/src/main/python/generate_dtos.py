#! /usr/bin/env python2
# coding=utf-8

import os
import sys
import stat
import getopt
import json
import shutil
import re

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


def scanInputDirectory(dir_name):
    input_files = []
    for item in os.listdir(dir_name):
        sub_path = os.path.join(dir_name, item)
        mode = os.stat(sub_path)[stat.ST_MODE]
        if stat.S_ISDIR(mode):
            input_files.extend(scanInputDirectory(sub_path))
        elif sub_path[-5:] == '.json':
            input_files.append(sub_path)
    return input_files

fetchName = lambda name: name[:1].upper() + name[1:]


def writeProperties(indent, out, fields, builder):
    for i in range(len(fields)):
        if i > 0:
            out.write("\n")
        if builder == False:
            if "validator" in fields[i]:
                validators = fields[i]["validator"]
                for validator in validators:
                    out.write(indent * ' ')
                    out.write("@" + validator["type"] + "(")
                    firstItem = True
                    for key, value in validator.items():
                        if key != "type":
                            if not firstItem:
                                out.write(", ")
                            firstItem = False
                            out.write(key + " = ")
                            if type(value) is unicode or type(value) is str:
                                out.write("\"" + value + "\"")
                            else:
                                out.write(str(value))
                    out.write(")\n")
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
        out.write(
            "public " + fields[i]["type"] + " get" + fetchName(fields[i]["name"]) + "() {\n")
        out.write((indent + 2) * ' ')
        out.write("return " + fields[i]["name"] + ";\n")
        out.write(indent * ' ')
        out.write("}\n\n")
        out.write(indent * ' ')
        if builder == True:
            out.write("public Builder set" + fetchName(
                fields[i]["name"]) + "(" + fields[i]["type"] + " " + fields[i]["name"] + ") {\n")
        else:
            out.write("public void set" + fetchName(
                fields[i]["name"]) + "(" + fields[i]["type"] + " " + fields[i]["name"] + ") {\n")
        out.write((indent + 2) * ' ')
        out.write(
            "this." + fields[i]["name"] + " = " + fields[i]["name"] + ";\n")
        if builder == True:
            out.write((indent + 2) * ' ')
            out.write("return this;\n")
        out.write(indent * ' ')
        out.write("}\n")


def getFieldName(name):
    name = fetchName(name)
    return "_".join(l.upper() for l in re.findall('[A-Z][^A-Z]*', name))


def writeFields(indent, out, fields, aliases, class_name):
    projections = {}
    for field in aliases:
        fieldName = "ALIAS_" + getFieldName(field["name"])
        fieldValue = field["value"]
        out.write(indent * ' ')
        out.write("{0}(alias(\"{1}\", \"{2}\")),\n".format(fieldName, fieldValue, field["name"]))
    for field in fields:
        fieldName = getFieldName(field["name"])
        params = []
        if "field" in field:
            params.append("\"{0}\"".format(field["field"]))
        params.append("\"{0}\"".format(field["name"]))
        if "aliases" in field:
            for alias in field["aliases"]:
                aliasFieldName = "ALIAS_" + getFieldName(alias)
                params.append("{0}".format(aliasFieldName))
        method = "dbField"
        if "dbField" in field and field["dbField"] is False:
            method = "field"
        fieldParam = "{0}({1})".format(method, ", ".join(params))
        out.write(indent * ' ')
        out.write("{0}({1}),\n".format(fieldName, fieldParam))

        if "projections" in field:
            for j in range(len(field["projections"])):
                projection = field["projections"][j]
                if projection not in projections:
                    projections[projection] = []
                projections[projection].append(fieldName)
    out.write(indent * ' ')
    out.write(";\n\n")

    for key in projections:
        out.write(indent * ' ')
        out.write("public static final Set<{0}Fields> {1} = ImmutableSet.of(\n".format(
            class_name, key))
        first = True
        for projection in projections[key]:
            if first:
                first = False
            else:
                out.write(',\n')
            out.write((indent + 4) * ' ')
            out.write(projection)
        out.write(");\n")


def generateDto(input_file, output_dir):
    impl_dir = output_dir + '/impl/'
    field_dir = output_dir + '/field/'
    data = json.load(open(input_file))
    entity = data["entity"]
    fields = data["fields"]
    aliases = []
    if "aliases" in data:
        aliases = data["aliases"]

    # Create directors if needed
    if not os.path.exists(impl_dir):
        os.makedirs(impl_dir)
    if not os.path.exists(field_dir):
        os.makedirs(field_dir)

    class_name = input_file[input_file.rfind("/") + 1: -5]
    impl_file_name = impl_dir + class_name + "Dto.java"
    field_file_name = field_dir + class_name + "Fields.java"

    # Create file
    impl_file = open(impl_file_name, "w")
    field_file = open(field_file_name, "w")

    importList = [
        "cn.edu.uestc.acmicpc.db.dto.BaseDto",
        "cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder",
        "cn.edu.uestc.acmicpc.db.entity.{0}".format(entity),
        "javax.validation.constraints.*",
        "org.hibernate.validator.constraints.*",
        "java.sql.Timestamp",
        "java.util.*"
    ]
    for field in fields:
        if "classpath" in field:
            importList.append(field["classpath"])

    # imports
    impl_file.write("""package cn.edu.uestc.acmicpc.db.dto.impl;

""")
    for package in sorted(importList):
        impl_file.write("import {0};\n".format(package))

    # Class definition
    impl_file.write("""
public class {0}Dto implements BaseDto<{1}> {{

  public {0}Dto() {{
  }}

""".format(class_name, entity))

    # Constructor
    impl_file.write("  public {0}Dto(".format(class_name))
    for i in xrange(len(fields)):
        field = fields[i]
        if i > 0:
            impl_file.write(", ")
        impl_file.write("{0} {1}".format(field["type"], field["name"]))
    impl_file.write(") {\n")
    for field in fields:
        impl_file.write("    this.{0} = {0};\n".format(field["name"]))
    impl_file.write("  }\n\n")

    writeProperties(2, impl_file, fields, False)

    # Equals
    impl_file.write("""
  @Override
  public boolean equals(Object o) {{
    if (this == o) {{
      return true;
    }}
    if (!(o instanceof {0}Dto)) {{
      return false;
    }}

    {0}Dto that = ({0}Dto) o; 
""".format(class_name))
    impl_file.write("    return true")
    for field in fields:
        impl_file.write('\n')
        impl_file.write(8 * ' ')
        impl_file.write("&& Objects.equals(this.{0}, that.{1})".format(
            field["name"], field["name"]))
    impl_file.write(";\n  }\n")

    # HashCode
    impl_file.write("""
  @Override
  public int hashCode() {
    return Objects.hash(""")
    for i in xrange(len(fields)):
        field = fields[i]
        if i > 0:
            impl_file.write(",\n")
            impl_file.write(8 * ' ')
        impl_file.write(field["name"])
    impl_file.write(");\n  }\n")

    impl_file.write("\n")
    impl_file.write(2 * ' ')
    impl_file.write("public static Builder builder() {\n")
    impl_file.write(4 * ' ')
    impl_file.write("return new Builder();\n")
    impl_file.write(2 * ' ')
    impl_file.write("}\n\n")

    # builder
    impl_file.write(2 * ' ')
    impl_file.write(
        "public static class Builder implements BaseDtoBuilder<" + class_name + "Dto> {\n\n")
    impl_file.write(4 * ' ')
    impl_file.write("private Builder() {\n")
    impl_file.write(4 * ' ')
    impl_file.write("}\n\n")
    impl_file.write(4 * ' ')
    impl_file.write("@Override\n")
    impl_file.write(4 * ' ')
    impl_file.write("public " + class_name + "Dto build() {\n")
    impl_file.write(6 * ' ')
    impl_file.write("return new " + class_name + "Dto(")
    for i in range(0, len(fields)):
        if i > 0:
            impl_file.write(", ")
        impl_file.write(fields[i]["name"])
    impl_file.write(");\n")
    impl_file.write(4 * ' ')
    impl_file.write("}\n\n")

    impl_file.write(4 * ' ')
    impl_file.write("@Override\n")
    impl_file.write(4 * ' ')
    impl_file.write(
        "public " + class_name + "Dto build(Map<String, Object> properties) {\n")
    for i in range(0, len(fields)):
        impl_file.write(6 * ' ')
        impl_file.write(fields[i]["name"] + " = (" + fields[i]["type"] +
                        ") properties.get(\"" + fields[i]["name"] + "\");\n")
    impl_file.write(6 * ' ')
    impl_file.write("return build();\n")
    impl_file.write(4 * ' ')
    impl_file.write("}\n\n")

    writeProperties(4, impl_file, fields, True)
    impl_file.write(2 * ' ')
    impl_file.write("}\n")
    impl_file.write("}\n")

    impl_file.close()

    # field class
    field_file.write("""package cn.edu.uestc.acmicpc.db.dto.field;

import static cn.edu.uestc.acmicpc.db.dto.FieldProjection.*;

import cn.edu.uestc.acmicpc.db.dto.FieldProjection;
import cn.edu.uestc.acmicpc.db.dto.Fields;

import com.google.common.collect.ImmutableSet;

import java.util.Set;
""")
    field_file.write("""
public enum {0}Fields implements Fields {{

""".format(class_name))

    writeFields(2, field_file, fields, aliases, class_name)

    field_file.write("""
  private final FieldProjection projection;

  @Override
  public FieldProjection getProjection() {{
    return projection;
  }}

  {0}Fields(FieldProjection projection) {{
    this.projection = projection;
  }}
}}

""".format(class_name))

    field_file.close()

if __name__ == "__main__":
    input_dir, output_dir = parseOpt(sys.argv[1:])

    base_dir = os.getcwd() + "/"
    input_dir = base_dir + input_dir + "/"
    output_dir = base_dir + output_dir + "/"

    # Remove exists directories
    if os.path.exists(output_dir):
        shutil.rmtree(output_dir)

    # Scanf input directory and get input files
    input_files = scanInputDirectory(input_dir)

    # Generate output
    for input_file in input_files:
        generateDto(input_file, output_dir)
