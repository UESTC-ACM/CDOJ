/**
 * All function used in problem page.
 */

$(document).ready(function () {

  var currentProblem = $('#problem_title').attr('value');
  //Blind Submit
  $('#submitCode').click(function () {
    var info = {
      'codeContent': null,
      'languageId': null,
      'contestId': null,
      'problemId': currentProblem
    };
    //Get code content
    info.codeContent = $('#codeContent').val();
    //Get language Id
    info.languageId = $('#languageSelector').find('.active').attr('value');

    console.log(info);
    jsonPost('/status/submit', info, function (data) {
      if (data.result == 'error')
        alert(data.error_msg);
      else if (data.result == 'success') {
        $('#problem-show-tab').find('a[href="#tab-problem-status"]').trigger('click');

        //TODO change it like PC^2
      } else {
        console.log(data);
        alert('Please login first!');
        //$("#loginModal").modal();
      }
    });
  });

});
