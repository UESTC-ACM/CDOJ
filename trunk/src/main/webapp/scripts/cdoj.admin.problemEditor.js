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

/**
 * Created with IntelliJ IDEA.
 * User: mzry1992
 * Date: 13-2-4
 * Time: 下午5:39
 * To change this template use File | Settings | File Templates.
 */

var epicEditorOpts = {
    container: 'epiceditor',
    basePath: '/plugins/epiceditor',
    clientSideStorage: true,
    localStorageName: 'epiceditor',
    useNativeFullsreen: true,
    parser: marked,
    file: {
        name: 'epiceditor',
        defaultContent: '',
        autoSave: 100
    },
    theme: {
        base:'/themes/base/epiceditor.css',
        preview:'/themes/preview/github.css',
        editor:'/themes/editor/epic-light.css'
    },
    focusOnLoad: true,
    shortcut: {
        modifier: 18,
        fullscreen: 70,
        preview: 80
    }
};

var editors = {
    "problemDTO_description":undefined,
    "problemDTO_input":undefined,
    "problemDTO_output":undefined,
    "problemDTO_sampleInput":undefined,
    "problemDTO_sampleOutput":undefined,
    "problemDTO_hint":undefined
};

//TODO load problem data if edit mode is edit.
//TODO clean the styles paste into editor

$(document).ready(function () {
    $.each(editors,function(editorId) {
        var editorOpts = epicEditorOpts;
        editorOpts.localStorageName =
            editorOpts.container =
                editorOpts.file.name = editorId;
        editors[editorId] = new EpicEditor(editorOpts).load();
    });
    console.log(editors);

    $('input#submit').click(function (e) {
        $.each(editors,function() {
            console.log(this.previewer.innerHTML);
        });
        return false;
    });
});