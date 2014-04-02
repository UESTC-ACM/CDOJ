/* globals qq */
/**
 * Common APIs exposed to creators of upload via form/iframe handlers.  This is reused and possibly overridden
 * in some cases by specific form upload handlers.
 *
 * @constructor
 */
qq.AbstractUploadHandlerForm = function(spec) {
    "use strict";

    var options = spec.options,
        handler = this,
        proxy = spec.proxy,
        formHandlerInstanceId = qq.getUniqueId(),
        onloadCallbacks = {},
        detachLoadEvents = {},
        postMessageCallbackTimers = {},
        isCors = options.isCors,
        fileState = {},
        inputName = options.inputName,
        onCancel = proxy.onCancel,
        onUuidChanged = proxy.onUuidChanged,
        getName = proxy.getName,
        getUuid = proxy.getUuid,
        log = proxy.log,
        corsMessageReceiver = new qq.WindowReceiveMessage({log: log});


    /**
     * Remove any trace of the file from the handler.
     *
     * @param id ID of the associated file
     */
    function expungeFile(id) {
        delete detachLoadEvents[id];
        delete fileState[id];

        // If we are dealing with CORS, we might still be waiting for a response from a loaded iframe.
        // In that case, terminate the timer waiting for a message from the loaded iframe
        // and stop listening for any more messages coming from this iframe.
        if (isCors) {
            clearTimeout(postMessageCallbackTimers[id]);
            delete postMessageCallbackTimers[id];
            corsMessageReceiver.stopReceivingMessages(id);
        }

        var iframe = document.getElementById(handler._getIframeName(id));
        if (iframe) {
            // To cancel request set src to something else.  We use src="javascript:false;"
            // because it doesn't trigger ie6 prompt on https
            iframe.setAttribute("src", "java" + String.fromCharCode(115) + "cript:false;"); //deal with "JSLint: javascript URL" warning, which apparently cannot be turned off

            qq(iframe).remove();
        }
    }

    /**
     * If we are in CORS mode, we must listen for messages (containing the server response) from the associated
     * iframe, since we cannot directly parse the content of the iframe due to cross-origin restrictions.
     *
     * @param iframe Listen for messages on this iframe.
     * @param callback Invoke this callback with the message from the iframe.
     */
    function registerPostMessageCallback(iframe, callback) {
        var iframeName = iframe.id,
            fileId = getFileIdForIframeName(iframeName),
            uuid = getUuid(fileId);

        onloadCallbacks[uuid] = callback;

        // When the iframe has loaded (after the server responds to an upload request)
        // declare the attempt a failure if we don't receive a valid message shortly after the response comes in.
        detachLoadEvents[fileId] = qq(iframe).attach("load", function() {
            if (fileState[fileId].input) {
                log("Received iframe load event for CORS upload request (iframe name " + iframeName + ")");

                postMessageCallbackTimers[iframeName] = setTimeout(function() {
                    var errorMessage = "No valid message received from loaded iframe for iframe name " + iframeName;
                    log(errorMessage, "error");
                    callback({
                        error: errorMessage
                    });
                }, 1000);
            }
        });

        // Listen for messages coming from this iframe.  When a message has been received, cancel the timer
        // that declares the upload a failure if a message is not received within a reasonable amount of time.
        corsMessageReceiver.receiveMessage(iframeName, function(message) {
            log("Received the following window message: '" + message + "'");
            var fileId = getFileIdForIframeName(iframeName),
                response = handler._parseJsonResponse(fileId, message),
                uuid = response.uuid,
                onloadCallback;

            if (uuid && onloadCallbacks[uuid]) {
                log("Handling response for iframe name " + iframeName);
                clearTimeout(postMessageCallbackTimers[iframeName]);
                delete postMessageCallbackTimers[iframeName];

                handler._detachLoadEvent(iframeName);

                onloadCallback = onloadCallbacks[uuid];

                delete onloadCallbacks[uuid];
                corsMessageReceiver.stopReceivingMessages(iframeName);
                onloadCallback(response);
            }
            else if (!uuid) {
                log("'" + message + "' does not contain a UUID - ignoring.");
            }
        });
    }

    /**
     * Generates an iframe to be used as a target for upload-related form submits.  This also adds the iframe
     * to the current `document`.  Note that the iframe is hidden from view.
     *
     * @param name Name of the iframe.
     * @returns {HTMLIFrameElement} The created iframe
     */
    function initIframeForUpload(name) {
        var iframe = qq.toElement("<iframe src='javascript:false;' name='" + name + "' />");

        iframe.setAttribute("id", name);

        iframe.style.display = "none";
        document.body.appendChild(iframe);

        return iframe;
    }

    /**
     * @param iframeName `document`-unique Name of the associated iframe
     * @returns {*} ID of the associated file
     */
    function getFileIdForIframeName(iframeName) {
        return iframeName.split("_")[0];
    }


    qq.extend(this, {
        add: function(id, fileInput) {
            fileState[id] = {input: fileInput};

            fileInput.setAttribute("name", inputName);

            // remove file input from DOM
            if (fileInput.parentNode){
                qq(fileInput).remove();
            }
        },

        getInput: function(id) {
            return fileState[id].input;
        },

        isValid: function(id) {
            return fileState[id] !== undefined &&
                fileState[id].input !== undefined;
        },

        reset: function() {
            fileState.length = 0;
        },

        expunge: function(id) {
            return expungeFile(id);
        },

        cancel: function(id) {
            var onCancelRetVal = onCancel(id, getName(id));

            if (onCancelRetVal instanceof qq.Promise) {
                return onCancelRetVal.then(function() {
                    this.expunge(id);
                });
            }
            else if (onCancelRetVal !== false) {
                this.expunge(id);
                return true;
            }

            return false;
        },

        upload: function(id) {
            // implementation-specific
        },

        /**
         * @param fileId ID of the associated file
         * @returns {string} The `document`-unique name of the iframe
         */
        _getIframeName: function(fileId) {
            return fileId + "_" + formHandlerInstanceId;
        },

        /**
         * Creates an iframe with a specific document-unique name.
         *
         * @param id ID of the associated file
         * @returns {HTMLIFrameElement}
         */
        _createIframe: function(id) {
            var iframeName = handler._getIframeName(id);

            return initIframeForUpload(iframeName);
        },

        /**
         * @param id ID of the associated file
         * @param innerHtmlOrMessage JSON message
         * @returns {*} The parsed response, or an empty object if the response could not be parsed
         */
        _parseJsonResponse: function(id, innerHtmlOrMessage) {
            var response;

            try {
                response = qq.parseJson(innerHtmlOrMessage);

                if (response.newUuid !== undefined) {
                    onUuidChanged(id, response.newUuid);
                }
            }
            catch(error) {
                log("Error when attempting to parse iframe upload response (" + error.message + ")", "error");
                response = {};
            }

            return response;
        },

        /**
         * Generates a form element and appends it to the `document`.  When the form is submitted, a specific iframe is targeted.
         * The name of the iframe is passed in as a property of the spec parameter, and must be unique in the `document`.  Note
         * that the form is hidden from view.
         *
         * @param spec An object containing various properties to be used when constructing the form.  Required properties are
         * currently: `method`, `endpoint`, `params`, `paramsInBody`, and `targetName`.
         * @returns {HTMLFormElement} The created form
         */
        _initFormForUpload: function(spec) {
            var method = spec.method,
                endpoint = spec.endpoint,
                params = spec.params,
                paramsInBody = spec.paramsInBody,
                targetName = spec.targetName,
                form = qq.toElement("<form method='" + method + "' enctype='multipart/form-data'></form>"),
                url = endpoint;

            if (paramsInBody) {
                qq.obj2Inputs(params, form);
            }
            else {
                url = qq.obj2url(params, endpoint);
            }

            form.setAttribute("action", url);
            form.setAttribute("target", targetName);
            form.style.display = "none";
            document.body.appendChild(form);

            return form;
        },

        /**
         * This function either delegates to a more specific message handler if CORS is involved,
         * or simply registers a callback when the iframe has been loaded that invokes the passed callback
         * after determining if the content of the iframe is accessible.
         *
         * @param iframe Associated iframe
         * @param callback Callback to invoke after we have determined if the iframe content is accessible.
         */
        _attachLoadEvent: function(iframe, callback) {
            /*jslint eqeq: true*/
            var responseDescriptor;

            if (isCors) {
                registerPostMessageCallback(iframe, callback);
            }
            else {
                detachLoadEvents[iframe.id] = qq(iframe).attach("load", function(){
                    log("Received response for " + iframe.id);

                    // when we remove iframe from dom
                    // the request stops, but in IE load
                    // event fires
                    if (!iframe.parentNode){
                        return;
                    }

                    try {
                        // fixing Opera 10.53
                        if (iframe.contentDocument &&
                            iframe.contentDocument.body &&
                            iframe.contentDocument.body.innerHTML == "false"){
                            // In Opera event is fired second time
                            // when body.innerHTML changed from false
                            // to server response approx. after 1 sec
                            // when we upload file with iframe
                            return;
                        }
                    }
                    catch (error) {
                        //IE may throw an "access is denied" error when attempting to access contentDocument on the iframe in some cases
                        log("Error when attempting to access iframe during handling of upload response (" + error.message + ")", "error");
                        responseDescriptor = {success: false};
                    }

                    callback(responseDescriptor);
                });
            }
        },

        /**
         * Called when we are no longer interested in being notified when an iframe has loaded.
         *
         * @param id Associated file ID
         */
        _detachLoadEvent: function(id) {
            if (detachLoadEvents[id] !== undefined) {
                detachLoadEvents[id]();
                delete detachLoadEvents[id];
            }
        },

        _getFileState: function(id) {
            return fileState[id];
        }
    });
};
