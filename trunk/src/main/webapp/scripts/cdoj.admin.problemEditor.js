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

var problemDTO = {
    "problemDTO.problemId":null,
    "problemDTO.title":undefined,
    "problemDTO.description":undefined,
    "problemDTO.input":undefined,
    "problemDTO.output":undefined,
    "problemDTO.sampleInput":undefined,
    "problemDTO.sampleOutput":undefined,
    "problemDTO.hint":undefined,
    "problemDTO.source":undefined,
    "problemDTO.timeLimit":0,
    "problemDTO.memoryLimit":0,
    "problemDTO.isSpj":0,
    "problemDTO.isVisible":0,
    "problemDTO.outputLimit":0,
    "problemDTO.javaTimeLimit":0,
    "problemDTO.javaMemoryLimit":0,
    "problemDTO.dataCount":0,
    "problemDTO.difficulty":0
};

var problemId;
var editMode = "new";

//TODO clean the styles paste into editor

$(document).ready(function () {

    editMode = $('#editorFlag').attr("value");
    if (editMode == "edit")
        problemId = $('#problemId')[0].innerHTML;

    $.each(editors,function(editorId) {
        var editorOpts = epicEditorOpts;
        editorOpts.container = editorId;
        if (editMode == "new") {
            editorOpts.file.name = editorId+"new";
            editors[editorId] = new EpicEditor(editorOpts).load();
        }
        else {
            editorOpts.file.name = editorId+problemId;
            var oldContent = $('#'+editorId)[0].innerHTML;
            console.log(oldContent);
            editors[editorId] = new EpicEditor(editorOpts).load();
            editors[editorId].importFile(editorOpts.file.name,oldContent);
        }
    });

    $('input#submit').click(function (e) {
        if (editMode == "edit")
            problemDTO["problemDTO.problemId"] = problemId;
        problemDTO["problemDTO.title"] = $('#problemDTO_title').val();
        problemDTO["problemDTO.source"] = $('#problemDTO_source').val();
        $.each(editors,function(editorId) {
            problemDTO[editorId.replace('_','.')] = this.exportFile();
        });
        $.post("/admin/problem/edit",problemDTO,function(data) {
            if (validation($('#problemEditor'),data)) {
                alert("Successful!");
                $.each(editors,function() {
                    this.remove(this.settings.file.name);
                });
                window.location.href= "/admin/problem/list";
            }
            else
                $('html,body').animate({scrollTop: '0px'}, 400);
        });
        return false;
    });
});