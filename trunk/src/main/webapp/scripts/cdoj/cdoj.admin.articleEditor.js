/**
 * All function used in problem statement editor page.
 */

var epicEditorOpts = {
  container : 'epiceditor',
  basePath : '/plugins/epiceditor',
  clientSideStorage : false,
  localStorageName : 'epiceditor',
  useNativeFullsreen : true,
  parser : marked,
  file : {
    name : 'epiceditor',
    defaultContent : '',
    autoSave : 100
  },
  theme : {
    base : '/themes/base/epiceditor.css',
    preview : '/themes/preview/github.css',
    editor : '/themes/editor/epic-light.css'
  },
  focusOnLoad : true,
  shortcut : {
    modifier : 18,
    fullscreen : 70,
    preview : 80
  }
};

var editors = {
  "content" : undefined
};

var articleDTO = {
  "articleId" : null,
  "title" : undefined,
  "author" : undefined,
  "content" : undefined
};

var articleId;
var action;
// TODO clean the styles paste into editor

$(document).ready(
    function() {

      articleId = $('#articleId')[0].innerHTML;
      action = $('#articleId').attr('type');
      if (action == 'new')
        articleId = 'new';
      
      $.each(editors, function(editorId) {
        epicEditorOpts.container = editorId;
        epicEditorOpts.uploadUrl = '/picture/uploadProblemPicture/';
        epicEditorOpts.pictureListUrl = '/picture/getUploadedPictures/';
        epicEditorOpts.file.name = editorId + articleId;
        var oldContent = $('#' + editorId)[0].innerHTML.toString();
        oldContent = js.lang.String.decodeHtml(oldContent);
        editors[editorId] = new EpicEditor(epicEditorOpts).load();
        editors[editorId].importFile(epicEditorOpts.file.name, oldContent);
      });

      $('input#submit').click(function() {
        articleDTO['action'] = action;
        if (action == 'new')
          articleDTO['articleId'] = null;
        else
          articleDTO['articleId'] = articleId;
        articleDTO['title'] = $('#title').val();
        articleDTO['author'] = $('#author').val();
        $.each(editors, function(editorId) {
          articleDTO[editorId] = this.exportFile();
        });
        console.log(articleDTO);
        jsonPost('/admin/article/edit', articleDTO, function(data) {
          $('#articleEditor').formValidate({
            result : data,
            onSuccess : function() {
              alert('Successful!');
              $.each(editors, function() {
                this.remove(this.settings.file.name);
              });
              window.location.href = '/admin/article/list';
            },
            onFail : function() {
              $('html,body').animate({
                scrollTop : '0px'
              }, 400);
            }
          });
        });
        return false;
      });
    });