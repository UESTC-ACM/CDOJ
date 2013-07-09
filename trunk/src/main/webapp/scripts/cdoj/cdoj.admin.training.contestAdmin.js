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
 * All function used in summer training contest admin page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

var trainingContestId;
var uploaderUrl;
var updateUrl;

$(document).ready(function () {
    trainingContestId = $('#trainingContestId')[0].innerHTML;
    uploaderUrl = '/training/admin/contest/uploadRank/'+trainingContestId;
    updateUrl = '/admin/problem/updateProblemData/'+trainingContestId;

    $('#fileUploader').fineUploader({
        request: {
            endpoint: uploaderUrl,
            inputName: 'uploadFile'
        },
        validation: {
            allowedExtensions: ['xls'],
            sizeLimit: 52428800 // 50 MB = 50 * 1024 * 1024 bytes
        },
        text: {
            uploadButton: '<i class="icon-upload icon-white"></i>Choose file'
        },
        template:
            '<div class="qq-upload-button btn btn-success">{uploadButtonText}</div>' +
                '<pre class="qq-upload-drop-area span12"><span>{dragZoneText}</span></pre>' +
                '<span class="qq-drop-processing"><span>{dropProcessingText}</span><span class="qq-drop-processing-spinner"></span></span>' +
                '<ul class="qq-upload-list" style="padding-top: 10px; text-align: center;"></ul>',
        classes: {
            success: 'alert alert-success',
            fail: 'alert alert-error'
        },
        multiple: false
    }).on('complete', function(event, id, name, response) {
            if (response.success == 'true') {
                $('#fileUploaderAttention').empty();
                console.log(response);
                var summary = response.trainingContestRankList.trainingUserRankSummaryList;
                var tbody = $('#rankList');
                tbody.find('tr').remove();
                $.each(summary, function(index, data) {
                    var html = $('<tr></tr>');
                    html.append($('<td>' + data.rank + '</td>'));
                    html.append($('<td>' + data.nickName + '</td>'));
                    html.append($('<td style="text-align: left;"><img id="usersAvatar" email="' + data.user.userEmail + '"/>' + data.user.userName + '</td>'));
                    html.append($('<td>' + data.solved + '</td>'));
                    html.append($('<td>' + data.penalty + '</td>'));
                    tbody.append(html);
                });

                // get userList avatars
                $('img#usersAvatar').setAvatar({
                    size: 37,
                    image: 'http://www.acm.uestc.edu.cn/images/akari_small.jpg'
                });
            }
            else {
                $('#fileUploaderAttention').empty();
                $('#fileUploaderAttention').append(response.error);
            }
        });

    $('input#submit').click(function(){
        return false;
    });
});