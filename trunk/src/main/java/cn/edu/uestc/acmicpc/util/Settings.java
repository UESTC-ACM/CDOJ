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
import org.apache.struts2.ServletActionContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Global static class to get configuration parameters from
 * <strong>settings.xml</strong>.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
public class Settings {
    /**
     * Global encoding
     */
    public static final String SETTING_ENCODING;

    /**
     * Upload file size limit(in MB)
     */
    public static final Integer SETTING_UPLOAD_SIZE;

    /**
     * Upload file's types
     */
    public static final String SETTING_UPLOAD_TYPES;

    /**
     * Upload files store folder
     */
    public static final String SETTING_UPLOAD_FOLDER;

    /**
     * setting map from configuration file.
     */
    private static Map<String, Map<String, String>> settings;

    /**
     * description map from configuration file.
     */
    private static Map<String, Map<String, String>> descriptions;

    /**
     * location of <strong>settings.xml</strong>.
     */
    private static final String SETTINGS_XML_PATH = "/WEB-INF/settings.xml";

    /**
     * Static constructor.
     */
    static {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SETTING_ENCODING = getConfig("setting", "encoding");
        SETTING_UPLOAD_SIZE = Integer.valueOf(getConfig("setting", "uploadSize"));
        SETTING_UPLOAD_TYPES = getConfig("setting", "uploadTypes");
        SETTING_UPLOAD_FOLDER = getConfig("settings", "uploadFolder");
    }

    /**
     * initialize configuration mappings from configuration file.
     *
     * @throws cn.edu.uestc.acmicpc.util.exception.AppException
     *
     */
    private static void init() throws AppException {
        settings = new HashMap<String, Map<String, String>>();
        descriptions = new HashMap<String, Map<String, String>>();
        String path = ServletActionContext.getServletContext().getRealPath(SETTINGS_XML_PATH);
        XmlParser xmlParser = new XmlParser(path);
        XmlNode root;
        try {
            root = xmlParser.parse().getChildList().get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new AppException("there no root node in the settings file.");
        }
        for (XmlNode node : root.getChildList()) {
            Map<String, String> childSettings = new HashMap<String, String>();
            Map<String, String> childDescriptions = new HashMap<String, String>();
            for (XmlNode child : node.getChildList()) {
                childSettings.put(child.getAttribute("id").toUpperCase(), child.getInnerText());
                childDescriptions.put(child.getAttribute("description").toUpperCase(), child.getInnerText());
            }
            settings.put(node.getAttribute("name").toUpperCase(), childSettings);
            descriptions.put(node.getAttribute("name").toUpperCase(), childDescriptions);
        }
    }

    /**
     * Get configuration value from <strong>settings.xml</strong>.
     *
     * @param category category name
     * @param name     item name
     * @return item value
     */
    public static String getConfig(String category, String name) {
        category = category.toUpperCase();
        name = name.toUpperCase();
        return settings.get(category) == null
                || settings.get(category).get(name) == null ? "" : settings.get(category).get(name).trim();
    }

    /**
     * Get configuration description from <strong>settings.xml</strong>.
     *
     * @param category category name
     * @param name     item name
     * @return item description
     */
    public static String getDescription(String category, String name) {
        category = category.toUpperCase();
        name = name.toUpperCase();
        return descriptions.get(category) == null ? "" : descriptions.get(category).get(name);
    }
}
