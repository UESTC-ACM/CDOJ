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
 * Javascript for all page.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

!function ($) {

    $(function () {

        // make code pretty
        window.prettyPrint && prettyPrint();

        // get avatars
        $('img#userAvatar').setAvatar({});

        $("#registerModal").setDialog({
            callback: function() {
                info=$("#registerModal").find(".form-horizontal").getFormData();
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

        $("#logoutButton").setButton({
            callback: function() {
                $.post('/user/logout',function(data) {
                    if (data["result"] == "ok")
                        window.location.reload();
                });
            }
        });
    })
}(window.jQuery)

