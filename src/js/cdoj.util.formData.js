/**
 * Get and set form data.
 */

(function ($) {
    'use strict';

    /**
     * Default extraction
     *
     * @param input A validate jQuery :input node.
     * @return {*} The data in this input node.
     */
    var defaultExtraction = function (input) {
        return input.val();
    };

    /**
     * Default setter
     *
     * @param input
     * @param data
     */
    var defaultSetter = function (input, data) {
        if (data === undefined)
            data = '';
        input.attr('value', data);
    };

    /**
     * All extractions
     *
     * @type {{}}
     */
    var dataExtractions = {
        radio: function (input) {
            var result = $(':radio[name="' + input.attr('name') + '"]:checked').val();
            if (result == 'all')
                result = undefined;
            return result;
        },
        select: function (input) {
            var result = input.val();
            if (result == -1)
                result = undefined;
            return result;
        }
    };

    /**
     * All setters
     *
     * @type {{radio: Function}}
     */
    var dataSetter = {
        radio: function (input, data) {
            if (data === undefined)
                data = 'all';
            if (input.attr('value') == data)
                input.attr('checked',true);
            else
                input.attr('checked',false);
        },
        select: function (input, data) {
            if (data === undefined)
                data = -1;
            input.attr('value',data);
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
        $.each(dataExtractions, function (index, extraction) {
            if (index == input.attr('type') ||
                index == input[0].localName)
                result = extraction(input);
        });
        return result;
    }

    /**
     * Set data
     *
     * @param input
     * @param value
     */
    function setData(input, value) {
        if (input === null || input.length === 0)
            return;
        var hasSet = false;
        $.each(dataSetter, function (index, setter) {
            if (index == input.attr('type') ||
                index == input[0].localName) {
                setter(input, value);
                hasSet = true;
            }
        });
        if (hasSet === false)
            defaultSetter(input, value);
    }

    /**
     * Unused node list
     *
     * @type {Array}
     */
    var ignoreList = ['submit', 'reset', 'button', 'image', 'file'];

    /**
     * Ignore some unused :input node
     *
     * @param input
     * @return {*}
     */
    function ignore(input) {
        return $.inArray(input.attr('type'), ignoreList);
    }

    /**
     * Get selected form's all data field.
     */
    $.fn.getFormData = function () {
        var inputs = $(this).find(':input');
        var result = {};

        $.each(inputs, function () {
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
    $.fn.setFormData = function (data) {
        var form = $(this);
        $.each(data, function (index, value) {
            var input = form.find(':input[name="' + index + '"]');
            if (ignore(input) == -1)
                if (input !== null)
                    setData(input, value);
        });
    };

    $.fn.resetFormData = function () {
        var inputs = $(this).find(':input');

        $.each(inputs, function () {
            var input = $(this);
            if (ignore(input) == -1)
                if (input !== null)
                    setData(input, undefined);
        });
    };

}(jQuery));
