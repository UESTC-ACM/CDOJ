/*globals qq, XMLHttpRequest*/
qq.DeleteFileAjaxRequester = function(o) {
    "use strict";

    var requester,
        options = {
            method: "DELETE",
            uuidParamName: "qquuid",
            endpointStore: {},
            maxConnections: 3,
            customHeaders: {},
            paramsStore: {},
            demoMode: false,
            cors: {
                expected: false,
                sendCredentials: false
            },
            log: function(str, level) {},
            onDelete: function(id) {},
            onDeleteComplete: function(id, xhrOrXdr, isError) {}
        };

    qq.extend(options, o);

    function getMandatedParams() {
        if (options.method.toUpperCase() === "POST") {
            return {
                "_method": "DELETE"
            };
        }

        return {};
    }

    requester = qq.extend(this, new qq.AjaxRequester({
        validMethods: ["POST", "DELETE"],
        method: options.method,
        endpointStore: options.endpointStore,
        paramsStore: options.paramsStore,
        mandatedParams: getMandatedParams(),
        maxConnections: options.maxConnections,
        customHeaders: options.customHeaders,
        demoMode: options.demoMode,
        log: options.log,
        onSend: options.onDelete,
        onComplete: options.onDeleteComplete,
        cors: options.cors
    }));


    qq.extend(this, {
        sendDelete: function(id, uuid, additionalMandatedParams) {
            var additionalOptions = additionalMandatedParams || {};

            options.log("Submitting delete file request for " + id);

            if (options.method === "DELETE") {
                requester.initTransport(id)
                    .withPath(uuid)
                    .withParams(additionalOptions)
                    .send();
            }
            else {
                additionalOptions[options.uuidParamName] = uuid;
                requester.initTransport(id)
                    .withParams(additionalOptions)
                    .send();
            }
        }
    });
};
