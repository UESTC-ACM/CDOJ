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
 * validation!
 * @param the form need validate
 * @param result from server
 * @return {Boolean}, if pass validate, return true.
 */
function validation(form, result) {

    if (result["result"] != null) {
        //has result! no field error!
        if (result["result"] != "ok") //unknow error
        {
            alert(result["error_msg"]);
            return false;
        }
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

!function ($) {

    $(function () {

        // make code pretty
        window.prettyPrint && prettyPrint();
        // get avatars
        $('img#userAvatar').setAvatar();

        $('#activityCarousel').carousel();

        $('.info-problem-source').tooltip();

        $("#registerModal").setDialog({
            callback: function(e) {
                info=$("#registerModal").find(".form-horizontal").serializeArray();
                $.post('/user/register', info, function(data) {
                    $("#registerModal").find(".form-horizontal").checkValidate({
                        result: data,
                        onSuccess: function(e) {
                            $("#registerModal").modal('hide');
                            window.location.reload();
                        }
                    });
                });
            }
        });

        $("#loginModal").setDialog({
            callback: function(e) {
                info=$("#loginModal").find(".form-horizontal").serializeArray();
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

        $("#logoutButton").setButton({
            callback: function(e) {
                $.post('/user/logout',function(data) {
                    if (data["result"] == "ok")
                        window.location.reload();
                });
            }
        });
    })
}(window.jQuery)

