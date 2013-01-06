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
CKEDITOR.dialog.add("textarea", function (b) {
    return{title:b.lang.forms.textarea.title, minWidth:350, minHeight:220, onShow:function () {
        delete this.textarea;
        var a = this.getParentEditor().getSelection().getSelectedElement();
        a && "textarea" == a.getName() && (this.textarea = a, this.setupContent(a))
    }, onOk:function () {
        var a, b = this.textarea, c = !b;
        c && (a = this.getParentEditor(), b = a.document.createElement("textarea"));
        this.commitContent(b);
        c && a.insertElement(b)
    }, contents:[
        {id:"info", label:b.lang.forms.textarea.title, title:b.lang.forms.textarea.title,
            elements:[
                {id:"_cke_saved_name", type:"text", label:b.lang.common.name, "default":"", accessKey:"N", setup:function (a) {
                    this.setValue(a.data("cke-saved-name") || a.getAttribute("name") || "")
                }, commit:function (a) {
                    this.getValue() ? a.data("cke-saved-name", this.getValue()) : (a.data("cke-saved-name", !1), a.removeAttribute("name"))
                }},
                {type:"hbox", widths:["50%", "50%"], children:[
                    {id:"cols", type:"text", label:b.lang.forms.textarea.cols, "default":"", accessKey:"C", style:"width:50px", validate:CKEDITOR.dialog.validate.integer(b.lang.common.validateNumberFailed),
                        setup:function (a) {
                            this.setValue(a.hasAttribute("cols") && a.getAttribute("cols") || "")
                        }, commit:function (a) {
                        this.getValue() ? a.setAttribute("cols", this.getValue()) : a.removeAttribute("cols")
                    }},
                    {id:"rows", type:"text", label:b.lang.forms.textarea.rows, "default":"", accessKey:"R", style:"width:50px", validate:CKEDITOR.dialog.validate.integer(b.lang.common.validateNumberFailed), setup:function (a) {
                        this.setValue(a.hasAttribute("rows") && a.getAttribute("rows") || "")
                    }, commit:function (a) {
                        this.getValue() ? a.setAttribute("rows",
                            this.getValue()) : a.removeAttribute("rows")
                    }}
                ]},
                {id:"value", type:"textarea", label:b.lang.forms.textfield.value, "default":"", setup:function (a) {
                    this.setValue(a.$.defaultValue)
                }, commit:function (a) {
                    a.$.value = a.$.defaultValue = this.getValue()
                }}
            ]}
    ]}
});