/**
 * All function used in problem data admin page.
 */

var problemId;
var uploaderUrl;
var updateUrl;

$(document).ready(function () {
  problemId = $('#problemId')[0].innerHTML;
  uploaderUrl = '/admin/problem/uploadProblemDataFile/'+problemId;
  updateUrl = '/admin/problem/updateProblemData';

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
    problemDataDTO["problemId"] = problemId;
    console.log(problemDataDTO);
    jsonPost(updateUrl, problemDataDTO, function(data) {
      $('#problemDataEditor').formValidate({
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