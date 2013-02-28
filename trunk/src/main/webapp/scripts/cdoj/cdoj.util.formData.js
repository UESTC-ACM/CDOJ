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
 * Get and set form data.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

(function($) {
    'use strict';

    /**
     * Default extraction
     *
     * @param input A validate jQuery :input node.
     * @return {*} The data in this input node.
     */
    var defaultExtraction = function(input) {
        return input.val();
    };

    /**
     * All extractions
     *
     * @type {{}}
     */
    var dataExtractions = {
        radio: function(input) {
            var result = $(':radio[name="'+input.attr('name')+'"]:checked').val();
            if (result == 'all')
                result = undefined;
            return result;
        }
    };

    /**
     * Extract data from :input node.
     *
     * @param input
     * @return {*}
     */
    function extractData(input) {
        var result = defaultExtraction(input);
        $.each(dataExtractions,function(index,extraction){
            if (index == input.attr('type'))
                result = extraction(input);
        });
        return result;
    }

    /**
     * Unused node list
     *
     * @type {Array}
     */
    var ignoreList = ['submit','reset','button','image','file'];

    /**
     * Ignore some unused :input node
     *
     * @param input
     * @return {*}
     */
    function ignore(input) {
        return $.inArray(input.attr('type'),ignoreList);
    }

    /**
     * Get selected form's all data field.
     */
    $.fn.getFormData = function() {
        var inputs = $(this).find(':input');
        var result = {};

        $.each(inputs,function(){
            var input = $(this);
            if (ignore(input) == -1)
                result[input.attr('name')] = extractData(input);
        });
        return result;
    };

    /**
     * Set data into selected form.
     *
     * @param data
     */
    $.fn.setFormData = function(data) {
        $.each(data,function(index,value) {

        });
    }

}(jQuery));