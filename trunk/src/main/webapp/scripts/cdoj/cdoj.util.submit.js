/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

/**
 * Blind marked to jQuery node element.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

function setSubmitDialog() {
    $('#problemSubmitModal').setDialog({
        callback: function () {
            info = {
                'codeContent': null,
                'languageId': null,
                'contestId': null,
                'problemId': null
            }
            //Get code content
            info['codeContent'] = $('#codeContent').val();
            //Get language Id
            info['languageId'] = $('#languageSelector').find('.active').attr('value');
            //Get contest Id

            //Get problem Id
            info['problemId'] = $('#submitProblemId').attr('value');

            $.post('/status/submit', info, function (data) {
                if (data.result == 'error')
                    alert(data.error_msg);
                else if (data.result == 'ok')
                    window.location.href= '/status/index';
                else {
                    alert('Please login first!');
                    $("#problemSubmitModal").modal('hide');
                    $("#loginModal").modal();
                }
            });
        }
    });
}