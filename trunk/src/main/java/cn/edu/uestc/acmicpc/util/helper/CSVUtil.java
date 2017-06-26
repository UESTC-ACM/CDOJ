package cn.edu.uestc.acmicpc.util.helper;

import au.com.bytecode.opencsv.CSVReader;
import cn.edu.uestc.acmicpc.util.annotation.CSVMap;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Excel file parser
 */
public class CSVUtil {

  /**
   * Check all fields exists in the header line
   *
   * @param columnMap
   *          column maps
   * @param clazz
   *          specified class
   * @throws AppException
   */
  public static void checkHeaderLineContainsAllFields(Map<String, Integer> columnMap, Class<?> clazz)
      throws AppException {
    for (Field field : clazz.getFields()) {
      if (field.isAnnotationPresent(CSVMap.class)) {
        CSVMap csvMap = field.getAnnotation(CSVMap.class);
        String column = csvMap.value();
        if ("".equals(column)) {
          // Default set as field name
          column = field.getName();
        }
        if (!columnMap.containsKey(column)) {
          throw new AppException("Missing " + column + " column in csv file!");
        }
      }
    }
  }

  public static <T> T parse(String[] line, Map<String, Integer> columnMap, Class<T> clazz)
      throws AppException {
    T result;
    try {
      result = clazz.newInstance();
    } catch (Exception e) {
      throw new AppException("Could not create instance of " + clazz.getName() + "!");
    }
    for (Field field : clazz.getFields()) {
      if (field.isAnnotationPresent(CSVMap.class)) {
        CSVMap csvMap = field.getAnnotation(CSVMap.class);
        String column = csvMap.value();
        if ("".equals(column)) {
          // Default set as field name
          column = field.getName();
        }
        Integer position = columnMap.get(column);
        try {
          field.set(result, line[position]);
        } catch (IllegalAccessException e) {
          throw new AppException("Could not set value to " + clazz.getName() + "."
              + field.getName() + "!");
        }
      }
    }
    return result;
  }

  public static <T> List<T> parseArray(File csvFile, Class<T> clazz) throws AppException {
    if (csvFile == null) {
      return null;
    }

    List<T> list = new LinkedList<>();
    CSVReader reader = null;
    try {
      reader = new CSVReader(new FileReader(csvFile), ',', '"');

      // Loading header line
      String[] headerLine = reader.readNext();
      AppExceptionUtil.assertNotNull(headerLine, "Do not upload empty csv file!");

      Map<String, Integer> columnMap = new HashMap<>();
      for (Integer position = 0; position < headerLine.length; position++) {
        String header = headerLine[position];
        columnMap.put(header, position);
      }

      // Check all fields exists in the header line
      checkHeaderLineContainsAllFields(columnMap, clazz);

      // Loading content
      String[] nextLine;
      while ((nextLine = reader.readNext()) != null) {
        list.add(parse(nextLine, columnMap, clazz));
      }
    } catch (FileNotFoundException e) {
      throw new AppException("Error while opening csv file!");
    } catch (IOException e) {
      throw new AppException("Error while reading csv file!");
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          throw new AppException("Error while reading csv file!");
        }
      }
    }

    return list;
  }
}
