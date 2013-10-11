/**
 * All function used in problem page.
 */

$(document).ready(function () {
  if ($('#problem-show').length === 0) return;

  $('#languageSelector').setButtonRadio({});

  //Only refresh status at status tab
  statusList.autoRefresh('off');
  $('#problem-show-tab').find('a[href="#tab-problem-status"]').on('show.bs.tab', function () {
    statusList.refreshList();
    statusList.autoRefresh('on');
  });
  $('#problem-show-tab').find('a[href!="#tab-problem-status"]').on('show.bs.tab', function () {
    statusList.autoRefresh('off');
  });

  if (currentUser === undefined || currentUser === null) {
    $('a[href="#tab-problem-submit"]')
        .attr('data-toggle', '')
        .parent()
        .addClass('pure-menu-disabled')
        .tooltip({
          title: 'Please login first!'
        });
  } else {
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

      jsonPost('/status/submit', info, function (data) {
        if (data.result == 'error')
          alert(data.error_msg);
        else if (data.result == 'success') {
          $('#problem-show-tab').find('a[href="#tab-problem-status"]').tab('show');

          //TODO change it like PC^2
        } else {
          console.log(data);
          alert('Please login first!');
          //$("#loginModal").modal();
        }
      });
    });
  }

});
