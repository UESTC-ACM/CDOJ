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
 * All function used in problem page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

$(document).ready(function () {

    markdown();

    $.each($('.sample'),function(){
        var md = $(this).html()
            .replace('<textarea>','')
            .replace('</textarea>','')
            .replace('<TEXTAREA>','')
            .replace('</TEXTAREA>','')
            .replace('\r','<br/>');
        $(this).empty().append(md);
    });

    // make code pretty
    prettify();

    // make sample input and output have the same height
    var height = 0;
    $.each($('.sample'),function(){
        height = Math.max(height,$(this).height());
    });
    $.each($('.sample'),function(){
        $(this).css('height',height);
    })

    //wired up problem submit modal
    $("#problemSubmitModal").setDialog({
        callback: function() {
            info=$("#loginModal").find(".form-horizontal").getFormData();
            $.post('/user/login', info, function(data) {
                $("#loginModal").find(".form-horizontal").checkValidate({
                    result: data,
                    onSuccess: function(e) {
                        $("#loginModal").modal('hide');
                        window.location.reload();
                    }
                });
            });
        }
    });

    MathJax.Hub.Config({tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']],
        displayMath: [['\\[','\\]'], ['$$','$$']]}});
    MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
});
