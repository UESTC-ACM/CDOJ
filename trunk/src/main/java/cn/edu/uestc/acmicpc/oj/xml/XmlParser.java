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

package cn.edu.uestc.acmicpc.oj.xml;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Simple xml parser in order to mapping the xml file into a {@link XmlNode} object.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class XmlParser {
    private final String fileName;

    /**
     * Construct a {@link XmlParser} and set specified xml file name.
     *
     * @param fileName the name of the xml file
     */
    public XmlParser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * parser the xml file and return a specified {@link XmlNode} object.
     *
     * @return the {@link XmlNode} representing the root element
     * @throws AppException
     */
    public XmlNode parse() throws AppException {
        XmlNode root = new XmlNode();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory
                    .newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);
            NodeList nodeList = document.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                XmlNode child = node.getNodeType() == Node.DOCUMENT_TYPE_NODE
                        || node.getNodeType() == Node.COMMENT_NODE ? null
                        : parseNode(nodeList.item(i));
                if (child != null)
                    root.addChild(child);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("unknown exception occurs.");
        }
        return root;
    }

    /**
     * parse the node and set node's attributes and child nodes.
     *
     * @param node the node to be parsed
     * @return a {@link XmlNode} object with all child nodes and attributes
     * @throws AppException
     */
    private XmlNode parseNode(Node node) throws AppException {
        if (node.getNodeType() == Node.DOCUMENT_TYPE_NODE
                || node.getNodeType() == Node.COMMENT_NODE
                || node.getNodeType() == Node.TEXT_NODE)
            return null;
        XmlNode result = new XmlNode();
        NamedNodeMap namedNodeMap = node.getAttributes();
        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < namedNodeMap.getLength(); ++i) {
            String key = namedNodeMap.item(i).getNodeName();
            String value = namedNodeMap.item(i).getNodeValue();
            result.addAttribute(key, value);
        }

        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node current = nodeList.item(i);
            XmlNode child = parseNode(current);
            if (child != null)
                result.addChild(child);
        }
        result.setTagName(node.getNodeName());
        result.setInnerText(node.getTextContent());
        return result;
    }
}
