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
CKEDITOR.dialog.add("about", function (a) {
    a = a.lang.about;
    return{title:CKEDITOR.env.ie ? a.dlgTitle : a.title, minWidth:390, minHeight:230, contents:[
        {id:"tab1", label:"", title:"", expand:!0, padding:0, elements:[
            {type:"html", html:'<style type="text/css">.cke_about_container{color:#000 !important;padding:10px 10px 0;margin-top:5px}.cke_about_container p{margin: 0 0 10px;}.cke_about_container .cke_about_logo{height:81px;background-color:#fff;background-image:url(' + CKEDITOR.plugins.get("about").path + 'dialogs/logo_ckeditor.png);background-position:center; background-repeat:no-repeat;margin-bottom:10px;}.cke_about_container a{cursor:pointer !important;color:#00B2CE !important;text-decoration:underline !important;}</style><div class="cke_about_container"><div class="cke_about_logo"></div><p>CKEditor ' +
                CKEDITOR.version + " (revision " + CKEDITOR.revision + ')<br><a href="http://ckeditor.com/">http://ckeditor.com</a></p><p>' + a.help.replace("$1", '<a href="http://docs.ckeditor.com/user">' + a.userGuide + "</a>") + "</p><p>" + a.moreInfo + '<br><a href="http://ckeditor.com/about/license">http://ckeditor.com/about/license</a></p><p>' + a.copy.replace("$1", '<a href="http://cksource.com/">CKSource</a> - Frederico Knabben') + "</p></div>"}
        ]}
    ], buttons:[CKEDITOR.dialog.cancelButton]}
});