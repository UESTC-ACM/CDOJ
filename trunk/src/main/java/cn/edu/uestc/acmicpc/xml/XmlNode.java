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

package cn.edu.uestc.acmicpc.xml;

import cn.edu.uestc.acmicpc.util.AppException;

import java.util.*;

/**
 * Basic node object used by xml parser.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class XmlNode implements Iterable<XmlNode> {

    /**
     * Tag's name in xml file.
     */
    private String tagName;

    /**
     * Get the name of the tag which this node mapping for.
     *
     * @return the tag's name
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Set the name of the tag which this node mapping for.
     *
     * @param value the tag's name we want to set.
     */
    public void setTagName(String value) {
        tagName = value;
    }

    /**
     * Inner text field.
     */
    private String innerText;

    /**
     * Get inner text field of the tag which this node mapping for.
     *
     * @return the tag's inner text field
     */
    public String getInnerText() {
        return innerText;
    }

    /**
     * Set inner text field of the tag which this node mapping for.
     *
     * @param innerText the tag's inner text field we want to set
     */
    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

    /**
     * Get the all the child nodes of this node.
     *
     * @return child nodes list of this node
     */
    public List<XmlNode> getChildList() {
        return childList;
    }


    /**
     * Set child nodes list of this node.
     *
     * @param childList new child nodes list we want to set
     */
    public void setChildList(List<XmlNode> childList) {
        this.childList = childList;
    }

    /**
     * Get all attributes of this node.
     *
     * @return attribute list
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Add new attribute into the attribute list, if the attribute list is {@code null}
     * or there is same attribute in the list, throw {@link AppException}.
     *
     * @param key   the name of the attribute we want to add
     * @param value attribute innerText
     * @throws AppException
     */
    public void addAttribute(String key, String value) throws AppException {
        if (getAttributes() == null)
            throw new AppException("The attribute list is null.");
        else if (getAttributes().containsKey(key))
            throw new AppException("There is same attribute in the attribute list.");
        else
            getAttributes().put(key, value);
    }

    /**
     * Remove specified attribute  from the attribute list, if the  attribute is {@code null}
     * or there is such attribute in the list, throw {@link AppException}.
     *
     * @param key the name of the attribute we want to remove
     * @throws AppException
     */
    public void removeAttribute(String key) throws AppException {
        if (getAttributes() == null)
            throw new AppException("The attribute list is null.");
        else if (!getAttributes().containsKey(key))
            throw new AppException("There is such attribute in the attribute list.");
        else
            getAttributes().remove(key);
    }

    /**
     * Get specified attribute innerText from the attribute list, if the attribute
     * list is {@code null} or there is no such attribute in the attribute list,
     * return empty string, otherwise return the innerText of this attribute.
     *
     * @param key the attribute name
     * @return the innerText of the attribute
     * @throws AppException
     */
    public String getAttribute(String key) throws AppException {
        if (getAttributes() == null)
            throw new AppException("The attribute list is null.");
        else if (!getAttributes().containsKey(key))
            return "";
        else
            return getAttributes().get(key);
    }

    /**
     * Child nodes list.
     */
    private List<XmlNode> childList;

    /**
     * Attribute list.
     */
    private Map<String, String> attributes;

    /**
     * Set attribute list.
     *
     * @param attributes new attribute list with Map
     */
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Construct a xml node and initialize the child nodes list and attribute list.
     */
    public XmlNode() {
        setChildList(new LinkedList<XmlNode>());
        setAttributes(new HashMap<String, String>());
    }

    /**
     * Add a new child node into the child nodes list, if the child node or child nodes list
     * is {@code null}, or this is same node in the child node list, throw {@link AppException}.
     *
     * @param child new child node
     * @throws AppException
     */
    public void addChild(XmlNode child) throws AppException {
        if (getChildList() == null)
            throw new AppException("The child node list does not exist.");
        if (child == null)
            throw new AppException("The new node is null.");
        for (XmlNode node : getChildList())
            if (node == child)
                throw new AppException("This node is contained in the child node list.");
        getChildList().add(child);
    }

    /**
     * Get the iterator of parameters.
     *
     * @return the iterator is of child nodes list.
     */
    @Override
    public Iterator<XmlNode> iterator() {
        return getChildList().iterator();
    }
}
