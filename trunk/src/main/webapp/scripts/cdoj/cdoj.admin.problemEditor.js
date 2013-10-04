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
  "description":undefined,
  "input":undefined,
  "output":undefined,
  "sampleInput":undefined,
  "sampleOutput":undefined,
  "hint":undefined
};

var problemDTO = {
  "problemId":null,
  "title":undefined,
  "description":undefined,
  "input":undefined,
  "output":undefined,
  "sampleInput":undefined,
  "sampleOutput":undefined,
  "hint":undefined,
  "source":undefined
};

var problemId;

//TODO clean the styles paste into editor

$(document).ready(function () {

  problemId = $('#problemId')[0].innerHTML;

  $.each(editors,function(editorId) {
    epicEditorOpts.container = editorId;
    epicEditorOpts.uploadUrl = '/admin/problem/uploadProblemPicture/' + problemId;
    epicEditorOpts.pictureListUrl = '/admin/problem/getUploadedPictures/' + problemId;
    epicEditorOpts.file.name = editorId+problemId;
    var oldContent = $('#'+editorId)[0].innerHTML.toString();
    oldContent = js.lang.String.decodeHtml(oldContent);
    editors[editorId] = new EpicEditor(epicEditorOpts).load();
    editors[editorId].importFile(epicEditorOpts.file.name,oldContent);
  });

  $('input#submit').click(function () {
    problemDTO['problemId'] = problemId;
    problemDTO['title'] = $('#title').val();
    problemDTO['source'] = $('#source').val();
    $.each(editors,function(editorId) {
      problemDTO[editorId] = this.exportFile();
    });
    jsonPost('/admin/problem/edit',problemDTO,function(data) {
      $('#problemEditor').formValidate({
        result: data,
        onSuccess: function(){
          alert('Successful!');
          $.each(editors,function() {
            this.remove(this.settings.file.name);
          });
          window.location.href= '/admin/problem/list';
        },
        onFail: function(){
          $('html,body').animate({scrollTop: '0px'}, 400);
        }
      });
    });
    return false;
  });
});