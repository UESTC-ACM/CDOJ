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

//
// 都要用到的js都在这里
//

/**
 * validation!
 * @param the form need validate
 * @param result from server
 * @return {Boolean}, if pass validate, return true.
 */
function validation(form, result) {

    if (result["result"] != null) {
        //has result! no field error!
        if (result["result"] != "ok") //unknow error
            alert(result["result"]);
        return true;
    }
    else {
        //field error!

        // Clear existing errors on submit
        form.find("div.error").removeClass("error");
        form.find("span.s2_help_inline").remove();
        form.find("div.s2_validation_errors").remove();

        $.each(result, function(index, value) {
            var element = form.find(":input[name=\"" + index + "\"]"), controlGroup, controls;
            if (element && element.length > 0) {

                // select first element
                element = $(element[0]);
                controlGroup = element.closest("div.control-group");
                controlGroup.addClass('error');
                controls = controlGroup.find("div.controls");
                if (controls) {
                    controls.append("<span class='help-inline s2_help_inline'>" + value[0] + "</span>");
                }
            }
        });
        return false;
    }
}

/**
 * Show a dialog ONLY have one primary button
 * @param dialog
 * @param callback
 * @constructor
 */
function Dialog(dialog, callback) {
    dialog.on("show", function() {    // wire up the button
        $("a.btn-primary").on("click", callback);
    });
    dialog.on("hide", function() {    // remove the event listeners when the dialog is dismissed
        $("a.btn-primary").off("click");
    });
}

/**
 * Button click event!
 * @param button
 * @param callback
 * @constructor
 */
function Button(button,callback) {
    button.on("click",callback);
}

!function ($) {

    $(function () {

        // make code pretty
        window.prettyPrint && prettyPrint();

        // 主页的slide
        $('#activityCarousel').carousel();

        //题目列表来源弹出效果
        $('.info-problem-source').tooltip();

        Dialog($("#registerModal"),function(e) {
            info=$("#registerModal .form-horizontal").serializeArray();
            $.post('/user/register', info, function(data) {
                if (validation($("#registerModal .form-horizontal"),data) == true) {
                    $("#registerModal").modal('hide');
                    window.location.reload();
                }
            });
        });

        Dialog($("#loginModal"),function(e) {
            info=$(".form-horizontal").serializeArray();
            $.post('/user/login', info, function(data) {
                if (validation($(".form-horizontal"),data) == true) {
                    $("#loginModal").modal('hide');
                    window.location.reload();
                }
            });
        });

        Button($("#logoutButton"),function(e) {
            $.post('/user/logout',function(data) {
                if (data["result"] == "ok")
                    window.location.reload();
            });
        });
    })
}(window.jQuery)

