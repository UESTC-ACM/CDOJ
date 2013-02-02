/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.util;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.oj.xml.XmlNode;
import cn.edu.uestc.acmicpc.oj.xml.XmlParser;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Global static class to get configuration parameters from
 * <strong>settings.xml</strong>.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 5
 */
public class Settings {
    /**
     * Global encoding
     */
    public final String SETTING_ENCODING;

    /**
     * Upload file size limit(in MB)
     */
    public final Integer SETTING_UPLOAD_SIZE;

    /**
     * Upload file's types
     */
    public final String SETTING_UPLOAD_TYPES;

    /**
     * Upload files store folder
     */
    public final String SETTING_UPLOAD_FOLDER;

    /**
     * Data path name
     */
    public final String JUDGE_DATA_PATH;

    /**
     * Work path name
     */
    public final String JUDGE_TEMP_PATH;

    /**
     * setting map from configuration file.
     */
    private static Map<String, Object> settings;

    /**
     * location of <strong>settings.xml</strong>.
     */
    private static final String SETTINGS_XML_PATH = "classpath:settings.xml";

    /**
     * Static constructor.
     */
    public Settings() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SETTING_ENCODING = (String) getConfig("setting", "encoding", "value");
        SETTING_UPLOAD_SIZE = Integer.valueOf((String) getConfig("setting", "uploadSize", "value"));
        SETTING_UPLOAD_TYPES = (String) getConfig("setting", "uploadTypes", "value");
        SETTING_UPLOAD_FOLDER = (String) getConfig("setting", "uploadFolder", "value");

        JUDGE_DATA_PATH = (String) getConfig("judge", "dataPath", "value");
        JUDGE_TEMP_PATH = (String) getConfig("judge", "tempPath", "value");

        System.out.println(SETTING_ENCODING);
        System.out.println(SETTING_UPLOAD_SIZE);
        System.out.println(SETTING_UPLOAD_TYPES);
        System.out.println(SETTING_UPLOAD_FOLDER);

        System.out.println(JUDGE_TEMP_PATH);
        System.out.println(JUDGE_DATA_PATH);


    }

    /**
     * initialize configuration mappings from configuration file.
     *
     * @throws cn.edu.uestc.acmicpc.util.exception.AppException
     *
     */
    private void init() throws AppException {
        settings = new HashMap<>();
        String path;
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(SETTINGS_XML_PATH);
        try {
            path = resource.getFile().getPath();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println(path);
        XmlParser xmlParser = new XmlParser(path);
        XmlNode root;
        try {
            root = xmlParser.parse().getChildList().get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new AppException("there no root node in the settings file.");
        }
        for (XmlNode node : root.getChildList()) { // settings
            Map<String, Object> map = new HashMap<>();
            for (XmlNode childNode : node.getChildList()) { // item or list
                if (childNode.getTagName().equals("list")) {
                    List<Map<String, String>> list = new LinkedList<>();
                    for (XmlNode childNode2 : childNode.getChildList()) { // item
                        list.add(parseItem(childNode2));
                    }
                    map.put(childNode.getAttribute("name"), list);
                } else { //item
                    map.put(childNode.getAttribute("name"), parseItem(childNode));
                }
            }
            settings.put(node.getAttribute("name"), map);
        }
    }

    /**
     * parse item's information
     *
     * @param node item node
     * @return item map
     * @throws AppException
     */
    private Map<String, String> parseItem(XmlNode node) throws AppException {
        Map<String, String> result = new HashMap<>();
        if (node.getInnerText() != null)
            result.put("value", node.getInnerText());
        for (XmlNode childNode : node.getChildList()) {
            result.put(childNode.getAttribute("name"), childNode.getAttribute("value"));
        }
        return result;
    }

    /**
     * Get configuration value from <strong>settings.xml</strong>.
     *
     * @param path setting path
     * @return item value
     */
    private Object getConfig(String... path) {
        try {
            Object current = settings;
            for (String aPath : path) {
                Method method = current.getClass().getMethod("get", Object.class);
                current = method.invoke(current, aPath);
            }
            if (current instanceof String) {
                return ((String) current).trim();
            } else {
                return current;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
