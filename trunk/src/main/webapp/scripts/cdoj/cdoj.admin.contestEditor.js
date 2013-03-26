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
 * All function used in contest editor page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

var problemListTable;
var lineId;
var addProblemLine =
    '<tr>' +
        '<td><a href="#" class="remove_problem"><i class="icon-minus"></i></a></td>' +
        '<td contenteditable="true" class="problem_id"></td>' +
        '<td class="problem_title"></td>' +
        '<td class="problem_difficult"></td>' +
    '</tr>';

function getDifficulty(difficulty) {
    difficulty = Math.max(1, Math.min(difficulty, 5));
    var html = '';
    for (var i = 1; i <= difficulty; i++)
        html += '<i class="difficulty-level icon-star pull-left" style="margin: 0px;" value="' + i + '"></i>';
    for (var i = difficulty + 1; i <= 5; i++)
        html += '<i class="difficulty-level icon-star-empty pull-left" style="margin: 0px;" value="' + i + '"></i>';
    return html;
}

function removeProblem(id) {
    problemListTable.find('tr[value="'+id+'"]').remove();
}

function updateProblem(id) {
    var line = problemListTable.find('tr[value="'+id+'"]');
    var problemId = line.find('.problem_id')[0].innerHTML;

    var condition = {
        "currentPage": 1,
        "problemCondition.startId": problemId,
        "problemCondition.endId": problemId
    }

    $.post('/admin/problem/search', condition, function (data) {
        var currentProblemId = line.find('.problem_id')[0].innerHTML;
        if (currentProblemId == problemId) {
            line.removeClass('error');
            line.find('.problem_title').empty();
            line.find('.problem_difficult').empty();
            if (data.problemList.length == 0) {
                line.addClass('error');
                line.find('.problem_title').append('No such problem');
            }
            else {
                var problem = data.problemList[0];
                line.find('.problem_title').append(problem.title);
                line.find('.problem_difficult').append(getDifficulty(problem.difficulty));
            }
        }
    });
}

$(document).ready(function () {
    //Date picker
    $('#contestDTO_time_days').datepicker({
        format: 'yyyy-mm-dd'
    })

    //Problem list table
    problemListTable = $('#problemList');

    lineId = 0;
    //Problem add button
    $('#add_problem').setButton({
        callback: function(){
            var line = $(addProblemLine);
            var nowId = lineId;
            lineId++;
            line.attr('value', nowId);
            problemListTable.append(line);
            line = problemListTable.find('tr[value="'+nowId+'"]');

            line.find('.remove_problem').live('click', function(){
                removeProblem(nowId);
                return false;
            });

            line.find('.problem_id').live('keydown', function(){
                if (event.keyCode == 13)
                    return false;
            });
            line.find('.problem_id').live('keyup', function(){
                updateProblem(nowId);
            });

            return false;
        }
    })
});