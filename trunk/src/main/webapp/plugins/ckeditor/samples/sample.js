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
(function () {
    CKEDITOR.on("instanceReady", function (b) {
        var b = b.editor, a = CKEDITOR.document.$.getElementsByName("ckeditor-sample-required-plugins"), a = a.length ? CKEDITOR.dom.element.get(a[0]).getAttribute("content").split(",") : [], c = [];
        if (a.length) {
            for (var d = 0; d < a.length; d++)b.plugins[a[d]] || c.push("<code>" + a[d] + "</code>");
            c.length && CKEDITOR.dom.element.createFromHtml('<div class="warning"><span>To fully experience this demo, the ' + c.join(", ") + " plugin" + (1 < c.length ? "s are" : " is") + " required.</span></div>").insertBefore(b.container)
        }
    })
})();