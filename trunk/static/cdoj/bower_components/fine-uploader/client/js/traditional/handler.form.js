/*globals qq*/
/**
 * Upload handler used that assumes the current user agent does not have any support for the
 * File API, and, therefore, makes use of iframes and forms to submit the files directly to
 * a generic server.
 *
 * @param options Options passed from the base handler
 * @param proxy Callbacks & methods used to query for or push out data/changes
 */
qq.UploadHandlerForm = function(options, proxy) {
    "use strict";

    var handler = this,
        uploadCompleteCallback = proxy.onUploadComplete,
        onUuidChanged = proxy.onUuidChanged,
        getName = proxy.getName,
        getUuid = proxy.getUuid,
        uploadComplete = uploadCompleteCallback,
        log = proxy.log;


    /**
     * Returns json object received by iframe from server.
     */
    function getIframeContentJson(id, iframe) {
        /*jshint evil: true*/

        var response;

        //IE may throw an "access is denied" error when attempting to access contentDocument on the iframe in some cases
        try {
            // iframe.contentWindow.document - for IE<7
            var doc = iframe.contentDocument || iframe.contentWindow.document,
                innerHtml = doc.body.innerHTML;

            log("converting iframe's innerHTML to JSON");
            log("innerHTML = " + innerHtml);
            //plain text response may be wrapped in <pre> tag
            if (innerHtml && innerHtml.match(/^<pre/i)) {
                innerHtml = doc.body.firstChild.firstChild.nodeValue;
            }

            response = handler._parseJsonResponse(id, innerHtml);
        }
        catch(error) {
            log("Error when attempting to parse form upload response (" + error.message + ")", "error");
            response = {success: false};
        }

        return response;
    }

    /**
     * Creates form, that will be submitted to iframe
     */
    function createForm(id, iframe){
        var params = options.paramsStore.get(id),
            method = options.demoMode ? "GET" : "POST",
            endpoint = options.endpointStore.get(id),
            name = getName(id);

        params[options.uuidName] = getUuid(id);
        params[options.filenameParam] = name;

        return handler._initFormForUpload({
            method: method,
            endpoint: endpoint,
            params: params,
            paramsInBody: options.paramsInBody,
            targetName: iframe.name
        });
    }

    qq.extend(this, new qq.AbstractUploadHandlerForm({
            options: {
                isCors: options.cors.expected,
                inputName: options.inputName
            },
        
            proxy: {
                onCancel: options.onCancel,
                onUuidChanged: onUuidChanged,
                getName: getName,
                getUuid: getUuid,
                log: log
            }
        }
    ));

    qq.extend(this, {
        upload: function(id) {
            var input = handler._getFileState(id).input,
                fileName = getName(id),
                iframe = handler._createIframe(id),
                form;

            if (!input){
                throw new Error("file with passed id was not added, or already uploaded or canceled");
            }

            options.onUpload(id, getName(id));

            form = createForm(id, iframe);
            form.appendChild(input);

            handler._attachLoadEvent(iframe, function(responseFromMessage){
                log("iframe loaded");

                var response = responseFromMessage ? responseFromMessage : getIframeContentJson(id, iframe);

                handler._detachLoadEvent(id);

                //we can't remove an iframe if the iframe doesn't belong to the same domain
                if (!options.cors.expected) {
                    qq(iframe).remove();
                }

                if (!response.success) {
                    if (options.onAutoRetry(id, fileName, response)) {
                        return;
                    }
                }
                options.onComplete(id, fileName, response);
                uploadComplete(id);
            });

            log("Sending upload request for " + id);
            form.submit();
            qq(form).remove();
        }
    });
};
