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
    "articleDTO_content":undefined
};

var articleDTO = {
    "articleDTO.articleId": null,
    "articleDTO.title": undefined,
    "articleDTO.author": undefined,
    "articleDTO.content": undefined
};

var articleId;

//TODO clean the styles paste into editor

$(document).ready(function () {

    articleId = $('#articleId')[0].innerHTML;

    $.each(editors,function(editorId) {
        epicEditorOpts.container = editorId;
        epicEditorOpts.uploadUrl = '/admin/article/uploadArticlePicture/' + articleId;
        epicEditorOpts.pictureListUrl = '/admin/article/getUploadedPictures/' + articleId;
        epicEditorOpts.file.name = editorId+articleId;
        var oldContent = $('#'+editorId)[0].innerHTML.toString();
        oldContent = js.lang.String.decodeHtml(oldContent);
        editors[editorId] = new EpicEditor(epicEditorOpts).load();
        editors[editorId].importFile(epicEditorOpts.file.name,oldContent);
    });

    $('input#submit').click(function () {
        articleDTO['articleDTO.articleId'] = articleId;
        articleDTO['articleDTO.title'] = $('#articleDTO_title').val();
        articleDTO['articleDTO.author'] = $('#articleDTO_author').val();
        $.each(editors,function(editorId) {
            articleDTO[editorId.replace('_','.')] = this.exportFile();
        });
        $.post('/admin/article/edit', articleDTO,function(data) {
            $('#articleEditor').checkValidate({
                result: data,
                onSuccess: function(){
                    alert('Successful!');
                    $.each(editors,function() {
                        this.remove(this.settings.file.name);
                    });
                    window.location.href= '/admin/article/list';
                },
                onFail: function(){
                    $('html,body').animate({scrollTop: '0px'}, 400);
                }
            });
        });
        return false;
    });
});