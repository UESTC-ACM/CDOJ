/**
 * Javascript for user center page.
 */

var currentUserPageUser;

$(document).ready(function () {
  $('img#userAvatar-large').setAvatar({
    image: 'http://www.acm.uestc.edu.cn/images/akari.jpg'
  });
  currentUserPageUser = $('#currentUserPageUser').attr('value');
  if (currentUser == currentUserPageUser) {
    var $userAvatarWrap = $('#userAvatarWarp');
    $userAvatarWrap.tooltip({
      placement: 'bottom'
    });
    $userAvatarWrap.attr('href', 'http://gravatar.com/emails/');

    $('#userEditModal').setDialog({
      callback: function () {
        var info = $('#userEditModal').getFormData();
        if (info["password"] == '')
          info["password"] = info["passwordRepeat"] = undefined;
        jsonPost('/user/edit', info, function (data) {
          console.log(data);
          $('#userEditModal').find('.form-horizontal').formValidate({
            result: data,
            onSuccess: function () {
              alert('Edit profile successful!');
              window.location.reload();
            }
          });
        });
      }
    });
  }

  $.post('/user/problemStatus/' + currentUserPageUser, function (data) {
    var problemStatus = data.problemStatus;
    var status = [];
    $.each(problemStatus, function(index, data) {
      if (data == 'PASS')
        status.push({
          problemId: index - 1,
          status: 1
        });
      else if (data == 'FAIL')
        status.push({
          problemId: index - 1,
          status: 2
        });
      else
        status.push({
          problemId: index - 1,
          status: 0
        });
    });
    var problemCount = status.length;
    console.log(status, problemCount);

    var colors = ['#f2eada', '#45b97c', '#aa2116'];
    var margin = { top: 20, right: 0, bottom: 20, left: 0 };
    var width = 769 - margin.left - margin.right;
    var gridSize = Math.floor(width / 15);
    var gridHeight = 30;
    var height = gridHeight * Math.ceil(problemCount / 15);

    var chart = d3.select("#chart").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    var problemLabel = chart.selectAll('.problemLabel')
        .data(status)
        .enter().append("text")
        .text(function(d) { return d.problemId + 1; })
        .attr("x", function (d) {
          return gridSize * (d.problemId % 15 + 0.5);
        })
        .attr("y", function (d) {
          return gridHeight * (Math.floor(d.problemId / 15) + 0.7);
        })
        .style('fill', colors[0])
        .style("text-anchor", "middle")
        .attr('class', 'problemLabel')
        .attr("value", function(d) {
          return d.problemId + 1;
        })
        .transition().duration(2000)
        .style('fill', '#000000');

    var heatMap = chart.selectAll(".problemStatus")
        .data(status)
        .enter().append("rect")
        .attr("x", function (d) {
          return gridSize * (d.problemId % 15);
        })
        .attr("y", function (d) {
          return gridHeight * Math.floor(d.problemId / 15);
        })
        .attr("rx", 4)
        .attr("ry", 4)
        .attr("class", "problemStatus bordered")
        .attr("value", function(d) {
          return d.problemId + 1;
        })
        .attr("width", gridSize)
        .attr("height", gridHeight)
        .style("fill", colors[0])
        .transition().duration(2000)
        .style('fill', function (d) {
          return colors[d.status];
        });

    $('.problemStatus').click(function(){
      var problemId = $(this).attr('value');
      window.location.href = '/problem/show/' + problemId;
    });
  })
});