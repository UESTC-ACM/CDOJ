/*
 * cdoj, UESTC ACMICPC Online Judge
 *
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 * mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

/**
 * Tools for insert picture.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

PictureDialog = function (userOptions) {
    var options = mergeOptions({
            problemId: undefined
        },
        userOptions);

    var modal = $('#pictureModal');
    var pictureSelector = $('#pictureSelector');

    function GetPictureNode(picture) {
        var img = $('<img class="thumbnail-picture" src="' + picture.url + '" alt=""/>');
        var href = $('<a href="#" class="thumbnail"></a>');
        if (picture.selected)
            href.addClass('alert alert-success');
        var result = $('<li class="span2"></li>');
        result.append(href.append(img));
        return result;
    }

    function GetUploadNode() {
        var img = $('<img class="thumbnail-picture" src="' + '/images/upload.png' + '" alt=""/>');
        var btn = $('<div id="btnUpload" class="thumbnail"></a>');
        var result = $('<li class="span2"></li>');
        result.append(btn.append(img));
        return result;
    }

    /**
     * @return {string}
     */
    function GetMarkDownCode(title, url) {
        return '![' + title + '](' + url + ')';
    }

    var pictureList;

    function BlindUploadBtn() {
        var uploadBtn = pictureSelector.find('#btnUpload');
        var uploadUrl = '/admin/problem/uploadProblemPicture/' + options.problemId;
        var uploader = new qq.FineUploaderBasic({
            button: uploadBtn[0],
        //uploadBtn.fineUploader({
            request: {
                endpoint: uploadUrl,
                inputName: 'uploadFile'
            },
            validation: {
                allowedExtensions: ['jpeg', 'jpg', 'gif', 'png']
            },
            callbacks: {
                onComplete: function(id, fileName, responseJSON) {
                    if (responseJSON.success) {
                        pictureList.push({
                            url: responseJSON.uploadedFileUrl,
                            selected: false
                        });
                    } else {
                        alert('Upload ' + fileName + 'failed, reason: ' + responseJSON.error);
                    }
                    Refresh();
                }
            }
        });

        uploadBtn = pictureSelector.find('#btnUpload :file');
        uploadBtn.css('width', '152px');
        uploadBtn.css('height', '130px');
        console.log(uploadBtn);
    }

    function Refresh() {
        pictureSelector.empty();
        pictureSelector.append(GetUploadNode());
        $.each(pictureList, function(){
            pictureSelector.append(GetPictureNode(this));
        });
        BlindUploadBtn();
    }

    function Init() {
        pictureList = [];
        var url = '/admin/problem/getUploadedPictures/' + options.problemId;
        $.post(url, function(data){
            if (data.success) {
                $.each(data.pictures, function(){
                    pictureList.push({
                        url: this,
                        selected: true
                    });
                });
            } else {
                alert(data.error);
            }
            Refresh();
        });
    }

    Init();

    modal.modal();
};