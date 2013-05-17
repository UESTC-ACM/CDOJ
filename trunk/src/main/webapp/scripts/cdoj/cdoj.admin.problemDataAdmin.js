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
 * All function used in problem data admin page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

var problemId;
var uploaderUrl;
var updateUrl;

$(document).ready(function () {
    problemId = $('#problemId')[0].innerHTML;
    uploaderUrl = '/admin/problem/uploadProblemDataFile/'+problemId;
    updateUrl = '/admin/problem/updateProblemData/'+problemId;

    $('#fileUploader').fineUploader({
        request: {
            endpoint: uploaderUrl,
            inputName: 'uploadFile'
        },
        validation: {
            allowedExtensions: ['zip'],
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
            if (response.success == 'true')
                $('#fileUploaderAttention').replaceWith('Total data: '+response.total);
            else
                $('#fileUploaderAttention').replaceWith(response.error);
    });

    $('input#submit').click(function(){
        var problemDataDTO = $('#problemDataEditor').getFormData();
        problemDataDTO["problemDataDTO.problemId"] = problemId;
        console.log(problemDataDTO);
        $.post(updateUrl, problemDataDTO, function(data) {
            $('#problemDataEditor').checkValidate({
                result: data,
                onSuccess: function(){
                    alert('Successful!');
                    window.location.href= '/admin/problem/list';
                }
            })
        });
        return false;
    });
});