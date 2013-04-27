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

import cn.edu.uestc.acmicpc.oj.xml.XmlNode;
import cn.edu.uestc.acmicpc.oj.xml.XmlParser;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
 */
@SuppressWarnings("UnusedDeclaration")
public class Settings implements ApplicationContextAware {
    /**
     * Global encoding
     */
    @SuppressWarnings("WeakerAccess")
    public String SETTING_ENCODING;

    /**
     * Upload file size limit(in MB)
     */
    @SuppressWarnings("WeakerAccess")
    public Integer SETTING_UPLOAD_SIZE;

    /**
     * Upload file's types
     */
    @SuppressWarnings("WeakerAccess")
    public String SETTING_UPLOAD_TYPES;

    /**
     * Upload files store folder
     */
    public String SETTING_UPLOAD_FOLDER;

    /**
     * Problems' pictures store folder
     */
    public String SETTING_PROBLEM_PICTURE_FOLDER;

    /**
     * Judge core's name.
     */
    public String JUDGE_JUDGE_CORE;

    /**
     * Data path name
     */
    public String JUDGE_DATA_PATH;

    /**
     * Work path name
     */
    public String JUDGE_TEMP_PATH;

    public String EMAIL_USERNAME;
    public String EMAIL_PASSWORD;
    public String EMAIL_STMP_SERVER;
    public String EMAIL_ADDRESS;

    /**
     * Judge information list
     */
    public List<Map<String, String>> JUDGE_LIST;

    /**
     * setting map from configuration file.
     */
    private static Map<String, Object> settings;

    /**
     * location of <strong>settings.xml</strong>.
     */
    private static final String SETTINGS_XML_PATH = "classpath:settings.xml";

    /**
     * Spring application context
     */
    @Autowired
    private ApplicationContext applicationContext;

    private String getAbsolutePath(String path) throws IOException {
        return applicationContext.getResource("").getFile().getAbsolutePath() + "/" + path;
    }

    /**
     * initialize configuration mappings from configuration file.
     *
     * @throws cn.edu.uestc.acmicpc.util.exception.AppException
     *
     */
    @SuppressWarnings({"UnusedDeclaration", "unchecked"})
    private void init() throws AppException, IOException {
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
                if (childNode.getTagName().trim().equals("list")) {
                    List<Map<String, String>> list = new LinkedList<>();
                    for (XmlNode childNode2 : childNode.getChildList()) { // item
                        list.add(parseItem(childNode2));
                    }
                    map.put(childNode.getAttribute("name").trim(), list);
                } else { //item
                    map.put(childNode.getAttribute("name").trim(), parseItem(childNode));
                }
            }
            settings.put(node.getAttribute("name").trim(), map);
        }

        SETTING_ENCODING = (String) getConfig("setting", "encoding", "value");
        SETTING_UPLOAD_SIZE = Integer.valueOf((String) getConfig("setting", "uploadSize", "value"));
        SETTING_UPLOAD_TYPES = (String) getConfig("setting", "uploadTypes", "value");
        SETTING_UPLOAD_FOLDER = getAbsolutePath((String) getConfig("setting", "uploadFolder", "value"));
        SETTING_PROBLEM_PICTURE_FOLDER = getAbsolutePath((String) getConfig("setting", "problemPictureFolder", "value"));

        JUDGE_JUDGE_CORE = (String) getConfig("judge", "judgeCore", "value");
        JUDGE_DATA_PATH = getAbsolutePath((String) getConfig("judge", "dataPath", "value"));
        JUDGE_TEMP_PATH = getAbsolutePath((String) getConfig("judge", "tempPath", "value"));
        JUDGE_LIST = (List<Map<String, String>>) getConfig("judge", "judges");

        EMAIL_ADDRESS = (String) getConfig("email", "address", "value");
        EMAIL_USERNAME = (String) getConfig("email", "username", "value");
        EMAIL_PASSWORD = (String) getConfig("email", "password", "value");
        EMAIL_STMP_SERVER = (String) getConfig("email", "stmpServer", "value");
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
        if (node.getInnerText() != null && !StringUtil.isNullOrWhiteSpace(node.getInnerText()))
            result.put("value", node.getInnerText().trim());
        for (XmlNode childNode : node.getChildList()) {
            result.put(childNode.getAttribute("name"), childNode.getAttribute("value").trim());
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
