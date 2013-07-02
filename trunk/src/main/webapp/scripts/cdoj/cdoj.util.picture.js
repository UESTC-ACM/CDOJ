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
            uploadUrl: undefined,
            pictureListUrl: undefined
        },
        userOptions);

    var modal = $('#pictureModal');
    var pictureSelector = $('#pictureSelector');

    function GetPictureNode(id, picture) {
        var img = $('<img class="thumbnail-picture" src="' + picture.url + '" alt=""/>');
        var href = $('<a href="#" class="thumbnail picUploaded" value="' + id + '"></a>');
        if (picture.selected)
        {
            var selectFlag = $('<span class="badge badge-success" style="position: absolute; margin: 10px 0 0 100px; z-index: 9999;"><i class="icon-ok"></i></span>');
            href.append(selectFlag);
        }
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
        var uploadUrl = options.uploadUrl;
        var uploader = new qq.FineUploaderBasic({
            button: uploadBtn[0],
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
                        console.log(responseJSON);
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
    }

    function BlindSelectBtn() {
        var picUploaded = pictureSelector.find('.picUploaded');
        $.each(picUploaded, function(){
            var id = $(this).attr('value');
            $(this).live('click', function(){
                pictureList[id].selected = !pictureList[id].selected;
                Refresh();
                return false;
            });
        });
    }

    function BlindInsertBtn() {
        var insertBtn = modal.find('#btnInsert');
        $(insertBtn).on('click', function(){
            var select = [];
            $.each(pictureList, function(){
                if (this.selected == true)
                    select.push(this);
            });
            var message = '';
            if (select.length == 0) {
                message = message + 'Please select at least one picture!';
            } else {
                $.each(select, function(id, data){
                    if (id != 0)
                        message = message + '\n';
                    message = message + GetMarkDownCode('Title', data.url);
                });
            }
            var resultModal = $('#resultModal');
            var resultArea = resultModal.find('#resultArea');
            resultArea.empty();
            resultArea.append(message);
            modal.modal('hide');
            resultModal.modal();
            return false;
        });
    }

    function Refresh() {
        pictureSelector.empty();
        pictureSelector.append(GetUploadNode());
        $.each(pictureList, function(id, data){
            pictureSelector.append(GetPictureNode(id, data));
        });
        BlindUploadBtn();
        BlindSelectBtn();
    }

    function Init() {
        pictureList = [];
        var url = options.pictureListUrl;
        $.post(url, function(data){
            if (data.success) {
                $.each(data.pictures, function(){
                    pictureList.push({
                        url: this,
                        selected: false
                    });
                });
            } else {
                alert(data.error);
            }
            Refresh();
        });
        BlindInsertBtn();
    }

    Init();

    modal.modal();
};