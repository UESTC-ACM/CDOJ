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
 * All function used in problem statement editor page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */


var epicEditorOpts = {
    container: 'epiceditor',
    basePath: '/plugins/epiceditor',
    clientSideStorage: false,
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
    "problemDTO.source":undefined
};

var problemId;
var editMode = 'new';

//TODO clean the styles paste into editor

$(document).ready(function () {

    editMode = $('#editorFlag').attr('value');
    if (editMode == 'edit')
        problemId = $('#problemId')[0].innerHTML;

    $.each(editors,function(editorId) {
        epicEditorOpts.container = editorId;
        if (editMode == 'new') {
            epicEditorOpts.clientSideStorage = true;
            epicEditorOpts.file.name = editorId+'new';
            editors[editorId] = new EpicEditor(epicEditorOpts).load();
        }
        else {
            epicEditorOpts.file.name = editorId+problemId;
            var oldContent = $('#'+editorId)[0].innerHTML.toString();
            oldContent = js.lang.String.decodeHtml(oldContent);
            editors[editorId] = new EpicEditor(epicEditorOpts).load();
            editors[editorId].importFile(epicEditorOpts.file.name,oldContent);
        }
    });

    $('input#submit').click(function () {
        if (editMode == 'edit')
            problemDTO['problemDTO.problemId'] = problemId;
        problemDTO['problemDTO.title'] = $('#problemDTO_title').val();
        problemDTO['problemDTO.source'] = $('#problemDTO_source').val();
        $.each(editors,function(editorId) {
            problemDTO[editorId.replace('_','.')] = this.exportFile();
        });
        $.post('/admin/problem/edit',problemDTO,function(data) {
            $('#problemEditor').checkValidate({
                result: data,
                onSuccess: function(){
                    alert('Successful!');
                    $.each(editors,function() {
                        this.remove(this.settings.file.name);
                    });
                    window.location.href= '/problem/show/'+problemId;
                },
                onFail: function(){
                    $('html,body').animate({scrollTop: '0px'}, 400);
                }
            });
        });
        return false;
    });
});