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
 * Blind dialog to a modal.
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
    $.fn.setDialog = function(userOptions) {
        var options = mergeOptions({
                callback: function() {},
                button: $('a.btn-primary')
            },
            userOptions);

        $.each(this, function() {
            var self = $(this);
            self.on("show", function() {    // wire up the button
                options.button.on("click", options.callback);
            });
            self.on("hide", function() {    // remove the event listeners when the dialog is dismissed
                options.button.off("click");
            });
        });

        return this;
    };

    /**
     * Set button information.
     *
     * @param userOptions
     */
    $.fn.setButton = function(userOptions) {
        var options = mergeOptions({
                callback: function() {}
            },
            userOptions);

        $.each(this, function() {
            $(this).on("click", options.callback);
        });

        return this;
    };

}(jQuery));