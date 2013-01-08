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
CKEDITOR.dialog.add("hiddenfield", function (d) {
    return{title:d.lang.forms.hidden.title, hiddenField:null, minWidth:350, minHeight:110, onShow:function () {
        delete this.hiddenField;
        var a = this.getParentEditor(), b = a.getSelection(), c = b.getSelectedElement();
        c && (c.data("cke-real-element-type") && "hiddenfield" == c.data("cke-real-element-type")) && (this.hiddenField = c, c = a.restoreRealElement(this.hiddenField), this.setupContent(c), b.selectElement(this.hiddenField))
    }, onOk:function () {
        var a = this.getValueOf("info", "_cke_saved_name");
        this.getValueOf("info", "value");
        var b = this.getParentEditor(), a = CKEDITOR.env.ie && !(8 <= CKEDITOR.document.$.documentMode) ? b.document.createElement('<input name="' + CKEDITOR.tools.htmlEncode(a) + '">') : b.document.createElement("input");
        a.setAttribute("type", "hidden");
        this.commitContent(a);
        a = b.createFakeElement(a, "cke_hidden", "hiddenfield");
        this.hiddenField ? (a.replace(this.hiddenField), b.getSelection().selectElement(a)) : b.insertElement(a);
        return!0
    }, contents:[
        {id:"info", label:d.lang.forms.hidden.title, title:d.lang.forms.hidden.title,
            elements:[
                {id:"_cke_saved_name", type:"text", label:d.lang.forms.hidden.name, "default":"", accessKey:"N", setup:function (a) {
                    this.setValue(a.data("cke-saved-name") || a.getAttribute("name") || "")
                }, commit:function (a) {
                    this.getValue() ? a.setAttribute("name", this.getValue()) : a.removeAttribute("name")
                }},
                {id:"value", type:"text", label:d.lang.forms.hidden.value, "default":"", accessKey:"V", setup:function (a) {
                    this.setValue(a.getAttribute("value") || "")
                }, commit:function (a) {
                    this.getValue() ? a.setAttribute("value", this.getValue()) :
                        a.removeAttribute("value")
                }}
            ]}
    ]}
});