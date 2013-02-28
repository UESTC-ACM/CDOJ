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
 * Validate form data (base on server return result)
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

(function($) {
    'use strict';

    /**
     * Set dialog information.
     *
     * @param userOptions
     */
    $.fn.checkValidate = function(userOptions) {
        var options = mergeOptions({
                result: {"result": "ok"},
                onSuccess: function(e){}
            },
            userOptions);

        var result = options.result;

        $.each(this, function() {
            var self = $(this);

            if (result["result"] != null) {
                //has result! no field error!
                if (result["result"] != "ok") //unknow error
                {
                    alert(result["error_msg"]);
                    return false;
                }
                options.onSuccess();
                return true;
            }
            else {
                // Clear existing errors on submit
                self.find("div.error").removeClass("error");
                self.find("span.s2_help_inline").remove();
                self.find("div.s2_validation_errors").remove();

                $.each(result, function(index, value) {
                    var element = self.find(":input[name=\"" + index + "\"]"), controlGroup, controls;
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
        });
    };

}(jQuery));