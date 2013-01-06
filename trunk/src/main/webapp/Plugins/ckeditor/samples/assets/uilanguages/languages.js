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
var CKEDITOR_LANGS = function () {
    var c = {af:"Afrikaans", ar:"Arabic", bg:"Bulgarian", bn:"Bengali/Bangla", bs:"Bosnian", ca:"Catalan", cs:"Czech", cy:"Welsh", da:"Danish", de:"German", el:"Greek", en:"English", "en-au":"English (Australia)", "en-ca":"English (Canadian)", "en-gb":"English (United Kingdom)", eo:"Esperanto", es:"Spanish", et:"Estonian", eu:"Basque", fa:"Persian", fi:"Finnish", fo:"Faroese", fr:"French", "fr-ca":"French (Canada)", gl:"Galician", gu:"Gujarati", he:"Hebrew", hi:"Hindi", hr:"Croatian", hu:"Hungarian", is:"Icelandic",
        it:"Italian", ja:"Japanese", ka:"Georgian", km:"Khmer", ko:"Korean", ku:"Kurdish", lt:"Lithuanian", lv:"Latvian", mn:"Mongolian", ms:"Malay", nb:"Norwegian Bokmal", nl:"Dutch", no:"Norwegian", pl:"Polish", pt:"Portuguese (Portugal)", "pt-br":"Portuguese (Brazil)", ro:"Romanian", ru:"Russian", sk:"Slovak", sl:"Slovenian", sr:"Serbian (Cyrillic)", "sr-latn":"Serbian (Latin)", sv:"Swedish", th:"Thai", tr:"Turkish", uk:"Ukrainian", vi:"Vietnamese", zh:"Chinese Traditional", "zh-cn":"Chinese Simplified"}, b = [], a;
    for (a in CKEDITOR.lang.languages)b.push({code:a,
        name:c[a] || a});
    b.sort(function (a, b) {
        return a.name < b.name ? -1 : 1
    });
    return b
}();