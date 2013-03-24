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
var addProblemLine =
    '<tr>' +
        '<td><a href="#" class="remove_problem"><i class="icon-minus"></i></a></td>' +
        '<td contenteditable="true" class="problem_id"></td>' +
        '<td class="problem_title"></td>' +
        '<td class="problem_difficult"></td>' +
    '</tr>';

function removeProblem(id) {
    problemListTable.find('tr[value="'+id+'"]').remove();
    $.each(problemListTable.find('tr'), function(){
        var myId = $(this).attr('value');
        if (myId > id)
            $(this).attr('value', myId-1);
    });
}

function updateProblem(id) {
    var line = problemListTable.find('tr[value="'+id+'"]');
    //var problemId = line.find('problem_id')[0].innerHTML.toString();
    line.find('.problem_title').append(id+" --> ");
}

$(document).ready(function () {
    //Date picker
    $('#contestDTO_time_days').datepicker({
        format: 'yyyy-mm-dd'
    })

    //Problem list table
    problemListTable = $('#problemList');

    //Problem add button
    $('#add_problem').setButton({
        callback: function(){
            var lineId = problemListTable.find('tr').size();
            var line = $(addProblemLine);
            line.attr('value', lineId);
            problemListTable.append(line);
            line = problemListTable.find('tr[value="'+lineId+'"]');

            line.find('.remove_problem').live('click', function(){
                console.log(line.attr('value'));
                removeProblem(line.attr('value'));
                return false;
            });

            line.find('.problem_id').live('keydown', function(){
                if (event.keyCode == 13) {
                    console.log(line.attr('value'));
                    updateProblem(line.attr('value'));
                    return false;
                }
            });
            return false;
        }
    })
});