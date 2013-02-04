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
 * Created with IntelliJ IDEA.
 * User: mzry1992
 * Date: 13-2-4
 * Time: 下午5:39
 * To change this template use File | Settings | File Templates.
 */

CKEDITOR.disableAutoInline = true;

$(document).ready(function () {
    var editorDescription = CKEDITOR.inline(document.getElementById('problemDTO_description'));
    var editorInput = CKEDITOR.inline(document.getElementById('problemDTO_input'));
    var editorOutput = CKEDITOR.inline(document.getElementById('problemDTO_output'));
    var editorHint = CKEDITOR.inline(document.getElementById('problemDTO_hint'));

    $('input#submit').click(function (e) {
        problemDTO = {
            "problemDTO.sampleInput":$('#problemDTO_sampleInput')[0].innerText,
            "problemDTO.sampleOutput":$('#problemDTO_sampleOutput')[0].innerText
        }
        console.log(problemDTO);
        return false;
    });
});