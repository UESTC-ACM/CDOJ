/*!
* Fine Uploader
*
* Copyright 2013, Widen Enterprises, Inc. info@fineuploader.com
*
* Version: 4.3.1
*
* Homepage: http://fineuploader.com
*
* Repository: git://github.com/Widen/fine-uploader.git
*
* Licensed under GNU GPL v3, see LICENSE
*/ 


/*globals window, navigator, document, FormData, File, HTMLInputElement, XMLHttpRequest, Blob, Storage, ActiveXObject */
var qq = function(element) {
    "use strict";

    return {
        hide: function() {
            element.style.display = "none";
            return this;
        },

        /** Returns the function which detaches attached event */
        attach: function(type, fn) {
            if (element.addEventListener){
                element.addEventListener(type, fn, false);
            } else if (element.attachEvent){
                element.attachEvent("on" + type, fn);
            }
            return function() {
                qq(element).detach(type, fn);
            };
        },

        detach: function(type, fn) {
            if (element.removeEventListener){
                element.removeEventListener(type, fn, false);
            } else if (element.attachEvent){
                element.detachEvent("on" + type, fn);
            }
            return this;
        },

        contains: function(descendant) {
            // The [W3C spec](http://www.w3.org/TR/domcore/#dom-node-contains)
            // says a `null` (or ostensibly `undefined`) parameter
            // passed into `Node.contains` should result in a false return value.
            // IE7 throws an exception if the parameter is `undefined` though.
            if (!descendant) {
                return false;
            }

            // compareposition returns false in this case
            if (element === descendant) {
                return true;
            }

            if (element.contains){
                return element.contains(descendant);
            } else {
                /*jslint bitwise: true*/
                return !!(descendant.compareDocumentPosition(element) & 8);
            }
        },

        /**
         * Insert this element before elementB.
         */
        insertBefore: function(elementB) {
            elementB.parentNode.insertBefore(element, elementB);
            return this;
        },

        remove: function() {
            element.parentNode.removeChild(element);
            return this;
        },

        /**
         * Sets styles for an element.
         * Fixes opacity in IE6-8.
         */
        css: function(styles) {
            /*jshint eqnull: true*/
            if (element.style == null) {
                throw new qq.Error("Can't apply style to node as it is not on the HTMLElement prototype chain!");
            }

            /*jshint -W116*/
            if (styles.opacity != null){
                if (typeof element.style.opacity !== "string" && typeof(element.filters) !== "undefined"){
                    styles.filter = "alpha(opacity=" + Math.round(100 * styles.opacity) + ")";
                }
            }
            qq.extend(element.style, styles);

            return this;
        },

        hasClass: function(name) {
            var re = new RegExp("(^| )" + name + "( |$)");
            return re.test(element.className);
        },

        addClass: function(name) {
            if (!qq(element).hasClass(name)){
                element.className += " " + name;
            }
            return this;
        },

        removeClass: function(name) {
            var re = new RegExp("(^| )" + name + "( |$)");
            element.className = element.className.replace(re, " ").replace(/^\s+|\s+$/g, "");
            return this;
        },

        getByClass: function(className) {
            var candidates,
                result = [];

            if (element.querySelectorAll){
                return element.querySelectorAll("." + className);
            }

            candidates = element.getElementsByTagName("*");

            qq.each(candidates, function(idx, val) {
                if (qq(val).hasClass(className)){
                    result.push(val);
                }
            });
            return result;
        },

        children: function() {
            var children = [],
                child = element.firstChild;

            while (child){
                if (child.nodeType === 1){
                    children.push(child);
                }
                child = child.nextSibling;
            }

            return children;
        },

        setText: function(text) {
            element.innerText = text;
            element.textContent = text;
            return this;
        },

        clearText: function() {
            return qq(element).setText("");
        },

        // Returns true if the attribute exists on the element
        // AND the value of the attribute is NOT "false" (case-insensitive)
        hasAttribute: function(attrName) {
            var attrVal;

            if (element.hasAttribute) {

                if (!element.hasAttribute(attrName)) {
                    return false;
                }

                /*jshint -W116*/
                return (/^false$/i).exec(element.getAttribute(attrName)) == null;
            }
            else {
                attrVal = element[attrName];

                if (attrVal === undefined) {
                    return false;
                }

                /*jshint -W116*/
                return (/^false$/i).exec(attrVal) == null;
            }
        }
    };
};

(function(){
    "use strict";

    qq.log = function(message, level) {
        if (window.console) {
            if (!level || level === "info") {
                window.console.log(message);
            }
            else
            {
                if (window.console[level]) {
                    window.console[level](message);
                }
                else {
                    window.console.log("<" + level + "> " + message);
                }
            }
        }
    };

    qq.isObject = function(variable) {
        return variable && !variable.nodeType && Object.prototype.toString.call(variable) === "[object Object]";
    };

    qq.isFunction = function(variable) {
        return typeof(variable) === "function";
    };

    /**
     * Check the type of a value.  Is it an "array"?
     *
     * @param value value to test.
     * @returns true if the value is an array or associated with an `ArrayBuffer`
     */
    qq.isArray = function(value) {
        return Object.prototype.toString.call(value) === "[object Array]" ||
            (value && window.ArrayBuffer && value.buffer && value.buffer.constructor === ArrayBuffer);
    };

    // Looks for an object on a `DataTransfer` object that is associated with drop events when utilizing the Filesystem API.
    qq.isItemList = function(maybeItemList) {
        return Object.prototype.toString.call(maybeItemList) === "[object DataTransferItemList]";
    };

    // Looks for an object on a `NodeList` or an `HTMLCollection`|`HTMLFormElement`|`HTMLSelectElement`
    // object that is associated with collections of Nodes.
    qq.isNodeList = function(maybeNodeList) {
        return Object.prototype.toString.call(maybeNodeList) === "[object NodeList]" ||
            // If `HTMLCollection` is the actual type of the object, we must determine this
            // by checking for expected properties/methods on the object
            (maybeNodeList.item && maybeNodeList.namedItem);
    };

    qq.isString = function(maybeString) {
        return Object.prototype.toString.call(maybeString) === "[object String]";
    };

    qq.trimStr = function(string) {
        if (String.prototype.trim) {
            return string.trim();
        }

        return string.replace(/^\s+|\s+$/g,"");
    };


    /**
     * @param str String to format.
     * @returns {string} A string, swapping argument values with the associated occurrence of {} in the passed string.
     */
    qq.format = function(str) {

        var args =  Array.prototype.slice.call(arguments, 1),
            newStr = str,
            nextIdxToReplace = newStr.indexOf("{}");

        qq.each(args, function(idx, val) {
            var strBefore = newStr.substring(0, nextIdxToReplace),
                strAfter = newStr.substring(nextIdxToReplace+2);

            newStr = strBefore + val + strAfter;
            nextIdxToReplace = newStr.indexOf("{}", nextIdxToReplace + val.length);

            // End the loop if we have run out of tokens (when the arguments exceed the # of tokens)
            if (nextIdxToReplace < 0) {
                return false;
            }
        });

        return newStr;
    };

    qq.isFile = function(maybeFile) {
        return window.File && Object.prototype.toString.call(maybeFile) === "[object File]";
    };

    qq.isFileList = function(maybeFileList) {
        return window.FileList && Object.prototype.toString.call(maybeFileList) === "[object FileList]";
    };

    qq.isFileOrInput = function(maybeFileOrInput) {
        return qq.isFile(maybeFileOrInput) || qq.isInput(maybeFileOrInput);
    };

    qq.isInput = function(maybeInput, notFile) {
        var evaluateType = function(type) {
            var normalizedType = type.toLowerCase();

            if (notFile) {
                return normalizedType !== "file";
            }

            return normalizedType === "file";
        };

        if (window.HTMLInputElement) {
            if (Object.prototype.toString.call(maybeInput) === "[object HTMLInputElement]") {
                if (maybeInput.type && evaluateType(maybeInput.type)) {
                    return true;
                }
            }
        }
        if (maybeInput.tagName) {
            if (maybeInput.tagName.toLowerCase() === "input") {
                if (maybeInput.type && evaluateType(maybeInput.type)) {
                    return true;
                }
            }
        }

        return false;
    };

    qq.isBlob = function(maybeBlob) {
        return window.Blob && Object.prototype.toString.call(maybeBlob) === "[object Blob]";
    };

    qq.isXhrUploadSupported = function() {
        var input = document.createElement("input");
        input.type = "file";

        return (
            input.multiple !== undefined &&
                typeof File !== "undefined" &&
                typeof FormData !== "undefined" &&
                typeof (qq.createXhrInstance()).upload !== "undefined" );
    };

    // Fall back to ActiveX is native XHR is disabled (possible in any version of IE).
    qq.createXhrInstance = function() {
        if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        }

        try {
            return new ActiveXObject("MSXML2.XMLHTTP.3.0");
        }
        catch(error) {
            qq.log("Neither XHR or ActiveX are supported!", "error");
            return null;
        }
    };

    qq.isFolderDropSupported = function(dataTransfer) {
        return (dataTransfer.items && dataTransfer.items[0].webkitGetAsEntry);
    };

    qq.isFileChunkingSupported = function() {
        return !qq.android() && //android's impl of Blob.slice is broken
            qq.isXhrUploadSupported() &&
            (File.prototype.slice !== undefined || File.prototype.webkitSlice !== undefined || File.prototype.mozSlice !== undefined);
    };

    qq.sliceBlob = function(fileOrBlob, start, end) {
        var slicer = fileOrBlob.slice || fileOrBlob.mozSlice || fileOrBlob.webkitSlice;

        return slicer.call(fileOrBlob, start, end);
    };

    qq.arrayBufferToHex = function(buffer) {
        var bytesAsHex = "",
            bytes = new Uint8Array(buffer);


        qq.each(bytes, function(idx, byte) {
            var byteAsHexStr = byte.toString(16);

            if (byteAsHexStr.length < 2) {
                byteAsHexStr = "0" + byteAsHexStr;
            }

            bytesAsHex += byteAsHexStr;
        });

        return bytesAsHex;
    };

    qq.readBlobToHex = function(blob, startOffset, length) {
        var initialBlob = qq.sliceBlob(blob, startOffset, startOffset + length),
            fileReader = new FileReader(),
            promise = new qq.Promise();

        fileReader.onload = function() {
            promise.success(qq.arrayBufferToHex(fileReader.result));
        };

        fileReader.readAsArrayBuffer(initialBlob);

        return promise;
    };

    qq.extend = function(first, second, extendNested) {
        qq.each(second, function(prop, val) {
            if (extendNested && qq.isObject(val)) {
                if (first[prop] === undefined) {
                    first[prop] = {};
                }
                qq.extend(first[prop], val, true);
            }
            else {
                first[prop] = val;
            }
        });

        return first;
    };

    /**
     * Allow properties in one object to override properties in another,
     * keeping track of the original values from the target object.
     *
     * Note that the pre-overriden properties to be overriden by the source will be passed into the `sourceFn` when it is invoked.
     *
     * @param target Update properties in this object from some source
     * @param sourceFn A function that, when invoked, will return properties that will replace properties with the same name in the target.
     * @returns {object} The target object
     */
    qq.override = function(target, sourceFn) {
        var super_ = {},
            source = sourceFn(super_);

        qq.each(source, function(srcPropName, srcPropVal) {
            if (target[srcPropName] !== undefined) {
                super_[srcPropName] = target[srcPropName];
            }

            target[srcPropName] = srcPropVal;
        });

        return target;
    };

    /**
     * Searches for a given element in the array, returns -1 if it is not present.
     * @param {Number} [from] The index at which to begin the search
     */
    qq.indexOf = function(arr, elt, from){
        if (arr.indexOf) {
            return arr.indexOf(elt, from);
        }

        from = from || 0;
        var len = arr.length;

        if (from < 0) {
            from += len;
        }

        for (; from < len; from+=1){
            if (arr.hasOwnProperty(from) && arr[from] === elt){
                return from;
            }
        }
        return -1;
    };

    //this is a version 4 UUID
    qq.getUniqueId = function(){
        return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function(c) {
            /*jslint eqeq: true, bitwise: true*/
            var r = Math.random()*16|0, v = c == "x" ? r : (r&0x3|0x8);
            return v.toString(16);
        });
    };

    //
    // Browsers and platforms detection

    qq.ie       = function(){
        return navigator.userAgent.indexOf("MSIE") !== -1;
    };
    qq.ie7      = function(){
        return navigator.userAgent.indexOf("MSIE 7") !== -1;
    };
    qq.ie10     = function(){
        return navigator.userAgent.indexOf("MSIE 10") !== -1;
    };
    qq.ie11     = function(){
        return (navigator.userAgent.indexOf("Trident") !== -1 &&
            navigator.userAgent.indexOf("rv:11") !== -1);
    };
    qq.safari   = function(){
        return navigator.vendor !== undefined && navigator.vendor.indexOf("Apple") !== -1;
    };
    qq.chrome   = function(){
        return navigator.vendor !== undefined && navigator.vendor.indexOf("Google") !== -1;
    };
    qq.opera   = function(){
        return navigator.vendor !== undefined && navigator.vendor.indexOf("Opera") !== -1;
    };
    qq.firefox  = function(){
        return (!qq.ie11() && navigator.userAgent.indexOf("Mozilla") !== -1 && navigator.vendor !== undefined && navigator.vendor === "");
    };
    qq.windows  = function(){
        return navigator.platform === "Win32";
    };
    qq.android = function(){
        return navigator.userAgent.toLowerCase().indexOf("android") !== -1;
    };
    qq.ios7 = function() {
        return qq.ios() && navigator.userAgent.indexOf(" OS 7_") !== -1;
    };
    qq.ios = function() {
        /*jshint -W014 */
        return navigator.userAgent.indexOf("iPad") !== -1
            || navigator.userAgent.indexOf("iPod") !== -1
            || navigator.userAgent.indexOf("iPhone") !== -1;
    };

    //
    // Events

    qq.preventDefault = function(e){
        if (e.preventDefault){
            e.preventDefault();
        } else{
            e.returnValue = false;
        }
    };

    /**
     * Creates and returns element from html string
     * Uses innerHTML to create an element
     */
    qq.toElement = (function(){
        var div = document.createElement("div");
        return function(html){
            div.innerHTML = html;
            var element = div.firstChild;
            div.removeChild(element);
            return element;
        };
    }());

    //key and value are passed to callback for each entry in the iterable item
    qq.each = function(iterableItem, callback) {
        var keyOrIndex, retVal;

        if (iterableItem) {
            // Iterate through [`Storage`](http://www.w3.org/TR/webstorage/#the-storage-interface) items
            if (window.Storage && iterableItem.constructor === window.Storage) {
                for (keyOrIndex = 0; keyOrIndex < iterableItem.length; keyOrIndex++) {
                    retVal = callback(iterableItem.key(keyOrIndex), iterableItem.getItem(iterableItem.key(keyOrIndex)));
                    if (retVal === false) {
                        break;
                    }
                }
            }
            // `DataTransferItemList` & `NodeList` objects are array-like and should be treated as arrays
            // when iterating over items inside the object.
            else if (qq.isArray(iterableItem) || qq.isItemList(iterableItem) || qq.isNodeList(iterableItem)) {
                for (keyOrIndex = 0; keyOrIndex < iterableItem.length; keyOrIndex++) {
                    retVal = callback(keyOrIndex, iterableItem[keyOrIndex]);
                    if (retVal === false) {
                        break;
                    }
                }
            }
            else if (qq.isString(iterableItem)) {
                for (keyOrIndex = 0; keyOrIndex < iterableItem.length; keyOrIndex++) {
                    retVal = callback(keyOrIndex, iterableItem.charAt(keyOrIndex));
                    if (retVal === false) {
                        break;
                    }
                }
            }
            else {
                for (keyOrIndex in iterableItem) {
                    if (Object.prototype.hasOwnProperty.call(iterableItem, keyOrIndex)) {
                        retVal = callback(keyOrIndex, iterableItem[keyOrIndex]);
                        if (retVal === false) {
                            break;
                        }
                    }
                }
            }
        }
    };

    //include any args that should be passed to the new function after the context arg
    qq.bind = function(oldFunc, context) {
        if (qq.isFunction(oldFunc)) {
            var args =  Array.prototype.slice.call(arguments, 2);

            return function() {
                var newArgs = qq.extend([], args);
                if (arguments.length) {
                    newArgs = newArgs.concat(Array.prototype.slice.call(arguments));
                }
                return oldFunc.apply(context, newArgs);
            };
        }

        throw new Error("first parameter must be a function!");
    };

    /**
     * obj2url() takes a json-object as argument and generates
     * a querystring. pretty much like jQuery.param()
     *
     * how to use:
     *
     *    `qq.obj2url({a:'b',c:'d'},'http://any.url/upload?otherParam=value');`
     *
     * will result in:
     *
     *    `http://any.url/upload?otherParam=value&a=b&c=d`
     *
     * @param  Object JSON-Object
     * @param  String current querystring-part
     * @return String encoded querystring
     */
    qq.obj2url = function(obj, temp, prefixDone){
        /*jshint laxbreak: true*/
        var uristrings = [],
            prefix = "&",
            add = function(nextObj, i){
                var nextTemp = temp
                    ? (/\[\]$/.test(temp)) // prevent double-encoding
                    ? temp
                    : temp+"["+i+"]"
                    : i;
                if ((nextTemp !== "undefined") && (i !== "undefined")) {
                    uristrings.push(
                        (typeof nextObj === "object")
                            ? qq.obj2url(nextObj, nextTemp, true)
                            : (Object.prototype.toString.call(nextObj) === "[object Function]")
                            ? encodeURIComponent(nextTemp) + "=" + encodeURIComponent(nextObj())
                            : encodeURIComponent(nextTemp) + "=" + encodeURIComponent(nextObj)
                    );
                }
            };

        if (!prefixDone && temp) {
            prefix = (/\?/.test(temp)) ? (/\?$/.test(temp)) ? "" : "&" : "?";
            uristrings.push(temp);
            uristrings.push(qq.obj2url(obj));
        } else if ((Object.prototype.toString.call(obj) === "[object Array]") && (typeof obj !== "undefined") ) {
            qq.each(obj, function(idx, val) {
                add(val, idx);
            });
        } else if ((typeof obj !== "undefined") && (obj !== null) && (typeof obj === "object")){
            qq.each(obj, function(prop, val) {
                add(val, prop);
            });
        } else {
            uristrings.push(encodeURIComponent(temp) + "=" + encodeURIComponent(obj));
        }

        if (temp) {
            return uristrings.join(prefix);
        } else {
            return uristrings.join(prefix)
                .replace(/^&/, "")
                .replace(/%20/g, "+");
        }
    };

    qq.obj2FormData = function(obj, formData, arrayKeyName) {
        if (!formData) {
            formData = new FormData();
        }

        qq.each(obj, function(key, val) {
            key = arrayKeyName ? arrayKeyName + "[" + key + "]" : key;

            if (qq.isObject(val)) {
                qq.obj2FormData(val, formData, key);
            }
            else if (qq.isFunction(val)) {
                formData.append(key, val());
            }
            else {
                formData.append(key, val);
            }
        });

        return formData;
    };

    qq.obj2Inputs = function(obj, form) {
        var input;

        if (!form) {
            form = document.createElement("form");
        }

        qq.obj2FormData(obj, {
            append: function(key, val) {
                input = document.createElement("input");
                input.setAttribute("name", key);
                input.setAttribute("value", val);
                form.appendChild(input);
            }
        });

        return form;
    };

    qq.setCookie = function(name, value, days) {
        var date = new Date(),
            expires = "";

        if (days) {
            date.setTime(date.getTime()+(days*24*60*60*1000));
            expires = "; expires="+date.toGMTString();
        }

        document.cookie = name+"="+value+expires+"; path=/";
    };

    qq.getCookie = function(name) {
        var nameEQ = name + "=",
            ca = document.cookie.split(";"),
            cookie;

        qq.each(ca, function(idx, part) {
            /*jshint -W116 */
            var cookiePart = part;
            while (cookiePart.charAt(0) == " ") {
                cookiePart = cookiePart.substring(1, cookiePart.length);
            }

            if (cookiePart.indexOf(nameEQ) === 0) {
                cookie = cookiePart.substring(nameEQ.length, cookiePart.length);
                return false;
            }
        });

        return cookie;
    };

    qq.getCookieNames = function(regexp) {
        var cookies = document.cookie.split(";"),
            cookieNames = [];

        qq.each(cookies, function(idx, cookie) {
            cookie = qq.trimStr(cookie);

            var equalsIdx = cookie.indexOf("=");

            if (cookie.match(regexp)) {
                cookieNames.push(cookie.substr(0, equalsIdx));
            }
        });

        return cookieNames;
    };

    qq.deleteCookie = function(name) {
        qq.setCookie(name, "", -1);
    };

    qq.areCookiesEnabled = function() {
        var randNum = Math.random() * 100000,
            name = "qqCookieTest:" + randNum;
        qq.setCookie(name, 1);

        if (qq.getCookie(name)) {
            qq.deleteCookie(name);
            return true;
        }
        return false;
    };

    /**
     * Not recommended for use outside of Fine Uploader since this falls back to an unchecked eval if JSON.parse is not
     * implemented.  For a more secure JSON.parse polyfill, use Douglas Crockford's json2.js.
     */
    qq.parseJson = function(json) {
        /*jshint evil: true*/
        if (window.JSON && qq.isFunction(JSON.parse)) {
            return JSON.parse(json);
        } else {
            return eval("(" + json + ")");
        }
    };

    /**
     * Retrieve the extension of a file, if it exists.
     *
     * @param filename
     * @returns {string || undefined}
     */
    qq.getExtension = function(filename) {
        var extIdx = filename.lastIndexOf(".") + 1;

        if (extIdx > 0) {
            return filename.substr(extIdx, filename.length - extIdx);
        }
    };

    qq.getFilename = function(blobOrFileInput) {
        /*jslint regexp: true*/

        if (qq.isInput(blobOrFileInput)) {
            // get input value and remove path to normalize
            return blobOrFileInput.value.replace(/.*(\/|\\)/, "");
        }
        else if (qq.isFile(blobOrFileInput)) {
            if (blobOrFileInput.fileName !== null && blobOrFileInput.fileName !== undefined) {
                return blobOrFileInput.fileName;
            }
        }

        return blobOrFileInput.name;
    };

    /**
     * A generic module which supports object disposing in dispose() method.
     * */
    qq.DisposeSupport = function() {
        var disposers = [];

        return {
            /** Run all registered disposers */
            dispose: function() {
                var disposer;
                do {
                    disposer = disposers.shift();
                    if (disposer) {
                        disposer();
                    }
                }
                while (disposer);
            },

            /** Attach event handler and register de-attacher as a disposer */
            attach: function() {
                var args = arguments;
                /*jslint undef:true*/
                this.addDisposer(qq(args[0]).attach.apply(this, Array.prototype.slice.call(arguments, 1)));
            },

            /** Add disposer to the collection */
            addDisposer: function(disposeFunction) {
                disposers.push(disposeFunction);
            }
        };
    };
}());

/* globals qq */
/**
 * Fine Uploader top-level Error container.  Inherits from `Error`.
 */
(function() {
    "use strict";

    qq.Error = function(message) {
        this.message = "[Fine Uploader " + qq.version + "] " + message;
    };

    qq.Error.prototype = new Error();
}());

/*global qq */
qq.version="4.3.1";

/* globals qq */
qq.supportedFeatures = (function () {
    "use strict";

    var supportsUploading,
        supportsAjaxFileUploading,
        supportsFolderDrop,
        supportsChunking,
        supportsResume,
        supportsUploadViaPaste,
        supportsUploadCors,
        supportsDeleteFileXdr,
        supportsDeleteFileCorsXhr,
        supportsDeleteFileCors,
        supportsFolderSelection,
        supportsImagePreviews;


    function testSupportsFileInputElement() {
        var supported = true,
            tempInput;

        try {
            tempInput = document.createElement("input");
            tempInput.type = "file";
            qq(tempInput).hide();

            if (tempInput.disabled) {
                supported = false;
            }
        }
        catch (ex) {
            supported = false;
        }

        return supported;
    }

    //only way to test for Filesystem API support since webkit does not expose the DataTransfer interface
    function isChrome21OrHigher() {
        return (qq.chrome() || qq.opera()) &&
            navigator.userAgent.match(/Chrome\/[2][1-9]|Chrome\/[3-9][0-9]/) !== undefined;
    }

    //only way to test for complete Clipboard API support at this time
    function isChrome14OrHigher() {
        return (qq.chrome() || qq.opera()) &&
            navigator.userAgent.match(/Chrome\/[1][4-9]|Chrome\/[2-9][0-9]/) !== undefined;
    }

    //Ensure we can send cross-origin `XMLHttpRequest`s
    function isCrossOriginXhrSupported() {
        if (window.XMLHttpRequest) {
            var xhr = qq.createXhrInstance();

            //Commonly accepted test for XHR CORS support.
            return xhr.withCredentials !== undefined;
        }

        return false;
    }

    //Test for (terrible) cross-origin ajax transport fallback for IE9 and IE8
    function isXdrSupported() {
        return window.XDomainRequest !== undefined;
    }

    // CORS Ajax requests are supported if it is either possible to send credentialed `XMLHttpRequest`s,
    // or if `XDomainRequest` is an available alternative.
    function isCrossOriginAjaxSupported() {
        if (isCrossOriginXhrSupported()) {
            return true;
        }

        return isXdrSupported();
    }

    function isFolderSelectionSupported() {
        // We know that folder selection is only supported in Chrome via this proprietary attribute for now
        return document.createElement("input").webkitdirectory !== undefined;
    }


    supportsUploading = testSupportsFileInputElement();

    supportsAjaxFileUploading = supportsUploading && qq.isXhrUploadSupported();

    supportsFolderDrop = supportsAjaxFileUploading && isChrome21OrHigher();

    supportsChunking = supportsAjaxFileUploading && qq.isFileChunkingSupported();

    supportsResume = supportsAjaxFileUploading && supportsChunking && qq.areCookiesEnabled();

    supportsUploadViaPaste = supportsAjaxFileUploading && isChrome14OrHigher();

    supportsUploadCors = supportsUploading && (window.postMessage !== undefined || supportsAjaxFileUploading);

    supportsDeleteFileCorsXhr = isCrossOriginXhrSupported();

    supportsDeleteFileXdr = isXdrSupported();

    supportsDeleteFileCors = isCrossOriginAjaxSupported();

    supportsFolderSelection = isFolderSelectionSupported();

    supportsImagePreviews = supportsAjaxFileUploading && window.FileReader !== undefined;


    return {
        uploading: supportsUploading,
        ajaxUploading: supportsAjaxFileUploading,
        fileDrop: supportsAjaxFileUploading, //NOTE: will also return true for touch-only devices.  It's not currently possible to accurately test for touch-only devices
        folderDrop: supportsFolderDrop,
        chunking: supportsChunking,
        resume: supportsResume,
        uploadCustomHeaders: supportsAjaxFileUploading,
        uploadNonMultipart: supportsAjaxFileUploading,
        itemSizeValidation: supportsAjaxFileUploading,
        uploadViaPaste: supportsUploadViaPaste,
        progressBar: supportsAjaxFileUploading,
        uploadCors: supportsUploadCors,
        deleteFileCorsXhr: supportsDeleteFileCorsXhr,
        deleteFileCorsXdr: supportsDeleteFileXdr, //NOTE: will also return true in IE10, where XDR is also supported
        deleteFileCors: supportsDeleteFileCors,
        canDetermineSize: supportsAjaxFileUploading,
        folderSelection: supportsFolderSelection,
        imagePreviews: supportsImagePreviews,
        imageValidation: supportsImagePreviews,
        pause: supportsChunking
    };

}());

/*globals qq*/
qq.Promise = function() {
    "use strict";

    var successArgs, failureArgs,
        successCallbacks = [],
        failureCallbacks = [],
        doneCallbacks = [],
        state = 0;

    qq.extend(this, {
        then: function(onSuccess, onFailure) {
            if (state === 0) {
                if (onSuccess) {
                    successCallbacks.push(onSuccess);
                }
                if (onFailure) {
                    failureCallbacks.push(onFailure);
                }
            }
            else if (state === -1) {
                onFailure && onFailure.apply(null, failureArgs);
            }
            else if (onSuccess) {
                onSuccess.apply(null,successArgs);
            }

            return this;
        },

        done: function(callback) {
            if (state === 0) {
                doneCallbacks.push(callback);
            }
            else {
                callback.apply(null, failureArgs === undefined ? successArgs : failureArgs);
            }

            return this;
        },

        success: function() {
            state = 1;
            successArgs = arguments;

            if (successCallbacks.length) {
                qq.each(successCallbacks, function(idx, callback) {
                    callback.apply(null, successArgs);
                });
            }

            if(doneCallbacks.length) {
                qq.each(doneCallbacks, function(idx, callback) {
                    callback.apply(null, successArgs);
                });
            }

            return this;
        },

        failure: function() {
            state = -1;
            failureArgs = arguments;

            if (failureCallbacks.length) {
                qq.each(failureCallbacks, function(idx, callback) {
                    callback.apply(null, failureArgs);
                });
            }

            if(doneCallbacks.length) {
                qq.each(doneCallbacks, function(idx, callback) {
                    callback.apply(null, failureArgs);
                });
            }

            return this;
        }
    });
};

/*globals qq*/

/**
 * This module represents an upload or "Select File(s)" button.  It's job is to embed an opaque `<input type="file">`
 * element as a child of a provided "container" element.  This "container" element (`options.element`) is used to provide
 * a custom style for the `<input type="file">` element.  The ability to change the style of the container element is also
 * provided here by adding CSS classes to the container on hover/focus.
 *
 * TODO Eliminate the mouseover and mouseout event handlers since the :hover CSS pseudo-class should now be
 * available on all supported browsers.
 *
 * @param o Options to override the default values
 */
qq.UploadButton = function(o) {
    "use strict";


    var disposeSupport = new qq.DisposeSupport(),

        options = {
            // "Container" element
            element: null,

            // If true adds `multiple` attribute to `<input type="file">`
            multiple: false,

            // Corresponds to the `accept` attribute on the associated `<input type="file">`
            acceptFiles: null,

            // A true value allows folders to be selected, if supported by the UA
            folders: false,

            // `name` attribute of `<input type="file">`
            name: "qqfile",

            // Called when the browser invokes the onchange handler on the `<input type="file">`
            onChange: function(input) {},

            // **This option will be removed** in the future as the :hover CSS pseudo-class is available on all supported browsers
            hoverClass: "qq-upload-button-hover",

            focusClass: "qq-upload-button-focus"
        },
        input, buttonId;

    // Overrides any of the default option values with any option values passed in during construction.
    qq.extend(options, o);

    buttonId = qq.getUniqueId();

    // Embed an opaque `<input type="file">` element as a child of `options.element`.
    function createInput() {
        var input = document.createElement("input");

        input.setAttribute(qq.UploadButton.BUTTON_ID_ATTR_NAME, buttonId);

        if (options.multiple) {
            input.setAttribute("multiple", "");
        }

        if (options.folders && qq.supportedFeatures.folderSelection) {
            // selecting directories is only possible in Chrome now, via a vendor-specific prefixed attribute
            input.setAttribute("webkitdirectory", "");
        }

        if (options.acceptFiles) {
            input.setAttribute("accept", options.acceptFiles);
        }

        input.setAttribute("type", "file");
        input.setAttribute("name", options.name);

        qq(input).css({
            position: "absolute",
            // in Opera only 'browse' button
            // is clickable and it is located at
            // the right side of the input
            right: 0,
            top: 0,
            fontFamily: "Arial",
            // 4 persons reported this, the max values that worked for them were 243, 236, 236, 118
            fontSize: "118px",
            margin: 0,
            padding: 0,
            cursor: "pointer",
            opacity: 0
        });

        options.element.appendChild(input);

        disposeSupport.attach(input, "change", function(){
            options.onChange(input);
        });

        // **These event handlers will be removed** in the future as the :hover CSS pseudo-class is available on all supported browsers
        disposeSupport.attach(input, "mouseover", function(){
            qq(options.element).addClass(options.hoverClass);
        });
        disposeSupport.attach(input, "mouseout", function(){
            qq(options.element).removeClass(options.hoverClass);
        });

        disposeSupport.attach(input, "focus", function(){
            qq(options.element).addClass(options.focusClass);
        });
        disposeSupport.attach(input, "blur", function(){
            qq(options.element).removeClass(options.focusClass);
        });

        // IE and Opera, unfortunately have 2 tab stops on file input
        // which is unacceptable in our case, disable keyboard access
        if (window.attachEvent) {
            // it is IE or Opera
            input.setAttribute("tabIndex", "-1");
        }

        return input;
    }

    // Make button suitable container for input
    qq(options.element).css({
        position: "relative",
        overflow: "hidden",
        // Make sure browse button is in the right side in Internet Explorer
        direction: "ltr"
    });

    input = createInput();


    // Exposed API
    qq.extend(this, {
        getInput: function() {
            return input;
        },

        getButtonId: function() {
            return buttonId;
        },

        setMultiple: function(isMultiple) {
            if (isMultiple !== options.multiple) {
                if (isMultiple) {
                    input.setAttribute("multiple", "");
                }
                else {
                    input.removeAttribute("multiple");
                }
            }
        },

        setAcceptFiles: function(acceptFiles) {
            if (acceptFiles !== options.acceptFiles) {
                input.setAttribute("accept", acceptFiles);
            }
        },

        reset: function(){
            if (input.parentNode){
                qq(input).remove();
            }

            qq(options.element).removeClass(options.focusClass);
            input = createInput();
        }
    });
};

qq.UploadButton.BUTTON_ID_ATTR_NAME = "qq-button-id";

/*globals qq */
qq.UploadData = function(uploaderProxy) {
    "use strict";

    var data = [],
        byUuid = {},
        byStatus = {};


    function getDataByIds(idOrIds) {
        if (qq.isArray(idOrIds)) {
            var entries = [];

            qq.each(idOrIds, function(idx, id) {
                entries.push(data[id]);
            });

            return entries;
        }

        return data[idOrIds];
    }

    function getDataByUuids(uuids) {
        if (qq.isArray(uuids)) {
            var entries = [];

            qq.each(uuids, function(idx, uuid) {
                entries.push(data[byUuid[uuid]]);
            });

            return entries;
        }

        return data[byUuid[uuids]];
    }

    function getDataByStatus(status) {
        var statusResults = [],
            statuses = [].concat(status);

        qq.each(statuses, function(index, statusEnum) {
            var statusResultIndexes = byStatus[statusEnum];

            if (statusResultIndexes !== undefined) {
                qq.each(statusResultIndexes, function(i, dataIndex) {
                    statusResults.push(data[dataIndex]);
                });
            }
        });

        return statusResults;
    }

    qq.extend(this, {
        /**
         * Adds a new file to the data cache for tracking purposes.
         *
         * @param uuid Initial UUID for this file.
         * @param name Initial name of this file.
         * @param size Size of this file, -1 if this cannot be determined
         * @param status Initial `qq.status` for this file.  If null/undefined, `qq.status.SUBMITTING`.
         * @returns {number} Internal ID for this file.
         */
        addFile: function(uuid, name, size, status) {
            status = status || qq.status.SUBMITTING;

            var id = data.push({
                name: name,
                originalName: name,
                uuid: uuid,
                size: size,
                status: status
            }) - 1;

            data[id].id = id;
            byUuid[uuid] = id;

            if (byStatus[status] === undefined) {
                byStatus[status] = [];
            }
            byStatus[status].push(id);

            uploaderProxy.onStatusChange(id, null, status);

            return id;
        },

        retrieve: function(optionalFilter) {
            if (qq.isObject(optionalFilter) && data.length)  {
                if (optionalFilter.id !== undefined) {
                    return getDataByIds(optionalFilter.id);
                }

                else if (optionalFilter.uuid !== undefined) {
                    return getDataByUuids(optionalFilter.uuid);
                }

                else if (optionalFilter.status) {
                    return getDataByStatus(optionalFilter.status);
                }
            }
            else {
                return qq.extend([], data, true);
            }
        },

        reset: function() {
            data = [];
            byUuid = {};
            byStatus = {};
        },

        setStatus: function(id, newStatus) {
            var oldStatus = data[id].status,
                byStatusOldStatusIndex = qq.indexOf(byStatus[oldStatus], id);

            byStatus[oldStatus].splice(byStatusOldStatusIndex, 1);

            data[id].status = newStatus;

            if (byStatus[newStatus] === undefined) {
                byStatus[newStatus] = [];
            }
            byStatus[newStatus].push(id);

            uploaderProxy.onStatusChange(id, oldStatus, newStatus);
        },

        uuidChanged: function(id, newUuid) {
            var oldUuid = data[id].uuid;

            data[id].uuid = newUuid;
            byUuid[newUuid] = id;
            delete byUuid[oldUuid];
        },

        updateName: function(id, newName) {
            data[id].name = newName;
        }
    });
};

qq.status = {
    SUBMITTING: "submitting",
    SUBMITTED: "submitted",
    REJECTED: "rejected",
    QUEUED: "queued",
    CANCELED: "canceled",
    PAUSED: "paused",
    UPLOADING: "uploading",
    UPLOAD_RETRYING: "retrying upload",
    UPLOAD_SUCCESSFUL: "upload successful",
    UPLOAD_FAILED: "upload failed",
    DELETE_FAILED: "delete failed",
    DELETING: "deleting",
    DELETED: "deleted"
};

/*globals qq*/
/**
 * Defines the public API for FineUploaderBasic mode.
 */
(function(){
    "use strict";

    qq.basePublicApi = {
        log: function(str, level) {
            if (this._options.debug && (!level || level === "info")) {
                qq.log("[Fine Uploader " + qq.version + "] " + str);
            }
            else if (level && level !== "info") {
                qq.log("[Fine Uploader " + qq.version + "] " + str, level);

            }
        },

        setParams: function(params, id) {
            this._paramsStore.set(params, id);
        },

        setDeleteFileParams: function(params, id) {
            this._deleteFileParamsStore.set(params, id);
        },

        // Re-sets the default endpoint, an endpoint for a specific file, or an endpoint for a specific button
        setEndpoint: function(endpoint, id) {
            this._endpointStore.set(endpoint, id);
        },

        getInProgress: function() {
            return this._uploadData.retrieve({
                status: [
                    qq.status.UPLOADING,
                    qq.status.UPLOAD_RETRYING,
                    qq.status.QUEUED
                ]
            }).length;
        },

        getNetUploads: function() {
            return this._netUploaded;
        },

        uploadStoredFiles: function() {
            var idToUpload;

            if (this._storedIds.length === 0) {
                this._itemError("noFilesError");
            }
            else {
                while (this._storedIds.length) {
                    idToUpload = this._storedIds.shift();
                    this._uploadFile(idToUpload);
                }
            }
        },

        clearStoredFiles: function(){
            this._storedIds = [];
        },

        retry: function(id) {
            return this._manualRetry(id);
        },

        cancel: function(id) {
            this._handler.cancel(id);
        },

        cancelAll: function() {
            var storedIdsCopy = [],
                self = this;

            qq.extend(storedIdsCopy, this._storedIds);
            qq.each(storedIdsCopy, function(idx, storedFileId) {
                self.cancel(storedFileId);
            });

            this._handler.cancelAll();
        },

        reset: function() {
            this.log("Resetting uploader...");

            this._handler.reset();
            this._storedIds = [];
            this._autoRetries = [];
            this._retryTimeouts = [];
            this._preventRetries = [];
            this._thumbnailUrls = [];

            qq.each(this._buttons, function(idx, button) {
                button.reset();
            });

            this._paramsStore.reset();
            this._endpointStore.reset();
            this._netUploadedOrQueued = 0;
            this._netUploaded = 0;
            this._uploadData.reset();
            this._buttonIdsForFileIds = [];

            this._pasteHandler && this._pasteHandler.reset();
            this._options.session.refreshOnReset && this._refreshSessionData();

            this._succeededSinceLastAllComplete = [];
            this._failedSinceLastAllComplete = [];
        },

        addFiles: function(filesOrInputs, params, endpoint) {
            var verifiedFilesOrInputs = [],
                fileOrInputIndex, fileOrInput, fileIndex;

            if (filesOrInputs) {
                if (!qq.isFileList(filesOrInputs)) {
                    filesOrInputs = [].concat(filesOrInputs);
                }

                for (fileOrInputIndex = 0; fileOrInputIndex < filesOrInputs.length; fileOrInputIndex+=1) {
                    fileOrInput = filesOrInputs[fileOrInputIndex];

                    if (qq.isFileOrInput(fileOrInput)) {
                        if (qq.isInput(fileOrInput) && qq.supportedFeatures.ajaxUploading) {
                            for (fileIndex = 0; fileIndex < fileOrInput.files.length; fileIndex++) {
                                this._handleNewFile(fileOrInput.files[fileIndex], verifiedFilesOrInputs);
                            }
                        }
                        else {
                            this._handleNewFile(fileOrInput, verifiedFilesOrInputs);
                        }
                    }
                    else {
                        this.log(fileOrInput + " is not a File or INPUT element!  Ignoring!", "warn");
                    }
                }

                this.log("Received " + verifiedFilesOrInputs.length + " files or inputs.");
                this._prepareItemsForUpload(verifiedFilesOrInputs, params, endpoint);
            }
        },

        addBlobs: function(blobDataOrArray, params, endpoint) {
            if (blobDataOrArray) {
                var blobDataArray = [].concat(blobDataOrArray),
                    verifiedBlobDataList = [],
                    self = this;

                qq.each(blobDataArray, function(idx, blobData) {
                    var blobOrBlobData;

                    if (qq.isBlob(blobData) && !qq.isFileOrInput(blobData)) {
                        blobOrBlobData = {
                            blob: blobData,
                            name: self._options.blobs.defaultName
                        };
                    }
                    else if (qq.isObject(blobData) && blobData.blob && blobData.name) {
                        blobOrBlobData = blobData;
                    }
                    else {
                        self.log("addBlobs: entry at index " + idx + " is not a Blob or a BlobData object", "error");
                    }

                    blobOrBlobData && self._handleNewFile(blobOrBlobData, verifiedBlobDataList);
                });

                this._prepareItemsForUpload(verifiedBlobDataList, params, endpoint);
            }
            else {
                this.log("undefined or non-array parameter passed into addBlobs", "error");
            }
        },

        getUuid: function(id) {
            return this._uploadData.retrieve({id: id}).uuid;
        },

        setUuid: function(id, newUuid) {
            return this._uploadData.uuidChanged(id, newUuid);
        },

        getResumableFilesData: function() {
            return this._handler.getResumableFilesData();
        },

        getSize: function(id) {
            return this._uploadData.retrieve({id: id}).size;
        },

        getName: function(id) {
            return this._uploadData.retrieve({id: id}).name;
        },

        setName: function(id, newName) {
            this._uploadData.updateName(id, newName);
        },

        getFile: function(fileOrBlobId) {
            return this._handler.getFile(fileOrBlobId);
        },

        deleteFile: function(id) {
            return this._onSubmitDelete(id);
        },

        setDeleteFileEndpoint: function(endpoint, id) {
            this._deleteFileEndpointStore.set(endpoint, id);
        },

        doesExist: function(fileOrBlobId) {
            return this._handler.isValid(fileOrBlobId);
        },

        getUploads: function(optionalFilter) {
            return this._uploadData.retrieve(optionalFilter);
        },

        getButton: function(fileId) {
            return this._getButton(this._buttonIdsForFileIds[fileId]);
        },

        // Generate a variable size thumbnail on an img or canvas,
        // returning a promise that is fulfilled when the attempt completes.
        // Thumbnail can either be based off of a URL for an image returned
        // by the server in the upload response, or the associated `Blob`.
        drawThumbnail: function(fileId, imgOrCanvas, maxSize, fromServer) {
            if (this._imageGenerator) {
                var fileOrUrl = this._thumbnailUrls[fileId],
                    options = {
                        scale: maxSize > 0,
                        maxSize: maxSize > 0 ? maxSize : null
                    };

                // If client-side preview generation is possible
                // and we are not specifically looking for the image URl returned by the server...
                if (!fromServer && qq.supportedFeatures.imagePreviews) {
                    fileOrUrl = this.getFile(fileId);
                }

                /* jshint eqeqeq:false,eqnull:true */
                if (fileOrUrl == null) {
                    return new qq.Promise().failure(imgOrCanvas, "File or URL not found.");
                }

                return this._imageGenerator.generate(fileOrUrl, imgOrCanvas, options);
            }
        },

        pauseUpload: function(id) {
            var uploadData = this._uploadData.retrieve({id: id});

            if (!qq.supportedFeatures.pause || !this._options.chunking.enabled) {
                return false;
            }

            // Pause only really makes sense if the file is uploading or retrying
            if (qq.indexOf([qq.status.UPLOADING, qq.status.UPLOAD_RETRYING], uploadData.status) >= 0) {
                if (this._handler.pause(id)) {
                    this._uploadData.setStatus(id, qq.status.PAUSED);
                    return true;
                }
                else {
                    qq.log(qq.format("Unable to pause file ID {} ({}).", id, this.getName(id)), "error");
                }
            }
            else {
                qq.log(qq.format("Ignoring pause for file ID {} ({}).  Not in progress.", id, this.getName(id)), "error");
            }

            return false;
        },

        continueUpload: function(id) {
            var uploadData = this._uploadData.retrieve({id: id});

            if (!qq.supportedFeatures.pause || !this._options.chunking.enabled) {
                return false;
            }

            if (uploadData.status === qq.status.PAUSED) {
                qq.log(qq.format("Paused file ID {} ({}) will be continued.  Not paused.", id, this.getName(id)));
                this._uploadFile(id);
                return true;
            }
            else {
                qq.log(qq.format("Ignoring continue for file ID {} ({}).  Not paused.", id, this.getName(id)), "error");
            }

            return false;
        },

        getRemainingAllowedItems: function() {
            var allowedItems = this._options.validation.itemLimit;

            if (allowedItems > 0) {
                return this._options.validation.itemLimit - this._netUploadedOrQueued;
            }

            return null;
        }
    };




    /**
     * Defines the private (internal) API for FineUploaderBasic mode.
     */
    qq.basePrivateApi = {
        _initFormSupportAndParams: function() {
            this._formSupport = qq.FormSupport && new qq.FormSupport(
                this._options.form, qq.bind(this.uploadStoredFiles, this), qq.bind(this.log, this)
            );

            if (this._formSupport && this._formSupport.attachedToForm) {
                this._paramsStore = this._createStore(
                    this._options.request.params,  this._formSupport.getFormInputsAsObject
                );

                this._options.autoUpload = this._formSupport.newAutoUpload;
                if (this._formSupport.newEndpoint) {
                    this._options.request.endpoint = this._formSupport.newEndpoint;
                }
            }
            else {
                this._paramsStore = this._createStore(this._options.request.params);
            }
        },

        _uploadFile: function(id) {
            if (!this._handler.upload(id)) {
                this._uploadData.setStatus(id, qq.status.QUEUED);
            }
        },

        // Attempts to refresh session data only if the `qq.Session` module exists
        // and a session endpoint has been specified.  The `onSessionRequestComplete`
        // callback will be invoked once the refresh is complete.
        _refreshSessionData: function() {
            var self = this,
                options = this._options.session;

            /* jshint eqnull:true */
            if (qq.Session && this._options.session.endpoint != null) {
                if (!this._session) {
                    qq.extend(options, this._options.cors);

                    options.log = qq.bind(this.log, this);
                    options.addFileRecord = qq.bind(this._addCannedFile, this);

                    this._session = new qq.Session(options);
                }

                setTimeout(function() {
                    self._session.refresh().then(function(response, xhrOrXdr) {

                        self._options.callbacks.onSessionRequestComplete(response, true, xhrOrXdr);

                    }, function(response, xhrOrXdr) {

                        self._options.callbacks.onSessionRequestComplete(response, false, xhrOrXdr);
                    });
                }, 0);
            }
        },

        // Updates internal state with a file record (not backed by a live file).  Returns the assigned ID.
        _addCannedFile: function(sessionData) {
            var id = this._uploadData.addFile(sessionData.uuid, sessionData.name, sessionData.size,
                qq.status.UPLOAD_SUCCESSFUL);

            sessionData.deleteFileEndpoint && this.setDeleteFileEndpoint(sessionData.deleteFileEndpoint, id);
            sessionData.deleteFileParams && this.setDeleteFileParams(sessionData.deleteFileParams, id);

            if (sessionData.thumbnailUrl) {
                this._thumbnailUrls[id] = sessionData.thumbnailUrl;
            }

            this._netUploaded++;
            this._netUploadedOrQueued++;

            return id;
        },

        // Updates internal state when a new file has been received, and adds it along with its ID to a passed array.
        _handleNewFile: function(file, newFileWrapperList) {
            var size = -1,
                uuid = qq.getUniqueId(),
                name = qq.getFilename(file),
                id;

            if (file.size >= 0) {
                size = file.size;
            }
            else if (file.blob) {
                size = file.blob.size;
            }

            id = this._uploadData.addFile(uuid, name, size);
            this._handler.add(id, file);
            this._trackButton(id);

            this._netUploadedOrQueued++;

            newFileWrapperList.push({id: id, file: file});
        },

        // Maps a file with the button that was used to select it.
        _trackButton: function(id) {
            var buttonId;

            if (qq.supportedFeatures.ajaxUploading) {
                buttonId = this._handler.getFile(id).qqButtonId;
            }
            else {
                buttonId = this._getButtonId(this._handler.getInput(id));
            }

            if (buttonId) {
                this._buttonIdsForFileIds[id] = buttonId;
            }
        },

        // Creates an internal object that tracks various properties of each extra button,
        // and then actually creates the extra button.
        _generateExtraButtonSpecs: function() {
            var self = this;

            this._extraButtonSpecs = {};

            qq.each(this._options.extraButtons, function(idx, extraButtonOptionEntry) {
                var multiple = extraButtonOptionEntry.multiple,
                    validation = qq.extend({}, self._options.validation, true),
                    extraButtonSpec = qq.extend({}, extraButtonOptionEntry);

                if (multiple === undefined) {
                    multiple = self._options.multiple;
                }

                if (extraButtonSpec.validation) {
                    qq.extend(validation, extraButtonOptionEntry.validation, true);
                }

                qq.extend(extraButtonSpec, {
                    multiple: multiple,
                    validation: validation
                }, true);

                self._initExtraButton(extraButtonSpec);
            });
        },

        // Creates an extra button element
        _initExtraButton: function(spec) {
            var button = this._createUploadButton({
                element: spec.element,
                multiple: spec.multiple,
                accept: spec.validation.acceptFiles,
                folders: spec.folders,
                allowedExtensions: spec.validation.allowedExtensions
            });

            this._extraButtonSpecs[button.getButtonId()] = spec;
        },

        /**
         * Gets the internally used tracking ID for a button.
         *
         * @param buttonOrFileInputOrFile `File`, `<input type="file">`, or a button container element
         * @returns {*} The button's ID, or undefined if no ID is recoverable
         * @private
         */
        _getButtonId: function(buttonOrFileInputOrFile) {
            var inputs, fileInput;

            // If the item is a `Blob` it will never be associated with a button or drop zone.
            if (buttonOrFileInputOrFile && !buttonOrFileInputOrFile.blob && !qq.isBlob(buttonOrFileInputOrFile)) {
                if (qq.isFile(buttonOrFileInputOrFile)) {
                    return buttonOrFileInputOrFile.qqButtonId;
                }
                else if (buttonOrFileInputOrFile.tagName.toLowerCase() === "input" &&
                    buttonOrFileInputOrFile.type.toLowerCase() === "file") {

                    return buttonOrFileInputOrFile.getAttribute(qq.UploadButton.BUTTON_ID_ATTR_NAME);
                }

                inputs = buttonOrFileInputOrFile.getElementsByTagName("input");

                qq.each(inputs, function(idx, input) {
                    if (input.getAttribute("type") === "file") {
                        fileInput = input;
                        return false;
                    }
                });

                if (fileInput) {
                    return fileInput.getAttribute(qq.UploadButton.BUTTON_ID_ATTR_NAME);
                }
            }
        },

        _annotateWithButtonId: function(file, associatedInput) {
            if (qq.isFile(file)) {
                file.qqButtonId = this._getButtonId(associatedInput);
            }
        },

        _getButton: function(buttonId) {
            var extraButtonsSpec = this._extraButtonSpecs[buttonId];

            if (extraButtonsSpec) {
                return extraButtonsSpec.element;
            }
            else if (buttonId === this._defaultButtonId) {
                return this._options.button;
            }
        },

        _handleCheckedCallback: function(details) {
            var self = this,
                callbackRetVal = details.callback();

            if (callbackRetVal instanceof qq.Promise) {
                this.log(details.name + " - waiting for " + details.name + " promise to be fulfilled for " + details.identifier);
                return callbackRetVal.then(
                    function(successParam) {
                        self.log(details.name + " promise success for " + details.identifier);
                        details.onSuccess(successParam);
                    },
                    function() {
                        if (details.onFailure) {
                            self.log(details.name + " promise failure for " + details.identifier);
                            details.onFailure();
                        }
                        else {
                            self.log(details.name + " promise failure for " + details.identifier);
                        }
                    });
            }

            if (callbackRetVal !== false) {
                details.onSuccess(callbackRetVal);
            }
            else {
                if (details.onFailure) {
                    this.log(details.name + " - return value was 'false' for " + details.identifier + ".  Invoking failure callback.");
                    details.onFailure();
                }
                else {
                    this.log(details.name + " - return value was 'false' for " + details.identifier + ".  Will not proceed.");
                }
            }

            return callbackRetVal;
        },

        /**
         * Generate a tracked upload button.
         *
         * @param spec Object containing a required `element` property
         * along with optional `multiple`, `accept`, and `folders`.
         * @returns {qq.UploadButton}
         * @private
         */
        _createUploadButton: function(spec) {
            var self = this,
                acceptFiles = spec.accept || this._options.validation.acceptFiles,
                allowedExtensions = spec.allowedExtensions || this._options.validation.allowedExtensions;

            function allowMultiple() {
                if (qq.supportedFeatures.ajaxUploading) {
                    // Workaround for bug in iOS7 (see #1039)
                    if (qq.ios7() && self._isAllowedExtension(allowedExtensions, ".mov")) {
                        return false;
                    }

                    if (spec.multiple === undefined) {
                        return self._options.multiple;
                    }

                    return spec.multiple;
                }

                return false;
            }

            var button = new qq.UploadButton({
                element: spec.element,
                folders: spec.folders,
                name: this._options.request.inputName,
                multiple: allowMultiple(),
                acceptFiles: acceptFiles,
                onChange: function(input) {
                    self._onInputChange(input);
                },
                hoverClass: this._options.classes.buttonHover,
                focusClass: this._options.classes.buttonFocus
            });

            this._disposeSupport.addDisposer(function() {
                button.dispose();
            });

            self._buttons.push(button);

            return button;
        },

        _createUploadHandler: function(additionalOptions, namespace) {
            var self = this,
                options = {
                    debug: this._options.debug,
                    maxConnections: this._options.maxConnections,
                    cors: this._options.cors,
                    demoMode: this._options.demoMode,
                    paramsStore: this._paramsStore,
                    endpointStore: this._endpointStore,
                    chunking: this._options.chunking,
                    resume: this._options.resume,
                    blobs: this._options.blobs,
                    log: qq.bind(self.log, self),
                    onProgress: function(id, name, loaded, total){
                        self._onProgress(id, name, loaded, total);
                        self._options.callbacks.onProgress(id, name, loaded, total);
                    },
                    onComplete: function(id, name, result, xhr){
                        var retVal = self._onComplete(id, name, result, xhr);

                        // If the internal `_onComplete` handler returns a promise, don't invoke the `onComplete` callback
                        // until the promise has been fulfilled.
                        if (retVal instanceof  qq.Promise) {
                            retVal.done(function() {
                                self._options.callbacks.onComplete(id, name, result, xhr);
                            });
                        }
                        else {
                            self._options.callbacks.onComplete(id, name, result, xhr);
                        }
                    },
                    onCancel: function(id, name) {
                        return self._handleCheckedCallback({
                            name: "onCancel",
                            callback: qq.bind(self._options.callbacks.onCancel, self, id, name),
                            onSuccess: qq.bind(self._onCancel, self, id, name),
                            identifier: id
                        });
                    },
                    onUpload: function(id, name) {
                        self._onUpload(id, name);
                        self._options.callbacks.onUpload(id, name);
                    },
                    onUploadChunk: function(id, name, chunkData) {
                        self._onUploadChunk(id, chunkData);
                        self._options.callbacks.onUploadChunk(id, name, chunkData);
                    },
                    onUploadChunkSuccess: function(id, chunkData, result, xhr) {
                        self._options.callbacks.onUploadChunkSuccess.apply(self, arguments);
                    },
                    onResume: function(id, name, chunkData) {
                        return self._options.callbacks.onResume(id, name, chunkData);
                    },
                    onAutoRetry: function(id, name, responseJSON, xhr) {
                        return self._onAutoRetry.apply(self, arguments);
                    },
                    onUuidChanged: function(id, newUuid) {
                        self.log("Server requested UUID change from '" + self.getUuid(id) + "' to '" + newUuid + "'");
                        self.setUuid(id, newUuid);
                    },
                    getName: qq.bind(self.getName, self),
                    getUuid: qq.bind(self.getUuid, self),
                    getSize: qq.bind(self.getSize, self)
                };

            qq.each(this._options.request, function(prop, val) {
                options[prop] = val;
            });

            if (additionalOptions) {
                qq.each(additionalOptions, function(key, val) {
                    options[key] = val;
                });
            }

            return new qq.UploadHandler(options, namespace);
        },

        _createDeleteHandler: function() {
            var self = this;

            return new qq.DeleteFileAjaxRequester({
                method: this._options.deleteFile.method.toUpperCase(),
                maxConnections: this._options.maxConnections,
                uuidParamName: this._options.request.uuidName,
                customHeaders: this._options.deleteFile.customHeaders,
                paramsStore: this._deleteFileParamsStore,
                endpointStore: this._deleteFileEndpointStore,
                demoMode: this._options.demoMode,
                cors: this._options.cors,
                log: qq.bind(self.log, self),
                onDelete: function(id) {
                    self._onDelete(id);
                    self._options.callbacks.onDelete(id);
                },
                onDeleteComplete: function(id, xhrOrXdr, isError) {
                    self._onDeleteComplete(id, xhrOrXdr, isError);
                    self._options.callbacks.onDeleteComplete(id, xhrOrXdr, isError);
                }

            });
        },

        _createPasteHandler: function() {
            var self = this;

            return new qq.PasteSupport({
                targetElement: this._options.paste.targetElement,
                callbacks: {
                    log: qq.bind(self.log, self),
                    pasteReceived: function(blob) {
                        self._handleCheckedCallback({
                            name: "onPasteReceived",
                            callback: qq.bind(self._options.callbacks.onPasteReceived, self, blob),
                            onSuccess: qq.bind(self._handlePasteSuccess, self, blob),
                            identifier: "pasted image"
                        });
                    }
                }
            });
        },

        _createUploadDataTracker: function() {
            var self = this;

            return new qq.UploadData({
                getName: function(id) {
                    return self.getName(id);
                },
                getUuid: function(id) {
                    return self.getUuid(id);
                },
                getSize: function(id) {
                    return self.getSize(id);
                },
                onStatusChange: function(id, oldStatus, newStatus) {
                    self._onUploadStatusChange(id, oldStatus, newStatus);
                    self._options.callbacks.onStatusChange(id, oldStatus, newStatus);
                    self._maybeAllComplete(id, newStatus);
                }
            });
        },

        _onUploadStatusChange: function(id, oldStatus, newStatus) {
            // Make sure a "queued" retry attempt is canceled if the upload has been paused
            if (newStatus === qq.status.PAUSED) {
                clearTimeout(this._retryTimeouts[id]);
            }
        },

        _handlePasteSuccess: function(blob, extSuppliedName) {
            var extension = blob.type.split("/")[1],
                name = extSuppliedName;

            /*jshint eqeqeq: true, eqnull: true*/
            if (name == null) {
                name = this._options.paste.defaultName;
            }

            name += "." + extension;

            this.addBlobs({
                name: name,
                blob: blob
            });
        },

        _preventLeaveInProgress: function(){
            var self = this;

            this._disposeSupport.attach(window, "beforeunload", function(e){
                if (self.getInProgress()) {
                    e = e || window.event;
                    // for ie, ff
                    e.returnValue = self._options.messages.onLeave;
                    // for webkit
                    return self._options.messages.onLeave;
                }
            });
        },

        _onSubmit: function(id, name) {
            //nothing to do yet in core uploader
        },

        _onProgress: function(id, name, loaded, total) {
            //nothing to do yet in core uploader
        },

        _onComplete: function(id, name, result, xhr) {
            if (!result.success) {
                this._netUploadedOrQueued--;
                this._uploadData.setStatus(id, qq.status.UPLOAD_FAILED);
            }
            else {
                if (result.thumbnailUrl) {
                    this._thumbnailUrls[id] = result.thumbnailUrl;
                }

                this._netUploaded++;
                this._uploadData.setStatus(id, qq.status.UPLOAD_SUCCESSFUL);
            }

            this._maybeParseAndSendUploadError(id, name, result, xhr);

            return result.success ? true : false;
        },

        _maybeAllComplete: function(id, status) {
            var self = this,
                notFinished = this._uploadData.retrieve({
                status: [
                    qq.status.UPLOADING,
                    qq.status.UPLOAD_RETRYING,
                    qq.status.QUEUED,
                    qq.status.SUBMITTING,
                    qq.status.SUBMITTED,
                    qq.status.PAUSED,
                ]
            }).length;

            if (status === qq.status.UPLOAD_SUCCESSFUL) {
                this._succeededSinceLastAllComplete.push(id);
            }
            else if (status === qq.status.UPLOAD_FAILED) {
                this._failedSinceLastAllComplete.push(id);
            }

            if (notFinished === 0 &&
                (this._succeededSinceLastAllComplete.length || this._failedSinceLastAllComplete.length)) {
                // Attempt to ensure onAllComplete is not invoked before other callbacks, such as onCancel & onComplete
                setTimeout(function() {
                    self._options.callbacks.onAllComplete(
                        qq.extend([], self._succeededSinceLastAllComplete),
                        qq.extend([], self._failedSinceLastAllComplete)
                    );

                    self._succeededSinceLastAllComplete = [];
                    self._failedSinceLastAllComplete = [];
                }, 0);
            }
        },

        _onCancel: function(id, name) {
            this._netUploadedOrQueued--;

            clearTimeout(this._retryTimeouts[id]);

            var storedItemIndex = qq.indexOf(this._storedIds, id);
            if (!this._options.autoUpload && storedItemIndex >= 0) {
                this._storedIds.splice(storedItemIndex, 1);
            }

            this._uploadData.setStatus(id, qq.status.CANCELED);
        },

        _isDeletePossible: function() {
            if (!qq.DeleteFileAjaxRequester || !this._options.deleteFile.enabled) {
                return false;
            }

            if (this._options.cors.expected) {
                if (qq.supportedFeatures.deleteFileCorsXhr) {
                    return true;
                }

                if (qq.supportedFeatures.deleteFileCorsXdr && this._options.cors.allowXdr) {
                    return true;
                }

                return false;
            }

            return true;
        },

        _onSubmitDelete: function(id, onSuccessCallback, additionalMandatedParams) {
            var uuid = this.getUuid(id),
                adjustedOnSuccessCallback;

            if (onSuccessCallback) {
                adjustedOnSuccessCallback = qq.bind(onSuccessCallback, this, id, uuid, additionalMandatedParams);
            }

            if (this._isDeletePossible()) {
                this._handleCheckedCallback({
                    name: "onSubmitDelete",
                    callback: qq.bind(this._options.callbacks.onSubmitDelete, this, id),
                    onSuccess: adjustedOnSuccessCallback ||
                        qq.bind(this._deleteHandler.sendDelete, this, id, uuid, additionalMandatedParams),
                    identifier: id
                });
                return true;
            }
            else {
                this.log("Delete request ignored for ID " + id + ", delete feature is disabled or request not possible " +
                    "due to CORS on a user agent that does not support pre-flighting.", "warn");
                return false;
            }
        },

        _onDelete: function(id) {
            this._uploadData.setStatus(id, qq.status.DELETING);
        },

        _onDeleteComplete: function(id, xhrOrXdr, isError) {
            var name = this.getName(id);

            if (isError) {
                this._uploadData.setStatus(id, qq.status.DELETE_FAILED);
                this.log("Delete request for '" + name + "' has failed.", "error");

                // For error reporing, we only have accesss to the response status if this is not
                // an `XDomainRequest`.
                if (xhrOrXdr.withCredentials === undefined) {
                    this._options.callbacks.onError(id, name, "Delete request failed", xhrOrXdr);
                }
                else {
                    this._options.callbacks.onError(id, name, "Delete request failed with response code " + xhrOrXdr.status, xhrOrXdr);
                }
            }
            else {
                this._netUploadedOrQueued--;
                this._netUploaded--;
                this._handler.expunge(id);
                this._uploadData.setStatus(id, qq.status.DELETED);
                this.log("Delete request for '" + name + "' has succeeded.");
            }
        },

        _onUpload: function(id, name) {
            this._uploadData.setStatus(id, qq.status.UPLOADING);
        },

        _onUploadChunk: function(id, chunkData) {
            //nothing to do in the base uploader
        },

        _onInputChange: function(input) {
            var fileIndex;

            if (qq.supportedFeatures.ajaxUploading) {
                for (fileIndex = 0; fileIndex < input.files.length; fileIndex++) {
                    this._annotateWithButtonId(input.files[fileIndex], input);
                }

                this.addFiles(input.files);
            }
            // Android 2.3.x will fire `onchange` even if no file has been selected
            else if (input.value.length > 0) {
                this.addFiles(input);
            }

            qq.each(this._buttons, function(idx, button) {
                button.reset();
            });
        },

        _onBeforeAutoRetry: function(id, name) {
            this.log("Waiting " + this._options.retry.autoAttemptDelay + " seconds before retrying " + name + "...");
        },

        /**
         * Attempt to automatically retry a failed upload.
         *
         * @param id The file ID of the failed upload
         * @param name The name of the file associated with the failed upload
         * @param responseJSON Response from the server, parsed into a javascript object
         * @param xhr Ajax transport used to send the failed request
         * @param callback Optional callback to be invoked if a retry is prudent.
         * Invoked in lieu of asking the upload handler to retry.
         * @returns {boolean} true if an auto-retry will occur
         * @private
         */
        _onAutoRetry: function(id, name, responseJSON, xhr, callback) {
            var self = this;

            self._preventRetries[id] = responseJSON[self._options.retry.preventRetryResponseProperty];

            if (self._shouldAutoRetry(id, name, responseJSON)) {
                self._maybeParseAndSendUploadError.apply(self, arguments);
                self._options.callbacks.onAutoRetry(id, name, self._autoRetries[id] + 1);
                self._onBeforeAutoRetry(id, name);

                self._retryTimeouts[id] = setTimeout(function() {
                    self.log("Retrying " + name + "...");
                    self._autoRetries[id]++;
                    self._uploadData.setStatus(id, qq.status.UPLOAD_RETRYING);

                    if (callback) {
                        callback(id);
                    }
                    else {
                        self._handler.retry(id);
                    }
                }, self._options.retry.autoAttemptDelay * 1000);

                return true;
            }
        },

        _shouldAutoRetry: function(id, name, responseJSON) {
            var uploadData = this._uploadData.retrieve({id: id});

            /*jshint laxbreak: true */
            if (!this._preventRetries[id]
                && this._options.retry.enableAuto
                && uploadData.status !== qq.status.PAUSED) {

                if (this._autoRetries[id] === undefined) {
                    this._autoRetries[id] = 0;
                }

                return this._autoRetries[id] < this._options.retry.maxAutoAttempts;
            }

            return false;
        },

        //return false if we should not attempt the requested retry
        _onBeforeManualRetry: function(id) {
            var itemLimit = this._options.validation.itemLimit;

            if (this._preventRetries[id]) {
                this.log("Retries are forbidden for id " + id, "warn");
                return false;
            }
            else if (this._handler.isValid(id)) {
                var fileName = this.getName(id);

                if (this._options.callbacks.onManualRetry(id, fileName) === false) {
                    return false;
                }

                if (itemLimit > 0 && this._netUploadedOrQueued+1 > itemLimit) {
                    this._itemError("retryFailTooManyItems");
                    return false;
                }

                this.log("Retrying upload for '" + fileName + "' (id: " + id + ")...");
                return true;
            }
            else {
                this.log("'" + id + "' is not a valid file ID", "error");
                return false;
            }
        },

        /**
         * Conditionally orders a manual retry of a failed upload.
         *
         * @param id File ID of the failed upload
         * @param callback Optional callback to invoke if a retry is prudent.
         * In lieu of asking the upload handler to retry.
         * @returns {boolean} true if a manual retry will occur
         * @private
         */
        _manualRetry: function(id, callback) {
            if (this._onBeforeManualRetry(id)) {
                this._netUploadedOrQueued++;
                this._uploadData.setStatus(id, qq.status.UPLOAD_RETRYING);

                if (callback) {
                    callback(id);
                }
                else {
                    this._handler.retry(id);
                }

                return true;
            }
        },

        _maybeParseAndSendUploadError: function(id, name, response, xhr) {
            // Assuming no one will actually set the response code to something other than 200
            // and still set 'success' to true...
            if (!response.success){
                if (xhr && xhr.status !== 200 && !response.error) {
                    this._options.callbacks.onError(id, name, "XHR returned response code " + xhr.status, xhr);
                }
                else {
                    var errorReason = response.error ? response.error : this._options.text.defaultResponseError;
                    this._options.callbacks.onError(id, name, errorReason, xhr);
                }
            }
        },

        _prepareItemsForUpload: function(items, params, endpoint) {
            if (items.length === 0) {
                this._itemError("noFilesError");
                return;
            }

            var validationDescriptors = this._getValidationDescriptors(items),
                buttonId = this._getButtonId(items[0].file),
                button = this._getButton(buttonId);

            this._handleCheckedCallback({
                name: "onValidateBatch",
                callback: qq.bind(this._options.callbacks.onValidateBatch, this, validationDescriptors, button),
                onSuccess: qq.bind(this._onValidateBatchCallbackSuccess, this, validationDescriptors, items, params, endpoint, button),
                onFailure: qq.bind(this._onValidateBatchCallbackFailure, this, items),
                identifier: "batch validation"
            });
        },

        _upload: function(id, params, endpoint) {
            var name = this.getName(id);

            if (params) {
                this.setParams(params, id);
            }

            if (endpoint) {
                this.setEndpoint(endpoint, id);
            }

            this._handleCheckedCallback({
                name: "onSubmit",
                callback: qq.bind(this._options.callbacks.onSubmit, this, id, name),
                onSuccess: qq.bind(this._onSubmitCallbackSuccess, this, id, name),
                onFailure: qq.bind(this._fileOrBlobRejected, this, id, name),
                identifier: id
            });
        },

        _onSubmitCallbackSuccess: function(id, name) {
            this._onSubmit.apply(this, arguments);
            this._uploadData.setStatus(id, qq.status.SUBMITTED);
            this._onSubmitted.apply(this, arguments);
            this._options.callbacks.onSubmitted.apply(this, arguments);

            if (this._options.autoUpload) {
                this._uploadFile(id);
            }
            else {
                this._storeForLater(id);
            }
        },

        _onSubmitted: function(id) {
            //nothing to do in the base uploader
        },

        _storeForLater: function(id) {
            this._storedIds.push(id);
        },

        _onValidateBatchCallbackSuccess: function(validationDescriptors, items, params, endpoint, button) {
            var errorMessage,
                itemLimit = this._options.validation.itemLimit,
                proposedNetFilesUploadedOrQueued = this._netUploadedOrQueued;

            if (itemLimit === 0 || proposedNetFilesUploadedOrQueued <= itemLimit) {
                if (items.length > 0) {
                    this._handleCheckedCallback({
                        name: "onValidate",
                        callback: qq.bind(this._options.callbacks.onValidate, this, validationDescriptors[0], button),
                        onSuccess: qq.bind(this._onValidateCallbackSuccess, this, items, 0, params, endpoint),
                        onFailure: qq.bind(this._onValidateCallbackFailure, this, items, 0, params, endpoint),
                        identifier: "Item '" + items[0].file.name + "', size: " + items[0].file.size
                    });
                }
                else {
                    this._itemError("noFilesError");
                }
            }
            else {
                this._onValidateBatchCallbackFailure(items);
                errorMessage = this._options.messages.tooManyItemsError
                    .replace(/\{netItems\}/g, proposedNetFilesUploadedOrQueued)
                    .replace(/\{itemLimit\}/g, itemLimit);
                this._batchError(errorMessage);
            }
        },

        _onValidateBatchCallbackFailure: function(fileWrappers) {
            var self = this;

            qq.each(fileWrappers, function(idx, fileWrapper) {
                self._fileOrBlobRejected(fileWrapper.id);
            });
        },

        _onValidateCallbackSuccess: function(items, index, params, endpoint) {
            var self = this,
                nextIndex = index+1,
                validationDescriptor = this._getValidationDescriptor(items[index].file);

            this._validateFileOrBlobData(items[index], validationDescriptor)
                .then(
                    function() {
                        self._upload(items[index].id, params, endpoint);
                        self._maybeProcessNextItemAfterOnValidateCallback(true, items, nextIndex, params, endpoint);
                    },
                    function() {
                        self._maybeProcessNextItemAfterOnValidateCallback(false, items, nextIndex, params, endpoint);
                    }
                );
        },

        _onValidateCallbackFailure: function(items, index, params, endpoint) {
            var nextIndex = index+ 1;

            this._fileOrBlobRejected(items[0].id, items[0].file.name);

            this._maybeProcessNextItemAfterOnValidateCallback(false, items, nextIndex, params, endpoint);
        },

        _maybeProcessNextItemAfterOnValidateCallback: function(validItem, items, index, params, endpoint) {
            var self = this;

            if (items.length > index) {
                if (validItem || !this._options.validation.stopOnFirstInvalidFile) {
                    //use setTimeout to prevent a stack overflow with a large number of files in the batch & non-promissory callbacks
                    setTimeout(function() {
                        var validationDescriptor = self._getValidationDescriptor(items[index].file);

                        self._handleCheckedCallback({
                            name: "onValidate",
                            callback: qq.bind(self._options.callbacks.onValidate, self, items[index].file),
                            onSuccess: qq.bind(self._onValidateCallbackSuccess, self, items, index, params, endpoint),
                            onFailure: qq.bind(self._onValidateCallbackFailure, self, items, index, params, endpoint),
                            identifier: "Item '" + validationDescriptor.name + "', size: " + validationDescriptor.size
                        });
                    }, 0);
                }
                else if (!validItem) {
                    for (; index < items.length; index++) {
                        self._fileOrBlobRejected(items[index].id);
                    }
                }
            }
        },

        /**
         * Performs some internal validation checks on an item, defined in the `validation` option.
         *
         * @param fileWrapper Wrapper containing a `file` along with an `id`
         * @param validationDescriptor Normalized information about the item (`size`, `name`).
         * @returns qq.Promise with appropriate callbacks invoked depending on the validity of the file
         * @private
         */
        _validateFileOrBlobData: function(fileWrapper, validationDescriptor) {
            var self = this,
                file = fileWrapper.file,
                name = validationDescriptor.name,
                size = validationDescriptor.size,
                buttonId = this._getButtonId(file),
                validationBase = this._getValidationBase(buttonId),
                validityChecker = new qq.Promise();

            validityChecker.then(
                function() {},
                function() {
                    self._fileOrBlobRejected(fileWrapper.id, name);
                });

            if (qq.isFileOrInput(file) && !this._isAllowedExtension(validationBase.allowedExtensions, name)) {
                this._itemError("typeError", name, file);
                return validityChecker.failure();
            }

            if (size === 0) {
                this._itemError("emptyError", name, file);
                return validityChecker.failure();
            }

            if (size && validationBase.sizeLimit && size > validationBase.sizeLimit) {
                this._itemError("sizeError", name, file);
                return validityChecker.failure();
            }

            if (size && size < validationBase.minSizeLimit) {
                this._itemError("minSizeError", name, file);
                return validityChecker.failure();
            }

            if (qq.ImageValidation && qq.supportedFeatures.imagePreviews && qq.isFile(file)) {
                new qq.ImageValidation(file, qq.bind(self.log, self)).validate(validationBase.image).then(
                    validityChecker.success,
                    function(errorCode) {
                        self._itemError(errorCode + "ImageError", name, file);
                        validityChecker.failure();
                    }
                );
            }
            else {
                validityChecker.success();
            }

            return validityChecker;
        },

        _fileOrBlobRejected: function(id) {
            this._netUploadedOrQueued--;
            this._uploadData.setStatus(id, qq.status.REJECTED);
        },

        /**
         * Constructs and returns a message that describes an item/file error.  Also calls `onError` callback.
         *
         * @param code REQUIRED - a code that corresponds to a stock message describing this type of error
         * @param maybeNameOrNames names of the items that have failed, if applicable
         * @param item `File`, `Blob`, or `<input type="file">`
         * @private
         */
        _itemError: function(code, maybeNameOrNames, item) {
            var message = this._options.messages[code],
                allowedExtensions = [],
                names = [].concat(maybeNameOrNames),
                name = names[0],
                buttonId = this._getButtonId(item),
                validationBase = this._getValidationBase(buttonId),
                extensionsForMessage, placeholderMatch;

            function r(name, replacement){ message = message.replace(name, replacement); }

            qq.each(validationBase.allowedExtensions, function(idx, allowedExtension) {
                    /**
                     * If an argument is not a string, ignore it.  Added when a possible issue with MooTools hijacking the
                     * `allowedExtensions` array was discovered.  See case #735 in the issue tracker for more details.
                     */
                if (qq.isString(allowedExtension)) {
                    allowedExtensions.push(allowedExtension);
                }
            });

            extensionsForMessage = allowedExtensions.join(", ").toLowerCase();

            r("{file}", this._options.formatFileName(name));
            r("{extensions}", extensionsForMessage);
            r("{sizeLimit}", this._formatSize(validationBase.sizeLimit));
            r("{minSizeLimit}", this._formatSize(validationBase.minSizeLimit));

            placeholderMatch = message.match(/(\{\w+\})/g);
            if (placeholderMatch !== null) {
                qq.each(placeholderMatch, function(idx, placeholder) {
                    r(placeholder, names[idx]);
                });
            }

            this._options.callbacks.onError(null, name, message, undefined);

            return message;
        },

        _batchError: function(message) {
            this._options.callbacks.onError(null, null, message, undefined);
        },

        _isAllowedExtension: function(allowed, fileName) {
            var valid = false;

            if (!allowed.length) {
                return true;
            }

            qq.each(allowed, function(idx, allowedExt) {
                /**
                 * If an argument is not a string, ignore it.  Added when a possible issue with MooTools hijacking the
                 * `allowedExtensions` array was discovered.  See case #735 in the issue tracker for more details.
                 */
                if (qq.isString(allowedExt)) {
                    /*jshint eqeqeq: true, eqnull: true*/
                    var extRegex = new RegExp("\\." + allowedExt + "$", "i");

                    if (fileName.match(extRegex) != null) {
                        valid = true;
                        return false;
                    }
                }
            });

            return valid;
        },

        _formatSize: function(bytes){
            var i = -1;
            do {
                bytes = bytes / 1000;
                i++;
            } while (bytes > 999);

            return Math.max(bytes, 0.1).toFixed(1) + this._options.text.sizeSymbols[i];
        },

        _wrapCallbacks: function() {
            var self, safeCallback;

            self = this;

            safeCallback = function(name, callback, args) {
                var errorMsg;

                try {
                    return callback.apply(self, args);
                }
                catch (exception) {
                    errorMsg = exception.message || exception.toString();
                    self.log("Caught exception in '" + name + "' callback - " + errorMsg, "error");
                }
            };

            /* jshint forin: false, loopfunc: true */
            for (var prop in this._options.callbacks) {
                (function() {
                    var callbackName, callbackFunc;
                    callbackName = prop;
                    callbackFunc = self._options.callbacks[callbackName];
                    self._options.callbacks[callbackName] = function() {
                        return safeCallback(callbackName, callbackFunc, arguments);
                    };
                }());
            }
        },

        _parseFileOrBlobDataName: function(fileOrBlobData) {
            var name;

            if (qq.isFileOrInput(fileOrBlobData)) {
                if (fileOrBlobData.value) {
                    // it is a file input
                    // get input value and remove path to normalize
                    name = fileOrBlobData.value.replace(/.*(\/|\\)/, "");
                } else {
                    // fix missing properties in Safari 4 and firefox 11.0a2
                    name = (fileOrBlobData.fileName !== null && fileOrBlobData.fileName !== undefined) ? fileOrBlobData.fileName : fileOrBlobData.name;
                }
            }
            else {
                name = fileOrBlobData.name;
            }

            return name;
        },

        _parseFileOrBlobDataSize: function(fileOrBlobData) {
            var size;

            if (qq.isFileOrInput(fileOrBlobData)) {
                if (fileOrBlobData.value === undefined) {
                    // fix missing properties in Safari 4 and firefox 11.0a2
                    size = (fileOrBlobData.fileSize !== null && fileOrBlobData.fileSize !== undefined) ? fileOrBlobData.fileSize : fileOrBlobData.size;
                }
            }
            else {
                size = fileOrBlobData.blob.size;
            }

            return size;
        },

        _getValidationDescriptor: function(fileOrBlobData) {
            var fileDescriptor = {},
                name = this._parseFileOrBlobDataName(fileOrBlobData),
                size = this._parseFileOrBlobDataSize(fileOrBlobData);

            fileDescriptor.name = name;
            if (size !== undefined) {
                fileDescriptor.size = size;
            }

            return fileDescriptor;
        },

        _getValidationDescriptors: function(fileWrappers) {
            var self = this,
                fileDescriptors = [];

            qq.each(fileWrappers, function(idx, fileWrapper) {
                fileDescriptors.push(self._getValidationDescriptor(fileWrapper.file));
            });

            return fileDescriptors;
        },

        _createStore: function(initialValue, readOnlyValues) {
            var store = {},
                catchall = initialValue,
                copy = function(orig) {
                    if (qq.isObject(orig)) {
                        return qq.extend({}, orig);
                    }
                    return orig;
                },
                getReadOnlyValues = function() {
                    if (qq.isFunction(readOnlyValues)) {
                        return readOnlyValues();
                    }
                    return readOnlyValues;
                },
                includeReadOnlyValues = function(existing) {
                    if (readOnlyValues && qq.isObject(existing)) {
                        qq.extend(existing, getReadOnlyValues());
                    }
                };

            return {
                set: function(val, id) {
                    /*jshint eqeqeq: true, eqnull: true*/
                    if (id == null) {
                        store = {};
                        catchall = copy(val);
                    }
                    else {
                        store[id] = copy(val);
                    }
                },

                get: function(id) {
                    var values;

                    /*jshint eqeqeq: true, eqnull: true*/
                    if (id != null && store[id]) {
                        values = store[id];
                    }
                    else {
                        values = catchall;
                    }

                    includeReadOnlyValues(values);

                    return copy(values);
                },

                remove: function(fileId) {
                    return delete store[fileId];
                },

                reset: function() {
                    store = {};
                    catchall = initialValue;
                }
            };
        },

        // Allows camera access on either the default or an extra button for iOS devices.
        _handleCameraAccess: function() {
            if (this._options.camera.ios && qq.ios()) {
                var acceptIosCamera = "image/*;capture=camera",
                    button = this._options.camera.button,
                    buttonId = button ? this._getButtonId(button) : this._defaultButtonId,
                    optionRoot = this._options;

                // If we are not targeting the default button, it is an "extra" button
                if (buttonId && buttonId !== this._defaultButtonId) {
                    optionRoot = this._extraButtonSpecs[buttonId];
                }

                // Camera access won't work in iOS if the `multiple` attribute is present on the file input
                optionRoot.multiple = false;

                // update the options
                if (optionRoot.validation.acceptFiles === null) {
                    optionRoot.validation.acceptFiles = acceptIosCamera;
                }
                else {
                    optionRoot.validation.acceptFiles += "," + acceptIosCamera;
                }

                // update the already-created button
                qq.each(this._buttons, function(idx, button) {
                    if (button.getButtonId() === buttonId) {
                        button.setMultiple(optionRoot.multiple);
                        button.setAcceptFiles(optionRoot.acceptFiles);

                        return false;
                    }
                });
            }
        },

        // Get the validation options for this button.  Could be the default validation option
        // or a specific one assigned to this particular button.
        _getValidationBase: function(buttonId) {
            var extraButtonSpec = this._extraButtonSpecs[buttonId];

            return extraButtonSpec ? extraButtonSpec.validation : this._options.validation;
        }
    };
}());

/*globals qq*/
(function(){
    "use strict";

    qq.FineUploaderBasic = function(o) {
        // These options define FineUploaderBasic mode.
        this._options = {
            debug: false,
            button: null,
            multiple: true,
            maxConnections: 3,
            disableCancelForFormUploads: false,
            autoUpload: true,

            request: {
                endpoint: "/server/upload",
                params: {},
                paramsInBody: true,
                customHeaders: {},
                forceMultipart: true,
                inputName: "qqfile",
                uuidName: "qquuid",
                totalFileSizeName: "qqtotalfilesize",
                filenameParam: "qqfilename"
            },

            validation: {
                allowedExtensions: [],
                sizeLimit: 0,
                minSizeLimit: 0,
                itemLimit: 0,
                stopOnFirstInvalidFile: true,
                acceptFiles: null,
                image: {
                    maxHeight: 0,
                    maxWidth: 0,
                    minHeight: 0,
                    minWidth: 0
                }
            },

            callbacks: {
                onSubmit: function(id, name){},
                onSubmitted: function(id, name){},
                onComplete: function(id, name, responseJSON, maybeXhr){},
                onAllComplete: function(successful, failed) {},
                onCancel: function(id, name){},
                onUpload: function(id, name){},
                onUploadChunk: function(id, name, chunkData){},
                onUploadChunkSuccess: function(id, chunkData, responseJSON, xhr){},
                onResume: function(id, fileName, chunkData){},
                onProgress: function(id, name, loaded, total){},
                onError: function(id, name, reason, maybeXhrOrXdr) {},
                onAutoRetry: function(id, name, attemptNumber) {},
                onManualRetry: function(id, name) {},
                onValidateBatch: function(fileOrBlobData) {},
                onValidate: function(fileOrBlobData) {},
                onSubmitDelete: function(id) {},
                onDelete: function(id){},
                onDeleteComplete: function(id, xhrOrXdr, isError){},
                onPasteReceived: function(blob) {},
                onStatusChange: function(id, oldStatus, newStatus) {},
                onSessionRequestComplete: function(response, success, xhrOrXdr) {}
            },

            messages: {
                typeError: "{file} has an invalid extension. Valid extension(s): {extensions}.",
                sizeError: "{file} is too large, maximum file size is {sizeLimit}.",
                minSizeError: "{file} is too small, minimum file size is {minSizeLimit}.",
                emptyError: "{file} is empty, please select files again without it.",
                noFilesError: "No files to upload.",
                tooManyItemsError: "Too many items ({netItems}) would be uploaded.  Item limit is {itemLimit}.",
                maxHeightImageError: "Image is too tall.",
                maxWidthImageError: "Image is too wide.",
                minHeightImageError: "Image is not tall enough.",
                minWidthImageError: "Image is not wide enough.",
                retryFailTooManyItems: "Retry failed - you have reached your file limit.",
                onLeave: "The files are being uploaded, if you leave now the upload will be canceled."
            },

            retry: {
                enableAuto: false,
                maxAutoAttempts: 3,
                autoAttemptDelay: 5,
                preventRetryResponseProperty: "preventRetry"
            },

            classes: {
                buttonHover: "qq-upload-button-hover",
                buttonFocus: "qq-upload-button-focus"
            },

            chunking: {
                enabled: false,
                partSize: 2000000,
                paramNames: {
                    partIndex: "qqpartindex",
                    partByteOffset: "qqpartbyteoffset",
                    chunkSize: "qqchunksize",
                    totalFileSize: "qqtotalfilesize",
                    totalParts: "qqtotalparts"
                }
            },

            resume: {
                enabled: false,
                id: null,
                cookiesExpireIn: 7, //days
                paramNames: {
                    resuming: "qqresume"
                }
            },

            formatFileName: function(fileOrBlobName) {
                if (fileOrBlobName !== undefined && fileOrBlobName.length > 33) {
                    fileOrBlobName = fileOrBlobName.slice(0, 19) + "..." + fileOrBlobName.slice(-14);
                }
                return fileOrBlobName;
            },

            text: {
                defaultResponseError: "Upload failure reason unknown",
                sizeSymbols: ["kB", "MB", "GB", "TB", "PB", "EB"]
            },

            deleteFile : {
                enabled: false,
                method: "DELETE",
                endpoint: "/server/upload",
                customHeaders: {},
                params: {}
            },

            cors: {
                expected: false,
                sendCredentials: false,
                allowXdr: false
            },

            blobs: {
                defaultName: "misc_data"
            },

            paste: {
                targetElement: null,
                defaultName: "pasted_image"
            },

            camera: {
                ios: false,

                // if ios is true: button is null means target the default button, otherwise target the button specified
                button: null
            },

            // This refers to additional upload buttons to be handled by Fine Uploader.
            // Each element is an object, containing `element` as the only required
            // property.  The `element` must be a container that will ultimately
            // contain an invisible `<input type="file">` created by Fine Uploader.
            // Optional properties of each object include `multiple`, `validation`,
            // and `folders`.
            extraButtons: [],

            // Depends on the session module.  Used to query the server for an initial file list
            // during initialization and optionally after a `reset`.
            session: {
                endpoint: null,
                params: {},
                customHeaders: {},
                refreshOnReset: true
            },

            // Send parameters associated with an existing form along with the files
            form: {
                // Element ID, HTMLElement, or null
                element: "qq-form",

                // Overrides the base `autoUpload`, unless `element` is null.
                autoUpload: false,

                // true = upload files on form submission (and squelch submit event)
                interceptSubmit: true
            }
        };

        // Replace any default options with user defined ones
        qq.extend(this._options, o, true);

        this._buttons = [];
        this._extraButtonSpecs = {};
        this._buttonIdsForFileIds = [];

        this._wrapCallbacks();
        this._disposeSupport =  new qq.DisposeSupport();

        this._storedIds = [];
        this._autoRetries = [];
        this._retryTimeouts = [];
        this._preventRetries = [];
        this._thumbnailUrls = [];

        this._netUploadedOrQueued = 0;
        this._netUploaded = 0;
        this._uploadData = this._createUploadDataTracker();

        this._initFormSupportAndParams();

        this._deleteFileParamsStore = this._createStore(this._options.deleteFile.params);

        this._endpointStore = this._createStore(this._options.request.endpoint);
        this._deleteFileEndpointStore = this._createStore(this._options.deleteFile.endpoint);

        this._handler = this._createUploadHandler();

        this._deleteHandler = qq.DeleteFileAjaxRequester && this._createDeleteHandler();

        if (this._options.button) {
            this._defaultButtonId = this._createUploadButton({element: this._options.button}).getButtonId();
        }

        this._generateExtraButtonSpecs();

        this._handleCameraAccess();

        if (this._options.paste.targetElement) {
            if (qq.PasteSupport) {
                this._pasteHandler = this._createPasteHandler();
            }
            else {
                qq.log("Paste support module not found", "info");
            }
        }

        this._preventLeaveInProgress();

        this._imageGenerator = qq.ImageGenerator && new qq.ImageGenerator(qq.bind(this.log, this));
        this._refreshSessionData();

        this._succeededSinceLastAllComplete = [];
        this._failedSinceLastAllComplete = [];
    };

    // Define the private & public API methods.
    qq.FineUploaderBasic.prototype = qq.basePublicApi;
    qq.extend(qq.FineUploaderBasic.prototype, qq.basePrivateApi);
}());

/*globals qq, XDomainRequest*/
/** Generic class for sending non-upload ajax requests and handling the associated responses **/
qq.AjaxRequester = function (o) {
    "use strict";

    var log, shouldParamsBeInQueryString,
        queue = [],
        requestData = {},
        options = {
            validMethods: ["POST"],
            method: "POST",
            contentType: "application/x-www-form-urlencoded",
            maxConnections: 3,
            customHeaders: {},
            endpointStore: {},
            paramsStore: {},
            mandatedParams: {},
            allowXRequestedWithAndCacheControl: true,
            successfulResponseCodes: {
                "DELETE": [200, 202, 204],
                "POST": [200, 204],
                "GET": [200]
            },
            cors: {
                expected: false,
                sendCredentials: false
            },
            log: function (str, level) {},
            onSend: function (id) {},
            onComplete: function (id, xhrOrXdr, isError) {},
            onProgress: null
        };

    qq.extend(options, o);
    log = options.log;

    if (qq.indexOf(options.validMethods, options.method) < 0) {
        throw new Error("'" + options.method + "' is not a supported method for this type of request!");
    }

    // [Simple methods](http://www.w3.org/TR/cors/#simple-method)
    // are defined by the W3C in the CORS spec as a list of methods that, in part,
    // make a CORS request eligible to be exempt from preflighting.
    function isSimpleMethod() {
        return qq.indexOf(["GET", "POST", "HEAD"], options.method) >= 0;
    }

    // [Simple headers](http://www.w3.org/TR/cors/#simple-header)
    // are defined by the W3C in the CORS spec as a list of headers that, in part,
    // make a CORS request eligible to be exempt from preflighting.
    function containsNonSimpleHeaders(headers) {
        var containsNonSimple = false;

        qq.each(containsNonSimple, function(idx, header) {
            if (qq.indexOf(["Accept", "Accept-Language", "Content-Language", "Content-Type"], header) < 0) {
                containsNonSimple = true;
                return false;
            }
        });

        return containsNonSimple;
    }

    function isXdr(xhr) {
        //The `withCredentials` test is a commonly accepted way to determine if XHR supports CORS.
        return options.cors.expected && xhr.withCredentials === undefined;
    }

    // Returns either a new `XMLHttpRequest` or `XDomainRequest` instance.
    function getCorsAjaxTransport() {
        var xhrOrXdr;

        if (window.XMLHttpRequest || window.ActiveXObject) {
            xhrOrXdr = qq.createXhrInstance();

            if (xhrOrXdr.withCredentials === undefined) {
                xhrOrXdr = new XDomainRequest();
            }
        }

        return xhrOrXdr;
    }

    // Returns either a new XHR/XDR instance, or an existing one for the associated `File` or `Blob`.
    function getXhrOrXdr(id, dontCreateIfNotExist) {
        var xhrOrXdr = requestData[id].xhr;

        if (!xhrOrXdr && !dontCreateIfNotExist) {
            if (options.cors.expected) {
                xhrOrXdr = getCorsAjaxTransport();
            }
            else {
                xhrOrXdr = qq.createXhrInstance();
            }

            requestData[id].xhr = xhrOrXdr;
        }

        return xhrOrXdr;
    }

    // Removes element from queue, sends next request
    function dequeue(id) {
        var i = qq.indexOf(queue, id),
            max = options.maxConnections,
            nextId;

        delete requestData[id];
        queue.splice(i, 1);

        if (queue.length >= max && i < max) {
            nextId = queue[max - 1];
            sendRequest(nextId);
        }
    }

    function onComplete(id, xdrError) {
        var xhr = getXhrOrXdr(id),
            method = options.method,
            isError = xdrError === true;

        dequeue(id);

        if (isError) {
            log(method + " request for " + id + " has failed", "error");
        }
        else if (!isXdr(xhr) && !isResponseSuccessful(xhr.status)) {
            isError = true;
            log(method + " request for " + id + " has failed - response code " + xhr.status, "error");
        }

        options.onComplete(id, xhr, isError);
    }

    function getParams(id) {
        var onDemandParams = requestData[id].additionalParams,
            mandatedParams = options.mandatedParams,
            params;

        if (options.paramsStore.get) {
            params = options.paramsStore.get(id);
        }

        if (onDemandParams) {
            qq.each(onDemandParams, function (name, val) {
                params = params || {};
                params[name] = val;
            });
        }

        if (mandatedParams) {
            qq.each(mandatedParams, function (name, val) {
                params = params || {};
                params[name] = val;
            });
        }

        return params;
    }

    function sendRequest(id) {
        var xhr = getXhrOrXdr(id),
            method = options.method,
            params = getParams(id),
            payload = requestData[id].payload,
            url;

        options.onSend(id);

        url = createUrl(id, params);

        // XDR and XHR status detection APIs differ a bit.
        if (isXdr(xhr)) {
            xhr.onload = getXdrLoadHandler(id);
            xhr.onerror = getXdrErrorHandler(id);
        }
        else {
            xhr.onreadystatechange = getXhrReadyStateChangeHandler(id);
        }


        registerForUploadProgress(id);

        // The last parameter is assumed to be ignored if we are actually using `XDomainRequest`.
        xhr.open(method, url, true);

        // Instruct the transport to send cookies along with the CORS request,
        // unless we are using `XDomainRequest`, which is not capable of this.
        if (options.cors.expected && options.cors.sendCredentials && !isXdr(xhr)) {
            xhr.withCredentials = true;
        }

        setHeaders(id);

        log("Sending " + method + " request for " + id);

        if (payload) {
            xhr.send(payload);
        }
        else if (shouldParamsBeInQueryString || !params) {
            xhr.send();
        }
        else if (params && options.contentType && options.contentType.toLowerCase().indexOf("application/x-www-form-urlencoded") >= 0) {
            xhr.send(qq.obj2url(params, ""));
        }
        else if (params && options.contentType && options.contentType.toLowerCase().indexOf("application/json") >= 0) {
            xhr.send(JSON.stringify(params));
        }
        else {
            xhr.send(params);
        }

        return xhr;
    }

    function createUrl(id, params) {
        var endpoint = options.endpointStore.get(id),
            addToPath = requestData[id].addToPath;

        /*jshint -W116,-W041 */
        if (addToPath != undefined) {
            endpoint += "/" + addToPath;
        }

        if (shouldParamsBeInQueryString && params) {
            return qq.obj2url(params, endpoint);
        }
        else {
            return endpoint;
        }
    }

    // Invoked by the UA to indicate a number of possible states that describe
    // a live `XMLHttpRequest` transport.
    function getXhrReadyStateChangeHandler(id) {
        return function () {
            if (getXhrOrXdr(id).readyState === 4) {
                onComplete(id);
            }
        };
    }

    function registerForUploadProgress(id) {
        var onProgress = options.onProgress;

        if (onProgress) {
            getXhrOrXdr(id).upload.onprogress = function(e) {
                if (e.lengthComputable) {
                    onProgress(id, e.loaded, e.total);
                }
            };
        }
    }

    // This will be called by IE to indicate **success** for an associated
    // `XDomainRequest` transported request.
    function getXdrLoadHandler(id) {
        return function () {
            onComplete(id);
        };
    }

    // This will be called by IE to indicate **failure** for an associated
    // `XDomainRequest` transported request.
    function getXdrErrorHandler(id) {
        return function () {
            onComplete(id, true);
        };
    }

    function setHeaders(id) {
        var xhr = getXhrOrXdr(id),
            customHeaders = options.customHeaders,
            onDemandHeaders = requestData[id].additionalHeaders || {},
            method = options.method,
            allHeaders = {};

        // If XDomainRequest is being used, we can't set headers, so just ignore this block.
        if (!isXdr(xhr)) {
            // Only attempt to add X-Requested-With & Cache-Control if permitted
            if (options.allowXRequestedWithAndCacheControl) {
                // Do not add X-Requested-With & Cache-Control if this is a cross-origin request
                // OR the cross-origin request contains a non-simple method or header.
                // This is done to ensure a preflight is not triggered exclusively based on the
                // addition of these 2 non-simple headers.
                if (!options.cors.expected || (!isSimpleMethod() || containsNonSimpleHeaders(customHeaders))) {
                    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
                    xhr.setRequestHeader("Cache-Control", "no-cache");
                }
            }

            if (options.contentType && (method === "POST" || method === "PUT")) {
                xhr.setRequestHeader("Content-Type", options.contentType);
            }

            qq.extend(allHeaders, qq.isFunction(customHeaders) ? customHeaders(id) : customHeaders);
            qq.extend(allHeaders, onDemandHeaders);

            qq.each(allHeaders, function (name, val) {
                xhr.setRequestHeader(name, val);
            });
        }
    }

    function isResponseSuccessful(responseCode) {
        return qq.indexOf(options.successfulResponseCodes[options.method], responseCode) >= 0;
    }

    function prepareToSend(id, addToPath, additionalParams, additionalHeaders, payload) {
        requestData[id] = {
            addToPath: addToPath,
            additionalParams: additionalParams,
            additionalHeaders: additionalHeaders,
            payload: payload
        };

        var len = queue.push(id);

        // if too many active connections, wait...
        if (len <= options.maxConnections) {
            return sendRequest(id);
        }
    }


    shouldParamsBeInQueryString = options.method === "GET" || options.method === "DELETE";

    qq.extend(this, {
        // Start the process of sending the request.  The ID refers to the file associated with the request.
        initTransport: function(id) {
            var path, params, headers, payload, cacheBuster;

            return {
                // Optionally specify the end of the endpoint path for the request.
                withPath: function(appendToPath) {
                    path = appendToPath;
                    return this;
                },

                // Optionally specify additional parameters to send along with the request.
                // These will be added to the query string for GET/DELETE requests or the payload
                // for POST/PUT requests.  The Content-Type of the request will be used to determine
                // how these parameters should be formatted as well.
                withParams: function(additionalParams) {
                    params = additionalParams;
                    return this;
                },

                // Optionally specify additional headers to send along with the request.
                withHeaders: function(additionalHeaders) {
                    headers = additionalHeaders;
                    return this;
                },

                // Optionally specify a payload/body for the request.
                withPayload: function(thePayload) {
                    payload = thePayload;
                    return this;
                },

                // Appends a cache buster (timestamp) to the request URL as a query parameter (only if GET or DELETE)
                withCacheBuster: function() {
                    cacheBuster = true;
                    return this;
                },

                // Send the constructed request.
                send: function() {
                    if (cacheBuster && qq.indexOf(["GET", "DELETE"], options.method) >= 0) {
                        params.qqtimestamp = new Date().getTime();
                    }

                    return prepareToSend(id, path, params, headers, payload);
                }
            };
        },

        canceled: function(id) {
            dequeue(id);
        }
    });
};

/*globals qq*/
/**
 * Base upload handler module.  Delegates to more specific handlers.
 *
 * @param o Options.  Passed along to the specific handler submodule as well.
 * @param namespace [optional] Namespace for the specific handler.
 */
qq.UploadHandler = function(o, namespace) {
    "use strict";

    var queue = [],
        options, log, handlerImpl;

    // Default options, can be overridden by the user
    options = {
        debug: false,
        forceMultipart: true,
        paramsInBody: false,
        paramsStore: {},
        endpointStore: {},
        filenameParam: "qqfilename",
        cors: {
            expected: false,
            sendCredentials: false
        },
        maxConnections: 3, // maximum number of concurrent uploads
        uuidName: "qquuid",
        totalFileSizeName: "qqtotalfilesize",
        chunking: {
            enabled: false,
            partSize: 2000000, //bytes
            paramNames: {
                partIndex: "qqpartindex",
                partByteOffset: "qqpartbyteoffset",
                chunkSize: "qqchunksize",
                totalParts: "qqtotalparts",
                filename: "qqfilename"
            }
        },
        resume: {
            enabled: false,
            id: null,
            cookiesExpireIn: 7, //days
            paramNames: {
                resuming: "qqresume"
            }
        },
        log: function(str, level) {},
        onProgress: function(id, fileName, loaded, total){},
        onComplete: function(id, fileName, response, xhr){},
        onCancel: function(id, fileName){},
        onUpload: function(id, fileName){},
        onUploadChunk: function(id, fileName, chunkData){},
        onUploadChunkSuccess: function(id, chunkData, response, xhr){},
        onAutoRetry: function(id, fileName, response, xhr){},
        onResume: function(id, fileName, chunkData){},
        onUuidChanged: function(id, newUuid){},
        getName: function(id) {}

    };
    qq.extend(options, o);

    log = options.log;

    /**
     * Removes element from queue, starts upload of next
     */
    function dequeue(id) {
        var i = qq.indexOf(queue, id),
            max = options.maxConnections,
            nextId;

        if (i >= 0) {
            queue.splice(i, 1);

            if (queue.length >= max && i < max){
                nextId = queue[max-1];
                handlerImpl.upload(nextId);
            }
        }
    }

    function cancelSuccess(id) {
        log("Cancelling " + id);
        options.paramsStore.remove(id);
        dequeue(id);
    }

    function determineHandlerImpl() {
        var handlerType = namespace ? qq[namespace] : qq,
            handlerModuleSubtype = qq.supportedFeatures.ajaxUploading ? "Xhr" : "Form";

        handlerImpl = new handlerType["UploadHandler" + handlerModuleSubtype](
            options,
            {onUploadComplete: dequeue, onUuidChanged: options.onUuidChanged,
                getName: options.getName, getUuid: options.getUuid, getSize: options.getSize, log: log}
        );
    }


    qq.extend(this, {
        /**
         * Adds file or file input to the queue
         * @returns id
         **/
        add: function(id, file) {
            return handlerImpl.add.apply(this, arguments);
        },

        /**
         * Sends the file identified by id
         */
        upload: function(id) {
            var len = queue.push(id);

            // if too many active uploads, wait...
            if (len <= options.maxConnections){
                handlerImpl.upload(id);
                return true;
            }

            return false;
        },

        retry: function(id) {
            var i = qq.indexOf(queue, id);
            if (i >= 0) {
                return handlerImpl.upload(id, true);
            }
            else {
                return this.upload(id);
            }
        },

        /**
         * Cancels file upload by id
         */
        cancel: function(id) {
            var cancelRetVal = handlerImpl.cancel(id);

            if (cancelRetVal instanceof qq.Promise) {
                cancelRetVal.then(function() {
                    cancelSuccess(id);
                });
            }
            else if (cancelRetVal !== false) {
                cancelSuccess(id);
            }
        },

        /**
         * Cancels all queued or in-progress uploads
         */
        cancelAll: function() {
            var self = this,
                queueCopy = [];

            qq.extend(queueCopy, queue);
            qq.each(queueCopy, function(idx, fileId) {
                self.cancel(fileId);
            });

            queue = [];
        },

        getFile: function(id) {
            if (handlerImpl.getFile) {
                return handlerImpl.getFile(id);
            }
        },

        getInput: function(id) {
            if (handlerImpl.getInput) {
                return handlerImpl.getInput(id);
            }
        },

        reset: function() {
            log("Resetting upload handler");
            this.cancelAll();
            queue = [];
            handlerImpl.reset();
        },

        expunge: function(id) {
            if (this.isValid(id)) {
                return handlerImpl.expunge(id);
            }
        },

        /**
         * Determine if the file exists.
         */
        isValid: function(id) {
            return handlerImpl.isValid(id);
        },

        getResumableFilesData: function() {
            if (handlerImpl.getResumableFilesData) {
                return handlerImpl.getResumableFilesData();
            }
            return [];
        },

        /**
         * This may or may not be implemented, depending on the handler.  For handlers where a third-party ID is
         * available (such as the "key" for Amazon S3), this will return that value.  Otherwise, the return value
         * will be undefined.
         *
         * @param id Internal file ID
         * @returns {*} Some identifier used by a 3rd-party service involved in the upload process
         */
        getThirdPartyFileId: function(id) {
            if (handlerImpl.getThirdPartyFileId && this.isValid(id)) {
                return handlerImpl.getThirdPartyFileId(id);
            }
        },

        /**
         * Attempts to pause the associated upload if the specific handler supports this and the file is "valid".
         * @param id ID of the upload/file to pause
         * @returns {boolean} true if the upload was paused
         */
        pause: function(id) {
            if (handlerImpl.pause && this.isValid(id) && handlerImpl.pause(id)) {
                dequeue(id);
                return true;
            }
        }
    });

    determineHandlerImpl();
};

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

/* globals qq */
/**
 * Common API exposed to creators of XHR handlers.  This is reused and possibly overriding in some cases by specific
 * XHR upload handlers.
 *
 * @constructor
 */
qq.AbstractUploadHandlerXhr = function(spec) {
    "use strict";

    var publicApi = this,
        options = spec.options,
        proxy = spec.proxy,
        fileState = {},
        chunking = options.chunking,
        onUpload = proxy.onUpload,
        onCancel = proxy.onCancel,
        getName = proxy.getName,
        getSize = proxy.getSize,
        log = proxy.log;


    function getChunk(fileOrBlob, startByte, endByte) {
        if (fileOrBlob.slice) {
            return fileOrBlob.slice(startByte, endByte);
        }
        else if (fileOrBlob.mozSlice) {
            return fileOrBlob.mozSlice(startByte, endByte);
        }
        else if (fileOrBlob.webkitSlice) {
            return fileOrBlob.webkitSlice(startByte, endByte);
        }
    }

    function abort(id) {
        var xhr = fileState[id].xhr,
            ajaxRequester = fileState[id].currentAjaxRequester;

        xhr.onreadystatechange = null;
        xhr.upload.onprogress = null;
        xhr.abort();
        ajaxRequester && ajaxRequester.canceled && ajaxRequester.canceled(id);
    }

    qq.extend(this, {
        /**
         * Adds File or Blob to the queue
         **/
        add: function(id, fileOrBlobData) {
            if (qq.isFile(fileOrBlobData)) {
                fileState[id] = {file: fileOrBlobData};
            }
            else if (qq.isBlob(fileOrBlobData.blob)) {
                fileState[id] =  {blobData: fileOrBlobData};
            }
            else {
                throw new Error("Passed obj is not a File or BlobData (in qq.UploadHandlerXhr)");
            }
        },

        getFile: function(id) {
            if (fileState[id]) {
                return fileState[id].file || fileState[id].blobData.blob;
            }
        },

        isValid: function(id) {
            return fileState[id] !== undefined;
        },

        reset: function() {
            fileState.length = 0;
        },

        expunge: function(id) {
            var xhr = fileState[id].xhr;

            xhr && abort(id);

            delete fileState[id];
        },

        /**
         * Sends the file identified by id to the server
         */
        upload: function(id, retry) {
            fileState[id] && delete fileState[id].paused;
            return onUpload(id, retry);
        },

        cancel: function(id) {
            var onCancelRetVal = onCancel(id, getName(id));

            if (onCancelRetVal instanceof qq.Promise) {
                return onCancelRetVal.then(function() {
                    fileState[id].canceled = true;
                    this.expunge(id);
                });
            }
            else if (onCancelRetVal !== false) {
                fileState[id].canceled = true;
                this.expunge(id);
                return true;
            }

            return false;
        },

        pause: function(id) {
            var xhr = fileState[id].xhr;

            if(xhr) {
                log(qq.format("Aborting XHR upload for {} '{}' due to pause instruction.", id, getName(id)));
                fileState[id].paused = true;
                abort(id);
                return true;
            }
        },

        /**
         * Creates an XHR instance for this file and stores it in the fileState.
         *
         * @param id File ID
         * @returns {XMLHttpRequest}
         */
        _createXhr: function(id) {
            return this._registerXhr(id, qq.createXhrInstance());
        },

        /**
         * Registers an XHR transport instance created elsewhere.
         *
         * @param id ID of the associated file
         * @param xhr XMLHttpRequest object instance
         * @returns {XMLHttpRequest}
         */
        _registerXhr: function(id, xhr, ajaxRequester) {
            fileState[id].xhr = xhr;
            fileState[id].currentAjaxRequester = ajaxRequester;
            return xhr;
        },

        _getMimeType: function(id) {
            return publicApi.getFile(id).type;
        },

        /**
         * @param id ID of the associated file
         * @returns {number} Number of parts this file can be divided into, or undefined if chunking is not supported in this UA
         */
        _getTotalChunks: function(id) {
            if (chunking) {
                var fileSize = getSize(id),
                    chunkSize = chunking.partSize;

                return Math.ceil(fileSize / chunkSize);
            }
        },

        _getChunkData: function(id, chunkIndex) {
            var chunkSize = chunking.partSize,
                fileSize = getSize(id),
                fileOrBlob = publicApi.getFile(id),
                startBytes = chunkSize * chunkIndex,
                endBytes = startBytes+chunkSize >= fileSize ? fileSize : startBytes+chunkSize,
                totalChunks = this._getTotalChunks(id);

            return {
                part: chunkIndex,
                start: startBytes,
                end: endBytes,
                count: totalChunks,
                blob: getChunk(fileOrBlob, startBytes, endBytes),
                size: endBytes - startBytes
            };
        },

        _getChunkDataForCallback: function(chunkData) {
            return {
                partIndex: chunkData.part,
                startByte: chunkData.start + 1,
                endByte: chunkData.end,
                totalParts: chunkData.count
            };
        },

        _getFileState: function(id) {
            return fileState[id];
        }
    });
};

/*globals qq */
/*jshint -W117 */
qq.WindowReceiveMessage = function(o) {
    "use strict";

    var options = {
            log: function(message, level) {}
        },
        callbackWrapperDetachers = {};

    qq.extend(options, o);

    qq.extend(this, {
        receiveMessage : function(id, callback) {
            var onMessageCallbackWrapper = function(event) {
                    callback(event.data);
                };

            if (window.postMessage) {
                callbackWrapperDetachers[id] = qq(window).attach("message", onMessageCallbackWrapper);
            }
            else {
                log("iframe message passing not supported in this browser!", "error");
            }
        },

        stopReceivingMessages : function(id) {
            if (window.postMessage) {
                var detacher = callbackWrapperDetachers[id];
                if (detacher) {
                    detacher();
                }
            }
        }
    });
};

/*globals qq */
/**
 * Defines the public API for FineUploader mode.
 */
(function(){
    "use strict";

    qq.uiPublicApi = {
        clearStoredFiles: function() {
            this._parent.prototype.clearStoredFiles.apply(this, arguments);
            this._templating.clearFiles();
        },

        addExtraDropzone: function(element){
            this._dnd && this._dnd.setupExtraDropzone(element);
        },

        removeExtraDropzone: function(element){
            if (this._dnd) {
                return this._dnd.removeDropzone(element);
            }
        },

        getItemByFileId: function(id) {
            return this._templating.getFileContainer(id);
        },

        reset: function() {
            this._parent.prototype.reset.apply(this, arguments);
            this._templating.reset();

            if (!this._options.button && this._templating.getButton()) {
                this._defaultButtonId = this._createUploadButton({element: this._templating.getButton()}).getButtonId();
            }

            if (this._dnd) {
                this._dnd.dispose();
                this._dnd = this._setupDragAndDrop();
            }

            this._totalFilesInBatch = 0;
            this._filesInBatchAddedToUi = 0;

            this._setupClickAndEditEventHandlers();
        },

        pauseUpload: function(id) {
            var paused = this._parent.prototype.pauseUpload.apply(this, arguments);

            paused && this._templating.uploadPaused(id);
            return paused;
        },

        continueUpload: function(id) {
            var continued = this._parent.prototype.continueUpload.apply(this, arguments);

            continued && this._templating.uploadContinued(id);
            return continued;
        },

        getId: function(fileContainerOrChildEl) {
            return this._templating.getFileId(fileContainerOrChildEl);
        },

        getDropTarget: function(fileId) {
            var file = this.getFile(fileId);

            return file.qqDropTarget;
        }
    };




    /**
     * Defines the private (internal) API for FineUploader mode.
     */
    qq.uiPrivateApi = {
        _getButton: function(buttonId) {
            var button = this._parent.prototype._getButton.apply(this, arguments);

            if (!button) {
                if (buttonId === this._defaultButtonId) {
                    button = this._templating.getButton();
                }
            }

            return button;
        },

        _removeFileItem: function(fileId) {
            this._templating.removeFile(fileId);
        },

        _setupClickAndEditEventHandlers: function() {
            this._fileButtonsClickHandler = qq.FileButtonsClickHandler && this._bindFileButtonsClickEvent();

            // A better approach would be to check specifically for focusin event support by querying the DOM API,
            // but the DOMFocusIn event is not exposed as a property, so we have to resort to UA string sniffing.
            this._focusinEventSupported = !qq.firefox();

            if (this._isEditFilenameEnabled())
            {
                this._filenameClickHandler = this._bindFilenameClickEvent();
                this._filenameInputFocusInHandler = this._bindFilenameInputFocusInEvent();
                this._filenameInputFocusHandler = this._bindFilenameInputFocusEvent();
            }
        },

        _setupDragAndDrop: function() {
            var self = this,
                dropZoneElements = this._options.dragAndDrop.extraDropzones,
                templating = this._templating,
                defaultDropZone = templating.getDropZone();

            defaultDropZone && dropZoneElements.push(defaultDropZone);

            return new qq.DragAndDrop({
                dropZoneElements: dropZoneElements,
                allowMultipleItems: this._options.multiple,
                classes: {
                    dropActive: this._options.classes.dropActive
                },
                callbacks: {
                    processingDroppedFiles: function() {
                        templating.showDropProcessing();
                    },
                    processingDroppedFilesComplete: function(files, targetEl) {
                        templating.hideDropProcessing();

                        qq.each(files, function(idx, file) {
                            file.qqDropTarget = targetEl;
                        });

                        if (files.length) {
                            self.addFiles(files, null, null);
                        }
                    },
                    dropError: function(code, errorData) {
                        self._itemError(code, errorData);
                    },
                    dropLog: function(message, level) {
                        self.log(message, level);
                    }
                }
            });
        },

        _bindFileButtonsClickEvent: function() {
            var self = this;

            return new qq.FileButtonsClickHandler({
                templating: this._templating,

                log: function(message, lvl) {
                    self.log(message, lvl);
                },

                onDeleteFile: function(fileId) {
                    self.deleteFile(fileId);
                },

                onCancel: function(fileId) {
                    self.cancel(fileId);
                },

                onRetry: function(fileId) {
                    qq(self._templating.getFileContainer(fileId)).removeClass(self._classes.retryable);
                    self.retry(fileId);
                },

                onPause: function(fileId) {
                    self.pauseUpload(fileId);
                },

                onContinue: function(fileId) {
                    self.continueUpload(fileId);
                },

                onGetName: function(fileId) {
                    return self.getName(fileId);
                }
            });
        },

        _isEditFilenameEnabled: function() {
            /*jshint -W014 */
            return this._templating.isEditFilenamePossible()
                && !this._options.autoUpload
                && qq.FilenameClickHandler
                && qq.FilenameInputFocusHandler
                && qq.FilenameInputFocusHandler;
        },

        _filenameEditHandler: function() {
            var self = this,
                templating = this._templating;

            return {
                templating: templating,
                log: function(message, lvl) {
                    self.log(message, lvl);
                },
                onGetUploadStatus: function(fileId) {
                    return self.getUploads({id: fileId}).status;
                },
                onGetName: function(fileId) {
                    return self.getName(fileId);
                },
                onSetName: function(id, newName) {
                    var formattedFilename = self._options.formatFileName(newName);

                    templating.updateFilename(id, formattedFilename);
                    self.setName(id, newName);
                },
                onEditingStatusChange: function(id, isEditing) {
                    var qqInput = qq(templating.getEditInput(id)),
                        qqFileContainer = qq(templating.getFileContainer(id));

                    if (isEditing) {
                        qqInput.addClass("qq-editing");
                        templating.hideFilename(id);
                        templating.hideEditIcon(id);
                    }
                    else {
                        qqInput.removeClass("qq-editing");
                        templating.showFilename(id);
                        templating.showEditIcon(id);
                    }

                    // Force IE8 and older to repaint
                    qqFileContainer.addClass("qq-temp").removeClass("qq-temp");
                }
            };
        },

        _onUploadStatusChange: function(id, oldStatus, newStatus) {
            this._parent.prototype._onUploadStatusChange.apply(this, arguments);

            if (this._isEditFilenameEnabled()) {
                // Status for a file exists before it has been added to the DOM, so we must be careful here.
                if (this._templating.getFileContainer(id) && newStatus !== qq.status.SUBMITTED) {
                    this._templating.markFilenameEditable(id);
                    this._templating.hideEditIcon(id);
                }
            }
        },

        _bindFilenameInputFocusInEvent: function() {
            var spec = qq.extend({}, this._filenameEditHandler());

            return new qq.FilenameInputFocusInHandler(spec);
        },

        _bindFilenameInputFocusEvent: function() {
            var spec = qq.extend({}, this._filenameEditHandler());

            return new qq.FilenameInputFocusHandler(spec);
        },

        _bindFilenameClickEvent: function() {
            var spec = qq.extend({}, this._filenameEditHandler());

            return new qq.FilenameClickHandler(spec);
        },

        _storeForLater: function(id) {
            this._parent.prototype._storeForLater.apply(this, arguments);
            this._templating.hideSpinner(id);
        },

        _onSubmit: function(id, name) {
            this._parent.prototype._onSubmit.apply(this, arguments);
            this._addToList(id, name);
        },

        // The file item has been added to the DOM.
        _onSubmitted: function(id) {
            // If the edit filename feature is enabled, mark the filename element as "editable" and the associated edit icon
            if (this._isEditFilenameEnabled()) {
                this._templating.markFilenameEditable(id);
                this._templating.showEditIcon(id);

                // If the focusin event is not supported, we must add a focus handler to the newly create edit filename text input
                if (!this._focusinEventSupported) {
                    this._filenameInputFocusHandler.addHandler(this._templating.getEditInput(id));
                }
            }
        },

        // Update the progress bar & percentage as the file is uploaded
        _onProgress: function(id, name, loaded, total){
            this._parent.prototype._onProgress.apply(this, arguments);

            this._templating.updateProgress(id, loaded, total);

            if (loaded === total) {
                this._templating.hideCancel(id);
                this._templating.hidePause(id);

                this._templating.setStatusText(id, this._options.text.waitingForResponse);

                // If last byte was sent, display total file size
                this._displayFileSize(id);
            }
            else {
                // If still uploading, display percentage - total size is actually the total request(s) size
                this._displayFileSize(id, loaded, total);
            }
        },

        _onComplete: function(id, name, result, xhr) {
            var parentRetVal = this._parent.prototype._onComplete.apply(this, arguments),
                templating = this._templating,
                self = this;

            function completeUpload(result) {
                templating.setStatusText(id);

                qq(templating.getFileContainer(id)).removeClass(self._classes.retrying);
                templating.hideProgress(id);

                if (!self._options.disableCancelForFormUploads || qq.supportedFeatures.ajaxUploading) {
                    templating.hideCancel(id);
                }
                templating.hideSpinner(id);

                if (result.success) {
                    self._markFileAsSuccessful(id);
                }
                else {
                    qq(templating.getFileContainer(id)).addClass(self._classes.fail);

                    if (self._templating.isRetryPossible() && !self._preventRetries[id]) {
                        qq(templating.getFileContainer(id)).addClass(self._classes.retryable);
                    }
                    self._controlFailureTextDisplay(id, result);
                }
            }

            // The parent may need to perform some async operation before we can accurately determine the status of the upload.
            if (parentRetVal instanceof qq.Promise) {
                parentRetVal.done(function(newResult) {
                    completeUpload(newResult);
                });

            }
            else {
                completeUpload(result);
            }

            return parentRetVal;
        },

        _markFileAsSuccessful: function(id) {
            var templating = this._templating;

            if (this._isDeletePossible()) {
                templating.showDeleteButton(id);
            }

            qq(templating.getFileContainer(id)).addClass(this._classes.success);

            this._maybeUpdateThumbnail(id);
        },

        _onUpload: function(id, name){
            var parentRetVal = this._parent.prototype._onUpload.apply(this, arguments);

            this._templating.showSpinner(id);

            return parentRetVal;
        },

        _onUploadChunk: function(id, chunkData) {
            this._parent.prototype._onUploadChunk.apply(this, arguments);

            // Only display the pause button if we have finished uploading at least one chunk
            chunkData.partIndex > 0 && this._templating.allowPause(id);
        },

        _onCancel: function(id, name) {
            this._parent.prototype._onCancel.apply(this, arguments);
            this._removeFileItem(id);
        },

        _onBeforeAutoRetry: function(id) {
            var retryNumForDisplay, maxAuto, retryNote;

            this._parent.prototype._onBeforeAutoRetry.apply(this, arguments);

            this._showCancelLink(id);

            if (this._options.retry.showAutoRetryNote) {
                retryNumForDisplay = this._autoRetries[id] + 1;
                maxAuto = this._options.retry.maxAutoAttempts;

                retryNote = this._options.retry.autoRetryNote.replace(/\{retryNum\}/g, retryNumForDisplay);
                retryNote = retryNote.replace(/\{maxAuto\}/g, maxAuto);

                this._templating.setStatusText(id, retryNote);
                qq(this._templating.getFileContainer(id)).addClass(this._classes.retrying);
            }
        },

        //return false if we should not attempt the requested retry
        _onBeforeManualRetry: function(id) {
            if (this._parent.prototype._onBeforeManualRetry.apply(this, arguments)) {
                this._templating.resetProgress(id);
                qq(this._templating.getFileContainer(id)).removeClass(this._classes.fail);
                this._templating.setStatusText(id);
                this._templating.showSpinner(id);
                this._showCancelLink(id);
                return true;
            }
            else {
                qq(this._templating.getFileContainer(id)).addClass(this._classes.retryable);
                return false;
            }
        },

        _onSubmitDelete: function(id) {
            var onSuccessCallback = qq.bind(this._onSubmitDeleteSuccess, this);

            this._parent.prototype._onSubmitDelete.call(this, id, onSuccessCallback);
        },

        _onSubmitDeleteSuccess: function(id, uuid, additionalMandatedParams) {
            if (this._options.deleteFile.forceConfirm) {
                this._showDeleteConfirm.apply(this, arguments);
            }
            else {
                this._sendDeleteRequest.apply(this, arguments);
            }
        },

        _onDeleteComplete: function(id, xhr, isError) {
            this._parent.prototype._onDeleteComplete.apply(this, arguments);

            this._templating.hideSpinner(id);

            if (isError) {
                this._templating.setStatusText(id, this._options.deleteFile.deletingFailedText);
                this._templating.showDeleteButton(id);
            }
            else {
                this._removeFileItem(id);
            }
        },

        _sendDeleteRequest: function(id, uuid, additionalMandatedParams) {
            this._templating.hideDeleteButton(id);
            this._templating.showSpinner(id);
            this._templating.setStatusText(id, this._options.deleteFile.deletingStatusText);
            this._deleteHandler.sendDelete.apply(this, arguments);
        },

        _showDeleteConfirm: function(id, uuid, mandatedParams) {
            /*jshint -W004 */
            var fileName = this.getName(id),
                confirmMessage = this._options.deleteFile.confirmMessage.replace(/\{filename\}/g, fileName),
                uuid = this.getUuid(id),
                deleteRequestArgs = arguments,
                self = this,
                retVal;

            retVal = this._options.showConfirm(confirmMessage);

            if (retVal instanceof qq.Promise) {
                retVal.then(function () {
                    self._sendDeleteRequest.apply(self, deleteRequestArgs);
                });
            }
            else if (retVal !== false) {
                self._sendDeleteRequest.apply(self, deleteRequestArgs);
            }
        },

        _addToList: function(id, name, canned) {
            var prependData,
                prependIndex = 0;

            if (this._options.display.prependFiles) {
                if (this._totalFilesInBatch > 1 && this._filesInBatchAddedToUi > 0) {
                    prependIndex = this._filesInBatchAddedToUi - 1;
                }

                prependData = {
                    index: prependIndex
                };
            }

            if (!canned) {
                if (this._options.disableCancelForFormUploads && !qq.supportedFeatures.ajaxUploading) {
                    this._templating.disableCancel();
                }

                if (!this._options.multiple) {
                    this._handler.cancelAll();
                    this._clearList();
                }
            }

            this._templating.addFile(id, this._options.formatFileName(name), prependData);

            if (canned) {
                this._thumbnailUrls[id] && this._templating.updateThumbnail(id, this._thumbnailUrls[id], true);
            }
            else {
                this._templating.generatePreview(id, this.getFile(id));
            }

            this._filesInBatchAddedToUi += 1;

            if (canned ||
                (this._options.display.fileSizeOnSubmit && qq.supportedFeatures.ajaxUploading)) {

                this._displayFileSize(id);
            }
        },

        _clearList: function(){
            this._templating.clearFiles();
            this.clearStoredFiles();
        },

        _displayFileSize: function(id, loadedSize, totalSize) {
            var size = this.getSize(id),
                sizeForDisplay = this._formatSize(size);

            if (size >= 0) {
                if (loadedSize !== undefined && totalSize !== undefined) {
                    sizeForDisplay = this._formatProgress(loadedSize, totalSize);
                }

                this._templating.updateSize(id, sizeForDisplay);
            }
        },

        _formatProgress: function (uploadedSize, totalSize) {
            var message = this._options.text.formatProgress;
            function r(name, replacement) { message = message.replace(name, replacement); }

            r("{percent}", Math.round(uploadedSize / totalSize * 100));
            r("{total_size}", this._formatSize(totalSize));
            return message;
        },

        _controlFailureTextDisplay: function(id, response) {
            var mode, maxChars, responseProperty, failureReason, shortFailureReason;

            mode = this._options.failedUploadTextDisplay.mode;
            maxChars = this._options.failedUploadTextDisplay.maxChars;
            responseProperty = this._options.failedUploadTextDisplay.responseProperty;

            if (mode === "custom") {
                failureReason = response[responseProperty];
                if (failureReason) {
                    if (failureReason.length > maxChars) {
                        shortFailureReason = failureReason.substring(0, maxChars) + "...";
                    }
                }
                else {
                    failureReason = this._options.text.failUpload;
                    this.log("'" + responseProperty + "' is not a valid property on the server response.", "warn");
                }

                this._templating.setStatusText(id, shortFailureReason || failureReason);

                if (this._options.failedUploadTextDisplay.enableTooltip) {
                    this._showTooltip(id, failureReason);
                }
            }
            else if (mode === "default") {
                this._templating.setStatusText(id, this._options.text.failUpload);
            }
            else if (mode !== "none") {
                this.log("failedUploadTextDisplay.mode value of '" + mode + "' is not valid", "warn");
            }
        },

        _showTooltip: function(id, text) {
            this._templating.getFileContainer(id).title = text;
        },

        _showCancelLink: function(id) {
            if (!this._options.disableCancelForFormUploads || qq.supportedFeatures.ajaxUploading) {
                this._templating.showCancel(id);
            }
        },

        _itemError: function(code, name, item) {
            var message = this._parent.prototype._itemError.apply(this, arguments);
            this._options.showMessage(message);
        },

        _batchError: function(message) {
            this._parent.prototype._batchError.apply(this, arguments);
            this._options.showMessage(message);
        },

        _setupPastePrompt: function() {
            var self = this;

            this._options.callbacks.onPasteReceived = function() {
                var message = self._options.paste.namePromptMessage,
                    defaultVal = self._options.paste.defaultName;

                return self._options.showPrompt(message, defaultVal);
            };
        },

        _fileOrBlobRejected: function(id, name) {
            this._totalFilesInBatch -= 1;
            this._parent.prototype._fileOrBlobRejected.apply(this, arguments);
        },

        _prepareItemsForUpload: function(items, params, endpoint) {
            this._totalFilesInBatch = items.length;
            this._filesInBatchAddedToUi = 0;
            this._parent.prototype._prepareItemsForUpload.apply(this, arguments);
        },

        _maybeUpdateThumbnail: function(fileId) {
            var thumbnailUrl = this._thumbnailUrls[fileId];

            this._templating.updateThumbnail(fileId, thumbnailUrl);
        },

        _addCannedFile: function(sessionData) {
            var id = this._parent.prototype._addCannedFile.apply(this, arguments);

            this._addToList(id, this.getName(id), true);
            this._templating.hideSpinner(id);
            this._templating.hideCancel(id);
            this._markFileAsSuccessful(id);

            return id;
        }
    };
}());

/*globals qq */
/**
 * This defines FineUploader mode, which is a default UI w/ drag & drop uploading.
 */
qq.FineUploader = function(o, namespace) {
    "use strict";

    // By default this should inherit instance data from FineUploaderBasic, but this can be overridden
    // if the (internal) caller defines a different parent.  The parent is also used by
    // the private and public API functions that need to delegate to a parent function.
    this._parent = namespace ? qq[namespace].FineUploaderBasic : qq.FineUploaderBasic;
    this._parent.apply(this, arguments);

    // Options provided by FineUploader mode
    qq.extend(this._options, {
        element: null,

        button: null,

        listElement: null,

        dragAndDrop: {
            extraDropzones: []
        },

        text: {
            formatProgress: "{percent}% of {total_size}",
            failUpload: "Upload failed",
            waitingForResponse: "Processing...",
            paused: "Paused"
        },

        template: "qq-template",

        classes: {
            retrying: "qq-upload-retrying",
            retryable: "qq-upload-retryable",
            success: "qq-upload-success",
            fail: "qq-upload-fail",
            editable: "qq-editable",
            hide: "qq-hide",
            dropActive: "qq-upload-drop-area-active"
        },

        failedUploadTextDisplay: {
            mode: "default", //default, custom, or none
            maxChars: 50,
            responseProperty: "error",
            enableTooltip: true
        },

        messages: {
            tooManyFilesError: "You may only drop one file",
            unsupportedBrowser: "Unrecoverable error - this browser does not permit file uploading of any kind."
        },

        retry: {
            showAutoRetryNote: true,
            autoRetryNote: "Retrying {retryNum}/{maxAuto}..."
        },

        deleteFile: {
            forceConfirm: false,
            confirmMessage: "Are you sure you want to delete {filename}?",
            deletingStatusText: "Deleting...",
            deletingFailedText: "Delete failed"

        },

        display: {
            fileSizeOnSubmit: false,
            prependFiles: false
        },

        paste: {
            promptForName: false,
            namePromptMessage: "Please name this image"
        },

        thumbnails: {
            placeholders: {
                waitUntilResponse: false,
                notAvailablePath: null,
                waitingPath: null
            }
        },

        showMessage: function(message){
            setTimeout(function() {
                window.alert(message);
            }, 0);
        },

        showConfirm: function(message) {
            return window.confirm(message);
        },

        showPrompt: function(message, defaultValue) {
            return window.prompt(message, defaultValue);
        }
    }, true);

    // Replace any default options with user defined ones
    qq.extend(this._options, o, true);

    this._templating = new qq.Templating({
        log: qq.bind(this.log, this),
        templateIdOrEl: this._options.template,
        containerEl: this._options.element,
        fileContainerEl: this._options.listElement,
        button: this._options.button,
        imageGenerator: this._imageGenerator,
        classes: {
            hide: this._options.classes.hide,
            editable: this._options.classes.editable
        },
        placeholders: {
            waitUntilUpdate: this._options.thumbnails.placeholders.waitUntilResponse,
            thumbnailNotAvailable: this._options.thumbnails.placeholders.notAvailablePath,
            waitingForThumbnail: this._options.thumbnails.placeholders.waitingPath
        },
        text: this._options.text
    });

    if (!qq.supportedFeatures.uploading || (this._options.cors.expected && !qq.supportedFeatures.uploadCors)) {
        this._templating.renderFailure(this._options.messages.unsupportedBrowser);
    }
    else {
        this._wrapCallbacks();

        this._templating.render();

        this._classes = this._options.classes;

        if (!this._options.button && this._templating.getButton()) {
            this._defaultButtonId = this._createUploadButton({element: this._templating.getButton()}).getButtonId();
        }

        this._setupClickAndEditEventHandlers();

        if (qq.DragAndDrop && qq.supportedFeatures.fileDrop) {
            this._dnd = this._setupDragAndDrop();
        }

        if (this._options.paste.targetElement && this._options.paste.promptForName) {
            if (qq.PasteSupport) {
                this._setupPastePrompt();
            }
            else {
                qq.log("Paste support module not found.", "info");
            }
        }

        this._totalFilesInBatch = 0;
        this._filesInBatchAddedToUi = 0;
    }
};

// Inherit the base public & private API methods
qq.extend(qq.FineUploader.prototype, qq.basePublicApi);
qq.extend(qq.FineUploader.prototype, qq.basePrivateApi);

// Add the FineUploader/default UI public & private UI methods, which may override some base methods.
qq.extend(qq.FineUploader.prototype, qq.uiPublicApi);
qq.extend(qq.FineUploader.prototype, qq.uiPrivateApi);

/* globals qq */
/* jshint -W065 */
/**
 * Module responsible for rendering all Fine Uploader UI templates.  This module also asserts at least
 * a limited amount of control over the template elements after they are added to the DOM.
 * Wherever possible, this module asserts total control over template elements present in the DOM.
 *
 * @param spec Specification object used to control various templating behaviors
 * @constructor
 */
qq.Templating = function(spec) {
    "use strict";

    var FILE_ID_ATTR = "qq-file-id",
        FILE_CLASS_PREFIX = "qq-file-id-",
        THUMBNAIL_MAX_SIZE_ATTR = "qq-max-size",
        THUMBNAIL_SERVER_SCALE_ATTR = "qq-server-scale",
        // This variable is duplicated in the DnD module since it can function as a standalone as well
        HIDE_DROPZONE_ATTR = "qq-hide-dropzone",
        isCancelDisabled = false,
        thumbnailMaxSize = -1,
        options = {
            log: null,
            templateIdOrEl: "qq-template",
            containerEl: null,
            fileContainerEl: null,
            button: null,
            imageGenerator: null,
            classes: {
                hide: "qq-hide",
                editable: "qq-editable"
            },
            placeholders: {
                waitUntilUpdate: false,
                thumbnailNotAvailable: null,
                waitingForThumbnail: null
            },
            text: {
                paused: "Paused"
            }
        },
        selectorClasses = {
            button: "qq-upload-button-selector",
            drop: "qq-upload-drop-area-selector",
            list: "qq-upload-list-selector",
            progressBarContainer: "qq-progress-bar-container-selector",
            progressBar: "qq-progress-bar-selector",
            file: "qq-upload-file-selector",
            spinner: "qq-upload-spinner-selector",
            size: "qq-upload-size-selector",
            cancel: "qq-upload-cancel-selector",
            pause: "qq-upload-pause-selector",
            continueButton: "qq-upload-continue-selector",
            deleteButton: "qq-upload-delete-selector",
            retry: "qq-upload-retry-selector",
            statusText: "qq-upload-status-text-selector",
            editFilenameInput: "qq-edit-filename-selector",
            editNameIcon: "qq-edit-filename-icon-selector",
            dropProcessing: "qq-drop-processing-selector",
            dropProcessingSpinner: "qq-drop-processing-spinner-selector",
            thumbnail: "qq-thumbnail-selector"
        },
        previewGeneration = {},
        cachedThumbnailNotAvailableImg = new qq.Promise(),
        cachedWaitingForThumbnailImg = new qq.Promise(),
        log,
        isEditElementsExist,
        isRetryElementExist,
        templateHtml,
        container,
        fileList,
        showThumbnails,
        serverScale;

    /**
     * Grabs the HTML from the script tag holding the template markup.  This function will also adjust
     * some internally-tracked state variables based on the contents of the template.
     * The template is filtered so that irrelevant elements (such as the drop zone if DnD is not supported)
     * are omitted from the DOM.  Useful errors will be thrown if the template cannot be parsed.
     *
     * @returns {{template: *, fileTemplate: *}} HTML for the top-level file items templates
     */
    function parseAndGetTemplate() {
        var scriptEl,
            scriptHtml,
            fileListNode,
            tempTemplateEl,
            fileListHtml,
            defaultButton,
            dropArea,
            thumbnail,
            dropProcessing;

        log("Parsing template");

        /*jshint -W116*/
        if (options.templateIdOrEl == null) {
            throw new Error("You MUST specify either a template element or ID!");
        }

        // Grab the contents of the script tag holding the template.
        if (qq.isString(options.templateIdOrEl)) {
            scriptEl = document.getElementById(options.templateIdOrEl);

            if (scriptEl === null) {
                throw new Error(qq.format("Cannot find template script at ID '{}'!", options.templateIdOrEl));
            }

            scriptHtml = scriptEl.innerHTML;
        }
        else {
            if (options.templateIdOrEl.innerHTML === undefined) {
                throw new Error("You have specified an invalid value for the template option!  " +
                    "It must be an ID or an Element.");
            }

            scriptHtml = options.templateIdOrEl.innerHTML;
        }

        scriptHtml = qq.trimStr(scriptHtml);
        tempTemplateEl = document.createElement("div");
        tempTemplateEl.appendChild(qq.toElement(scriptHtml));

        // Don't include the default template button in the DOM
        // if an alternate button container has been specified.
        if (options.button) {
            defaultButton = qq(tempTemplateEl).getByClass(selectorClasses.button)[0];
            if (defaultButton) {
                qq(defaultButton).remove();
            }
        }

        // Omit the drop processing element from the DOM if DnD is not supported by the UA,
        // or the drag and drop module is not found.
        // NOTE: We are consciously not removing the drop zone if the UA doesn't support DnD
        // to support layouts where the drop zone is also a container for visible elements,
        // such as the file list.
        if (!qq.DragAndDrop || !qq.supportedFeatures.fileDrop) {
            dropProcessing = qq(tempTemplateEl).getByClass(selectorClasses.dropProcessing)[0];
            if (dropProcessing) {
                qq(dropProcessing).remove();
            }

        }

        dropArea = qq(tempTemplateEl).getByClass(selectorClasses.drop)[0];

        // If DnD is not available then remove
        // it from the DOM as well.
        if (dropArea && !qq.DragAndDrop) {
            qq.log("DnD module unavailable.", "info");
            qq(dropArea).remove();
        }

        // If there is a drop area defined in the template, and the current UA doesn't support DnD,
        // and the drop area is marked as "hide before enter", ensure it is hidden as the DnD module
        // will not do this (since we will not be loading the DnD module)
        if (dropArea && !qq.supportedFeatures.fileDrop &&
            qq(dropArea).hasAttribute(HIDE_DROPZONE_ATTR)) {

            qq(dropArea).css({
                display: "none"
            });
        }

        // Ensure the `showThumbnails` flag is only set if the thumbnail element
        // is present in the template AND the current UA is capable of generating client-side previews.
        thumbnail = qq(tempTemplateEl).getByClass(selectorClasses.thumbnail)[0];
        if (!showThumbnails) {
            thumbnail && qq(thumbnail).remove();
        }
        else if (thumbnail) {
            thumbnailMaxSize = parseInt(thumbnail.getAttribute(THUMBNAIL_MAX_SIZE_ATTR));
            // Only enforce max size if the attr value is non-zero
            thumbnailMaxSize = thumbnailMaxSize > 0 ? thumbnailMaxSize : null;

            serverScale = qq(thumbnail).hasAttribute(THUMBNAIL_SERVER_SCALE_ATTR);
        }
        showThumbnails = showThumbnails && thumbnail;

        isEditElementsExist = qq(tempTemplateEl).getByClass(selectorClasses.editFilenameInput).length > 0;
        isRetryElementExist = qq(tempTemplateEl).getByClass(selectorClasses.retry).length > 0;

        fileListNode = qq(tempTemplateEl).getByClass(selectorClasses.list)[0];
        /*jshint -W116*/
        if (fileListNode == null) {
            throw new Error("Could not find the file list container in the template!");
        }

        fileListHtml = fileListNode.innerHTML;
        fileListNode.innerHTML = "";

        log("Template parsing complete");

        return {
            template: qq.trimStr(tempTemplateEl.innerHTML),
            fileTemplate: qq.trimStr(fileListHtml)
        };
    }

    function getFile(id) {
        return qq(fileList).getByClass(FILE_CLASS_PREFIX + id)[0];
    }

    function getTemplateEl(context, cssClass) {
        return qq(context).getByClass(cssClass)[0];
    }

    function prependFile(el, index) {
        var parentEl = fileList,
            beforeEl = parentEl.firstChild;

        if (index > 0) {
            beforeEl = qq(parentEl).children()[index].nextSibling;

        }

        parentEl.insertBefore(el, beforeEl);
    }

    function getCancel(id) {
        return getTemplateEl(getFile(id), selectorClasses.cancel);
    }

    function getPause(id) {
        return getTemplateEl(getFile(id), selectorClasses.pause);
    }

    function getContinue(id) {
        return getTemplateEl(getFile(id), selectorClasses.continueButton);
    }

    function getProgress(id) {
        return getTemplateEl(getFile(id), selectorClasses.progressBarContainer) ||
            getTemplateEl(getFile(id), selectorClasses.progressBar);
    }

    function getSpinner(id) {
        return getTemplateEl(getFile(id), selectorClasses.spinner);
    }

    function getEditIcon(id) {
        return getTemplateEl(getFile(id), selectorClasses.editNameIcon);
    }

    function getSize(id) {
        return getTemplateEl(getFile(id), selectorClasses.size);
    }

    function getDelete(id) {
        return getTemplateEl(getFile(id), selectorClasses.deleteButton);
    }

    function getRetry(id) {
        return getTemplateEl(getFile(id), selectorClasses.retry);
    }

    function getFilename(id) {
        return getTemplateEl(getFile(id), selectorClasses.file);
    }

    function getDropProcessing() {
        return getTemplateEl(container, selectorClasses.dropProcessing);
    }

    function getThumbnail(id) {
        return showThumbnails && getTemplateEl(getFile(id), selectorClasses.thumbnail);
    }

    function hide(el) {
        el && qq(el).addClass(options.classes.hide);
    }

    function show(el) {
        el && qq(el).removeClass(options.classes.hide);
    }

    function setProgressBarWidth(id, percent) {
        var bar = getProgress(id);

        if (bar && !qq(bar).hasClass(selectorClasses.progressBar)) {
            bar = qq(bar).getByClass(selectorClasses.progressBar)[0];
        }

        bar && qq(bar).css({width: percent + "%"});
    }

    // During initialization of the templating module we should cache any
    // placeholder images so we can quickly swap them into the file list on demand.
    // Any placeholder images that cannot be loaded/found are simply ignored.
    function cacheThumbnailPlaceholders() {
        var notAvailableUrl =  options.placeholders.thumbnailNotAvailable,
            waitingUrl = options.placeholders.waitingForThumbnail,
            spec = {
                maxSize: thumbnailMaxSize,
                scale: serverScale
            };

        if (showThumbnails) {
            if (notAvailableUrl) {
                options.imageGenerator.generate(notAvailableUrl, new Image(), spec).then(
                    function(updatedImg) {
                        cachedThumbnailNotAvailableImg.success(updatedImg);
                    },
                    function() {
                        cachedThumbnailNotAvailableImg.failure();
                        log("Problem loading 'not available' placeholder image at " + notAvailableUrl, "error");
                    }
                );
            }
            else {
                cachedThumbnailNotAvailableImg.failure();
            }

            if (waitingUrl) {
                options.imageGenerator.generate(waitingUrl, new Image(), spec).then(
                    function(updatedImg) {
                        cachedWaitingForThumbnailImg.success(updatedImg);
                    },
                    function() {
                        cachedWaitingForThumbnailImg.failure();
                        log("Problem loading 'waiting for thumbnail' placeholder image at " + waitingUrl, "error");
                    }
                );
            }
            else {
                cachedWaitingForThumbnailImg.failure();
            }
        }
    }

    // Displays a "waiting for thumbnail" type placeholder image
    // iff we were able to load it during initialization of the templating module.
    function displayWaitingImg(thumbnail) {
        cachedWaitingForThumbnailImg.then(function(img) {
            maybeScalePlaceholderViaCss(img, thumbnail);
            /* jshint eqnull:true */
            if (thumbnail.getAttribute("src") == null) {
                thumbnail.src = img.src;
                show(thumbnail);
            }
        }, function() {
            // In some browsers (such as IE9 and older) an img w/out a src attribute
            // are displayed as "broken" images, so we sohuld just hide the img tag
            // if we aren't going to display the "waiting" placeholder.
            hide(thumbnail);
        });
    }

    // Displays a "thumbnail not available" type placeholder image
    // iff we were able to load this placeholder during initialization
    // of the templating module or after preview generation has failed.
    function maybeSetDisplayNotAvailableImg(id, thumbnail) {
        var previewing = previewGeneration[id] || new qq.Promise().failure();

        cachedThumbnailNotAvailableImg.then(function(img) {
            previewing.then(
                function() {
                    delete previewGeneration[id];
                },
                function() {
                    maybeScalePlaceholderViaCss(img, thumbnail);
                    thumbnail.src = img.src;
                    show(thumbnail);
                    delete previewGeneration[id];
                }
            );
        });
    }

    // Ensures a placeholder image does not exceed any max size specified
    // via `style` attribute properties iff <canvas> was not used to scale
    // the placeholder AND the target <img> doesn't already have these `style` attribute properties set.
    function maybeScalePlaceholderViaCss(placeholder, thumbnail) {
        var maxWidth = placeholder.style.maxWidth,
            maxHeight = placeholder.style.maxHeight;

        if (maxHeight && maxWidth && !thumbnail.style.maxWidth && !thumbnail.style.maxHeight) {
            qq(thumbnail).css({
                maxWidth: maxWidth,
                maxHeight: maxHeight
            });
        }
    }


    qq.extend(options, spec);
    log = options.log;

    container = options.containerEl;
    showThumbnails = options.imageGenerator !== undefined;
    templateHtml = parseAndGetTemplate();

    cacheThumbnailPlaceholders();

    qq.extend(this, {
        render: function() {
            log("Rendering template in DOM.");

            container.innerHTML = templateHtml.template;
            hide(getDropProcessing());
            fileList = options.fileContainerEl || getTemplateEl(container, selectorClasses.list);

            log("Template rendering complete");
        },

        renderFailure: function(message) {
            var cantRenderEl = qq.toElement(message);
            container.innerHTML = "";
            container.appendChild(cantRenderEl);
        },

        reset: function() {
            this.render();
        },

        clearFiles: function() {
            fileList.innerHTML = "";
        },

        disableCancel: function() {
            isCancelDisabled = true;
        },

        addFile: function(id, name, prependInfo) {
            var fileEl = qq.toElement(templateHtml.fileTemplate),
                fileNameEl = getTemplateEl(fileEl, selectorClasses.file);

            qq(fileEl).addClass(FILE_CLASS_PREFIX + id);
            fileNameEl && qq(fileNameEl).setText(name);
            fileEl.setAttribute(FILE_ID_ATTR, id);

            if (prependInfo) {
                prependFile(fileEl, prependInfo.index);
            }
            else {
                fileList.appendChild(fileEl);
            }

            hide(getProgress(id));
            hide(getSize(id));
            hide(getDelete(id));
            hide(getRetry(id));
            hide(getPause(id));
            hide(getContinue(id));

            if (isCancelDisabled) {
                this.hideCancel(id);
            }
        },

        removeFile: function(id) {
            qq(getFile(id)).remove();
        },

        getFileId: function(el) {
            var currentNode = el;

            if (currentNode) {
                /*jshint -W116*/
                while (currentNode.getAttribute(FILE_ID_ATTR) == null) {
                    currentNode = currentNode.parentNode;
                }

                return parseInt(currentNode.getAttribute(FILE_ID_ATTR));
            }
        },

        getFileList: function() {
            return fileList;
        },

        markFilenameEditable: function(id) {
            var filename = getFilename(id);

            filename && qq(filename).addClass(options.classes.editable);
        },

        updateFilename: function(id, name) {
            var filename = getFilename(id);

            filename && qq(filename).setText(name);
        },

        hideFilename: function(id) {
            hide(getFilename(id));
        },

        showFilename: function(id) {
            show(getFilename(id));
        },

        isFileName: function(el) {
            return qq(el).hasClass(selectorClasses.file);
        },

        getButton: function() {
            return options.button || getTemplateEl(container, selectorClasses.button);
        },

        hideDropProcessing: function() {
            hide(getDropProcessing());
        },

        showDropProcessing: function() {
            show(getDropProcessing());
        },

        getDropZone: function() {
            return getTemplateEl(container, selectorClasses.drop);
        },

        isEditFilenamePossible: function() {
            return isEditElementsExist;
        },

        isRetryPossible: function() {
            return isRetryElementExist;
        },

        getFileContainer: function(id) {
            return getFile(id);
        },

        showEditIcon: function(id) {
            var icon = getEditIcon(id);

            icon && qq(icon).addClass(options.classes.editable);
        },

        hideEditIcon: function(id) {
            var icon = getEditIcon(id);

            icon && qq(icon).removeClass(options.classes.editable);
        },

        isEditIcon: function(el) {
            return qq(el).hasClass(selectorClasses.editNameIcon);
        },

        getEditInput: function(id) {
            return getTemplateEl(getFile(id), selectorClasses.editFilenameInput);
        },

        isEditInput: function(el) {
            return qq(el).hasClass(selectorClasses.editFilenameInput);
        },

        updateProgress: function(id, loaded, total) {
            var bar = getProgress(id),
                percent;

            if (bar) {
                percent = Math.round(loaded / total * 100);

                if (loaded === total) {
                    hide(bar);
                }
                else {
                    show(bar);
                }

                setProgressBarWidth(id, percent);
            }
        },

        hideProgress: function(id) {
            var bar = getProgress(id);

            bar && hide(bar);
        },

        resetProgress: function(id) {
            setProgressBarWidth(id, 0);
        },

        showCancel: function(id) {
            if (!isCancelDisabled) {
                var cancel = getCancel(id);

                cancel && qq(cancel).removeClass(options.classes.hide);
            }
        },

        hideCancel: function(id) {
            hide(getCancel(id));
        },

        isCancel: function(el)  {
            return qq(el).hasClass(selectorClasses.cancel);
        },

        allowPause: function(id) {
            show(getPause(id));
            hide(getContinue(id));
        },

        uploadPaused: function(id) {
            this.setStatusText(id, options.text.paused);
            this.allowContinueButton(id);
            hide(getSpinner(id));
        },

        hidePause: function(id) {
            hide(getPause(id));
        },

        isPause: function(el) {
            return qq(el).hasClass(selectorClasses.pause);
        },

        isContinueButton: function(el) {
            return qq(el).hasClass(selectorClasses.continueButton);
        },

        allowContinueButton: function(id) {
            show(getContinue(id));
            hide(getPause(id));
        },

        uploadContinued: function(id) {
            this.setStatusText(id, "");
            this.allowPause(id);
            show(getSpinner(id));
        },

        showDeleteButton: function(id) {
            show(getDelete(id));
        },

        hideDeleteButton: function(id) {
            hide(getDelete(id));
        },

        isDeleteButton: function(el) {
            return qq(el).hasClass(selectorClasses.deleteButton);
        },

        isRetry: function(el) {
            return qq(el).hasClass(selectorClasses.retry);
        },

        updateSize: function(id, text) {
            var size = getSize(id);

            if (size) {
                show(size);
                qq(size).setText(text);
            }
        },

        setStatusText: function(id, text) {
            var textEl = getTemplateEl(getFile(id), selectorClasses.statusText);

            if (textEl) {
                /*jshint -W116*/
                if (text == null) {
                    qq(textEl).clearText();
                }
                else {
                    qq(textEl).setText(text);
                }
            }
        },

        hideSpinner: function(id) {
            hide(getSpinner(id));
        },

        showSpinner: function(id) {
            show(getSpinner(id));
        },

        generatePreview: function(id, fileOrBlob) {
            var thumbnail = getThumbnail(id),
                spec = {
                    maxSize: thumbnailMaxSize,
                    scale: true,
                    orient: true
                };

            if (qq.supportedFeatures.imagePreviews) {
                previewGeneration[id] = new qq.Promise();

                if (thumbnail) {
                    displayWaitingImg(thumbnail);
                    return options.imageGenerator.generate(fileOrBlob, thumbnail, spec).then(
                        function() {
                            show(thumbnail);
                            previewGeneration[id].success();
                        },
                        function() {
                            previewGeneration[id].failure();

                            // Display the "not available" placeholder img only if we are
                            // not expecting a thumbnail at a later point, such as in a server response.
                            if (!options.placeholders.waitUntilUpdate) {
                                maybeSetDisplayNotAvailableImg(id, thumbnail);
                            }
                        });
                }
            }
            else if (thumbnail) {
                displayWaitingImg(thumbnail);
            }
        },

        updateThumbnail: function(id, thumbnailUrl, showWaitingImg) {
            var thumbnail = getThumbnail(id),
                spec = {
                    maxSize: thumbnailMaxSize,
                    scale: serverScale
                };

            if (thumbnail) {
                if (thumbnailUrl) {
                    if (showWaitingImg) {
                        displayWaitingImg(thumbnail);
                    }

                    return options.imageGenerator.generate(thumbnailUrl, thumbnail, spec).then(
                        function() {
                            show(thumbnail);
                        },
                        function() {
                            maybeSetDisplayNotAvailableImg(id, thumbnail);
                        }
                    );
                }
                else {
                    maybeSetDisplayNotAvailableImg(id, thumbnail);
                }
            }
        }
    });
};

/*globals qq */
qq.s3 = qq.s3 || {};

qq.s3.util = qq.s3.util || (function() {
    "use strict";

    return {
        AWS_PARAM_PREFIX: "x-amz-meta-",

        SESSION_TOKEN_PARAM_NAME: "x-amz-security-token",

        REDUCED_REDUNDANCY_PARAM_NAME: "x-amz-storage-class",
        REDUCED_REDUNDANCY_PARAM_VALUE: "REDUCED_REDUNDANCY",

        SERVER_SIDE_ENCRYPTION_PARAM_NAME: "x-amz-server-side-encryption",
        SERVER_SIDE_ENCRYPTION_PARAM_VALUE: "AES256",

        /**
         * This allows for the region to be specified in the bucket's endpoint URL, or not.
         *
         * Examples of some valid endpoints are:
         *     http://foo.s3.amazonaws.com
         *     https://foo.s3.amazonaws.com
         *     http://foo.s3-ap-northeast-1.amazonaws.com
         *     foo.s3.amazonaws.com
         *     http://foo.bar.com
         *     http://s3.amazonaws.com/foo.bar.com
         * ...etc
         *
         * @param endpoint The bucket's URL.
         * @returns {String || undefined} The bucket name, or undefined if the URL cannot be parsed.
         */
        getBucket: function(endpoint) {
            var patterns = [
                    //bucket in domain
                    /^(?:https?:\/\/)?([a-z0-9.\-_]+)\.s3(?:-[a-z0-9\-]+)?\.amazonaws\.com/i,
                    //bucket in path
                    /^(?:https?:\/\/)?s3(?:-[a-z0-9\-]+)?\.amazonaws\.com\/([a-z0-9.\-_]+)/i,
                    //custom domain
                    /^(?:https?:\/\/)?([a-z0-9.\-_]+)/i
                ],
                bucket;

            qq.each(patterns, function(idx, pattern) {
                var match = pattern.exec(endpoint);

                if (match) {
                    bucket = match[1];
                    return false;
                }
            });

            return bucket;
        },

        /**
         * Create a policy document to be signed and sent along with the S3 upload request.
         *
         * @param spec Object with properties use to construct the policy document.
         * @returns {Object} Policy doc.
         */
        getPolicy: function(spec) {
            var policy = {},
                conditions = [],
                bucket = qq.s3.util.getBucket(spec.endpoint),
                key = spec.key,
                acl = spec.acl,
                type = spec.type,
                expirationDate = new Date(),
                expectedStatus = spec.expectedStatus,
                sessionToken = spec.sessionToken,
                params = spec.params,
                successRedirectUrl = qq.s3.util.getSuccessRedirectAbsoluteUrl(spec.successRedirectUrl),
                minFileSize = spec.minFileSize,
                maxFileSize = spec.maxFileSize,
                reducedRedundancy = spec.reducedRedundancy,
                serverSideEncryption = spec.serverSideEncryption;

            policy.expiration = qq.s3.util.getPolicyExpirationDate(expirationDate);

            conditions.push({acl: acl});
            conditions.push({bucket: bucket});

            if (type) {
                conditions.push({"Content-Type": type});
            }

            if (expectedStatus) {
                conditions.push({success_action_status: expectedStatus.toString()});
            }

            if (successRedirectUrl) {
                conditions.push({success_action_redirect: successRedirectUrl});
            }

            if (reducedRedundancy) {
                conditions.push({});
                conditions[conditions.length - 1][qq.s3.util.REDUCED_REDUNDANCY_PARAM_NAME] = qq.s3.util.REDUCED_REDUNDANCY_PARAM_VALUE;
            }

            if (sessionToken) {
                conditions.push({});
                conditions[conditions.length - 1][qq.s3.util.SESSION_TOKEN_PARAM_NAME] = sessionToken;
            }

            if (serverSideEncryption) {
                conditions.push({});
                conditions[conditions.length - 1][qq.s3.util.SERVER_SIDE_ENCRYPTION_PARAM_NAME] = qq.s3.util.SERVER_SIDE_ENCRYPTION_PARAM_VALUE;
            }

            conditions.push({key: key});

            // user metadata
            qq.each(params, function(name, val) {
                var awsParamName = qq.s3.util.AWS_PARAM_PREFIX + name,
                    param = {};

                param[awsParamName] = encodeURIComponent(val);
                conditions.push(param);
            });

            policy.conditions = conditions;

            qq.s3.util.enforceSizeLimits(policy, minFileSize, maxFileSize);

            return policy;
        },

        /**
         * Update a previously constructed policy document with updated credentials.  Currently, this only requires we
         * update the session token.  This is only relevant if requests are being signed client-side.
         *
         * @param policy Live policy document
         * @param newSessionToken Updated session token.
         */
        refreshPolicyCredentials: function(policy, newSessionToken) {
            var sessionTokenFound = false;

            qq.each(policy.conditions, function(oldCondIdx, oldCondObj) {
                qq.each(oldCondObj, function(oldCondName, oldCondVal) {
                    if (oldCondName === qq.s3.util.SESSION_TOKEN_PARAM_NAME) {
                        oldCondObj[oldCondName] = newSessionToken;
                        sessionTokenFound = true;
                    }
                });
            });

            if (!sessionTokenFound) {
                policy.conditions.push({});
                policy.conditions[policy.conditions.length - 1][qq.s3.util.SESSION_TOKEN_PARAM_NAME] = newSessionToken;
            }
        },

        /**
         * Generates all parameters to be passed along with the S3 upload request.  This includes invoking a callback
         * that is expected to asynchronously retrieve a signature for the policy document.  Note that the server
         * signing the request should reject a "tainted" policy document that includes unexpected values, since it is
         * still possible for a malicious user to tamper with these values during policy document generation, b
         * before it is sent to the server for signing.
         *
         * @param spec Object with properties: `params`, `type`, `key`, `accessKey`, `acl`, `expectedStatus`, `successRedirectUrl`,
         * `reducedRedundancy`, serverSideEncryption, and `log()`, along with any options associated with `qq.s3.util.getPolicy()`.
         * @returns {qq.Promise} Promise that will be fulfilled once all parameters have been determined.
         */
        generateAwsParams: function(spec, signPolicyCallback) {
            var awsParams = {},
                customParams = spec.params,
                promise = new qq.Promise(),
                policyJson = qq.s3.util.getPolicy(spec),
                sessionToken = spec.sessionToken,
                type = spec.type,
                key = spec.key,
                accessKey = spec.accessKey,
                acl = spec.acl,
                expectedStatus = spec.expectedStatus,
                successRedirectUrl = qq.s3.util.getSuccessRedirectAbsoluteUrl(spec.successRedirectUrl),
                reducedRedundancy = spec.reducedRedundancy,
                serverSideEncryption = spec.serverSideEncryption,
                log = spec.log;

            awsParams.key = key;
            awsParams.AWSAccessKeyId = accessKey;

            if (type) {
                awsParams["Content-Type"] = type;
            }

            if (expectedStatus) {
                awsParams.success_action_status = expectedStatus;
            }

            if (successRedirectUrl) {
                awsParams.success_action_redirect = successRedirectUrl;
            }

            if (reducedRedundancy) {
                awsParams[qq.s3.util.REDUCED_REDUNDANCY_PARAM_NAME] = qq.s3.util.REDUCED_REDUNDANCY_PARAM_VALUE;
            }

            if (serverSideEncryption) {
                awsParams[qq.s3.util.SERVER_SIDE_ENCRYPTION_PARAM_NAME] = qq.s3.util.SERVER_SIDE_ENCRYPTION_PARAM_VALUE;
            }

            if (sessionToken) {
                awsParams[qq.s3.util.SESSION_TOKEN_PARAM_NAME] = sessionToken;
            }

            awsParams.acl = acl;

            // Custom (user-supplied) params must be prefixed with the value of `qq.s3.util.AWS_PARAM_PREFIX`.
            // Custom param values will be URI encoded as well.
            qq.each(customParams, function(name, val) {
                var awsParamName = qq.s3.util.AWS_PARAM_PREFIX + name;
                awsParams[awsParamName] = encodeURIComponent(val);
            });

            // Invoke a promissory callback that should provide us with a base64-encoded policy doc and an
            // HMAC signature for the policy doc.
            signPolicyCallback(policyJson).then(
                function(policyAndSignature, updatedAccessKey, updatedSessionToken) {
                    awsParams.policy = policyAndSignature.policy;
                    awsParams.signature = policyAndSignature.signature;

                    if (updatedAccessKey) {
                        awsParams.AWSAccessKeyId = updatedAccessKey;
                    }
                    if (updatedSessionToken) {
                        awsParams[qq.s3.util.SESSION_TOKEN_PARAM_NAME] = updatedSessionToken;
                    }

                    promise.success(awsParams);
                },
                function(errorMessage) {
                    errorMessage = errorMessage || "Can't continue further with request to S3 as we did not receive " +
                                                   "a valid signature and policy from the server.";

                    log("Policy signing failed.  " + errorMessage, "error");
                    promise.failure(errorMessage);
                }
            );

            return promise;
        },

        /**
         * Add a condition to an existing S3 upload request policy document used to ensure AWS enforces any size
         * restrictions placed on files server-side.  This is important to do, in case users mess with the client-side
         * checks already in place.
         *
         * @param policy Policy document as an `Object`, with a `conditions` property already attached
         * @param minSize Minimum acceptable size, in bytes
         * @param maxSize Maximum acceptable size, in bytes (0 = unlimited)
         */
        enforceSizeLimits: function(policy, minSize, maxSize) {
            var adjustedMinSize = minSize < 0 ? 0 : minSize,
                // Adjust a maxSize of 0 to the largest possible integer, since we must specify a high and a low in the request
                adjustedMaxSize = maxSize <= 0 ? 9007199254740992 : maxSize;

            if (minSize > 0 || maxSize > 0) {
                policy.conditions.push(["content-length-range", adjustedMinSize.toString(), adjustedMaxSize.toString()]);
            }
        },

        getPolicyExpirationDate: function(date) {
            /*jshint -W014 */
            // Is this going to be a problem if we encounter this moments before 2 AM just before daylight savings time ends?
            date.setMinutes(date.getMinutes() + 5);

            if (Date.prototype.toISOString) {
                return date.toISOString();
            }
            else {
                var pad = function(number) {
                    var r = String(number);

                    if ( r.length === 1 ) {
                        r = "0" + r;
                    }

                    return r;
                };

                return date.getUTCFullYear()
                        + "-" + pad( date.getUTCMonth() + 1 )
                        + "-" + pad( date.getUTCDate() )
                        + "T" + pad( date.getUTCHours() )
                        + ":" + pad( date.getUTCMinutes() )
                        + ":" + pad( date.getUTCSeconds() )
                        + "." + String( (date.getUTCMilliseconds()/1000).toFixed(3) ).slice( 2, 5 )
                        + "Z";
            }
        },

        /**
         * Looks at a response from S3 contained in an iframe and parses the query string in an attempt to identify
         * the associated resource.
         *
         * @param iframe Iframe containing response
         * @returns {{bucket: *, key: *, etag: *}}
         */
        parseIframeResponse: function(iframe) {
            var doc = iframe.contentDocument || iframe.contentWindow.document,
                queryString = doc.location.search,
                match = /bucket=(.+)&key=(.+)&etag=(.+)/.exec(queryString);

            if (match) {
                return {
                    bucket: match[1],
                    key: match[2],
                    etag: match[3].replace(/%22/g, "")
                };
            }
        },

        /**
         * @param successRedirectUrl Relative or absolute location of success redirect page
         * @returns {*|string} undefined if the parameter is undefined, otherwise the absolute location of the success redirect page
         */
        getSuccessRedirectAbsoluteUrl: function(successRedirectUrl) {
            if (successRedirectUrl) {
                var targetAnchorContainer = document.createElement("div"),
                    targetAnchor;

                if (qq.ie7()) {
                    // Note that we must make use of `innerHTML` for IE7 only instead of simply creating an anchor via
                    // `document.createElement('a')` and setting the `href` attribute.  The latter approach does not allow us to
                    // obtain an absolute URL in IE7 if the `endpoint` is a relative URL.
                    targetAnchorContainer.innerHTML = "<a href='" + successRedirectUrl + "'></a>";
                    targetAnchor = targetAnchorContainer.firstChild;
                    return targetAnchor.href;
                }
                else {
                    // IE8 and IE9 do not seem to derive an absolute URL from a relative URL using the `innerHTML`
                    // approach above, so we'll just create an anchor this way and set it's `href` attribute.
                    // Due to yet another quirk in IE8 and IE9, we have to set the `href` equal to itself
                    // in order to ensure relative URLs will be properly parsed.
                    targetAnchor = document.createElement("a");
                    targetAnchor.href = successRedirectUrl;
                    targetAnchor.href = targetAnchor.href;
                    return targetAnchor.href;
                }
            }
        },

        // AWS employs a strict interpretation of [RFC 3986](http://tools.ietf.org/html/rfc3986#page-12).
        // So, we must ensure all reserved characters listed in the spec are percent-encoded,
        // and spaces are replaced with "+".
        encodeQueryStringParam: function(param) {
            var percentEncoded = encodeURIComponent(param);

            // %-encode characters not handled by `encodeURIComponent` (to follow RFC 3986)
            percentEncoded = percentEncoded.replace(/[!'()]/g, escape);

            // %-encode characters not handled by `escape` (to follow RFC 3986)
            percentEncoded = percentEncoded.replace(/\*/g, "%2A");

            // replace percent-encoded spaces with a "+"
            return percentEncoded.replace(/%20/g, "+");
        }
    };
}());

/* globals qq */
/**
 * TODO
 * @constructor
 */
qq.AbstractNonTraditionalUploadHandlerXhr = function(spec) {
    "use strict";

    var handler = this,
        options = spec.options,
        proxy = spec.proxy,
        chunking = options.chunking,
        resume = options.resume,
        namespace = options.namespace,
        onUuidChanged = proxy.onUuidChanged,
        resumeEnabled = options.resumeEnabled,
        getEndpoint = proxy.getEndpoint,
        getName = proxy.getName,
        getSize = proxy.getSize,
        getUuid = proxy.getUuid,
        log = proxy.log,
        baseHandlerXhrApi = new qq.AbstractUploadHandlerXhr(spec);

    qq.extend(this, baseHandlerXhrApi);
    qq.extend(this, {
        getResumableFilesData: function() {
            return handler._getResumableFilesData();
        },

        getThirdPartyFileId: function(id) {
            return handler._getFileState(id).key;
        },

        /**
         * Determine if the associated file should be chunked.
         *
         * @param id ID of the associated file
         * @returns {*} true if chunking is enabled, possible, and the file can be split into more than 1 part
         */
        _shouldChunkThisFile: function(id) {
            var totalChunks,
                fileState = handler._getFileState(id);

            if (!fileState.chunking) {
                fileState.chunking = {};
                totalChunks = handler._getTotalChunks(id);
                if (totalChunks > 1) {
                    fileState.chunking.enabled = true;
                    fileState.chunking.parts = totalChunks;
                }
                else {
                    fileState.chunking.enabled = false;
                }
            }

            return fileState.chunking.enabled;
        },

        // If this is a resumable upload, grab the relevant data from storage and items in memory that track this upload
        // so we can pick up from where we left off.
        _maybePrepareForResume: function(id) {
            var fileState = handler._getFileState(id),
                localStorageId, persistedData;

            // Resume is enabled and possible and this is the first time we've tried to upload this file in this session,
            // so prepare for a resume attempt.
            if (resumeEnabled && fileState.key === undefined) {
                localStorageId = handler._getLocalStorageId(id);
                persistedData = localStorage.getItem(localStorageId);

                // If we haven't found this item in local storage, give up
                if (persistedData) {
                    log(qq.format("Identified file with ID {} and name of {} as resumable.", id, getName(id)));

                    persistedData = JSON.parse(persistedData);

                    onUuidChanged(id, persistedData.uuid);
                    fileState.key = persistedData.key;
                    fileState.loaded = persistedData.loaded;
                    fileState.chunking = persistedData.chunking;
                }
            }
        },

        // Persist any data needed to resume this upload in a new session.
        _maybePersistChunkedState: function(id) {
            var fileState = handler._getFileState(id),
                localStorageId, persistedData;

            // If local storage isn't supported by the browser, or if resume isn't enabled or possible, give up
            if (resumeEnabled) {
                localStorageId = handler._getLocalStorageId(id);

                persistedData = {
                    name: getName(id),
                    size: getSize(id),
                    uuid: getUuid(id),
                    key: fileState.key,
                    loaded: fileState.loaded,
                    chunking: fileState.chunking,
                    lastUpdated: Date.now()
                };

                localStorage.setItem(localStorageId, JSON.stringify(persistedData));
            }
        },

        // Removes a chunked upload record from local storage, if possible.
        // Returns true if the item was removed, false otherwise.
        _maybeDeletePersistedChunkData: function(id) {
            var localStorageId;

            if (resumeEnabled) {
                localStorageId = handler._getLocalStorageId(id);

                if (localStorageId && localStorage.getItem(localStorageId)) {
                    localStorage.removeItem(localStorageId);
                    return true;
                }
            }

            return false;
        },

        // Iterates through all XHR handler-created resume records (in local storage),
        // invoking the passed callback and passing in the key and value of each local storage record.
        _iterateResumeRecords: function(callback) {
            if (resumeEnabled) {
                qq.each(localStorage, function(key, item) {
                    if (key.indexOf(qq.format("qq{}resume-", namespace)) === 0) {
                        var uploadData = JSON.parse(item);
                        callback(key, uploadData);
                    }
                });
            }
        },

        /**
         * @returns {Array} Array of objects containing properties useful to integrators
         * when it is important to determine which files are potentially resumable.
         */
        _getResumableFilesData: function() {
            var resumableFilesData = [];

            handler._iterateResumeRecords(function(key, uploadData) {
                resumableFilesData.push({
                    name: uploadData.name,
                    size: uploadData.size,
                    uuid: uploadData.uuid,
                    partIdx: uploadData.chunking.lastSent + 1,
                    key: uploadData.key
                });
            });

            return resumableFilesData;
        },

        // Deletes any local storage records that are "expired".
        _removeExpiredChunkingRecords: function() {
            var expirationDays = resume.recordsExpireIn;

            handler._iterateResumeRecords(function(key, uploadData) {
                var expirationDate = new Date(uploadData.lastUpdated);

                // transform updated date into expiration date
                expirationDate.setDate(expirationDate.getDate() + expirationDays);

                if (expirationDate.getTime() <= Date.now()) {
                    log("Removing expired resume record with key " + key);
                    localStorage.removeItem(key);
                }
            });
        },

        /**
         * @param id File ID
         * @returns {string} Identifier for this item that may appear in the browser's local storage
         */
        _getLocalStorageId: function(id) {
            var name = getName(id),
                size = getSize(id),
                chunkSize = chunking.partSize,
                endpoint = getEndpoint(id);

            return qq.format("qq{}resume-{}-{}-{}-{}", namespace, name, size, chunkSize, endpoint);
        }
    });

    qq.override(this, function(super_) {
        return {
            add: function(id, fileOrBlobData) {
                super_.add.apply(this, arguments);

                if (resumeEnabled) {
                    handler._maybePrepareForResume(id);
                }
            }
        };
    });
};

/*globals qq*/
/**
 * Defines the public API for non-traditional FineUploaderBasic mode.
 */
(function(){
    "use strict";

    qq.nonTraditionalBasePublicApi = {
        setUploadSuccessParams: function(params, id) {
            this._uploadSuccessParamsStore.set(params, id);
        }
    };




    qq.nonTraditionalBasePrivateApi = {
        /**
         * When the upload has completed, if it is successful, send a request to the `successEndpoint` (if defined).
         * This will hold up the call to the `onComplete` callback until we have determined success of the upload
         * according to the local server, if a `successEndpoint` has been defined by the integrator.
         *
         * @param id ID of the completed upload
         * @param name Name of the associated item
         * @param result Object created from the server's parsed JSON response.
         * @param xhr Associated XmlHttpRequest, if this was used to send the request.
         * @returns {boolean || qq.Promise} true/false if success can be determined immediately, otherwise a `qq.Promise`
         * if we need to ask the server.
         * @private
         */
        _onComplete: function(id, name, result, xhr) {
            var success = result.success ? true : false,
                self = this,
                onCompleteArgs = arguments,
                successEndpoint = this._options.uploadSuccess.endpoint,
                successCustomHeaders = this._options.uploadSuccess.customHeaders,
                cors = this._options.cors,
                promise = new qq.Promise(),
                uploadSuccessParams = this._uploadSuccessParamsStore.get(id),

                // If we are waiting for confirmation from the local server, and have received it,
                // include properties from the local server response in the `response` parameter
                // sent to the `onComplete` callback, delegate to the parent `_onComplete`, and
                // fulfill the associated promise.
                onSuccessFromServer = function(successRequestResult) {
                    delete self._failedSuccessRequestCallbacks[id];
                    qq.extend(result, successRequestResult);
                    qq.FineUploaderBasic.prototype._onComplete.apply(self, onCompleteArgs);
                    promise.success(successRequestResult);
                },

                // If the upload success request fails, attempt to re-send the success request (via the core retry code).
                // The entire upload may be restarted if the server returns a "reset" property with a value of true as well.
                onFailureFromServer = function(successRequestResult) {
                    var callback = submitSuccessRequest;

                    qq.extend(result, successRequestResult);

                    if (result && result.reset) {
                        callback = null;
                    }

                    if (!callback) {
                        delete self._failedSuccessRequestCallbacks[id];
                    }
                    else {
                        self._failedSuccessRequestCallbacks[id] = callback;
                    }

                    if (!self._onAutoRetry(id, name, result, xhr, callback)) {
                        qq.FineUploaderBasic.prototype._onComplete.apply(self, onCompleteArgs);
                        promise.failure(successRequestResult);
                    }
                },
                submitSuccessRequest,
                successAjaxRequester;

            // Ask the local server if the file sent is ok.
            if (success && successEndpoint) {
                successAjaxRequester = new qq.UploadSuccessAjaxRequester({
                    endpoint: successEndpoint,
                    customHeaders: successCustomHeaders,
                    cors: cors,
                    log: qq.bind(this.log, this)
                });


                // combine custom params and default params
                qq.extend(uploadSuccessParams, self._getEndpointSpecificParams(id, result, xhr), true);

                submitSuccessRequest = qq.bind(function() {
                    successAjaxRequester.sendSuccessRequest(id, uploadSuccessParams)
                        .then(onSuccessFromServer, onFailureFromServer);
                }, self);

                submitSuccessRequest();

                return promise;
            }

            // If we are not asking the local server about the file, just delegate to the parent `_onComplete`.
            return qq.FineUploaderBasic.prototype._onComplete.apply(this, arguments);
        },

        // If the failure occurred on an upload success request (and a reset was not ordered), try to resend that instead.
        _manualRetry: function(id) {
            var successRequestCallback = this._failedSuccessRequestCallbacks[id];

            return qq.FineUploaderBasic.prototype._manualRetry.call(this, id, successRequestCallback);
        }
    };
}());

/*globals qq */
/**
 * This defines FineUploaderBasic mode w/ support for uploading to S3, which provides all the basic
 * functionality of Fine Uploader Basic as well as code to handle uploads directly to S3.
 * Some inherited options and API methods have a special meaning in the context of the S3 uploader.
 */
(function(){
    "use strict";

    qq.s3.FineUploaderBasic = function(o) {
        var options = {
            request: {
                // public key (required for server-side signing, ignored if `credentials` have been provided)
                accessKey: null,
                // Making this configurable in the traditional uploader was probably a bad idea.
                // Let's just set this to "uuid" in the S3 uploader and not document the fact that this can be changed.
                uuidName: "uuid"
            },

            objectProperties: {
                acl: "private",

                // 'uuid', 'filename', or a function which may be promissory
                key: "uuid",

                reducedRedundancy: false,

                serverSideEncryption: false
            },

            credentials: {
                // Public key (required).
                accessKey: null,
                // Private key (required).
                secretKey: null,
                // Expiration date for the credentials (required).  May be an ISO string or a `Date`.
                expiration: null,
                // Temporary credentials session token.
                // Only required for temporary credentials obtained via AssumeRoleWithWebIdentity.
                sessionToken: null
            },

            // optional/ignored if `credentials` is provided
            signature: {
                endpoint: null,
                customHeaders: {}
            },

            uploadSuccess: {
                endpoint: null,

                // In addition to the default params sent by Fine Uploader
                params: {},

                customHeaders: {}
            },

            // required if non-File-API browsers, such as IE9 and older, are used
            iframeSupport: {
                localBlankPagePath: null
            },

            chunking: {
                // minimum part size is 5 MiB when uploading to S3
                partSize: 5242880
            },

            resume: {
                recordsExpireIn: 7 // days
            },

            cors: {
                allowXdr: true
            },

            callbacks: {
                onCredentialsExpired: function() {}
            }
        };

        // Replace any default options with user defined ones
        qq.extend(options, o, true);

        if (!this.setCredentials(options.credentials, true)) {
            this._currentCredentials.accessKey = options.request.accessKey;
        }

        this._aclStore = this._createStore(options.objectProperties.acl);

        // Call base module
        qq.FineUploaderBasic.call(this, options);

        this._uploadSuccessParamsStore = this._createStore(this._options.uploadSuccess.params);

        // This will hold callbacks for failed uploadSuccess requests that will be invoked on retry.
        // Indexed by file ID.
        this._failedSuccessRequestCallbacks = {};

        // Holds S3 keys for file representations constructed from a session request.
        this._cannedKeys = {};
    };

    // Inherit basic public & private API methods.
    qq.extend(qq.s3.FineUploaderBasic.prototype, qq.basePublicApi);
    qq.extend(qq.s3.FineUploaderBasic.prototype, qq.basePrivateApi);
    qq.extend(qq.s3.FineUploaderBasic.prototype, qq.nonTraditionalBasePublicApi);
    qq.extend(qq.s3.FineUploaderBasic.prototype, qq.nonTraditionalBasePrivateApi);

    // Define public & private API methods for this module.
    qq.extend(qq.s3.FineUploaderBasic.prototype, {
        /**
         * @param id File ID
         * @returns {*} Key name associated w/ the file, if one exists
         */
        getKey: function(id) {
            /* jshint eqnull:true */
            if (this._cannedKeys[id] == null) {
                return this._handler.getThirdPartyFileId(id);
            }

            return this._cannedKeys[id];
        },

        /**
         * Override the parent's reset function to cleanup various S3-related items.
         */
        reset: function() {
            qq.FineUploaderBasic.prototype.reset.call(this);
            this._failedSuccessRequestCallbacks = [];
        },

        setUploadSuccessParams: function(params, id) {
            this._uploadSuccessParamsStore.set(params, id);
        },

        setCredentials: function(credentials, ignoreEmpty) {
            if (credentials && credentials.secretKey) {
                if (!credentials.accessKey) {
                    throw new qq.Error("Invalid credentials: no accessKey");
                }
                else if (!credentials.expiration) {
                    throw new qq.Error("Invalid credentials: no expiration");
                }
                else {
                    this._currentCredentials = qq.extend({}, credentials);

                    // Ensure expiration is a `Date`.  If initially a string, assuming it is in ISO format.
                    if (qq.isString(credentials.expiration)) {
                        this._currentCredentials.expiration = new Date(credentials.expiration);
                    }
                }

                return true;
            }
            else if (!ignoreEmpty) {
                throw new qq.Error("Invalid credentials parameter!");
            }
            else {
                this._currentCredentials = {};
            }
        },

        setAcl: function(acl, id) {
            this._aclStore.set(acl, id);
        },

        /**
         * Ensures the parent's upload handler creator passes any additional S3-specific options to the handler as well
         * as information required to instantiate the specific handler based on the current browser's capabilities.
         *
         * @returns {qq.UploadHandler}
         * @private
         */
        _createUploadHandler: function() {
            var self = this,
                additionalOptions = {
                    objectProperties: this._options.objectProperties,
                    aclStore: this._aclStore,
                    signature: this._options.signature,
                    iframeSupport: this._options.iframeSupport,
                    getKeyName: qq.bind(this._determineKeyName, this),
                    // pass size limit validation values to include in the request so AWS enforces this server-side
                    validation: {
                        minSizeLimit: this._options.validation.minSizeLimit,
                        maxSizeLimit: this._options.validation.sizeLimit
                    }
                };

            // We assume HTTP if it is missing from the start of the endpoint string.
            qq.override(this._endpointStore, function(super_) {
                return {
                    get: function(id) {
                        var endpoint = super_.get(id);

                        if (endpoint.indexOf("http") < 0) {
                            return "http://" + endpoint;
                        }

                        return endpoint;
                    }
                };
            });

            // Param names should be lower case to avoid signature mismatches
            qq.override(this._paramsStore, function(super_) {
                return {
                    get: function(id) {
                        var oldParams = super_.get(id),
                            modifiedParams = {};

                        qq.each(oldParams, function(name, val) {
                            modifiedParams[name.toLowerCase()] = val;
                        });

                        return modifiedParams;
                    }
                };
            });

            additionalOptions.signature.credentialsProvider = {
                get: function() {
                    return self._currentCredentials;
                },

                onExpired: function() {
                    var updateCredentials = new qq.Promise(),
                        callbackRetVal = self._options.callbacks.onCredentialsExpired();

                    if (callbackRetVal instanceof qq.Promise) {
                        callbackRetVal.then(function(credentials) {
                            try {
                                self.setCredentials(credentials);
                                updateCredentials.success();
                            }
                            catch (error) {
                                self.log("Invalid credentials returned from onCredentialsExpired callback! (" + error.message + ")", "error");
                                updateCredentials.failure("onCredentialsExpired did not return valid credentials.");
                            }
                        }, function(errorMsg) {
                            self.log("onCredentialsExpired callback indicated failure! (" + errorMsg + ")", "error");
                            updateCredentials.failure("onCredentialsExpired callback failed.");
                        });
                    }
                    else {
                        self.log("onCredentialsExpired callback did not return a promise!", "error");
                        updateCredentials.failure("Unexpected return value for onCredentialsExpired.");
                    }

                    return updateCredentials;
                }
            };

            return qq.FineUploaderBasic.prototype._createUploadHandler.call(this, additionalOptions, "s3");
        },

        /**
         * Determine the file's key name and passes it to the caller via a promissory callback.  This also may
         * delegate to an integrator-defined function that determines the file's key name on demand,
         * which also may be promissory.
         *
         * @param id ID of the file
         * @param filename Name of the file
         * @returns {qq.Promise} A promise that will be fulfilled when the key name has been determined (and will be passed to the caller via the success callback).
         * @private
         */
        _determineKeyName: function(id, filename) {
            /*jshint -W015*/
            var promise = new qq.Promise(),
                keynameLogic = this._options.objectProperties.key,
                extension = qq.getExtension(filename),
                onGetKeynameFailure = promise.failure,
                onGetKeynameSuccess = function(keyname, extension) {
                    var keynameToUse = keyname;

                    if (extension !== undefined) {
                        keynameToUse += "." + extension;
                    }

                    promise.success(keynameToUse);
                };

            switch(keynameLogic) {
                case "uuid":
                    onGetKeynameSuccess(this.getUuid(id), extension);
                    break;
                case "filename":
                    onGetKeynameSuccess(filename);
                    break;
                default:
                    if (qq.isFunction(keynameLogic)) {
                        this._handleKeynameFunction(keynameLogic, id, onGetKeynameSuccess, onGetKeynameFailure);
                    }
                    else {
                        this.log(keynameLogic + " is not a valid value for the s3.keyname option!", "error");
                        onGetKeynameFailure();
                    }
            }

            return promise;
        },

        /**
         * Called by the internal onUpload handler if the integrator has supplied a function to determine
         * the file's key name.  The integrator's function may be promissory.  We also need to fulfill
         * the promise contract associated with the caller as well.
         *
         * @param keynameFunc Integrator-supplied function that must be executed to determine the key name.  May be promissory.
         * @param id ID of the associated file
         * @param successCallback Invoke this if key name retrieval is successful, passing in the key name.
         * @param failureCallback Invoke this if key name retrieval was unsuccessful.
         * @private
         */
        _handleKeynameFunction: function(keynameFunc, id, successCallback, failureCallback) {
            var self = this,
                onSuccess = function(keyname) {
                    successCallback(keyname);
                },
                onFailure = function(reason) {
                    self.log(qq.format("Failed to retrieve key name for {}.  Reason: {}", id, reason || "null"), "error");
                    failureCallback(reason);
                },
                keyname = keynameFunc.call(this, id);


            if (keyname instanceof qq.Promise) {
                keyname.then(onSuccess, onFailure);
            }
            /*jshint -W116*/
            else if (keyname == null) {
                onFailure();
            }
            else {
                onSuccess(keyname);
            }
        },

        _getEndpointSpecificParams: function(id, response, maybeXhr) {
            var params = {
                key: this.getKey(id),
                uuid: this.getUuid(id),
                name: this.getName(id),
                bucket: qq.s3.util.getBucket(this._endpointStore.get(id))
            };

            if (maybeXhr && maybeXhr.getResponseHeader("ETag")) {
                params.etag = maybeXhr.getResponseHeader("ETag");
            }
            else if (response.etag) {
                params.etag = response.etag;
            }

            return params;
        },

        // Hooks into the base internal `_onSubmitDelete` to add key and bucket params to the delete file request.
        _onSubmitDelete: function(id, onSuccessCallback) {
            var additionalMandatedParams = {
                key: this.getKey(id),
                bucket: qq.s3.util.getBucket(this._endpointStore.get(id))
            };

            return qq.FineUploaderBasic.prototype._onSubmitDelete.call(this, id, onSuccessCallback, additionalMandatedParams);
        },

        _addCannedFile: function(sessionData) {
            var id;

            /* jshint eqnull:true */
            if (sessionData.s3Key == null) {
                throw new qq.Error("Did not find s3Key property in server session response.  This is required!");
            }
            else {
                id = qq.FineUploaderBasic.prototype._addCannedFile.apply(this, arguments);
                this._cannedKeys[id] = sessionData.s3Key;
            }

            return id;
        }
    });
}());

/* globals qq, CryptoJS */
/**
 * Handles signature determination for HTML Form Upload requests and Multipart Uploader requests (via the S3 REST API).
 *
 * If the S3 requests are to be signed server side, this module will send a POST request to the server in an attempt
 * to solicit signatures for various S3-related requests.  This module also parses the response and attempts
 * to determine if the effort was successful.
 *
 * If the S3 requests are to be signed client-side, without the help of a server, this module will utilize CryptoJS to
 * sign the requests directly in the browser and send them off to S3.
 *
 * @param o Options associated with all such requests
 * @returns {{getSignature: Function}} API method used to initiate the signature request.
 * @constructor
 */
qq.s3.RequestSigner = function(o) {
    "use strict";

    var requester,
        thisSignatureRequester = this,
        pendingSignatures = {},
        options = {
            expectingPolicy: false,
            method: "POST",
            signatureSpec: {
                credentialsProvider: {},
                endpoint: null,
                customHeaders: {}
            },
            maxConnections: 3,
            paramsStore: {},
            cors: {
                expected: false,
                sendCredentials: false
            },
            log: function(str, level) {}
        },
        credentialsProvider;

    qq.extend(options, o, true);
    credentialsProvider = options.signatureSpec.credentialsProvider;

    function handleSignatureReceived(id, xhrOrXdr, isError) {
        var responseJson = xhrOrXdr.responseText,
            pendingSignatureData = pendingSignatures[id],
            promise = pendingSignatureData.promise,
            errorMessage, response;

        delete pendingSignatures[id];

        // Attempt to parse what we would expect to be a JSON response
        if (responseJson) {
            try {
                response = qq.parseJson(responseJson);
            }
            catch (error) {
                options.log("Error attempting to parse signature response: " + error, "error");
            }
        }

        // If we have received a parsable response, and it has an `invalid` property,
        // the policy document or request headers may have been tampered with client-side.
        if (response && response.invalid) {
            isError = true;
            errorMessage = "Invalid policy document or request headers!";
        }
        // Make sure the response contains policy & signature properties
        else if (response) {
            if (options.expectingPolicy && !response.policy) {
                isError = true;
                errorMessage = "Response does not include the base64 encoded policy!";
            }
            else if (!response.signature) {
                isError = true;
                errorMessage = "Response does not include the signature!";
            }
        }
        // Something unknown went wrong
        else {
            isError = true;
            errorMessage = "Received an empty or invalid response from the server!";
        }

        if (isError) {
            if (errorMessage) {
                options.log(errorMessage, "error");
            }

            promise.failure(errorMessage);
        }
        else {
            promise.success(response);
        }
    }

    function getToSignAndEndOfUrl(type, bucket, key, contentType, headers, uploadId, partNum) {
        var method = "POST",
            headerNames = [],
            headersAsString = "",
            endOfUrl;

        /*jshint indent:false */
        switch(type) {
            case thisSignatureRequester.REQUEST_TYPE.MULTIPART_ABORT:
                method = "DELETE";
                endOfUrl = qq.format("uploadId={}", uploadId);
                break;
            case thisSignatureRequester.REQUEST_TYPE.MULTIPART_INITIATE:
                endOfUrl = "uploads";
                break;
            case thisSignatureRequester.REQUEST_TYPE.MULTIPART_COMPLETE:
                endOfUrl = qq.format("uploadId={}", uploadId);
                break;
            case thisSignatureRequester.REQUEST_TYPE.MULTIPART_UPLOAD:
                method = "PUT";
                endOfUrl = qq.format("partNumber={}&uploadId={}", partNum, uploadId);
                break;
        }

        endOfUrl = key + "?" + endOfUrl;

        qq.each(headers, function(name) {
            headerNames.push(name);
        });
        headerNames.sort();

        qq.each(headerNames, function(idx, name) {
            headersAsString += name + ":" + headers[name] + "\n";
        });

        return {
            toSign: qq.format("{}\n\n{}\n\n{}/{}/{}",
                        method, contentType || "", headersAsString || "\n", bucket, endOfUrl),
            endOfUrl: endOfUrl
        };
    }

    function determineSignatureClientSide(toBeSigned, signatureEffort, updatedAccessKey, updatedSessionToken) {
        var updatedHeaders;

        // REST API request
        if (toBeSigned.signatureConstructor) {
            if (updatedSessionToken) {
                updatedHeaders = toBeSigned.signatureConstructor.getHeaders();
                updatedHeaders[qq.s3.util.SESSION_TOKEN_PARAM_NAME] = updatedSessionToken;
                toBeSigned.signatureConstructor.withHeaders(updatedHeaders);
            }

            signApiRequest(toBeSigned.signatureConstructor.getToSign().stringToSign, signatureEffort);
        }
        // Form upload (w/ policy document)
        else {
            updatedSessionToken && qq.s3.util.refreshPolicyCredentials(toBeSigned, updatedSessionToken);
            signPolicy(toBeSigned, signatureEffort, updatedAccessKey, updatedSessionToken);
        }
    }

    function signPolicy(policy, signatureEffort, updatedAccessKey, updatedSessionToken) {
        var policyStr = JSON.stringify(policy),
            policyWordArray = CryptoJS.enc.Utf8.parse(policyStr),
            base64Policy = CryptoJS.enc.Base64.stringify(policyWordArray),
            policyHmacSha1 = CryptoJS.HmacSHA1(base64Policy, credentialsProvider.get().secretKey),
            policyHmacSha1Base64 = CryptoJS.enc.Base64.stringify(policyHmacSha1);

        signatureEffort.success({
            policy: base64Policy,
            signature: policyHmacSha1Base64
        }, updatedAccessKey, updatedSessionToken);
    }

    function signApiRequest(headersStr, signatureEffort) {
        var headersWordArray = CryptoJS.enc.Utf8.parse(headersStr),
            headersHmacSha1 = CryptoJS.HmacSHA1(headersWordArray, credentialsProvider.get().secretKey),
            headersHmacSha1Base64 = CryptoJS.enc.Base64.stringify(headersHmacSha1);

        signatureEffort.success({signature: headersHmacSha1Base64});
    }

    requester = qq.extend(this, new qq.AjaxRequester({
        method: options.method,
        contentType: "application/json; charset=utf-8",
        endpointStore: {
            get: function() {
                return options.signatureSpec.endpoint;
            }
        },
        paramsStore: options.paramsStore,
        maxConnections: options.maxConnections,
        customHeaders: options.signatureSpec.customHeaders,
        log: options.log,
        onComplete: handleSignatureReceived,
        cors: options.cors,
        successfulResponseCodes: {
            POST: [200]
        }
    }));


    qq.extend(this, {
        /**
         * On success, an object containing the parsed JSON response will be passed into the success handler if the
         * request succeeds.  Otherwise an error message will be passed into the failure method.
         *
         * @param id File ID.
         * @param toBeSigned an Object that holds the item(s) to be signed
         * @returns {qq.Promise} A promise that is fulfilled when the response has been received.
         */
        getSignature: function(id, toBeSigned) {
            var params = toBeSigned,
                signatureEffort = new qq.Promise();

            if (credentialsProvider.get().secretKey && window.CryptoJS) {
                if (credentialsProvider.get().expiration.getTime() > Date.now()) {
                    determineSignatureClientSide(toBeSigned, signatureEffort);
                }
                // If credentials are expired, ask for new ones before attempting to sign request
                else {
                    credentialsProvider.onExpired().then(function() {
                        determineSignatureClientSide(toBeSigned,
                            signatureEffort,
                            credentialsProvider.get().accessKey,
                            credentialsProvider.get().sessionToken);
                    }, function(errorMsg) {
                        options.log("Attempt to update expired credentials apparently failed! Unable to sign request.  ", "error");
                        signatureEffort.failure("Unable to sign request - expired credentials.");
                    });
                }
            }
            else {
                options.log("Submitting S3 signature request for " + id);

                if (params.signatureConstructor) {
                    params = {headers: params.signatureConstructor.getToSign().stringToSign};
                }

                requester.initTransport(id)
                    .withParams(params)
                    .send();

                pendingSignatures[id] = {
                    promise: signatureEffort
                };
            }

            return signatureEffort;
        },

        constructStringToSign: function(type, bucket, key) {
            var headers = {},
                uploadId, contentType, partNum, toSignAndEndOfUrl;

            return {
                withHeaders: function(theHeaders) {
                    headers = theHeaders;
                    return this;
                },

                withUploadId: function(theUploadId) {
                    uploadId = theUploadId;
                    return this;
                },

                withContentType: function(theContentType) {
                    contentType = theContentType;
                    return this;
                },

                withPartNum: function(thePartNum) {
                    partNum = thePartNum;
                    return this;
                },

                getToSign: function() {
                    var sessionToken = credentialsProvider.get().sessionToken;

                    headers["x-amz-date"] = new Date().toUTCString();

                    if (sessionToken) {
                        headers[qq.s3.util.SESSION_TOKEN_PARAM_NAME] = sessionToken;
                    }

                    toSignAndEndOfUrl = getToSignAndEndOfUrl(type, bucket, key, contentType, headers, uploadId, partNum);

                    return {
                        headers: (function() {
                            if (contentType) {
                                headers["Content-Type"] = contentType;
                            }

                            return headers;
                        }()),
                        endOfUrl: toSignAndEndOfUrl.endOfUrl,
                        stringToSign: toSignAndEndOfUrl.toSign
                    };
                },

                getHeaders: function() {
                    return qq.extend({}, headers);
                },

                getEndOfUrl: function() {
                    return toSignAndEndOfUrl && toSignAndEndOfUrl.endOfUrl;
                }
            };
        }
    });
};

qq.s3.RequestSigner.prototype.REQUEST_TYPE = {
    MULTIPART_INITIATE: "multipart_initiate",
    MULTIPART_COMPLETE: "multipart_complete",
    MULTIPART_ABORT: "multipart_abort",
    MULTIPART_UPLOAD: "multipart_upload"
};

/*globals qq, XMLHttpRequest*/
/**
 * Sends a POST request to the server to notify it of a successful upload to an endpoint.  The server is expected to indicate success
 * or failure via the response status.  Specific information about the failure can be passed from the server via an `error`
 * property (by default) in an "application/json" response.
 *
 * @param o Options associated with all requests.
 * @constructor
 */
qq.UploadSuccessAjaxRequester = function(o) {
    "use strict";

    var requester,
        pendingRequests = [],
        options = {
            method: "POST",
            endpoint: null,
            maxConnections: 3,
            customHeaders: {},
            paramsStore: {},
            cors: {
                expected: false,
                sendCredentials: false
            },
            log: function(str, level) {}
        };

    qq.extend(options, o);

    function handleSuccessResponse(id, xhrOrXdr, isError) {
        var promise = pendingRequests[id],
            responseJson = xhrOrXdr.responseText,
            successIndicator = {success: true},
            failureIndicator = {success: false},
            parsedResponse;

        delete pendingRequests[id];

        options.log(qq.format("Received the following response body to an upload success request for id {}: {}", id, responseJson));

        try {
            parsedResponse = qq.parseJson(responseJson);

            // If this is a cross-origin request, the server may return a 200 response w/ error or success properties
            // in order to ensure any specific error message is picked up by Fine Uploader for all browsers,
            // since XDomainRequest (used in IE9 and IE8) doesn't give you access to the
            // response body for an "error" response.
            if (isError || (parsedResponse && (parsedResponse.error || parsedResponse.success === false))) {
                options.log("Upload success request was rejected by the server.", "error");
                promise.failure(qq.extend(parsedResponse, failureIndicator));
            }
            else {
                options.log("Upload success was acknowledged by the server.");
                promise.success(qq.extend(parsedResponse, successIndicator));
            }
        }
        catch (error) {
            // This will be executed if a JSON response is not present.  This is not mandatory, so account for this properly.
            if (isError) {
                options.log(qq.format("Your server indicated failure in its upload success request response for id {}!", id), "error");
                promise.failure(failureIndicator);
            }
            else {
                options.log("Upload success was acknowledged by the server.");
                promise.success(successIndicator);
            }
        }
    }

    requester = qq.extend(this, new qq.AjaxRequester({
        method: options.method,
        endpointStore: {
            get: function() {
                return options.endpoint;
            }
        },
        paramsStore: options.paramsStore,
        maxConnections: options.maxConnections,
        customHeaders: options.customHeaders,
        log: options.log,
        onComplete: handleSuccessResponse,
        cors: options.cors,
        successfulResponseCodes: {
            POST: [200]
        }
    }));


    qq.extend(this, {
        /**
         * Sends a request to the server, notifying it that a recently submitted file was successfully sent.
         *
         * @param id ID of the associated file
         * @param spec `Object` with the properties that correspond to important values that we want to
         * send to the server with this request.
         * @returns {qq.Promise} A promise to be fulfilled when the response has been received and parsed.  The parsed
         * payload of the response will be passed into the `failure` or `success` promise method.
         */
        sendSuccessRequest: function(id, spec) {
            var promise = new qq.Promise();

            options.log("Submitting upload success request/notification for " + id);

            requester.initTransport(id)
                .withParams(spec)
                .send();

            pendingRequests[id] = promise;

            return promise;
        }
    });
};

/*globals qq*/
/**
 * Ajax requester used to send an ["Initiate Multipart Upload"](http://docs.aws.amazon.com/AmazonS3/latest/API/mpUploadInitiate.html)
 * request to S3 via the REST API.
 *
 * @param o Options from the caller - will override the defaults.
 * @constructor
 */
qq.s3.InitiateMultipartAjaxRequester = function(o) {
    "use strict";

    var requester,
        pendingInitiateRequests = {},
        options = {
            filenameParam: "qqfilename",
            method: "POST",
            endpointStore: null,
            paramsStore: null,
            signatureSpec: null,
            aclStore: null,
            reducedRedundancy: false,
            serverSideEncryption: false,
            maxConnections: 3,
            getContentType: function(id) {},
            getKey: function(id) {},
            getName: function(id) {},
            log: function(str, level) {}
        },
        getSignatureAjaxRequester;

    qq.extend(options, o);

    getSignatureAjaxRequester = new qq.s3.RequestSigner({
        signatureSpec: options.signatureSpec,
        cors: options.cors,
        log: options.log
    });


    /**
     * Determine all headers for the "Initiate MPU" request, including the "Authorization" header, which must be determined
     * by the local server.  This is a promissory function.  If the server responds with a signature, the headers
     * (including the Authorization header) will be passed into the success method of the promise.  Otherwise, the failure
     * method on the promise will be called.
     *
     * @param id Associated file ID
     * @returns {qq.Promise}
     */
    function getHeaders(id) {
        var bucket = qq.s3.util.getBucket(options.endpointStore.get(id)),
            headers = {},
            promise = new qq.Promise(),
            key = options.getKey(id),
            signatureConstructor;

        headers["x-amz-acl"] = options.aclStore.get(id);

        if (options.reducedRedundancy) {
            headers[qq.s3.util.REDUCED_REDUNDANCY_PARAM_NAME] = qq.s3.util.REDUCED_REDUNDANCY_PARAM_VALUE;
        }

        if (options.serverSideEncryption) {
            headers[qq.s3.util.SERVER_SIDE_ENCRYPTION_PARAM_NAME] = qq.s3.util.SERVER_SIDE_ENCRYPTION_PARAM_VALUE;
        }

        headers[qq.s3.util.AWS_PARAM_PREFIX + options.filenameParam] = encodeURIComponent(options.getName(id));

        qq.each(options.paramsStore.get(id), function(name, val) {
            headers[qq.s3.util.AWS_PARAM_PREFIX + name] = encodeURIComponent(val);
        });

        signatureConstructor = getSignatureAjaxRequester.constructStringToSign
            (getSignatureAjaxRequester.REQUEST_TYPE.MULTIPART_INITIATE, bucket, key)
            .withContentType(options.getContentType(id))
            .withHeaders(headers);

        // Ask the local server to sign the request.  Use this signature to form the Authorization header.
        getSignatureAjaxRequester.getSignature(id, {signatureConstructor: signatureConstructor}).then(function(response) {
            headers = signatureConstructor.getHeaders();
            headers.Authorization = "AWS " + options.signatureSpec.credentialsProvider.get().accessKey + ":" + response.signature;
            promise.success(headers, signatureConstructor.getEndOfUrl());
        }, promise.failure);

        return promise;
    }

    /**
     * Called by the base ajax requester when the response has been received.  We definitively determine here if the
     * "Initiate MPU" request has been a success or not.
     *
     * @param id ID associated with the file.
     * @param xhr `XMLHttpRequest` object containing the response, among other things.
     * @param isError A boolean indicating success or failure according to the base ajax requester (primarily based on status code).
     */
    function handleInitiateRequestComplete(id, xhr, isError) {
        var promise = pendingInitiateRequests[id],
            domParser = new DOMParser(),
            responseDoc = domParser.parseFromString(xhr.responseText, "application/xml"),
            uploadIdElements, messageElements, uploadId, errorMessage, status;

        delete pendingInitiateRequests[id];

        // The base ajax requester may declare the request to be a failure based on status code.
        if (isError) {
            status = xhr.status;

            messageElements = responseDoc.getElementsByTagName("Message");
            if (messageElements.length > 0) {
                errorMessage = messageElements[0].textContent;
            }
        }
        // If the base ajax requester has not declared this a failure, make sure we can retrieve the uploadId from the response.
        else {
            uploadIdElements = responseDoc.getElementsByTagName("UploadId");
            if (uploadIdElements.length > 0) {
                uploadId = uploadIdElements[0].textContent;
            }
            else {
                errorMessage = "Upload ID missing from request";
            }
        }

        // Either fail the promise (passing a descriptive error message) or declare it a success (passing the upload ID)
        if (uploadId === undefined) {
            if (errorMessage) {
                options.log(qq.format("Specific problem detected initiating multipart upload request for {}: '{}'.", id, errorMessage), "error");
            }
            else {
                options.log(qq.format("Unexplained error with initiate multipart upload request for {}.  Status code {}.", id, status), "error");
            }

            promise.failure("Problem initiating upload request with Amazon.", xhr);
        }
        else {
            options.log(qq.format("Initiate multipart upload request successful for {}.  Upload ID is {}", id, uploadId));
            promise.success(uploadId, xhr);
        }
    }

    requester = qq.extend(this, new qq.AjaxRequester({
        method: options.method,
        contentType: null,
        endpointStore: options.endpointStore,
        maxConnections: options.maxConnections,
        allowXRequestedWithAndCacheControl: false, //These headers are not necessary & would break some installations if added
        log: options.log,
        onComplete: handleInitiateRequestComplete,
        successfulResponseCodes: {
            POST: [200]
        }
    }));


    qq.extend(this, {
        /**
         * Sends the "Initiate MPU" request to AWS via the REST API.  First, though, we must get a signature from the
         * local server for the request.  If all is successful, the uploadId from AWS will be passed into the promise's
         * success handler. Otherwise, an error message will ultimately be passed into the failure method.
         *
         * @param id The ID associated with the file
         * @returns {qq.Promise}
         */
        send: function(id) {
            var promise = new qq.Promise();

            getHeaders(id).then(function(headers, endOfUrl) {
                options.log("Submitting S3 initiate multipart upload request for " + id);

                pendingInitiateRequests[id] = promise;
                requester.initTransport(id)
                    .withPath(endOfUrl)
                    .withHeaders(headers)
                    .send();
            }, promise.failure);

            return promise;
        }
    });
};

/*globals qq*/
/**
 * Ajax requester used to send an ["Complete Multipart Upload"](http://docs.aws.amazon.com/AmazonS3/latest/API/mpUploadComplete.html)
 * request to S3 via the REST API.
 *
 * @param o Options passed by the creator, to overwrite any default option values.
 * @constructor
 */
qq.s3.CompleteMultipartAjaxRequester = function(o) {
    "use strict";

    var requester,
        pendingCompleteRequests = {},
        options = {
            method: "POST",
            contentType: "text/xml",
            endpointStore: null,
            signatureSpec: null,
            maxConnections: 3,
            getKey: function(id) {},
            log: function(str, level) {}
        },
        getSignatureAjaxRequester;

    qq.extend(options, o);

    // Transport for requesting signatures (for the "Complete" requests) from the local server
    getSignatureAjaxRequester = new qq.s3.RequestSigner({
        signatureSpec: options.signatureSpec,
        cors: options.cors,
        log: options.log
    });

    /**
     * Attach all required headers (including Authorization) to the "Complete" request.  This is a promissory function
     * that will fulfill the associated promise once all headers have been attached or when an error has occurred that
     * prevents headers from being attached.
     *
     * @param id Associated file ID
     * @param uploadId ID of the associated upload, according to AWS
     * @returns {qq.Promise}
     */
    function getHeaders(id, uploadId) {
        var headers = {},
            promise = new qq.Promise(),
            endpoint = options.endpointStore.get(id),
            bucket = qq.s3.util.getBucket(endpoint),
            signatureConstructor = getSignatureAjaxRequester.constructStringToSign
                (getSignatureAjaxRequester.REQUEST_TYPE.MULTIPART_COMPLETE, bucket, options.getKey(id))
                .withUploadId(uploadId)
                .withContentType("application/xml; charset=UTF-8");

        // Ask the local server to sign the request.  Use this signature to form the Authorization header.
        getSignatureAjaxRequester.getSignature(id, {signatureConstructor: signatureConstructor}).then(function(response) {
            headers = signatureConstructor.getHeaders();
            headers.Authorization = "AWS " + options.signatureSpec.credentialsProvider.get().accessKey + ":" + response.signature;
            promise.success(headers, signatureConstructor.getEndOfUrl());
        }, promise.failure);

        return promise;
    }

    /**
     * Called by the base ajax requester when the response has been received.  We definitively determine here if the
     * "Complete MPU" request has been a success or not.
     *
     * @param id ID associated with the file.
     * @param xhr `XMLHttpRequest` object containing the response, among other things.
     * @param isError A boolean indicating success or failure according to the base ajax requester (primarily based on status code).
     */
    function handleCompleteRequestComplete(id, xhr, isError) {
        var promise = pendingCompleteRequests[id],
            domParser = new DOMParser(),
            endpoint = options.endpointStore.get(id),
            bucket = qq.s3.util.getBucket(endpoint),
            key = options.getKey(id),
            responseDoc = domParser.parseFromString(xhr.responseText, "application/xml"),
            bucketEls = responseDoc.getElementsByTagName("Bucket"),
            keyEls = responseDoc.getElementsByTagName("Key");

        delete pendingCompleteRequests[id];

        options.log(qq.format("Complete response status {}, body = {}", xhr.status, xhr.responseText));

        // If the base requester has determine this a failure, give up.
        if (isError) {
            options.log(qq.format("Complete Multipart Upload request for {} failed with status {}.", id, xhr.status), "error");
        }
        else {
            // Make sure the correct bucket and key has been specified in the XML response from AWS.
            if (bucketEls.length && keyEls.length) {
                if (bucketEls[0].textContent !== bucket) {
                    isError = true;
                    options.log(qq.format("Wrong bucket in response to Complete Multipart Upload request for {}.", id), "error");
                }

                // TODO Compare key name from response w/ expected key name if AWS ever fixes the encoding of key names in this response.
            }
            else {
                isError = true;
                options.log(qq.format("Missing bucket and/or key in response to Complete Multipart Upload request for {}.", id), "error");
            }
        }

        if (isError) {
            promise.failure("Problem asking Amazon to combine the parts!", xhr);
        }
        else {
            promise.success(xhr);
        }
    }

    /**
     * @param etagEntries Array of objects containing `etag` values and their associated `part` numbers.
     * @returns {string} XML string containing the body to send with the "Complete" request
     */
    function getCompleteRequestBody(etagEntries) {
        var doc = document.implementation.createDocument(null, "CompleteMultipartUpload", null);

        // Construct an XML document for each pair of etag/part values that correspond to part uploads.
        qq.each(etagEntries, function(idx, etagEntry) {
            var part = etagEntry.part,
                etag = etagEntry.etag,
                partEl = doc.createElement("Part"),
                partNumEl = doc.createElement("PartNumber"),
                partNumTextEl = doc.createTextNode(part),
                etagTextEl = doc.createTextNode(etag),
                etagEl = doc.createElement("ETag");

            etagEl.appendChild(etagTextEl);
            partNumEl.appendChild(partNumTextEl);
            partEl.appendChild(partNumEl);
            partEl.appendChild(etagEl);
            qq(doc).children()[0].appendChild(partEl);
        });

        // Turn the resulting XML document into a string fit for transport.
        return new XMLSerializer().serializeToString(doc);
    }

    requester = qq.extend(this, new qq.AjaxRequester({
        method: options.method,
        contentType: "application/xml; charset=UTF-8",
        endpointStore: options.endpointStore,
        maxConnections: options.maxConnections,
        allowXRequestedWithAndCacheControl: false, //These headers are not necessary & would break some installations if added
        log: options.log,
        onComplete: handleCompleteRequestComplete,
        successfulResponseCodes: {
            POST: [200]
        }
    }));


    qq.extend(this, {
        /**
         * Sends the "Complete" request and fulfills the returned promise when the success of this request is known.
         *
         * @param id ID associated with the file.
         * @param uploadId AWS uploadId for this file
         * @param etagEntries Array of objects containing `etag` values and their associated `part` numbers.
         * @returns {qq.Promise}
         */
        send: function(id, uploadId, etagEntries) {
            var promise = new qq.Promise();

            getHeaders(id, uploadId).then(function(headers, endOfUrl) {
                var body = getCompleteRequestBody(etagEntries);

                options.log("Submitting S3 complete multipart upload request for " + id);

                pendingCompleteRequests[id] = promise;
                delete headers["Content-Type"];

                requester.initTransport(id)
                    .withPath(endOfUrl)
                    .withHeaders(headers)
                    .withPayload(body)
                    .send();
            }, promise.failure);

            return promise;
        }
    });
};

/*globals qq */
/**
 * Ajax requester used to send an ["Abort Multipart Upload"](http://docs.aws.amazon.com/AmazonS3/latest/API/mpUploadAbort.html)
 * request to S3 via the REST API.

 * @param o
 * @constructor
 */
qq.s3.AbortMultipartAjaxRequester = function(o) {
    "use strict";

    var requester,
        options = {
            method: "DELETE",
            endpointStore: null,
            signatureSpec: null,
            maxConnections: 3,
            getKey: function(id) {},
            log: function(str, level) {}
        },
        getSignatureAjaxRequester;

    qq.extend(options, o);

    // Transport for requesting signatures (for the "Complete" requests) from the local server
    getSignatureAjaxRequester = new qq.s3.RequestSigner({
        signatureSpec: options.signatureSpec,
        cors: options.cors,
        log: options.log
    });

    /**
     * Attach all required headers (including Authorization) to the "Abort" request.  This is a promissory function
     * that will fulfill the associated promise once all headers have been attached or when an error has occurred that
     * prevents headers from being attached.
     *
     * @param id Associated file ID
     * @param uploadId ID of the associated upload, according to AWS
     * @returns {qq.Promise}
     */
    function getHeaders(id, uploadId) {
        var headers = {},
            promise = new qq.Promise(),
            endpoint = options.endpointStore.get(id),
            bucket = qq.s3.util.getBucket(endpoint),
            signatureConstructor = getSignatureAjaxRequester.constructStringToSign
                (getSignatureAjaxRequester.REQUEST_TYPE.MULTIPART_ABORT, bucket, options.getKey(id))
                .withUploadId(uploadId);

        // Ask the local server to sign the request.  Use this signature to form the Authorization header.
        getSignatureAjaxRequester.getSignature(id, {signatureConstructor: signatureConstructor}).then(function(response) {
            headers = signatureConstructor.getHeaders();
            headers.Authorization = "AWS " + options.signatureSpec.credentialsProvider.get().accessKey + ":" + response.signature;
            promise.success(headers, signatureConstructor.getEndOfUrl());
        }, promise.failure);

        return promise;
    }

    /**
     * Called by the base ajax requester when the response has been received.  We definitively determine here if the
     * "Abort MPU" request has been a success or not.
     *
     * @param id ID associated with the file.
     * @param xhr `XMLHttpRequest` object containing the response, among other things.
     * @param isError A boolean indicating success or failure according to the base ajax requester (primarily based on status code).
     */
    function handleAbortRequestComplete(id, xhr, isError) {
        var domParser = new DOMParser(),
            responseDoc = domParser.parseFromString(xhr.responseText, "application/xml"),
            errorEls = responseDoc.getElementsByTagName("Error"),
            awsErrorMsg;


        options.log(qq.format("Abort response status {}, body = {}", xhr.status, xhr.responseText));

        // If the base requester has determine this a failure, give up.
        if (isError) {
            options.log(qq.format("Abort Multipart Upload request for {} failed with status {}.", id, xhr.status), "error");
        }
        else {
            // Make sure the correct bucket and key has been specified in the XML response from AWS.
            if (errorEls.length) {
                isError = true;
                awsErrorMsg = responseDoc.getElementsByTagName("Message")[0].textContent;
                options.log(qq.format("Failed to Abort Multipart Upload request for {}.  Error: {}", id, awsErrorMsg), "error");
            }
            else {
                options.log(qq.format("Abort MPU request succeeded for file ID {}.", id));
            }
        }
    }


    requester = qq.extend(this, new qq.AjaxRequester({
        validMethods: ["DELETE"],
        method: options.method,
        contentType: null,
        endpointStore: options.endpointStore,
        maxConnections: options.maxConnections,
        allowXRequestedWithAndCacheControl: false, //These headers are not necessary & would break some installations if added
        log: options.log,
        onComplete: handleAbortRequestComplete,
        successfulResponseCodes: {
            DELETE: [204]
        }
    }));


    qq.extend(this, {
        /**
         * Sends the "Abort" request.
         *
         * @param id ID associated with the file.
         * @param uploadId AWS uploadId for this file
         */
        send: function(id, uploadId) {
            getHeaders(id, uploadId).then(function(headers, endOfUrl) {
                options.log("Submitting S3 Abort multipart upload request for " + id);
                requester.initTransport(id)
                    .withPath(endOfUrl)
                    .withHeaders(headers)
                    .send();
            });
        }
    });
};

/*globals qq */
/**
 * Upload handler used by the upload to S3 module that assumes the current user agent does not have any support for the
 * File API, and, therefore, makes use of iframes and forms to submit the files directly to S3 buckets via the associated
 * AWS API.
 *
 * @param options Options passed from the base handler
 * @param proxy Callbacks & methods used to query for or push out data/changes
 */
qq.s3.UploadHandlerForm = function(options, proxy) {
    "use strict";

    var handler = this,
        uploadCompleteCallback = proxy.onUploadComplete,
        onUuidChanged = proxy.onUuidChanged,
        getName = proxy.getName,
        getUuid = proxy.getUuid,
        log = proxy.log,
        onCompleteCallback = options.onComplete,
        onUpload = options.onUpload,
        onGetKeyName = options.getKeyName,
        filenameParam = options.filenameParam,
        paramsStore = options.paramsStore,
        endpointStore = options.endpointStore,
        aclStore = options.aclStore,
        reducedRedundancy = options.objectProperties.reducedRedundancy,
        serverSideEncryption = options.objectProperties.serverSideEncryption,
        validation = options.validation,
        signature = options.signature,
        successRedirectUrl = options.iframeSupport.localBlankPagePath,
        credentialsProvider = options.signature.credentialsProvider,
        getSignatureAjaxRequester = new qq.s3.RequestSigner({
            signatureSpec: signature,
            cors: options.cors,
            log: log
        });


    if (successRedirectUrl === undefined) {
        throw new Error("successRedirectEndpoint MUST be defined if you intend to use browsers that do not support the File API!");
    }

    /**
     * Attempt to parse the contents of an iframe after receiving a response from the server.  If the contents cannot be
     * read (perhaps due to a security error) it is safe to assume that the upload was not successful since Amazon should
     * have redirected to a known endpoint that should provide a parseable response.
     *
     * @param id ID of the associated file
     * @param iframe target of the form submit
     * @returns {boolean} true if the contents can be read, false otherwise
     */
    function isValidResponse(id, iframe) {
        var response,
            endpoint = options.endpointStore.get(id),
            bucket = qq.s3.util.getBucket(endpoint);


        //IE may throw an "access is denied" error when attempting to access contentDocument on the iframe in some cases
        try {
            // iframe.contentWindow.document - for IE<7
            var doc = iframe.contentDocument || iframe.contentWindow.document,
                innerHtml = doc.body.innerHTML;

            var responseData = qq.s3.util.parseIframeResponse(iframe);
            if (responseData.bucket === bucket &&
                responseData.key === qq.s3.util.encodeQueryStringParam(handler._getFileState(id).key)) {

                return true;
            }

            log("Response from AWS included an unexpected bucket or key name.", "error");

        }
        catch(error) {
            log("Error when attempting to parse form upload response (" + error.message + ")", "error");
        }

        return false;
    }

    function generateAwsParams(id) {
        /*jshint -W040 */
        var customParams = paramsStore.get(id);

        customParams[filenameParam] = getName(id);

        return qq.s3.util.generateAwsParams({
                endpoint: endpointStore.get(id),
                params: customParams,
                key: handler._getFileState(id).key,
                accessKey: credentialsProvider.get().accessKey,
                sessionToken: credentialsProvider.get().sessionToken,
                acl: aclStore.get(id),
                minFileSize: validation.minSizeLimit,
                maxFileSize: validation.maxSizeLimit,
                successRedirectUrl: successRedirectUrl,
                reducedRedundancy: reducedRedundancy,
                serverSideEncryption: serverSideEncryption,
                log: log
            },
            qq.bind(getSignatureAjaxRequester.getSignature, this, id));
    }

    /**
     * Creates form, that will be submitted to iframe
     */
    function createForm(id, iframe) {
        var promise = new qq.Promise(),
            method = options.demoMode ? "GET" : "POST",
            endpoint = options.endpointStore.get(id),
            fileName = getName(id);

        generateAwsParams(id).then(function(params) {
            var form = handler._initFormForUpload({
                method: method,
                endpoint: endpoint,
                params: params,
                paramsInBody: true,
                targetName: iframe.name
            });

            promise.success(form);
        }, function(errorMessage) {
            promise.failure(errorMessage);
            handleFinishedUpload(id, iframe, fileName, {error: errorMessage});
        });

        return promise;
    }

    function handleUpload(id) {
        var fileName = getName(id),
            iframe = handler._createIframe(id),
            input = handler._getFileState(id).input;

        createForm(id, iframe).then(function(form) {
            onUpload(id, fileName);

            form.appendChild(input);

            // Register a callback when the response comes in from S3
            handler._attachLoadEvent(iframe, function(response) {
                log("iframe loaded");

                // If the common response handler has determined success or failure immediately
                if (response) {
                    // If there is something fundamentally wrong with the response (such as iframe content is not accessible)
                    if (response.success === false) {
                        log("Amazon likely rejected the upload request", "error");
                    }
                }
                // The generic response (iframe onload) handler was not able to make a determination regarding the success of the request
                else {
                    response = {};
                    response.success = isValidResponse(id, iframe);

                    // If the more specific response handle detected a problem with the response from S3
                    if (response.success === false) {
                        log("A success response was received by Amazon, but it was invalid in some way.", "error");
                    }
                    else {
                        qq.extend(response, qq.s3.util.parseIframeResponse(iframe));
                    }
                }

                handleFinishedUpload(id, iframe, fileName, response);
            });

            log("Sending upload request for " + id);
            form.submit();
            qq(form).remove();
        });
    }

    function handleFinishedUpload(id, iframe, fileName, response) {
        handler._detachLoadEvent(id);

        iframe && qq(iframe).remove();

        if (!response.success) {
            if (options.onAutoRetry(id, fileName, response)) {
                return;
            }
        }
        onCompleteCallback(id, fileName, response);
        uploadCompleteCallback(id);
    }

    qq.extend(this, new qq.AbstractUploadHandlerForm({
            options: {
                isCors: false,
                inputName: "file"
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
                name = getName(id);

            if (!input){
                throw new Error("file with passed id was not added, or already uploaded or canceled");
            }

            if (this.isValid(id)) {
                if (handler._getFileState(id).key) {
                    handleUpload(id);
                }
                else {
                    // The S3 uploader module will either calculate the key or ask the server for it
                    // and will call us back once it is known.
                    onGetKeyName(id, name).then(function(key) {
                        handler._getFileState(id).key = key;
                        handleUpload(id);
                    }, function(errorReason) {
                        handleFinishedUpload(id, null, name, {error: errorReason});
                    });
                }
            }
        },

        getThirdPartyFileId: function(id) {
            return handler._getFileState(id).key;
        }
    });
};

/*globals qq */
/**
 * Upload handler used by the upload to S3 module that depends on File API support, and, therefore, makes use of
 * `XMLHttpRequest` level 2 to upload `File`s and `Blob`s directly to S3 buckets via the associated AWS API.
 *
 * If chunking is supported and enabled, the S3 Multipart Upload REST API is utilized.
 *
 * @param spec Options passed from the base handler
 * @param proxy Callbacks & methods used to query for or push out data/changes
 */
qq.s3.UploadHandlerXhr = function(spec, proxy) {
    "use strict";

    var uploadCompleteCallback = proxy.onUploadComplete,
        onUuidChanged = proxy.onUuidChanged,
        getName = proxy.getName,
        getUuid = proxy.getUuid,
        getSize = proxy.getSize,
        log = proxy.log,
        expectedStatus = 200,
        onProgress = spec.onProgress,
        onComplete = spec.onComplete,
        onUpload = spec.onUpload,
        onGetKeyName = spec.getKeyName,
        filenameParam = spec.filenameParam,
        paramsStore = spec.paramsStore,
        endpointStore = spec.endpointStore,
        aclStore = spec.aclStore,
        reducedRedundancy = spec.objectProperties.reducedRedundancy,
        serverSideEncryption = spec.objectProperties.serverSideEncryption,
        validation = spec.validation,
        signature = spec.signature,
        chunkingPossible = spec.chunking.enabled && qq.supportedFeatures.chunking,
        resumeEnabled = spec.resume.enabled && chunkingPossible && qq.supportedFeatures.resume && window.localStorage !== undefined,
        handler = this,
        credentialsProvider = spec.signature.credentialsProvider,
        policySignatureRequester = new qq.s3.RequestSigner({
            expectingPolicy: true,
            signatureSpec: signature,
            cors: spec.cors,
            log: log
        }),
        restSignatureRequester = new qq.s3.RequestSigner({
            signatureSpec: signature,
            cors: spec.cors,
            log: log
        }),
        initiateMultipartRequester = new qq.s3.InitiateMultipartAjaxRequester({
            filenameParam: filenameParam,
            endpointStore: endpointStore,
            paramsStore: paramsStore,
            signatureSpec: signature,
            aclStore: aclStore,
            reducedRedundancy: reducedRedundancy,
            serverSideEncryption: serverSideEncryption,
            cors: spec.cors,
            log: log,
            getContentType: function(id) {
                return handler._getMimeType(id);
            },
            getKey: function(id) {
                return getUrlSafeKey(id);
            },
            getName: function(id) {
                return getName(id);
            }
        }),
        completeMultipartRequester = new qq.s3.CompleteMultipartAjaxRequester({
            endpointStore: endpointStore,
            signatureSpec: signature,
            cors: spec.cors,
            log: log,
            getKey: function(id) {
                return getUrlSafeKey(id);
            }
        }),
        abortMultipartRequester = new qq.s3.AbortMultipartAjaxRequester({
            endpointStore: endpointStore,
            signatureSpec: signature,
            cors: spec.cors,
            log: log,
            getKey: function(id) {
                return getUrlSafeKey(id);
            }
        });


// ************************** Shared ******************************

    function getUrlSafeKey(id) {
        return encodeURIComponent(getActualKey(id));
    }

    function getActualKey(id) {
        return handler._getFileState(id).key;
    }

    function setKey(id, key) {
        handler._getFileState(id).key = key;
    }

    /**
     * Initiate the upload process and possibly delegate to a more specific handler if chunking is required.
     *
     * @param id Associated file ID
     */
    function handleUpload(id) {
        var fileOrBlob = handler.getFile(id);

        handler._createXhr(id);

        if (handler._shouldChunkThisFile(id)) {
            // We might be retrying a failed in-progress upload, so it's important that we
            // don't reset this value so we don't wipe out the record of all successfully
            // uploaded chunks for this file.
            if (handler._getFileState(id).loaded === undefined) {
                handler._getFileState(id).loaded = 0;
            }

            handleChunkedUpload(id);
        }
        else {
            handler._getFileState(id).loaded = 0;
            handleSimpleUpload(id);
        }
    }

    function getReadyStateChangeHandler(id) {
        var xhr = handler._getFileState(id).xhr;

        return function() {
            if (xhr.readyState === 4) {
                if (handler._getFileState(id).chunking.enabled) {
                    uploadChunkCompleted(id);
                }
                else {
                    uploadCompleted(id);
                }
            }
        };
    }

    // Determine if the upload should be restarted on the next retry attempt
    // based on the error code returned in the response from AWS.
    function shouldResetOnRetry(errorCode) {
        /*jshint -W014 */
        return errorCode === "EntityTooSmall"
            || errorCode === "InvalidPart"
            || errorCode === "InvalidPartOrder"
            || errorCode === "NoSuchUpload";
    }

    /**
     * Note that this is called when an upload has reached a termination point,
     * regardless of success/failure.  For example, it is called when we have
     * encountered an error during the upload or when the file may have uploaded successfully.
     *
     * @param id file ID
     * @param errorDetails Any error details associated with the upload.  Format: {error: message}.
     * @param requestXhr The XHR object associated with the call, if the upload XHR is not appropriate.
     */
    function uploadCompleted(id, errorDetails, requestXhr) {
        var xhr = requestXhr || handler._getFileState(id).xhr,
            name = getName(id),
            size = getSize(id),
            // This is the response we will use internally to determine if we need to do something special in case of a failure
            responseToExamine = parseResponse(id, requestXhr),
            // This is the response we plan on passing to external callbacks
            responseToBubble = errorDetails || parseResponse(id),
            paused = handler._getFileState(id).paused,
            /*jshint -W116*/
            isError = !paused && (errorDetails != null || responseToExamine.success !== true);

        // If this upload failed, we might want to completely start the upload over on retry in some cases.
        if (isError) {
            if (shouldResetOnRetry(responseToExamine.code)) {
                log("This is an unrecoverable error, we must restart the upload entirely on the next retry attempt.", "error");
                handler._maybeDeletePersistedChunkData(id);
                delete handler._getFileState(id).loaded;
                delete handler._getFileState(id).chunking;
            }
        }

        // If this upload failed AND we are expecting an auto-retry, we are not done yet.  Otherwise, we are done.
        if (!isError || !spec.onAutoRetry(id, name, responseToBubble, xhr)) {
            log(qq.format("Upload attempt for file ID {} to S3 is complete", id));

            // If the upload has not failed and has not been paused, clean up state date
            if (!isError && !paused) {
                responseToBubble.success = true;
                onProgress(id, name, size, size);
                handler._maybeDeletePersistedChunkData(id);
                delete handler._getFileState(id).loaded;
                delete handler._getFileState(id).chunking;
            }

            // Only declare the upload complete (to listeners) if it has not been paused.
            if (paused) {
                qq.log(qq.format("Detected pause on {} ({}).", id, name));
            }
            else {
                onComplete(id, name, responseToBubble, xhr);
                handler._getFileState(id) && delete handler._getFileState(id).xhr;
                uploadCompleteCallback(id);
            }
        }
    }

    /**
     * @param id File ID
     * @param requestXhr The XHR object associated with the call, if the upload XHR is not appropriate.
     * @returns {object} Object containing the parsed response, or perhaps some error data injected in `error` and `code` properties
     */
    function parseResponse(id, requestXhr) {
        var xhr = requestXhr || handler._getFileState(id).xhr,
            response = {},
            parsedErrorProps;

        try {
            log(qq.format("Received response status {} with body: {}", xhr.status, xhr.responseText));

            if (xhr.status === expectedStatus) {
                response.success = true;
            }
            else {
                parsedErrorProps = parseError(xhr.responseText);

                if (parsedErrorProps) {
                    response.error = parsedErrorProps.message;
                    response.code = parsedErrorProps.code;
                }
            }
        }
        catch(error) {
            log("Error when attempting to parse xhr response text (" + error.message + ")", "error");
        }

        return response;
    }

    /**
     * This parses an XML response by extracting the "Message" and "Code" elements that accompany AWS error responses.
     *
     * @param awsResponseXml XML response from AWS
     * @returns {object} Object w/ `code` and `message` properties, or undefined if we couldn't find error info in the XML document.
     */
    function parseError(awsResponseXml) {
        var parser = new DOMParser(),
            parsedDoc = parser.parseFromString(awsResponseXml, "application/xml"),
            errorEls = parsedDoc.getElementsByTagName("Error"),
            errorDetails = {},
            codeEls, messageEls;

        if (errorEls.length) {
            codeEls = parsedDoc.getElementsByTagName("Code");
            messageEls = parsedDoc.getElementsByTagName("Message");

            if (messageEls.length) {
                errorDetails.message = messageEls[0].textContent;
            }

            if (codeEls.length) {
                errorDetails.code = codeEls[0].textContent;
            }

            return errorDetails;
        }
    }

    function handleStartUploadSignal(id, retry) {
        var name = getName(id);

        if (handler.isValid(id)) {
            handler._maybePrepareForResume(id);

            if (getActualKey(id) !== undefined) {
                onUpload(id, name);
                handleUpload(id);
            }
            else {
                // The S3 uploader module will either calculate the key or ask the server for it
                // and will call us back once it is known.
                onGetKeyName(id, name).then(function(key) {
                    setKey(id, key);
                    onUpload(id, name);
                    handleUpload(id);
                }, function(errorReason) {
                    uploadCompleted(id, {error: errorReason});
                });
            }
        }
    }


// ************************** Simple Uploads ******************************

    // Starting point for incoming requests for simple (non-chunked) uploads.
    function handleSimpleUpload(id) {
        var xhr = handler._getFileState(id).xhr,
            name = getName(id),
            fileOrBlob = handler.getFile(id);

        xhr.upload.onprogress = function(e){
            if (e.lengthComputable){
                handler._getFileState(id).loaded = e.loaded;
                onProgress(id, name, e.loaded, e.total);
            }
        };

        xhr.onreadystatechange = getReadyStateChangeHandler(id);

        // Delegate to a function the sets up the XHR request and notifies us when it is ready to be sent, along w/ the payload.
        prepareForSend(id, fileOrBlob).then(function(toSend) {
            log("Sending upload request for " + id);
            xhr.send(toSend);
        });
    }

    /**
     * Used for simple (non-chunked) uploads to determine the parameters to send along with the request.  Part of this
     * process involves asking the local server to sign the request, so this function returns a promise.  The promise
     * is fulfilled when all parameters are determined, or when we determine that all parameters cannnot be calculated
     * due to some error.
     *
     * @param id File ID
     * @returns {qq.Promise}
     */
    function generateAwsParams(id) {
        /*jshint -W040 */
        var customParams = paramsStore.get(id);
        customParams[filenameParam] = getName(id);

        return qq.s3.util.generateAwsParams({
                endpoint: endpointStore.get(id),
                params: customParams,
                type: handler._getMimeType(id),
                key: getActualKey(id),
                accessKey: credentialsProvider.get().accessKey,
                sessionToken: credentialsProvider.get().sessionToken,
                acl: aclStore.get(id),
                expectedStatus: expectedStatus,
                minFileSize: validation.minSizeLimit,
                maxFileSize: validation.maxSizeLimit,
                reducedRedundancy: reducedRedundancy,
                serverSideEncryption: serverSideEncryption,
                log: log
            },
            qq.bind(policySignatureRequester.getSignature, this, id));
    }

    /**
     * Starts the upload process by delegating to an async function that determine parameters to be attached to the
     * request.  If all params can be determined, we are called back with the params and the caller of this function is
     * informed by invoking the `success` method on the promise returned by this function, passing the payload of the
     * request.  If some error occurs here, we delegate to a function that signals a failure for this upload attempt.
     *
     * Note that this is only used by the simple (non-chunked) upload process.
     *
     * @param id File ID
     * @param fileOrBlob `File` or `Blob` to send
     * @returns {qq.Promise}
     */
    function prepareForSend(id, fileOrBlob) {
        var formData = new FormData(),
            endpoint = endpointStore.get(id),
            url = endpoint,
            xhr = handler._getFileState(id).xhr,
            promise = new qq.Promise();

        generateAwsParams(id).then(
            // Success - all params determined
            function(awsParams) {
                xhr.open("POST", url, true);

                qq.obj2FormData(awsParams, formData);

                // AWS requires the file field be named "file".
                formData.append("file", fileOrBlob);

                promise.success(formData);
            },

            // Failure - we couldn't determine some params (likely the signature)
            function(errorMessage) {
                promise.failure(errorMessage);
                uploadCompleted(id, {error: errorMessage});
            }
        );

        return promise;
    }


// ************************** Chunked Uploads ******************************

    // Starting point for incoming requests for chunked uploads.
    function handleChunkedUpload(id) {
        maybeInitiateMultipart(id).then(
            // The "Initiate" request succeeded.  We are ready to send the first chunk.
            function(uploadId, xhr) {
                maybeUploadNextChunk(id);
            },

            // We were unable to initiate the chunked upload process.
            function(errorMessage, xhr) {
                uploadCompleted(id, {error: errorMessage}, xhr);
            }
        );
    }

    /**
     * Retrieves the 0-based index of the next chunk to send.  Note that AWS uses 1-based indexing.
     *
     * @param id File ID
     * @returns {number} The 0-based index of the next file chunk to be sent to S3
     */
    function getNextPartIdxToSend(id) {
        return handler._getFileState(id).chunking.lastSent >= 0 ? handler._getFileState(id).chunking.lastSent + 1 : 0;
    }

    // Either initiate an upload for the next chunk for an associated file, or initiate a
    // "Complete Multipart Upload" request if there are no more parts to be sent.
    function maybeUploadNextChunk(id) {
        var totalParts = handler._getFileState(id).chunking.parts,
            nextPartIdx = getNextPartIdxToSend(id);

        if (nextPartIdx < totalParts) {
            uploadNextChunk(id);
        }
        else {
            completeMultipart(id);
        }
    }

    // Sends a "Complete Multipart Upload" request and then signals completion of the upload
    // when the response to this request has been parsed.
    function completeMultipart(id) {
        var uploadId = handler._getFileState(id).chunking.uploadId,
            etagMap = handler._getFileState(id).chunking.etags;

        completeMultipartRequester.send(id, uploadId, etagMap).then(
            // Successfully completed
            function(xhr) {
                uploadCompleted(id, null, xhr);
            },

            // Complete request failed
            function(errorMsg, xhr) {
                uploadCompleted(id, {error: errorMsg}, xhr);
            }
        );
    }

    // Initiate the process to send the next chunk for a file.  This assumes there IS a "next" chunk.
    function uploadNextChunk(id) {
        var idx = getNextPartIdxToSend(id),
            name = getName(id),
            xhr = handler._getFileState(id).xhr,
            totalFileSize = getSize(id),
            chunkData = handler._getChunkData(id, idx),
            domain = spec.endpointStore.get(id);

        // Add appropriate headers to the multipart upload request.
        // Once these have been determined (asynchronously) attach the headers and send the chunk.
        addChunkedHeaders(id).then(function(headers, endOfUrl) {
            var url = domain + "/" + endOfUrl;

            spec.onUploadChunk(id, name, handler._getChunkDataForCallback(chunkData));

            xhr.upload.onprogress = function(e) {
                if (e.lengthComputable) {
                    var totalLoaded = e.loaded + handler._getFileState(id).loaded;

                    spec.onProgress(id, name, totalLoaded, totalFileSize);
                }
            };

            xhr.onreadystatechange = getReadyStateChangeHandler(id);

            xhr.open("PUT", url, true);

            qq.each(headers, function(name, val) {
                xhr.setRequestHeader(name, val);
            });

            log(qq.format("Sending part {} of {} for file ID {} - {} ({} bytes)", chunkData.part+1, chunkData.count, id, name, chunkData.size));
            xhr.send(chunkData.blob);
        }, function() {
            uploadCompleted(id, {error: "Problem signing the chunk!"}, xhr);
        });
    }

    /**
     * Determines headers that must be attached to the chunked (Multipart Upload) request.  One of these headers is an
     * Authorization value, which must be determined by asking the local server to sign the request first.  So, this
     * function returns a promise.  Once all headers are determined, the `success` method of the promise is called with
     * the headers object.  If there was some problem determining the headers, we delegate to the caller's `failure`
     * callback.
     *
     * @param id File ID
     * @returns {qq.Promise}
     */
    function addChunkedHeaders(id) {
        var headers = {},
            endpoint = spec.endpointStore.get(id),
            bucket = qq.s3.util.getBucket(endpoint),
            key = getUrlSafeKey(id),
            promise = new qq.Promise(),
            signatureConstructor = restSignatureRequester.constructStringToSign
                (restSignatureRequester.REQUEST_TYPE.MULTIPART_UPLOAD, bucket, key)
                .withPartNum(getNextPartIdxToSend(id) + 1)
                .withUploadId(handler._getFileState(id).chunking.uploadId);

        // Ask the local server to sign the request.  Use this signature to form the Authorization header.
        restSignatureRequester.getSignature(id, {signatureConstructor: signatureConstructor}).then(function(response) {
            headers = signatureConstructor.getHeaders();
            headers.Authorization = "AWS " + credentialsProvider.get().accessKey + ":" + response.signature;
            promise.success(headers, signatureConstructor.getEndOfUrl());
        }, promise.failure);

        return promise;
    }

    /**
     * Sends an "Initiate Multipart Upload" request to S3 via the REST API, but only if the MPU has not already been
     * initiated.
     *
     * @param id Associated file ID
     * @returns {qq.Promise} A promise that is fulfilled when the initiate request has been sent and the response has been parsed.
     */
    function maybeInitiateMultipart(id) {
        if (!handler._getFileState(id).chunking.uploadId) {
            return initiateMultipartRequester.send(id).then(
                function(uploadId) {
                    handler._getFileState(id).chunking.uploadId = uploadId;
                }
            );
        }
        else {
            return new qq.Promise().success(handler._getFileState(id).chunking.uploadId);
        }
    }

    // The (usually) last step in handling a chunked upload.  This is called after each chunk has been sent.
    // The request may be successful, or not.  If it was successful, we must extract the "ETag" element
    // in the XML response and store that along with the associated part number.
    // We need these items to "Complete" the multipart upload after all chunks have been successfully sent.
    function uploadChunkCompleted(id) {
        var idxSent = getNextPartIdxToSend(id),
            xhr = handler._getFileState(id).xhr,
            response = parseResponse(id),
            chunkData = handler._getChunkData(id, idxSent),
            etag;

        if (response.success) {
            handler._getFileState(id).chunking.lastSent = idxSent;
            etag = xhr.getResponseHeader("ETag");

            if (!handler._getFileState(id).chunking.etags) {
                handler._getFileState(id).chunking.etags = [];
            }
            handler._getFileState(id).chunking.etags.push({part: idxSent+1, etag: etag});

            // Update the bytes loaded counter to reflect all bytes successfully transferred in the associated chunked request
            handler._getFileState(id).loaded += chunkData.size;

            handler._maybePersistChunkedState(id);

            spec.onUploadChunkSuccess(id, handler._getChunkDataForCallback(chunkData), response, xhr);

            // We might not be done with this file...
            maybeUploadNextChunk(id);
        }
        else {
            if (response.error) {
                log(response.error, "error");
            }

            uploadCompleted(id);
        }
    }


    qq.extend(this, new qq.AbstractNonTraditionalUploadHandlerXhr({
            options: {
                namespace: "s3",
                chunking: chunkingPossible ? spec.chunking : null,
                resumeEnabled: resumeEnabled
            },

            proxy: {
                onUpload: handleStartUploadSignal,
                onCancel: spec.onCancel,
                onUuidChanged: onUuidChanged,
                getName: getName,
                getSize: getSize,
                getUuid: getUuid,
                getEndpoint: endpointStore.get,
                log: log
            }
        }
    ));

    qq.override(this, function(super_) {
        return {
            expunge: function(id) {
                var uploadId = handler._getFileState(id).chunking && handler._getFileState(id).chunking.uploadId,
                    existedInLocalStorage = handler._maybeDeletePersistedChunkData(id);

                if (uploadId !== undefined && existedInLocalStorage) {
                    abortMultipartRequester.send(id, uploadId);
                }

                super_.expunge(id);
            },

            _getLocalStorageId: function(id) {
                var baseStorageId = super_._getLocalStorageId(id),
                    endpoint = endpointStore.get(id),
                    bucketName = qq.s3.util.getBucket(endpoint);

                return baseStorageId + "-" + bucketName;
            }
        };
    });
};

/*globals qq */
/**
 * This defines FineUploader mode w/ support for uploading to S3, which provides all the basic
 * functionality of Fine Uploader as well as code to handle uploads directly to S3.
 * This module inherits all logic from FineUploader mode and FineUploaderBasicS3 mode and adds some UI-related logic
 * specific to the upload-to-S3 workflow.  Some inherited options and API methods have a special meaning
 * in the context of the S3 uploader.
 */
(function(){
    "use strict";

    qq.s3.FineUploader = function(o) {
        var options = {
            failedUploadTextDisplay: {
                mode: "custom"
            }
        };

        // Replace any default options with user defined ones
        qq.extend(options, o, true);

        // Inherit instance data from FineUploader, which should in turn inherit from s3.FineUploaderBasic.
        qq.FineUploader.call(this, options, "s3");

        if (!qq.supportedFeatures.ajaxUploading && options.iframeSupport.localBlankPagePath === undefined) {
            this._options.element.innerHTML = "<div>You MUST set the <code>localBlankPagePath</code> property " +
                "of the <code>iframeSupport</code> option since this browser does not support the File API!</div>";
        }
    };

    // Inherit the API methods from FineUploaderBasicS3
    qq.extend(qq.s3.FineUploader.prototype, qq.s3.FineUploaderBasic.prototype);

    // Inherit public and private API methods related to UI
    qq.extend(qq.s3.FineUploader.prototype, qq.uiPublicApi);
    qq.extend(qq.s3.FineUploader.prototype, qq.uiPrivateApi);

    // Define public & private API methods for this module.
    qq.extend(qq.s3.FineUploader.prototype, {
        /**
         * When the upload has completed, change the visible status to "processing" if we are expecting an async operation to
         * determine status of the file in S3.
         *
         * @param id ID of the completed upload
         * @param name Name of the associated item
         * @param result Object created from the server's parsed JSON response.
         * @param xhr Associated XmlHttpRequest, if this was used to send the request.
         * @returns {boolean || qq.Promise} true/false if success can be determined immediately, otherwise a `qq.Promise`
         * if we need to ask the server.
         * @private
         */
        _onComplete: function(id, name, result, xhr) {
            var parentRetVal = qq.FineUploader.prototype._onComplete.apply(this, arguments);

            if (parentRetVal instanceof qq.Promise) {
                this._templating.hideProgress(id);
                this._templating.setStatusText(id, this._options.text.waitingForResponse);
            }

            return parentRetVal;
        }
    });
}());

/*globals qq*/
qq.PasteSupport = function(o) {
    "use strict";

    var options, detachPasteHandler;

    options = {
        targetElement: null,
        callbacks: {
            log: function(message, level) {},
            pasteReceived: function(blob) {}
        }
    };

    function isImage(item) {
        return item.type &&
            item.type.indexOf("image/") === 0;
    }

    function registerPasteHandler() {
        qq(options.targetElement).attach("paste", function(event) {
            var clipboardData = event.clipboardData;

            if (clipboardData) {
                qq.each(clipboardData.items, function(idx, item) {
                    if (isImage(item)) {
                        var blob = item.getAsFile();
                        options.callbacks.pasteReceived(blob);
                    }
                });
            }
        });
    }

    function unregisterPasteHandler() {
        if (detachPasteHandler) {
            detachPasteHandler();
        }
    }

    qq.extend(options, o);
    registerPasteHandler();

    qq.extend(this, {
        reset: function() {
            unregisterPasteHandler();
        }
    });
};

/*globals qq, document, CustomEvent*/
qq.DragAndDrop = function(o) {
    "use strict";

    var options,
        HIDE_ZONES_EVENT_NAME = "qq-hidezones",
        HIDE_BEFORE_ENTER_ATTR = "qq-hide-dropzone",
        uploadDropZones = [],
        droppedFiles = [],
        disposeSupport = new qq.DisposeSupport();

    options = {
        dropZoneElements: [],
        allowMultipleItems: true,
        classes: {
            dropActive: null
        },
        callbacks: new qq.DragAndDrop.callbacks()
    };

    qq.extend(options, o, true);

    function uploadDroppedFiles(files, uploadDropZone) {
        // We need to convert the `FileList` to an actual `Array` to avoid iteration issues
        var filesAsArray = Array.prototype.slice.call(files);

        options.callbacks.dropLog("Grabbed " + files.length + " dropped files.");
        uploadDropZone.dropDisabled(false);
        options.callbacks.processingDroppedFilesComplete(filesAsArray, uploadDropZone.getElement());
    }

    function traverseFileTree(entry) {
        var dirReader,
            parseEntryPromise = new qq.Promise();

        if (entry.isFile) {
            entry.file(function(file) {
                droppedFiles.push(file);
                parseEntryPromise.success();
            },
            function(fileError) {
                options.callbacks.dropLog("Problem parsing '" + entry.fullPath + "'.  FileError code " + fileError.code + ".", "error");
                parseEntryPromise.failure();
            });
        }
        else if (entry.isDirectory) {
            dirReader = entry.createReader();
            dirReader.readEntries(function(entries) {
                var entriesLeft = entries.length;

                qq.each(entries, function(idx, entry) {
                    traverseFileTree(entry).done(function() {
                        entriesLeft-=1;

                        if (entriesLeft === 0) {
                            parseEntryPromise.success();
                        }
                    });
                });

                if (!entries.length) {
                    parseEntryPromise.success();
                }
            }, function(fileError) {
                options.callbacks.dropLog("Problem parsing '" + entry.fullPath + "'.  FileError code " + fileError.code + ".", "error");
                parseEntryPromise.failure();
            });
        }

        return parseEntryPromise;
    }

    function handleDataTransfer(dataTransfer, uploadDropZone) {
        var pendingFolderPromises = [],
            handleDataTransferPromise = new qq.Promise();

        options.callbacks.processingDroppedFiles();
        uploadDropZone.dropDisabled(true);

        if (dataTransfer.files.length > 1 && !options.allowMultipleItems) {
            options.callbacks.processingDroppedFilesComplete([]);
            options.callbacks.dropError("tooManyFilesError", "");
            uploadDropZone.dropDisabled(false);
            handleDataTransferPromise.failure();
        }
        else {
            droppedFiles = [];

            if (qq.isFolderDropSupported(dataTransfer)) {
                qq.each(dataTransfer.items, function(idx, item) {
                    var entry = item.webkitGetAsEntry();

                    if (entry) {
                        //due to a bug in Chrome's File System API impl - #149735
                        if (entry.isFile) {
                            droppedFiles.push(item.getAsFile());
                        }

                        else {
                            pendingFolderPromises.push(traverseFileTree(entry).done(function() {
                                pendingFolderPromises.pop();
                                if (pendingFolderPromises.length === 0) {
                                    handleDataTransferPromise.success();
                                }
                            }));
                        }
                    }
                });
            }
            else {
                droppedFiles = dataTransfer.files;
            }

            if (pendingFolderPromises.length === 0) {
                handleDataTransferPromise.success();
            }
        }

        return handleDataTransferPromise;
    }

    function setupDropzone(dropArea) {
        var dropZone = new qq.UploadDropZone({
            HIDE_ZONES_EVENT_NAME: HIDE_ZONES_EVENT_NAME,
            element: dropArea,
            onEnter: function(e){
                qq(dropArea).addClass(options.classes.dropActive);
                e.stopPropagation();
            },
            onLeaveNotDescendants: function(e){
                qq(dropArea).removeClass(options.classes.dropActive);
            },
            onDrop: function(e){
                handleDataTransfer(e.dataTransfer, dropZone).then(
                    function() {
                        uploadDroppedFiles(droppedFiles, dropZone);
                    },
                    function() {
                        options.callbacks.dropLog("Drop event DataTransfer parsing failed.  No files will be uploaded.", "error");
                    }
                );
            }
        });

        disposeSupport.addDisposer(function() {
            dropZone.dispose();
        });

        qq(dropArea).hasAttribute(HIDE_BEFORE_ENTER_ATTR) && qq(dropArea).hide();

        uploadDropZones.push(dropZone);

        return dropZone;
    }

    function isFileDrag(dragEvent) {
        var fileDrag;

        qq.each(dragEvent.dataTransfer.types, function(key, val) {
            if (val === "Files") {
                fileDrag = true;
                return false;
            }
        });

        return fileDrag;
    }

    function leavingDocumentOut(e) {
        /* jshint -W041, eqeqeq:false */
            // null coords for Chrome and Safari Windows
        return ((qq.chrome() || (qq.safari() && qq.windows())) && e.clientX == 0 && e.clientY == 0) ||
            // null e.relatedTarget for Firefox
            (qq.firefox() && !e.relatedTarget);
    }

    function setupDragDrop() {
        var dropZones = options.dropZoneElements;

        qq.each(dropZones, function(idx, dropZone) {
            var uploadDropZone = setupDropzone(dropZone);

            // IE <= 9 does not support the File API used for drag+drop uploads
            if (dropZones.length && (!qq.ie() || qq.ie10())) {
                disposeSupport.attach(document, "dragenter", function(e) {
                    if (!uploadDropZone.dropDisabled() && isFileDrag(e)) {
                        qq.each(dropZones, function(idx, dropZone) {
                            // We can't apply styles to non-HTMLElements, since they lack the `style` property
                            if (dropZone instanceof HTMLElement) {
                                qq(dropZone).css({display: "block"});
                            }
                        });
                    }
                });
            }
        });

        disposeSupport.attach(document, "dragleave", function(e) {
            if (leavingDocumentOut(e)) {
                qq.each(dropZones, function(idx, dropZone) {
                    qq(dropZone).hasAttribute(HIDE_BEFORE_ENTER_ATTR) && qq(dropZone).hide();
                });
            }
        });

        disposeSupport.attach(document, "drop", function(e){
            qq.each(dropZones, function(idx, dropZone) {
                qq(dropZone).hasAttribute(HIDE_BEFORE_ENTER_ATTR) && qq(dropZone).hide();
            });
            e.preventDefault();
        });

        disposeSupport.attach(document, HIDE_ZONES_EVENT_NAME, function(e) {
            qq.each(options.dropZoneElements, function(idx, zone) {
                qq(zone).hasAttribute(HIDE_BEFORE_ENTER_ATTR) && qq(zone).hide();
                qq(zone).removeClass(options.classes.dropActive);
            });
        });
    }

    setupDragDrop();

    qq.extend(this, {
        setupExtraDropzone: function(element) {
            options.dropZoneElements.push(element);
            setupDropzone(element);
        },

        removeDropzone: function(element) {
            var i,
                dzs = options.dropZoneElements;

            for(i in dzs) {
                if (dzs[i] === element) {
                    return dzs.splice(i, 1);
                }
            }
        },

        dispose: function() {
            disposeSupport.dispose();
            qq.each(uploadDropZones, function(idx, dropZone) {
                dropZone.dispose();
            });
        }
    });
};

qq.DragAndDrop.callbacks = function() {
    "use strict";

    return {
        processingDroppedFiles: function() {},
        processingDroppedFilesComplete: function(files, targetEl) {},
        dropError: function(code, errorSpecifics) {
            qq.log("Drag & drop error code '" + code + " with these specifics: '" + errorSpecifics + "'", "error");
        },
        dropLog: function(message, level) {
            qq.log(message, level);
        }
    };
};

qq.UploadDropZone = function(o){
    "use strict";

    var disposeSupport = new qq.DisposeSupport(),
        options, element, preventDrop, dropOutsideDisabled;

    options = {
        element: null,
        onEnter: function(e){},
        onLeave: function(e){},
        // is not fired when leaving element by hovering descendants
        onLeaveNotDescendants: function(e){},
        onDrop: function(e){}
    };

    qq.extend(options, o);
    element = options.element;

    function dragover_should_be_canceled(){
        return qq.safari() || (qq.firefox() && qq.windows());
    }

    function disableDropOutside(e){
        // run only once for all instances
        if (!dropOutsideDisabled ){

            // for these cases we need to catch onDrop to reset dropArea
            if (dragover_should_be_canceled){
                disposeSupport.attach(document, "dragover", function(e){
                    e.preventDefault();
                });
            } else {
                disposeSupport.attach(document, "dragover", function(e){
                    if (e.dataTransfer){
                        e.dataTransfer.dropEffect = "none";
                        e.preventDefault();
                    }
                });
            }

            dropOutsideDisabled = true;
        }
    }

    function isValidFileDrag(e){
        // e.dataTransfer currently causing IE errors
        // IE9 does NOT support file API, so drag-and-drop is not possible
        if (qq.ie() && !qq.ie10()) {
            return false;
        }

        var effectTest, dt = e.dataTransfer,
        // do not check dt.types.contains in webkit, because it crashes safari 4
        isSafari = qq.safari();

        // dt.effectAllowed is none in Safari 5
        // dt.types.contains check is for firefox

        // dt.effectAllowed crashes IE11 when files have been dragged from
        // the filesystem
        effectTest = (qq.ie10() || qq.ie11()) ? true : dt.effectAllowed !== "none";
        return dt && effectTest && (dt.files || (!isSafari && dt.types.contains && dt.types.contains("Files")));
    }

    function isOrSetDropDisabled(isDisabled) {
        if (isDisabled !== undefined) {
            preventDrop = isDisabled;
        }
        return preventDrop;
    }

    function triggerHidezonesEvent() {
        var hideZonesEvent;

        function triggerUsingOldApi() {
            hideZonesEvent = document.createEvent("Event");
            hideZonesEvent.initEvent(options.HIDE_ZONES_EVENT_NAME, true, true);
        }

        if (window.CustomEvent) {
            try {
                hideZonesEvent = new CustomEvent(options.HIDE_ZONES_EVENT_NAME);
            }
            catch (err) {
                triggerUsingOldApi();
            }
        }
        else {
            triggerUsingOldApi();
        }

        document.dispatchEvent(hideZonesEvent);
    }

    function attachEvents(){
        disposeSupport.attach(element, "dragover", function(e){
            if (!isValidFileDrag(e)) {
                return;
            }

            // dt.effectAllowed crashes IE11 when files have been dragged from
            // the filesystem
            var effect = (qq.ie() || qq.ie11()) ? null : e.dataTransfer.effectAllowed;
            if (effect === "move" || effect === "linkMove"){
                e.dataTransfer.dropEffect = "move"; // for FF (only move allowed)
            } else {
                e.dataTransfer.dropEffect = "copy"; // for Chrome
            }

            e.stopPropagation();
            e.preventDefault();
        });

        disposeSupport.attach(element, "dragenter", function(e){
            if (!isOrSetDropDisabled()) {
                if (!isValidFileDrag(e)) {
                    return;
                }
                options.onEnter(e);
            }
        });

        disposeSupport.attach(element, "dragleave", function(e){
            if (!isValidFileDrag(e)) {
                return;
            }

            options.onLeave(e);

            var relatedTarget = document.elementFromPoint(e.clientX, e.clientY);
            // do not fire when moving a mouse over a descendant
            if (qq(this).contains(relatedTarget)) {
                return;
            }

            options.onLeaveNotDescendants(e);
        });

        disposeSupport.attach(element, "drop", function(e) {
            if (!isOrSetDropDisabled()) {
                if (!isValidFileDrag(e)) {
                    return;
                }

                e.preventDefault();
                e.stopPropagation();
                options.onDrop(e);

                triggerHidezonesEvent();
            }
        });
    }

    disableDropOutside();
    attachEvents();

    qq.extend(this, {
        dropDisabled: function(isDisabled) {
            return isOrSetDropDisabled(isDisabled);
        },

        dispose: function() {
            disposeSupport.dispose();
        },

        getElement: function() {
            return element;
        }
    });
};

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

/*global qq, define */
/*jshint strict:false,bitwise:false,nonew:false,asi:true,-W064,-W116,-W089 */
/**
 * Mega pixel image rendering library for iOS6 Safari
 *
 * Fixes iOS6 Safari's image file rendering issue for large size image (over mega-pixel),
 * which causes unexpected subsampling when drawing it in canvas.
 * By using this library, you can safely render the image with proper stretching.
 *
 * Copyright (c) 2012 Shinichi Tomita <shinichi.tomita@gmail.com>
 * Released under the MIT license
 */
(function() {

  /**
   * Detect subsampling in loaded image.
   * In iOS, larger images than 2M pixels may be subsampled in rendering.
   */
  function detectSubsampling(img) {
    var iw = img.naturalWidth, ih = img.naturalHeight;
    if (iw * ih > 1024 * 1024) { // subsampling may happen over megapixel image
      var canvas = document.createElement('canvas');
      canvas.width = canvas.height = 1;
      var ctx = canvas.getContext('2d');
      ctx.drawImage(img, -iw + 1, 0);
      // subsampled image becomes half smaller in rendering size.
      // check alpha channel value to confirm image is covering edge pixel or not.
      // if alpha value is 0 image is not covering, hence subsampled.
      return ctx.getImageData(0, 0, 1, 1).data[3] === 0;
    } else {
      return false;
    }
  }

  /**
   * Detecting vertical squash in loaded image.
   * Fixes a bug which squash image vertically while drawing into canvas for some images.
   */
  function detectVerticalSquash(img, iw, ih) {
    var canvas = document.createElement('canvas');
    canvas.width = 1;
    canvas.height = ih;
    var ctx = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0);
    var data = ctx.getImageData(0, 0, 1, ih).data;
    // search image edge pixel position in case it is squashed vertically.
    var sy = 0;
    var ey = ih;
    var py = ih;
    while (py > sy) {
      var alpha = data[(py - 1) * 4 + 3];
      if (alpha === 0) {
        ey = py;
      } else {
        sy = py;
      }
      py = (ey + sy) >> 1;
    }
    var ratio = (py / ih);
    return (ratio===0)?1:ratio;
  }

  /**
   * Rendering image element (with resizing) and get its data URL
   */
  function renderImageToDataURL(img, options, doSquash) {
    var canvas = document.createElement('canvas'),
        mime = options.mime || "image/jpeg";

    renderImageToCanvas(img, canvas, options, doSquash);
    return canvas.toDataURL(mime, options.quality || 0.8);
  }

  /**
   * Rendering image element (with resizing) into the canvas element
   */
  function renderImageToCanvas(img, canvas, options, doSquash) {
    var iw = img.naturalWidth, ih = img.naturalHeight;
    var width = options.width, height = options.height;
    var ctx = canvas.getContext('2d');
    ctx.save();
    transformCoordinate(canvas, width, height, options.orientation);

    // Fine Uploader specific: Save some CPU cycles if not using iOS
    // Assumption: This logic is only needed to overcome iOS image sampling issues
    if (qq.ios()) {
        var subsampled = detectSubsampling(img);
        if (subsampled) {
          iw /= 2;
          ih /= 2;
        }
        var d = 1024; // size of tiling canvas
        var tmpCanvas = document.createElement('canvas');
        tmpCanvas.width = tmpCanvas.height = d;
        var tmpCtx = tmpCanvas.getContext('2d');
        var vertSquashRatio = doSquash ? detectVerticalSquash(img, iw, ih) : 1;
        var dw = Math.ceil(d * width / iw);
        var dh = Math.ceil(d * height / ih / vertSquashRatio);
        var sy = 0;
        var dy = 0;
        while (sy < ih) {
          var sx = 0;
          var dx = 0;
          while (sx < iw) {
            tmpCtx.clearRect(0, 0, d, d);
            tmpCtx.drawImage(img, -sx, -sy);
            ctx.drawImage(tmpCanvas, 0, 0, d, d, dx, dy, dw, dh);
            sx += d;
            dx += dw;
          }
          sy += d;
          dy += dh;
        }
        ctx.restore();
        tmpCanvas = tmpCtx = null;
    }
    else {
        ctx.drawImage(img, 0, 0, width, height);
    }
  }

  /**
   * Transform canvas coordination according to specified frame size and orientation
   * Orientation value is from EXIF tag
   */
  function transformCoordinate(canvas, width, height, orientation) {
    switch (orientation) {
      case 5:
      case 6:
      case 7:
      case 8:
        canvas.width = height;
        canvas.height = width;
        break;
      default:
        canvas.width = width;
        canvas.height = height;
    }
    var ctx = canvas.getContext('2d');
    switch (orientation) {
      case 2:
        // horizontal flip
        ctx.translate(width, 0);
        ctx.scale(-1, 1);
        break;
      case 3:
        // 180 rotate left
        ctx.translate(width, height);
        ctx.rotate(Math.PI);
        break;
      case 4:
        // vertical flip
        ctx.translate(0, height);
        ctx.scale(1, -1);
        break;
      case 5:
        // vertical flip + 90 rotate right
        ctx.rotate(0.5 * Math.PI);
        ctx.scale(1, -1);
        break;
      case 6:
        // 90 rotate right
        ctx.rotate(0.5 * Math.PI);
        ctx.translate(0, -height);
        break;
      case 7:
        // horizontal flip + 90 rotate right
        ctx.rotate(0.5 * Math.PI);
        ctx.translate(width, -height);
        ctx.scale(-1, 1);
        break;
      case 8:
        // 90 rotate left
        ctx.rotate(-0.5 * Math.PI);
        ctx.translate(-width, 0);
        break;
      default:
        break;
    }
  }


  /**
   * MegaPixImage class
   */
  function MegaPixImage(srcImage, errorCallback) {
    if (window.Blob && srcImage instanceof Blob) {
      var img = new Image();
      var URL = window.URL && window.URL.createObjectURL ? window.URL :
                window.webkitURL && window.webkitURL.createObjectURL ? window.webkitURL :
                null;
      if (!URL) { throw Error("No createObjectURL function found to create blob url"); }
      img.src = URL.createObjectURL(srcImage);
      this.blob = srcImage;
      srcImage = img;
    }
    if (!srcImage.naturalWidth && !srcImage.naturalHeight) {
      var _this = this;
      srcImage.onload = function() {
        var listeners = _this.imageLoadListeners;
        if (listeners) {
          _this.imageLoadListeners = null;
          for (var i=0, len=listeners.length; i<len; i++) {
            listeners[i]();
          }
        }
      };
      srcImage.onerror = errorCallback;
      this.imageLoadListeners = [];
    }
    this.srcImage = srcImage;
  }

  /**
   * Rendering megapix image into specified target element
   */
  MegaPixImage.prototype.render = function(target, options) {
    if (this.imageLoadListeners) {
      var _this = this;
      this.imageLoadListeners.push(function() { _this.render(target, options) });
      return;
    }
    options = options || {};
    var imgWidth = this.srcImage.naturalWidth, imgHeight = this.srcImage.naturalHeight,
        width = options.width, height = options.height,
        maxWidth = options.maxWidth, maxHeight = options.maxHeight,
        doSquash = !this.blob || this.blob.type === 'image/jpeg';
    if (width && !height) {
      height = (imgHeight * width / imgWidth) << 0;
    } else if (height && !width) {
      width = (imgWidth * height / imgHeight) << 0;
    } else {
      width = imgWidth;
      height = imgHeight;
    }
    if (maxWidth && width > maxWidth) {
      width = maxWidth;
      height = (imgHeight * width / imgWidth) << 0;
    }
    if (maxHeight && height > maxHeight) {
      height = maxHeight;
      width = (imgWidth * height / imgHeight) << 0;
    }
    var opt = { width : width, height : height };
    for (var k in options) opt[k] = options[k];

    var tagName = target.tagName.toLowerCase();
    if (tagName === 'img') {
      target.src = renderImageToDataURL(this.srcImage, opt, doSquash);
    } else if (tagName === 'canvas') {
      renderImageToCanvas(this.srcImage, target, opt, doSquash);
    }
    if (typeof this.onrender === 'function') {
      this.onrender(target);
    }
  };

  /**
   * Export class to global
   */
  if (typeof define === 'function' && define.amd) {
    define([], function() { return MegaPixImage; }); // for AMD loader
  } else {
    this.MegaPixImage = MegaPixImage;
  }

})();

/*globals qq, MegaPixImage */
/**
 * Draws a thumbnail of a Blob/File/URL onto an <img> or <canvas>.
 *
 * @constructor
 */
qq.ImageGenerator = function(log) {
    "use strict";

    function isImg(el) {
        return el.tagName.toLowerCase() === "img";
    }

    function isCanvas(el) {
        return el.tagName.toLowerCase() === "canvas";
    }

    function isImgCorsSupported() {
        return new Image().crossOrigin !== undefined;
    }

    function isCanvasSupported() {
        var canvas = document.createElement("canvas");

        return canvas.getContext && canvas.getContext("2d");
    }

    // This is only meant to determine the MIME type of a renderable image file.
    // It is used to ensure images drawn from a URL that have transparent backgrounds
    // are rendered correctly, among other things.
    function determineMimeOfFileName(nameWithPath) {
        /*jshint -W015 */
        var pathSegments = nameWithPath.split("/"),
            name = pathSegments[pathSegments.length - 1],
            extension = qq.getExtension(name);

        extension = extension && extension.toLowerCase();

        switch(extension) {
            case "jpeg":
            case "jpg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "bmp":
                return "image/bmp";
            case "gif":
                return "image/gif";
            case "tiff":
            case "tif":
                return "image/tiff";
        }
    }

    // This will likely not work correctly in IE8 and older.
    // It's only used as part of a formula to determine
    // if a canvas can be used to scale a server-hosted thumbnail.
    // If canvas isn't supported by the UA (IE8 and older)
    // this method should not even be called.
    function isCrossOrigin(url) {
        var targetAnchor = document.createElement("a"),
            targetProtocol, targetHostname, targetPort;

        targetAnchor.href = url;

        targetProtocol = targetAnchor.protocol;
        targetPort = targetAnchor.port;
        targetHostname = targetAnchor.hostname;

        if (targetProtocol.toLowerCase() !== window.location.protocol.toLowerCase()) {
            return true;
        }

        if (targetHostname.toLowerCase() !== window.location.hostname.toLowerCase()) {
            return true;
        }

        // IE doesn't take ports into consideration when determining if two endpoints are same origin.
        if (targetPort !== window.location.port && !qq.ie()) {
            return true;
        }

        return false;
    }

    function registerImgLoadListeners(img, promise) {
        img.onload = function() {
            img.onload = null;
            img.onerror = null;
            promise.success(img);
        };

        img.onerror = function() {
            img.onload = null;
            img.onerror = null;
            log("Problem drawing thumbnail!", "error");
            promise.failure(img, "Problem drawing thumbnail!");
        };
    }

    function registerCanvasDrawImageListener(canvas, promise) {
        var context = canvas.getContext("2d"),
            oldDrawImage = context.drawImage;

        // The image is drawn on the canvas by a third-party library,
        // and we want to know when this happens so we can fulfill the associated promise.
        context.drawImage = function() {
            oldDrawImage.apply(this, arguments);
            promise.success(canvas);
            context.drawImage = oldDrawImage;
        };
    }

    // Fulfills a `qq.Promise` when an image has been drawn onto the target,
    // whether that is a <canvas> or an <img>.  The attempt is considered a
    // failure if the target is not an <img> or a <canvas>, or if the drawing
    // attempt was not successful.
    function registerThumbnailRenderedListener(imgOrCanvas, promise) {
        var registered = isImg(imgOrCanvas) || isCanvas(imgOrCanvas);

        if (isImg(imgOrCanvas)) {
            registerImgLoadListeners(imgOrCanvas, promise);
        }
        else if (isCanvas(imgOrCanvas)) {
            registerCanvasDrawImageListener(imgOrCanvas, promise);
        }
        else {
            promise.failure(imgOrCanvas);
            log(qq.format("Element container of type {} is not supported!", imgOrCanvas.tagName), "error");
        }

        return registered;
    }

    // Draw a preview iff the current UA can natively display it.
    // Also rotate the image if necessary.
    function draw(fileOrBlob, container, options) {
        var drawPreview = new qq.Promise(),
            identifier = new qq.Identify(fileOrBlob, log),
            maxSize = options.maxSize,
            megapixErrorHandler = function() {
                container.onerror = null;
                container.onload = null;
                log("Could not render preview, file may be too large!", "error");
                drawPreview.failure(container, "Browser cannot render image!");
            };

        identifier.isPreviewable().then(
            function(mime) {
                var exif = new qq.Exif(fileOrBlob, log),
                    mpImg = new MegaPixImage(fileOrBlob, megapixErrorHandler);

                if (registerThumbnailRenderedListener(container, drawPreview)) {
                    exif.parse().then(
                        function(exif) {
                            var orientation = exif.Orientation;

                            mpImg.render(container, {
                                maxWidth: maxSize,
                                maxHeight: maxSize,
                                orientation: orientation,
                                mime: mime
                            });
                        },

                        function(failureMsg) {
                            log(qq.format("EXIF data could not be parsed ({}).  Assuming orientation = 1.", failureMsg));

                            mpImg.render(container, {
                                maxWidth: maxSize,
                                maxHeight: maxSize,
                                mime: mime
                            });
                        }
                    );
                }
            },

            function() {
                log("Not previewable");
                drawPreview.failure(container, "Not previewable");
            }
        );

        return drawPreview;
    }

    function drawOnCanvasOrImgFromUrl(url, canvasOrImg, draw, maxSize) {
        var tempImg = new Image(),
            tempImgRender = new qq.Promise();

        registerThumbnailRenderedListener(tempImg, tempImgRender);

        if (isCrossOrigin(url)) {
            tempImg.crossOrigin = "anonymous";
        }

        tempImg.src = url;

        tempImgRender.then(function() {
            registerThumbnailRenderedListener(canvasOrImg, draw);

            var mpImg = new MegaPixImage(tempImg);
            mpImg.render(canvasOrImg, {
                maxWidth: maxSize,
                maxHeight: maxSize,
                mime: determineMimeOfFileName(url)
            });
        });
    }

    function drawOnImgFromUrlWithCssScaling(url, img, draw, maxSize) {
        registerThumbnailRenderedListener(img, draw);
        qq(img).css({
            maxWidth: maxSize + "px",
            maxHeight: maxSize + "px"
        });

        img.src = url;
    }

    // Draw a (server-hosted) thumbnail given a URL.
    // This will optionally scale the thumbnail as well.
    // It attempts to use <canvas> to scale, but will fall back
    // to max-width and max-height style properties if the UA
    // doesn't support canvas or if the images is cross-domain and
    // the UA doesn't support the crossorigin attribute on img tags,
    // which is required to scale a cross-origin image using <canvas> &
    // then export it back to an <img>.
    function drawFromUrl(url, container, options) {
        var draw = new qq.Promise(),
            scale = options.scale,
            maxSize = scale ? options.maxSize : null;

        // container is an img, scaling needed
        if (scale && isImg(container)) {
            // Iff canvas is available in this UA, try to use it for scaling.
            // Otherwise, fall back to CSS scaling
            if (isCanvasSupported()) {
                // Attempt to use <canvas> for image scaling,
                // but we must fall back to scaling via CSS/styles
                // if this is a cross-origin image and the UA doesn't support <img> CORS.
                if (isCrossOrigin(url) && !isImgCorsSupported()) {
                    drawOnImgFromUrlWithCssScaling(url, container, draw, maxSize);
                }
                else {
                    drawOnCanvasOrImgFromUrl(url, container, draw, maxSize);
                }
            }
            else {
                drawOnImgFromUrlWithCssScaling(url, container, draw, maxSize);
            }
        }
        // container is a canvas, scaling optional
        else if (isCanvas(container)) {
            drawOnCanvasOrImgFromUrl(url, container, draw, maxSize);
        }
        // container is an img & no scaling: just set the src attr to the passed url
        else if (registerThumbnailRenderedListener(container, draw)) {
            container.src = url;
        }

        return draw;
    }


    qq.extend(this, {
        /**
         * Generate a thumbnail.  Depending on the arguments, this may either result in
         * a client-side rendering of an image (if a `Blob` is supplied) or a server-generated
         * image that may optionally be scaled client-side using <canvas> or CSS/styles (as a fallback).
         *
         * @param fileBlobOrUrl a `File`, `Blob`, or a URL pointing to the image
         * @param container <img> or <canvas> to contain the preview
         * @param options possible properties include `maxSize` (int), `orient` (bool), and `resize` (bool)
         * @returns qq.Promise fulfilled when the preview has been drawn, or the attempt has failed
         */
        generate: function(fileBlobOrUrl, container, options) {
            if (qq.isString(fileBlobOrUrl)) {
                log("Attempting to update thumbnail based on server response.");
                return drawFromUrl(fileBlobOrUrl, container, options || {});
            }
            else {
                log("Attempting to draw client-side image preview.");
                return draw(fileBlobOrUrl, container, options || {});
            }
        }
    });

    /*<testing>*/
    this._testing = {};
    this._testing.isImg = isImg;
    this._testing.isCanvas = isCanvas;
    this._testing.isCrossOrigin = isCrossOrigin;
    this._testing.determineMimeOfFileName = determineMimeOfFileName;
    /*</testing>*/
};

/*globals qq */
/**
 * EXIF image data parser.  Currently only parses the Orientation tag value,
 * but this may be expanded to other tags in the future.
 *
 * @param fileOrBlob Attempt to parse EXIF data in this `Blob`
 * @constructor
 */
qq.Exif = function(fileOrBlob, log) {
    "use strict";

    // Orientation is the only tag parsed here at this time.
    var TAG_IDS = [274],
        TAG_INFO = {
            274: {
                name: "Orientation",
                bytes: 2
            }
        };

    // Convert a little endian (hex string) to big endian (decimal).
    function parseLittleEndian(hex) {
        var result = 0,
            pow = 0;

        while (hex.length > 0) {
            result += parseInt(hex.substring(0, 2), 16) * Math.pow(2, pow);
            hex = hex.substring(2, hex.length);
            pow += 8;
        }

        return result;
    }

    // Find the byte offset, of Application Segment 1 (EXIF).
    // External callers need not supply any arguments.
    function seekToApp1(offset, promise) {
        var theOffset = offset,
            thePromise = promise;
        if (theOffset === undefined) {
            theOffset = 2;
            thePromise = new qq.Promise();
        }

        qq.readBlobToHex(fileOrBlob, theOffset, 4).then(function(hex) {
            var match = /^ffe([0-9])/.exec(hex);
            if (match) {
                if (match[1] !== "1") {
                    var segmentLength = parseInt(hex.slice(4, 8), 16);
                    seekToApp1(theOffset + segmentLength + 2, thePromise);
                }
                else {
                    thePromise.success(theOffset);
                }
            }
            else {
                thePromise.failure("No EXIF header to be found!");
            }
        });

        return thePromise;
    }

    // Find the byte offset of Application Segment 1 (EXIF) for valid JPEGs only.
    function getApp1Offset() {
        var promise = new qq.Promise();

        qq.readBlobToHex(fileOrBlob, 0, 6).then(function(hex) {
            if (hex.indexOf("ffd8") !== 0) {
                promise.failure("Not a valid JPEG!");
            }
            else {
                seekToApp1().then(function(offset) {
                    promise.success(offset);
                },
                function(error) {
                    promise.failure(error);
                });
            }
        });

        return promise;
    }

    // Determine the byte ordering of the EXIF header.
    function isLittleEndian(app1Start) {
        var promise = new qq.Promise();

        qq.readBlobToHex(fileOrBlob, app1Start + 10, 2).then(function(hex) {
            promise.success(hex === "4949");
        });

        return promise;
    }

    // Determine the number of directory entries in the EXIF header.
    function getDirEntryCount(app1Start, littleEndian) {
        var promise = new qq.Promise();

        qq.readBlobToHex(fileOrBlob, app1Start + 18, 2).then(function(hex) {
            if (littleEndian) {
                return promise.success(parseLittleEndian(hex));
            }
            else {
                promise.success(parseInt(hex, 16));
            }
        });

        return promise;
    }

    // Get the IFD portion of the EXIF header as a hex string.
    function getIfd(app1Start, dirEntries) {
        var offset = app1Start + 20,
            bytes = dirEntries * 12;

        return qq.readBlobToHex(fileOrBlob, offset, bytes);
    }

    // Obtain an array of all directory entries (as hex strings) in the EXIF header.
    function getDirEntries(ifdHex) {
        var entries = [],
            offset = 0;

        while (offset+24 <= ifdHex.length) {
            entries.push(ifdHex.slice(offset, offset + 24));
            offset += 24;
        }

        return entries;
    }

    // Obtain values for all relevant tags and return them.
    function getTagValues(littleEndian, dirEntries) {
        var TAG_VAL_OFFSET = 16,
            tagsToFind = qq.extend([], TAG_IDS),
            vals = {};

        qq.each(dirEntries, function(idx, entry) {
            var idHex = entry.slice(0, 4),
                id = littleEndian ? parseLittleEndian(idHex) : parseInt(idHex, 16),
                tagsToFindIdx = tagsToFind.indexOf(id),
                tagValHex, tagName, tagValLength;

            if (tagsToFindIdx >= 0) {
                tagName = TAG_INFO[id].name;
                tagValLength = TAG_INFO[id].bytes;
                tagValHex = entry.slice(TAG_VAL_OFFSET, TAG_VAL_OFFSET + (tagValLength*2));
                vals[tagName] = littleEndian ? parseLittleEndian(tagValHex) : parseInt(tagValHex, 16);

                tagsToFind.splice(tagsToFindIdx, 1);
            }

            if (tagsToFind.length === 0) {
                return false;
            }
        });

        return vals;
    }

    qq.extend(this, {
        /**
         * Attempt to parse the EXIF header for the `Blob` associated with this instance.
         *
         * @returns {qq.Promise} To be fulfilled when the parsing is complete.
         * If successful, the parsed EXIF header as an object will be included.
         */
        parse: function() {
            var parser = new qq.Promise(),
                onParseFailure = function(message) {
                    log(qq.format("EXIF header parse failed: '{}' ", message));
                    parser.failure(message);
                };

            getApp1Offset().then(function(app1Offset) {
                log(qq.format("Moving forward with EXIF header parsing for '{}'", fileOrBlob.name === undefined ? "blob" : fileOrBlob.name));

                isLittleEndian(app1Offset).then(function(littleEndian) {

                    log(qq.format("EXIF Byte order is {} endian", littleEndian ? "little" : "big"));

                    getDirEntryCount(app1Offset, littleEndian).then(function(dirEntryCount) {

                        log(qq.format("Found {} APP1 directory entries", dirEntryCount));

                        getIfd(app1Offset, dirEntryCount).then(function(ifdHex) {
                            var dirEntries = getDirEntries(ifdHex),
                                tagValues = getTagValues(littleEndian, dirEntries);

                            log("Successfully parsed some EXIF tags");

                            parser.success(tagValues);
                        }, onParseFailure);
                    }, onParseFailure);
                }, onParseFailure);
            }, onParseFailure);

            return parser;
        }
    });

    /*<testing>*/
    this._testing = {};
    this._testing.parseLittleEndian = parseLittleEndian;
    /*</testing>*/
};

/*globals qq */
qq.Identify = function(fileOrBlob, log) {
    "use strict";

    var PREVIEWABLE_MAGIC_BYTES = {
            "image/jpeg": "ffd8ff",
            "image/gif": "474946",
            "image/png": "89504e",
            "image/bmp": "424d",
            "image/tiff": ["49492a00", "4d4d002a"]
        };

    function isIdentifiable(magicBytes, questionableBytes) {
        var identifiable = false,
            magicBytesEntries = [].concat(magicBytes);

        qq.each(magicBytesEntries, function(idx, magicBytesArrayEntry) {
            if (questionableBytes.indexOf(magicBytesArrayEntry) === 0) {
                identifiable = true;
                return false;
            }
        });

        return identifiable;
    }

    qq.extend(this, {
        isPreviewable: function() {
            var idenitifer = new qq.Promise(),
                previewable = false,
                name = fileOrBlob.name === undefined ? "blob" : fileOrBlob.name;

            log(qq.format("Attempting to determine if {} can be rendered in this browser", name));

            qq.readBlobToHex(fileOrBlob, 0, 4).then(function(hex) {
                qq.each(PREVIEWABLE_MAGIC_BYTES, function(mime, bytes) {
                    if (isIdentifiable(bytes, hex)) {
                        // Safari is the only supported browser that can deal with TIFFs natively,
                        // so, if this is a TIFF and the UA isn't Safari, declare this file "non-previewable".
                        if (mime !== "image/tiff" || qq.safari()) {
                            previewable = true;
                            idenitifer.success(mime);
                        }

                        return false;
                    }
                });

                log(qq.format("'{}' is {} able to be rendered in this browser", name, previewable ? "" : "NOT"));

                if (!previewable) {
                    idenitifer.failure();
                }
            });

            return idenitifer;
        }
    });
};

/*globals qq*/
/**
 * Attempts to validate an image, wherever possible.
 *
 * @param blob File or Blob representing a user-selecting image.
 * @param log Uses this to post log messages to the console.
 * @constructor
 */
qq.ImageValidation = function(blob, log) {
    "use strict";

    /**
     * @param limits Object with possible image-related limits to enforce.
     * @returns {boolean} true if at least one of the limits has a non-zero value
     */
    function hasNonZeroLimits(limits) {
        var atLeastOne = false;

        qq.each(limits, function(limit, value) {
            if (value > 0) {
                atLeastOne = true;
                return false;
            }
        });

        return atLeastOne;
    }

    /**
     * @returns {qq.Promise} The promise is a failure if we can't obtain the width & height.
     * Otherwise, `success` is called on the returned promise with an object containing
     * `width` and `height` properties.
     */
    function getWidthHeight() {
        var sizeDetermination = new qq.Promise();

        new qq.Identify(blob, log).isPreviewable().then(function() {
            var image = new Image(),
                url = window.URL && window.URL.createObjectURL ? window.URL :
                      window.webkitURL && window.webkitURL.createObjectURL ? window.webkitURL :
                      null;

            if (url) {
                image.onerror = function() {
                    log("Cannot determine dimensions for image.  May be too large.", "error");
                    sizeDetermination.failure();
                };

                image.onload = function() {
                    sizeDetermination.success({
                        width: this.width,
                        height: this.height
                    });
                };

                image.src = url.createObjectURL(blob);
            }
            else {
                log("No createObjectURL function available to generate image URL!", "error");
                sizeDetermination.failure();
            }
        }, sizeDetermination.failure);

        return sizeDetermination;
    }

    /**
     *
     * @param limits Object with possible image-related limits to enforce.
     * @param dimensions Object containing `width` & `height` properties for the image to test.
     * @returns {String || undefined} The name of the failing limit.  Undefined if no failing limits.
     */
    function getFailingLimit(limits, dimensions) {
        var failingLimit;

        qq.each(limits, function(limitName, limitValue) {
            if (limitValue > 0) {
                var limitMatcher = /(max|min)(Width|Height)/.exec(limitName),
                    dimensionPropName = limitMatcher[2].charAt(0).toLowerCase() + limitMatcher[2].slice(1),
                    actualValue = dimensions[dimensionPropName];

                /*jshint -W015*/
                switch(limitMatcher[1]) {
                    case "min":
                        if (actualValue < limitValue) {
                            failingLimit = limitName;
                            return false;
                        }
                        break;
                    case "max":
                        if (actualValue > limitValue) {
                            failingLimit = limitName;
                            return false;
                        }
                        break;
                }
            }
        });

        return failingLimit;
    }

    /**
     * Validate the associated blob.
     *
     * @param limits
     * @returns {qq.Promise} `success` is called on the promise is the image is valid or
     * if the blob is not an image, or if the image is not verifiable.
     * Otherwise, `failure` with the name of the failing limit.
     */
    this.validate = function(limits) {
        var validationEffort = new qq.Promise();

        log("Attempting to validate image.");

        if (hasNonZeroLimits(limits)) {
            getWidthHeight().then(function(dimensions) {
                var failingLimit = getFailingLimit(limits, dimensions);

                if (failingLimit) {
                    validationEffort.failure(failingLimit);
                }
                else {
                    validationEffort.success();
                }
            }, validationEffort.success);
        }
        else {
            validationEffort.success();
        }

        return validationEffort;
    };
};

/* globals qq */
/**
 * Module used to control populating the initial list of files.
 *
 * @constructor
 */
qq.Session = function(spec) {
    "use strict";

    var options = {
        endpoint: null,
        params: {},
        customHeaders: {},
        cors: {},
        addFileRecord: function(sessionData) {},
        log: function(message, level) {}
    };

    qq.extend(options, spec, true);


    function isJsonResponseValid(response) {
        if (qq.isArray(response)) {
            return true;
        }

        options.log("Session response is not an array.", "error");
    }

    function handleFileItems(fileItems, success, xhrOrXdr, promise) {
        var someItemsIgnored = false;

        success = success && isJsonResponseValid(fileItems);

        if (success) {
            qq.each(fileItems, function(idx, fileItem) {
                /* jshint eqnull:true */
                if (fileItem.uuid == null) {
                    someItemsIgnored = true;
                    options.log(qq.format("Session response item {} did not include a valid UUID - ignoring.", idx), "error");
                }
                else if (fileItem.name == null) {
                    someItemsIgnored = true;
                    options.log(qq.format("Session response item {} did not include a valid name - ignoring.", idx), "error");
                }
                else {
                    try {
                        options.addFileRecord(fileItem);
                        return true;
                    }
                    catch(err) {
                        someItemsIgnored = true;
                        options.log(err.message, "error");
                    }
                }

                return false;
            });
        }

        promise[success && !someItemsIgnored ? "success" : "failure"](fileItems, xhrOrXdr);
    }

    // Initiate a call to the server that will be used to populate the initial file list.
    // Returns a `qq.Promise`.
    this.refresh = function() {
        /*jshint indent:false */
        var refreshEffort = new qq.Promise(),
            refreshCompleteCallback = function(response, success, xhrOrXdr) {
                handleFileItems(response, success, xhrOrXdr, refreshEffort);
            },
            requsterOptions = qq.extend({}, options),
            requester = new qq.SessionAjaxRequester(
                qq.extend(requsterOptions, {onComplete: refreshCompleteCallback})
            );

        requester.queryServer();

        return refreshEffort;
    };
};

/*globals qq, XMLHttpRequest*/
/**
 * Thin module used to send GET requests to the server, expecting information about session
 * data used to initialize an uploader instance.
 *
 * @param spec Various options used to influence the associated request.
 * @constructor
 */
qq.SessionAjaxRequester = function(spec) {
    "use strict";

    var requester,
        options = {
            endpoint: null,
            customHeaders: {},
            params: {},
            cors: {
                expected: false,
                sendCredentials: false
            },
            onComplete: function(response, success, xhrOrXdr) {},
            log: function(str, level) {}
        };

    qq.extend(options, spec);

    function onComplete(id, xhrOrXdr, isError) {
        var response = null;

        /* jshint eqnull:true */
        if (xhrOrXdr.responseText != null) {
            try {
                response = qq.parseJson(xhrOrXdr.responseText);
            }
            catch(err) {
                options.log("Problem parsing session response: " + err.message, "error");
                isError = true;
            }
        }

        options.onComplete(response, !isError, xhrOrXdr);
    }

    requester = qq.extend(this, new qq.AjaxRequester({
        validMethods: ["GET"],
        method: "GET",
        endpointStore: {
            get: function() {
                return options.endpoint;
            }
        },
        customHeaders: options.customHeaders,
        log: options.log,
        onComplete: onComplete,
        cors: options.cors
    }));


    qq.extend(this, {
        queryServer: function() {
            var params = qq.extend({}, options.params);

            options.log("Session query request.");

            requester.initTransport("sessionRefresh")
                .withParams(params)
                .withCacheBuster()
                .send();
        }
    });
};

/* globals qq */
/**
 * Module that handles support for existing forms.
 *
 * @param options Options passed from the integrator-supplied options related to form support.
 * @param startUpload Callback to invoke when files "stored" should be uploaded.
 * @param log Proxy for the logger
 * @constructor
 */
qq.FormSupport = function(options, startUpload, log) {
    "use strict";
    var self  = this,
        interceptSubmit = options.interceptSubmit,
        formEl = options.element,
        autoUpload = options.autoUpload;

    // Available on the public API associated with this module.
    qq.extend(this, {
        // To be used by the caller to determine if the endpoint will be determined by some processing
        // that occurs in this module, such as if the form has an action attribute.
        // Ignore if `attachToForm === false`.
        newEndpoint: null,

        // To be used by the caller to determine if auto uploading should be allowed.
        // Ignore if `attachToForm === false`.
        newAutoUpload: autoUpload,

        // true if a form was detected and is being tracked by this module
        attachedToForm: false,

        // Returns an object with names and values for all valid form elements associated with the attached form.
        getFormInputsAsObject: function() {
            /* jshint eqnull:true */
            if (formEl == null) {
                return null;
            }

            return self._form2Obj(formEl);
        }
    });

    // If the form contains an action attribute, this should be the new upload endpoint.
    function determineNewEndpoint(formEl) {
        if (formEl.getAttribute("action")) {
            self.newEndpoint = formEl.getAttribute("action");
        }
    }

    // Return true only if the form is valid, or if we cannot make this determination.
    // If the form is invalid, ensure invalid field(s) are highlighted in the UI.
    function validateForm(formEl, nativeSubmit) {
        if (formEl.checkValidity && !formEl.checkValidity()) {
            log("Form did not pass validation checks - will not upload.", "error");
            nativeSubmit();
        }
        else {
            return true;
        }
    }

    // Intercept form submit attempts, unless the integrator has told us not to do this.
    function maybeUploadOnSubmit(formEl) {
        var nativeSubmit = formEl.submit;

        // Intercept and squelch submit events.
        qq(formEl).attach("submit", function(event) {
            event = event || window.event;

            if (event.preventDefault) {
                event.preventDefault();
            }
            else {
                event.returnValue = false;
            }

            validateForm(formEl, nativeSubmit) && startUpload();
        });

        // The form's `submit()` function may be called instead (i.e. via jQuery.submit()).
        // Intercept that too.
        formEl.submit = function() {
            validateForm(formEl, nativeSubmit) && startUpload();
        };
    }

    // If the element value passed from the uploader is a string, assume it is an element ID - select it.
    // The rest of the code in this module depends on this being an HTMLElement.
    function determineFormEl(formEl) {
        if (formEl) {
            if (qq.isString(formEl)) {
                formEl = document.getElementById(formEl);
            }

            if (formEl) {
                log("Attaching to form element.");
                determineNewEndpoint(formEl);
                interceptSubmit && maybeUploadOnSubmit(formEl);
            }
        }

        return formEl;
    }

    formEl = determineFormEl(formEl);
    this.attachedToForm = !!formEl;
};

qq.extend(qq.FormSupport.prototype, {
    // Converts all relevant form fields to key/value pairs.  This is meant to mimic the data a browser will
    // construct from a given form when the form is submitted.
    _form2Obj: function(form) {
        "use strict";
        var obj = {},
            notIrrelevantType = function(type) {
                var irrelevantTypes = [
                    "button",
                    "image",
                    "reset",
                    "submit"
                ];

                return qq.indexOf(irrelevantTypes, type.toLowerCase()) < 0;
            },
            radioOrCheckbox = function(type) {
                return qq.indexOf(["checkbox", "radio"], type.toLowerCase()) >= 0;
            },
            ignoreValue = function(el) {
                if (radioOrCheckbox(el.type) && !el.checked) {
                    return true;
                }

                return el.disabled && el.type.toLowerCase() !== "hidden";
            },
            selectValue = function(select) {
                var value = null;

                qq.each(qq(select).children(), function(idx, child) {
                    if (child.tagName.toLowerCase() === "option" && child.selected) {
                        value = child.value;
                        return false;
                    }
                });

                return value;
            };

        qq.each(form.elements, function(idx, el) {
            if (qq.isInput(el, true) && notIrrelevantType(el.type) && !ignoreValue(el)) {
                obj[el.name] = el.value;
            }
            else if (el.tagName.toLowerCase() === "select" && !ignoreValue(el)) {
                var value = selectValue(el);

                if (value !== null) {
                    obj[el.name] = value;
                }
            }
        });

        return obj;
    }
});

/*globals qq */
// Base handler for UI (FineUploader mode) events.
// Some more specific handlers inherit from this one.
qq.UiEventHandler = function(s, protectedApi) {
    "use strict";

    var disposer = new qq.DisposeSupport(),
        spec = {
            eventType: "click",
            attachTo: null,
            onHandled: function(target, event) {}
        };


    // This makes up the "public" API methods that will be accessible
    // to instances constructing a base or child handler
    qq.extend(this, {
        addHandler: function(element) {
            addHandler(element);
        },

        dispose: function() {
            disposer.dispose();
        }
    });

    function addHandler(element) {
        disposer.attach(element, spec.eventType, function(event) {
            // Only in IE: the `event` is a property of the `window`.
            event = event || window.event;

            // On older browsers, we must check the `srcElement` instead of the `target`.
            var target = event.target || event.srcElement;

            spec.onHandled(target, event);
        });
    }

    // These make up the "protected" API methods that children of this base handler will utilize.
    qq.extend(protectedApi, {
        getFileIdFromItem: function(item) {
            return item.qqFileId;
        },

        getDisposeSupport: function() {
            return disposer;
        }
    });


    qq.extend(spec, s);

    if (spec.attachTo) {
        addHandler(spec.attachTo);
    }
};

/* global qq */
qq.FileButtonsClickHandler = function(s) {
    "use strict";

    var inheritedInternalApi = {},
        spec = {
            templating: null,
            log: function(message, lvl) {},
            onDeleteFile: function(fileId) {},
            onCancel: function(fileId) {},
            onRetry: function(fileId) {},
            onPause: function(fileId) {},
            onContinue: function(fileId) {},
            onGetName: function(fileId) {}
        },
        buttonHandlers = {
            cancel: function(id) { spec.onCancel(id); },
            retry:  function(id) { spec.onRetry(id); },
            deleteButton: function(id) { spec.onDeleteFile(id); },
            pause: function(id) { spec.onPause(id); },
            continueButton: function(id) { spec.onContinue(id); }
        };

    function examineEvent(target, event) {
        qq.each(buttonHandlers, function(buttonType, handler) {
            var firstLetterCapButtonType = buttonType.charAt(0).toUpperCase() + buttonType.slice(1),
                fileId;

            if (spec.templating["is" + firstLetterCapButtonType](target)) {
                fileId = spec.templating.getFileId(target);
                qq.preventDefault(event);
                spec.log(qq.format("Detected valid file button click event on file '{}', ID: {}.", spec.onGetName(fileId), fileId));
                handler(fileId);
                return false;
            }
        });
    }

    qq.extend(spec, s);

    spec.eventType = "click";
    spec.onHandled = examineEvent;
    spec.attachTo = spec.templating.getFileList();

    qq.extend(this, new qq.UiEventHandler(spec, inheritedInternalApi));
};

/*globals qq */
// Child of FilenameEditHandler.  Used to detect click events on filename display elements.
qq.FilenameClickHandler = function(s) {
    "use strict";

    var inheritedInternalApi = {},
        spec = {
            templating: null,
            log: function(message, lvl) {},
            classes: {
                file: "qq-upload-file",
                editNameIcon: "qq-edit-filename-icon"
            },
            onGetUploadStatus: function(fileId) {},
            onGetName: function(fileId) {}
        };

    qq.extend(spec, s);

    // This will be called by the parent handler when a `click` event is received on the list element.
    function examineEvent(target, event) {
        if (spec.templating.isFileName(target) || spec.templating.isEditIcon(target)) {
            var fileId = spec.templating.getFileId(target),
                status = spec.onGetUploadStatus(fileId);

            // We only allow users to change filenames of files that have been submitted but not yet uploaded.
            if (status === qq.status.SUBMITTED) {
                spec.log(qq.format("Detected valid filename click event on file '{}', ID: {}.", spec.onGetName(fileId), fileId));
                qq.preventDefault(event);

                inheritedInternalApi.handleFilenameEdit(fileId, target, true);
            }
        }
    }

    spec.eventType = "click";
    spec.onHandled = examineEvent;

    qq.extend(this, new qq.FilenameEditHandler(spec, inheritedInternalApi));
};

/*globals qq */
// Child of FilenameEditHandler.  Used to detect focusin events on file edit input elements.
qq.FilenameInputFocusInHandler = function(s, inheritedInternalApi) {
    "use strict";

    var spec = {
            templating: null,
            onGetUploadStatus: function(fileId) {},
            log: function(message, lvl) {}
        };

    if (!inheritedInternalApi) {
        inheritedInternalApi = {};
    }

    // This will be called by the parent handler when a `focusin` event is received on the list element.
    function handleInputFocus(target, event) {
        if (spec.templating.isEditInput(target)) {
            var fileId = spec.templating.getFileId(target),
                status = spec.onGetUploadStatus(fileId);

            if (status === qq.status.SUBMITTED) {
                spec.log(qq.format("Detected valid filename input focus event on file '{}', ID: {}.", spec.onGetName(fileId), fileId));
                inheritedInternalApi.handleFilenameEdit(fileId, target);
            }
        }
    }

    spec.eventType = "focusin";
    spec.onHandled = handleInputFocus;

    qq.extend(spec, s);
    qq.extend(this, new qq.FilenameEditHandler(spec, inheritedInternalApi));
};

/*globals qq */
/**
 * Child of FilenameInputFocusInHandler.  Used to detect focus events on file edit input elements.  This child module is only
 * needed for UAs that do not support the focusin event.  Currently, only Firefox lacks this event.
 *
 * @param spec Overrides for default specifications
 */
qq.FilenameInputFocusHandler = function(spec) {
    "use strict";

    spec.eventType = "focus";
    spec.attachTo = null;

    qq.extend(this, new qq.FilenameInputFocusInHandler(spec, {}));
};

/*globals qq */
// Handles edit-related events on a file item (FineUploader mode).  This is meant to be a parent handler.
// Children will delegate to this handler when specific edit-related actions are detected.
qq.FilenameEditHandler = function(s, inheritedInternalApi) {
    "use strict";

    var spec = {
            templating: null,
            log: function(message, lvl) {},
            onGetUploadStatus: function(fileId) {},
            onGetName: function(fileId) {},
            onSetName: function(fileId, newName) {},
            onEditingStatusChange: function(fileId, isEditing) {}
        };


    function getFilenameSansExtension(fileId) {
        var filenameSansExt = spec.onGetName(fileId),
            extIdx = filenameSansExt.lastIndexOf(".");

        if (extIdx > 0) {
            filenameSansExt = filenameSansExt.substr(0, extIdx);
        }

        return filenameSansExt;
    }

    function getOriginalExtension(fileId) {
        var origName = spec.onGetName(fileId);
        return qq.getExtension(origName);
    }

    // Callback iff the name has been changed
    function handleNameUpdate(newFilenameInputEl, fileId) {
        var newName = newFilenameInputEl.value,
            origExtension;

        if (newName !== undefined && qq.trimStr(newName).length > 0) {
            origExtension = getOriginalExtension(fileId);

            if (origExtension !== undefined) {
                newName = newName + "." + origExtension;
            }

            spec.onSetName(fileId, newName);
        }

        spec.onEditingStatusChange(fileId, false);
    }

    // The name has been updated if the filename edit input loses focus.
    function registerInputBlurHandler(inputEl, fileId) {
        inheritedInternalApi.getDisposeSupport().attach(inputEl, "blur", function() {
            handleNameUpdate(inputEl, fileId);
        });
    }

    // The name has been updated if the user presses enter.
    function registerInputEnterKeyHandler(inputEl, fileId) {
        inheritedInternalApi.getDisposeSupport().attach(inputEl, "keyup", function(event) {

            var code = event.keyCode || event.which;

            if (code === 13) {
                handleNameUpdate(inputEl, fileId);
            }
        });
    }

    qq.extend(spec, s);

    spec.attachTo = spec.templating.getFileList();

    qq.extend(this, new qq.UiEventHandler(spec, inheritedInternalApi));

    qq.extend(inheritedInternalApi, {
        handleFilenameEdit: function(id, target, focusInput) {
            var newFilenameInputEl = spec.templating.getEditInput(id);

            spec.onEditingStatusChange(id, true);

            newFilenameInputEl.value = getFilenameSansExtension(id);

            if (focusInput) {
                newFilenameInputEl.focus();
            }

            registerInputBlurHandler(newFilenameInputEl, id);
            registerInputEnterKeyHandler(newFilenameInputEl, id);
        }
    });
};

/*
CryptoJS v3.1.2
code.google.com/p/crypto-js
(c) 2009-2013 by Jeff Mott. All rights reserved.
code.google.com/p/crypto-js/wiki/License
*/
/**
 * CryptoJS core components.
 */
var CryptoJS = CryptoJS || (function (Math, undefined) {
    /**
     * CryptoJS namespace.
     */
    var C = {};

    /**
     * Library namespace.
     */
    var C_lib = C.lib = {};

    /**
     * Base object for prototypal inheritance.
     */
    var Base = C_lib.Base = (function () {
        function F() {}

        return {
            /**
             * Creates a new object that inherits from this object.
             *
             * @param {Object} overrides Properties to copy into the new object.
             *
             * @return {Object} The new object.
             *
             * @static
             *
             * @example
             *
             *     var MyType = CryptoJS.lib.Base.extend({
             *         field: 'value',
             *
             *         method: function () {
             *         }
             *     });
             */
            extend: function (overrides) {
                // Spawn
                F.prototype = this;
                var subtype = new F();

                // Augment
                if (overrides) {
                    subtype.mixIn(overrides);
                }

                // Create default initializer
                if (!subtype.hasOwnProperty('init')) {
                    subtype.init = function () {
                        subtype.$super.init.apply(this, arguments);
                    };
                }

                // Initializer's prototype is the subtype object
                subtype.init.prototype = subtype;

                // Reference supertype
                subtype.$super = this;

                return subtype;
            },

            /**
             * Extends this object and runs the init method.
             * Arguments to create() will be passed to init().
             *
             * @return {Object} The new object.
             *
             * @static
             *
             * @example
             *
             *     var instance = MyType.create();
             */
            create: function () {
                var instance = this.extend();
                instance.init.apply(instance, arguments);

                return instance;
            },

            /**
             * Initializes a newly created object.
             * Override this method to add some logic when your objects are created.
             *
             * @example
             *
             *     var MyType = CryptoJS.lib.Base.extend({
             *         init: function () {
             *             // ...
             *         }
             *     });
             */
            init: function () {
            },

            /**
             * Copies properties into this object.
             *
             * @param {Object} properties The properties to mix in.
             *
             * @example
             *
             *     MyType.mixIn({
             *         field: 'value'
             *     });
             */
            mixIn: function (properties) {
                for (var propertyName in properties) {
                    if (properties.hasOwnProperty(propertyName)) {
                        this[propertyName] = properties[propertyName];
                    }
                }

                // IE won't copy toString using the loop above
                if (properties.hasOwnProperty('toString')) {
                    this.toString = properties.toString;
                }
            },

            /**
             * Creates a copy of this object.
             *
             * @return {Object} The clone.
             *
             * @example
             *
             *     var clone = instance.clone();
             */
            clone: function () {
                return this.init.prototype.extend(this);
            }
        };
    }());

    /**
     * An array of 32-bit words.
     *
     * @property {Array} words The array of 32-bit words.
     * @property {number} sigBytes The number of significant bytes in this word array.
     */
    var WordArray = C_lib.WordArray = Base.extend({
        /**
         * Initializes a newly created word array.
         *
         * @param {Array} words (Optional) An array of 32-bit words.
         * @param {number} sigBytes (Optional) The number of significant bytes in the words.
         *
         * @example
         *
         *     var wordArray = CryptoJS.lib.WordArray.create();
         *     var wordArray = CryptoJS.lib.WordArray.create([0x00010203, 0x04050607]);
         *     var wordArray = CryptoJS.lib.WordArray.create([0x00010203, 0x04050607], 6);
         */
        init: function (words, sigBytes) {
            words = this.words = words || [];

            if (sigBytes != undefined) {
                this.sigBytes = sigBytes;
            } else {
                this.sigBytes = words.length * 4;
            }
        },

        /**
         * Converts this word array to a string.
         *
         * @param {Encoder} encoder (Optional) The encoding strategy to use. Default: CryptoJS.enc.Hex
         *
         * @return {string} The stringified word array.
         *
         * @example
         *
         *     var string = wordArray + '';
         *     var string = wordArray.toString();
         *     var string = wordArray.toString(CryptoJS.enc.Utf8);
         */
        toString: function (encoder) {
            return (encoder || Hex).stringify(this);
        },

        /**
         * Concatenates a word array to this word array.
         *
         * @param {WordArray} wordArray The word array to append.
         *
         * @return {WordArray} This word array.
         *
         * @example
         *
         *     wordArray1.concat(wordArray2);
         */
        concat: function (wordArray) {
            // Shortcuts
            var thisWords = this.words;
            var thatWords = wordArray.words;
            var thisSigBytes = this.sigBytes;
            var thatSigBytes = wordArray.sigBytes;

            // Clamp excess bits
            this.clamp();

            // Concat
            if (thisSigBytes % 4) {
                // Copy one byte at a time
                for (var i = 0; i < thatSigBytes; i++) {
                    var thatByte = (thatWords[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff;
                    thisWords[(thisSigBytes + i) >>> 2] |= thatByte << (24 - ((thisSigBytes + i) % 4) * 8);
                }
            } else if (thatWords.length > 0xffff) {
                // Copy one word at a time
                for (var i = 0; i < thatSigBytes; i += 4) {
                    thisWords[(thisSigBytes + i) >>> 2] = thatWords[i >>> 2];
                }
            } else {
                // Copy all words at once
                thisWords.push.apply(thisWords, thatWords);
            }
            this.sigBytes += thatSigBytes;

            // Chainable
            return this;
        },

        /**
         * Removes insignificant bits.
         *
         * @example
         *
         *     wordArray.clamp();
         */
        clamp: function () {
            // Shortcuts
            var words = this.words;
            var sigBytes = this.sigBytes;

            // Clamp
            words[sigBytes >>> 2] &= 0xffffffff << (32 - (sigBytes % 4) * 8);
            words.length = Math.ceil(sigBytes / 4);
        },

        /**
         * Creates a copy of this word array.
         *
         * @return {WordArray} The clone.
         *
         * @example
         *
         *     var clone = wordArray.clone();
         */
        clone: function () {
            var clone = Base.clone.call(this);
            clone.words = this.words.slice(0);

            return clone;
        },

        /**
         * Creates a word array filled with random bytes.
         *
         * @param {number} nBytes The number of random bytes to generate.
         *
         * @return {WordArray} The random word array.
         *
         * @static
         *
         * @example
         *
         *     var wordArray = CryptoJS.lib.WordArray.random(16);
         */
        random: function (nBytes) {
            var words = [];
            for (var i = 0; i < nBytes; i += 4) {
                words.push((Math.random() * 0x100000000) | 0);
            }

            return new WordArray.init(words, nBytes);
        }
    });

    /**
     * Encoder namespace.
     */
    var C_enc = C.enc = {};

    /**
     * Hex encoding strategy.
     */
    var Hex = C_enc.Hex = {
        /**
         * Converts a word array to a hex string.
         *
         * @param {WordArray} wordArray The word array.
         *
         * @return {string} The hex string.
         *
         * @static
         *
         * @example
         *
         *     var hexString = CryptoJS.enc.Hex.stringify(wordArray);
         */
        stringify: function (wordArray) {
            // Shortcuts
            var words = wordArray.words;
            var sigBytes = wordArray.sigBytes;

            // Convert
            var hexChars = [];
            for (var i = 0; i < sigBytes; i++) {
                var bite = (words[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff;
                hexChars.push((bite >>> 4).toString(16));
                hexChars.push((bite & 0x0f).toString(16));
            }

            return hexChars.join('');
        },

        /**
         * Converts a hex string to a word array.
         *
         * @param {string} hexStr The hex string.
         *
         * @return {WordArray} The word array.
         *
         * @static
         *
         * @example
         *
         *     var wordArray = CryptoJS.enc.Hex.parse(hexString);
         */
        parse: function (hexStr) {
            // Shortcut
            var hexStrLength = hexStr.length;

            // Convert
            var words = [];
            for (var i = 0; i < hexStrLength; i += 2) {
                words[i >>> 3] |= parseInt(hexStr.substr(i, 2), 16) << (24 - (i % 8) * 4);
            }

            return new WordArray.init(words, hexStrLength / 2);
        }
    };

    /**
     * Latin1 encoding strategy.
     */
    var Latin1 = C_enc.Latin1 = {
        /**
         * Converts a word array to a Latin1 string.
         *
         * @param {WordArray} wordArray The word array.
         *
         * @return {string} The Latin1 string.
         *
         * @static
         *
         * @example
         *
         *     var latin1String = CryptoJS.enc.Latin1.stringify(wordArray);
         */
        stringify: function (wordArray) {
            // Shortcuts
            var words = wordArray.words;
            var sigBytes = wordArray.sigBytes;

            // Convert
            var latin1Chars = [];
            for (var i = 0; i < sigBytes; i++) {
                var bite = (words[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff;
                latin1Chars.push(String.fromCharCode(bite));
            }

            return latin1Chars.join('');
        },

        /**
         * Converts a Latin1 string to a word array.
         *
         * @param {string} latin1Str The Latin1 string.
         *
         * @return {WordArray} The word array.
         *
         * @static
         *
         * @example
         *
         *     var wordArray = CryptoJS.enc.Latin1.parse(latin1String);
         */
        parse: function (latin1Str) {
            // Shortcut
            var latin1StrLength = latin1Str.length;

            // Convert
            var words = [];
            for (var i = 0; i < latin1StrLength; i++) {
                words[i >>> 2] |= (latin1Str.charCodeAt(i) & 0xff) << (24 - (i % 4) * 8);
            }

            return new WordArray.init(words, latin1StrLength);
        }
    };

    /**
     * UTF-8 encoding strategy.
     */
    var Utf8 = C_enc.Utf8 = {
        /**
         * Converts a word array to a UTF-8 string.
         *
         * @param {WordArray} wordArray The word array.
         *
         * @return {string} The UTF-8 string.
         *
         * @static
         *
         * @example
         *
         *     var utf8String = CryptoJS.enc.Utf8.stringify(wordArray);
         */
        stringify: function (wordArray) {
            try {
                return decodeURIComponent(escape(Latin1.stringify(wordArray)));
            } catch (e) {
                throw new Error('Malformed UTF-8 data');
            }
        },

        /**
         * Converts a UTF-8 string to a word array.
         *
         * @param {string} utf8Str The UTF-8 string.
         *
         * @return {WordArray} The word array.
         *
         * @static
         *
         * @example
         *
         *     var wordArray = CryptoJS.enc.Utf8.parse(utf8String);
         */
        parse: function (utf8Str) {
            return Latin1.parse(unescape(encodeURIComponent(utf8Str)));
        }
    };

    /**
     * Abstract buffered block algorithm template.
     *
     * The property blockSize must be implemented in a concrete subtype.
     *
     * @property {number} _minBufferSize The number of blocks that should be kept unprocessed in the buffer. Default: 0
     */
    var BufferedBlockAlgorithm = C_lib.BufferedBlockAlgorithm = Base.extend({
        /**
         * Resets this block algorithm's data buffer to its initial state.
         *
         * @example
         *
         *     bufferedBlockAlgorithm.reset();
         */
        reset: function () {
            // Initial values
            this._data = new WordArray.init();
            this._nDataBytes = 0;
        },

        /**
         * Adds new data to this block algorithm's buffer.
         *
         * @param {WordArray|string} data The data to append. Strings are converted to a WordArray using UTF-8.
         *
         * @example
         *
         *     bufferedBlockAlgorithm._append('data');
         *     bufferedBlockAlgorithm._append(wordArray);
         */
        _append: function (data) {
            // Convert string to WordArray, else assume WordArray already
            if (typeof data == 'string') {
                data = Utf8.parse(data);
            }

            // Append
            this._data.concat(data);
            this._nDataBytes += data.sigBytes;
        },

        /**
         * Processes available data blocks.
         *
         * This method invokes _doProcessBlock(offset), which must be implemented by a concrete subtype.
         *
         * @param {boolean} doFlush Whether all blocks and partial blocks should be processed.
         *
         * @return {WordArray} The processed data.
         *
         * @example
         *
         *     var processedData = bufferedBlockAlgorithm._process();
         *     var processedData = bufferedBlockAlgorithm._process(!!'flush');
         */
        _process: function (doFlush) {
            // Shortcuts
            var data = this._data;
            var dataWords = data.words;
            var dataSigBytes = data.sigBytes;
            var blockSize = this.blockSize;
            var blockSizeBytes = blockSize * 4;

            // Count blocks ready
            var nBlocksReady = dataSigBytes / blockSizeBytes;
            if (doFlush) {
                // Round up to include partial blocks
                nBlocksReady = Math.ceil(nBlocksReady);
            } else {
                // Round down to include only full blocks,
                // less the number of blocks that must remain in the buffer
                nBlocksReady = Math.max((nBlocksReady | 0) - this._minBufferSize, 0);
            }

            // Count words ready
            var nWordsReady = nBlocksReady * blockSize;

            // Count bytes ready
            var nBytesReady = Math.min(nWordsReady * 4, dataSigBytes);

            // Process blocks
            if (nWordsReady) {
                for (var offset = 0; offset < nWordsReady; offset += blockSize) {
                    // Perform concrete-algorithm logic
                    this._doProcessBlock(dataWords, offset);
                }

                // Remove processed words
                var processedWords = dataWords.splice(0, nWordsReady);
                data.sigBytes -= nBytesReady;
            }

            // Return processed words
            return new WordArray.init(processedWords, nBytesReady);
        },

        /**
         * Creates a copy of this object.
         *
         * @return {Object} The clone.
         *
         * @example
         *
         *     var clone = bufferedBlockAlgorithm.clone();
         */
        clone: function () {
            var clone = Base.clone.call(this);
            clone._data = this._data.clone();

            return clone;
        },

        _minBufferSize: 0
    });

    /**
     * Abstract hasher template.
     *
     * @property {number} blockSize The number of 32-bit words this hasher operates on. Default: 16 (512 bits)
     */
    var Hasher = C_lib.Hasher = BufferedBlockAlgorithm.extend({
        /**
         * Configuration options.
         */
        cfg: Base.extend(),

        /**
         * Initializes a newly created hasher.
         *
         * @param {Object} cfg (Optional) The configuration options to use for this hash computation.
         *
         * @example
         *
         *     var hasher = CryptoJS.algo.SHA256.create();
         */
        init: function (cfg) {
            // Apply config defaults
            this.cfg = this.cfg.extend(cfg);

            // Set initial values
            this.reset();
        },

        /**
         * Resets this hasher to its initial state.
         *
         * @example
         *
         *     hasher.reset();
         */
        reset: function () {
            // Reset data buffer
            BufferedBlockAlgorithm.reset.call(this);

            // Perform concrete-hasher logic
            this._doReset();
        },

        /**
         * Updates this hasher with a message.
         *
         * @param {WordArray|string} messageUpdate The message to append.
         *
         * @return {Hasher} This hasher.
         *
         * @example
         *
         *     hasher.update('message');
         *     hasher.update(wordArray);
         */
        update: function (messageUpdate) {
            // Append
            this._append(messageUpdate);

            // Update the hash
            this._process();

            // Chainable
            return this;
        },

        /**
         * Finalizes the hash computation.
         * Note that the finalize operation is effectively a destructive, read-once operation.
         *
         * @param {WordArray|string} messageUpdate (Optional) A final message update.
         *
         * @return {WordArray} The hash.
         *
         * @example
         *
         *     var hash = hasher.finalize();
         *     var hash = hasher.finalize('message');
         *     var hash = hasher.finalize(wordArray);
         */
        finalize: function (messageUpdate) {
            // Final message update
            if (messageUpdate) {
                this._append(messageUpdate);
            }

            // Perform concrete-hasher logic
            var hash = this._doFinalize();

            return hash;
        },

        blockSize: 512/32,

        /**
         * Creates a shortcut function to a hasher's object interface.
         *
         * @param {Hasher} hasher The hasher to create a helper for.
         *
         * @return {Function} The shortcut function.
         *
         * @static
         *
         * @example
         *
         *     var SHA256 = CryptoJS.lib.Hasher._createHelper(CryptoJS.algo.SHA256);
         */
        _createHelper: function (hasher) {
            return function (message, cfg) {
                return new hasher.init(cfg).finalize(message);
            };
        },

        /**
         * Creates a shortcut function to the HMAC's object interface.
         *
         * @param {Hasher} hasher The hasher to use in this HMAC helper.
         *
         * @return {Function} The shortcut function.
         *
         * @static
         *
         * @example
         *
         *     var HmacSHA256 = CryptoJS.lib.Hasher._createHmacHelper(CryptoJS.algo.SHA256);
         */
        _createHmacHelper: function (hasher) {
            return function (message, key) {
                return new C_algo.HMAC.init(hasher, key).finalize(message);
            };
        }
    });

    /**
     * Algorithm namespace.
     */
    var C_algo = C.algo = {};

    return C;
}(Math));

/*
CryptoJS v3.1.2
code.google.com/p/crypto-js
(c) 2009-2013 by Jeff Mott. All rights reserved.
code.google.com/p/crypto-js/wiki/License
*/
(function () {
    // Shortcuts
    var C = CryptoJS;
    var C_lib = C.lib;
    var WordArray = C_lib.WordArray;
    var C_enc = C.enc;

    /**
     * Base64 encoding strategy.
     */
    var Base64 = C_enc.Base64 = {
        /**
         * Converts a word array to a Base64 string.
         *
         * @param {WordArray} wordArray The word array.
         *
         * @return {string} The Base64 string.
         *
         * @static
         *
         * @example
         *
         *     var base64String = CryptoJS.enc.Base64.stringify(wordArray);
         */
        stringify: function (wordArray) {
            // Shortcuts
            var words = wordArray.words;
            var sigBytes = wordArray.sigBytes;
            var map = this._map;

            // Clamp excess bits
            wordArray.clamp();

            // Convert
            var base64Chars = [];
            for (var i = 0; i < sigBytes; i += 3) {
                var byte1 = (words[i >>> 2]       >>> (24 - (i % 4) * 8))       & 0xff;
                var byte2 = (words[(i + 1) >>> 2] >>> (24 - ((i + 1) % 4) * 8)) & 0xff;
                var byte3 = (words[(i + 2) >>> 2] >>> (24 - ((i + 2) % 4) * 8)) & 0xff;

                var triplet = (byte1 << 16) | (byte2 << 8) | byte3;

                for (var j = 0; (j < 4) && (i + j * 0.75 < sigBytes); j++) {
                    base64Chars.push(map.charAt((triplet >>> (6 * (3 - j))) & 0x3f));
                }
            }

            // Add padding
            var paddingChar = map.charAt(64);
            if (paddingChar) {
                while (base64Chars.length % 4) {
                    base64Chars.push(paddingChar);
                }
            }

            return base64Chars.join('');
        },

        /**
         * Converts a Base64 string to a word array.
         *
         * @param {string} base64Str The Base64 string.
         *
         * @return {WordArray} The word array.
         *
         * @static
         *
         * @example
         *
         *     var wordArray = CryptoJS.enc.Base64.parse(base64String);
         */
        parse: function (base64Str) {
            // Shortcuts
            var base64StrLength = base64Str.length;
            var map = this._map;

            // Ignore padding
            var paddingChar = map.charAt(64);
            if (paddingChar) {
                var paddingIndex = base64Str.indexOf(paddingChar);
                if (paddingIndex != -1) {
                    base64StrLength = paddingIndex;
                }
            }

            // Convert
            var words = [];
            var nBytes = 0;
            for (var i = 0; i < base64StrLength; i++) {
                if (i % 4) {
                    var bits1 = map.indexOf(base64Str.charAt(i - 1)) << ((i % 4) * 2);
                    var bits2 = map.indexOf(base64Str.charAt(i)) >>> (6 - (i % 4) * 2);
                    words[nBytes >>> 2] |= (bits1 | bits2) << (24 - (nBytes % 4) * 8);
                    nBytes++;
                }
            }

            return WordArray.create(words, nBytes);
        },

        _map: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/='
    };
}());

/*
CryptoJS v3.1.2
code.google.com/p/crypto-js
(c) 2009-2013 by Jeff Mott. All rights reserved.
code.google.com/p/crypto-js/wiki/License
*/
(function () {
    // Shortcuts
    var C = CryptoJS;
    var C_lib = C.lib;
    var Base = C_lib.Base;
    var C_enc = C.enc;
    var Utf8 = C_enc.Utf8;
    var C_algo = C.algo;

    /**
     * HMAC algorithm.
     */
    var HMAC = C_algo.HMAC = Base.extend({
        /**
         * Initializes a newly created HMAC.
         *
         * @param {Hasher} hasher The hash algorithm to use.
         * @param {WordArray|string} key The secret key.
         *
         * @example
         *
         *     var hmacHasher = CryptoJS.algo.HMAC.create(CryptoJS.algo.SHA256, key);
         */
        init: function (hasher, key) {
            // Init hasher
            hasher = this._hasher = new hasher.init();

            // Convert string to WordArray, else assume WordArray already
            if (typeof key == 'string') {
                key = Utf8.parse(key);
            }

            // Shortcuts
            var hasherBlockSize = hasher.blockSize;
            var hasherBlockSizeBytes = hasherBlockSize * 4;

            // Allow arbitrary length keys
            if (key.sigBytes > hasherBlockSizeBytes) {
                key = hasher.finalize(key);
            }

            // Clamp excess bits
            key.clamp();

            // Clone key for inner and outer pads
            var oKey = this._oKey = key.clone();
            var iKey = this._iKey = key.clone();

            // Shortcuts
            var oKeyWords = oKey.words;
            var iKeyWords = iKey.words;

            // XOR keys with pad constants
            for (var i = 0; i < hasherBlockSize; i++) {
                oKeyWords[i] ^= 0x5c5c5c5c;
                iKeyWords[i] ^= 0x36363636;
            }
            oKey.sigBytes = iKey.sigBytes = hasherBlockSizeBytes;

            // Set initial values
            this.reset();
        },

        /**
         * Resets this HMAC to its initial state.
         *
         * @example
         *
         *     hmacHasher.reset();
         */
        reset: function () {
            // Shortcut
            var hasher = this._hasher;

            // Reset
            hasher.reset();
            hasher.update(this._iKey);
        },

        /**
         * Updates this HMAC with a message.
         *
         * @param {WordArray|string} messageUpdate The message to append.
         *
         * @return {HMAC} This HMAC instance.
         *
         * @example
         *
         *     hmacHasher.update('message');
         *     hmacHasher.update(wordArray);
         */
        update: function (messageUpdate) {
            this._hasher.update(messageUpdate);

            // Chainable
            return this;
        },

        /**
         * Finalizes the HMAC computation.
         * Note that the finalize operation is effectively a destructive, read-once operation.
         *
         * @param {WordArray|string} messageUpdate (Optional) A final message update.
         *
         * @return {WordArray} The HMAC.
         *
         * @example
         *
         *     var hmac = hmacHasher.finalize();
         *     var hmac = hmacHasher.finalize('message');
         *     var hmac = hmacHasher.finalize(wordArray);
         */
        finalize: function (messageUpdate) {
            // Shortcut
            var hasher = this._hasher;

            // Compute HMAC
            var innerHash = hasher.finalize(messageUpdate);
            hasher.reset();
            var hmac = hasher.finalize(this._oKey.clone().concat(innerHash));

            return hmac;
        }
    });
}());

/*
CryptoJS v3.1.2
code.google.com/p/crypto-js
(c) 2009-2013 by Jeff Mott. All rights reserved.
code.google.com/p/crypto-js/wiki/License
*/
(function () {
    // Shortcuts
    var C = CryptoJS;
    var C_lib = C.lib;
    var WordArray = C_lib.WordArray;
    var Hasher = C_lib.Hasher;
    var C_algo = C.algo;

    // Reusable object
    var W = [];

    /**
     * SHA-1 hash algorithm.
     */
    var SHA1 = C_algo.SHA1 = Hasher.extend({
        _doReset: function () {
            this._hash = new WordArray.init([
                0x67452301, 0xefcdab89,
                0x98badcfe, 0x10325476,
                0xc3d2e1f0
            ]);
        },

        _doProcessBlock: function (M, offset) {
            // Shortcut
            var H = this._hash.words;

            // Working variables
            var a = H[0];
            var b = H[1];
            var c = H[2];
            var d = H[3];
            var e = H[4];

            // Computation
            for (var i = 0; i < 80; i++) {
                if (i < 16) {
                    W[i] = M[offset + i] | 0;
                } else {
                    var n = W[i - 3] ^ W[i - 8] ^ W[i - 14] ^ W[i - 16];
                    W[i] = (n << 1) | (n >>> 31);
                }

                var t = ((a << 5) | (a >>> 27)) + e + W[i];
                if (i < 20) {
                    t += ((b & c) | (~b & d)) + 0x5a827999;
                } else if (i < 40) {
                    t += (b ^ c ^ d) + 0x6ed9eba1;
                } else if (i < 60) {
                    t += ((b & c) | (b & d) | (c & d)) - 0x70e44324;
                } else /* if (i < 80) */ {
                    t += (b ^ c ^ d) - 0x359d3e2a;
                }

                e = d;
                d = c;
                c = (b << 30) | (b >>> 2);
                b = a;
                a = t;
            }

            // Intermediate hash value
            H[0] = (H[0] + a) | 0;
            H[1] = (H[1] + b) | 0;
            H[2] = (H[2] + c) | 0;
            H[3] = (H[3] + d) | 0;
            H[4] = (H[4] + e) | 0;
        },

        _doFinalize: function () {
            // Shortcuts
            var data = this._data;
            var dataWords = data.words;

            var nBitsTotal = this._nDataBytes * 8;
            var nBitsLeft = data.sigBytes * 8;

            // Add padding
            dataWords[nBitsLeft >>> 5] |= 0x80 << (24 - nBitsLeft % 32);
            dataWords[(((nBitsLeft + 64) >>> 9) << 4) + 14] = Math.floor(nBitsTotal / 0x100000000);
            dataWords[(((nBitsLeft + 64) >>> 9) << 4) + 15] = nBitsTotal;
            data.sigBytes = dataWords.length * 4;

            // Hash final blocks
            this._process();

            // Return final computed hash
            return this._hash;
        },

        clone: function () {
            var clone = Hasher.clone.call(this);
            clone._hash = this._hash.clone();

            return clone;
        }
    });

    /**
     * Shortcut function to the hasher's object interface.
     *
     * @param {WordArray|string} message The message to hash.
     *
     * @return {WordArray} The hash.
     *
     * @static
     *
     * @example
     *
     *     var hash = CryptoJS.SHA1('message');
     *     var hash = CryptoJS.SHA1(wordArray);
     */
    C.SHA1 = Hasher._createHelper(SHA1);

    /**
     * Shortcut function to the HMAC's object interface.
     *
     * @param {WordArray|string} message The message to hash.
     * @param {WordArray|string} key The secret key.
     *
     * @return {WordArray} The HMAC.
     *
     * @static
     *
     * @example
     *
     *     var hmac = CryptoJS.HmacSHA1(message, key);
     */
    C.HmacSHA1 = Hasher._createHmacHelper(SHA1);
}());

/*globals jQuery, qq*/
(function($) {
    "use strict";
    var $el,
        pluginOptions = ["uploaderType", "endpointType"];

    function init(options) {
        var xformedOpts = transformVariables(options || {}),
            newUploaderInstance = getNewUploaderInstance(xformedOpts);

        uploader(newUploaderInstance);
        addCallbacks(xformedOpts, newUploaderInstance);

        return $el;
    }

    function getNewUploaderInstance(params) {
        var uploaderType = pluginOption("uploaderType"),
            namespace = pluginOption("endpointType");

        // If the integrator has defined a specific type of uploader to load, use that, otherwise assume `qq.FineUploader`
        if (uploaderType) {
            // We can determine the correct constructor function to invoke by combining "FineUploader"
            // with the upper camel cased `uploaderType` value.
            uploaderType = uploaderType.charAt(0).toUpperCase() + uploaderType.slice(1).toLowerCase();

            if (namespace) {
                return new qq[namespace]["FineUploader" + uploaderType](params);
            }

            return new qq["FineUploader" + uploaderType](params);
        }
        else {
            if (namespace) {
                return new qq[namespace].FineUploader(params);
            }

            return new qq.FineUploader(params);
        }
    }

    function dataStore(key, val) {
        var data = $el.data("fineuploader");

        if (val) {
            if (data === undefined) {
                data = {};
            }
            data[key] = val;
            $el.data("fineuploader", data);
        }
        else {
            if (data === undefined) {
                return null;
            }
            return data[key];
        }
    }

    //the underlying Fine Uploader instance is stored in jQuery's data stored, associated with the element
    // tied to this instance of the plug-in
    function uploader(instanceToStore) {
        return dataStore("uploader", instanceToStore);
    }

    function pluginOption(option, optionVal) {
        return dataStore(option, optionVal);
    }

    // Implement all callbacks defined in Fine Uploader as functions that trigger appropriately names events and
    // return the result of executing the bound handler back to Fine Uploader
    function addCallbacks(transformedOpts, newUploaderInstance) {
        var callbacks = transformedOpts.callbacks = {};

        $.each(newUploaderInstance._options.callbacks, function(prop, nonJqueryCallback) {
            var name, callbackEventTarget;

            name = /^on(\w+)/.exec(prop)[1];
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
            callbackEventTarget = $el;

            callbacks[prop] = function() {
                var originalArgs = Array.prototype.slice.call(arguments),
                    transformedArgs = [],
                    nonJqueryCallbackRetVal, jqueryEventCallbackRetVal;

                $.each(originalArgs, function(idx, arg) {
                    transformedArgs.push(maybeWrapInJquery(arg));
                });

                nonJqueryCallbackRetVal = nonJqueryCallback.apply(this, originalArgs);

                try {
                    jqueryEventCallbackRetVal = callbackEventTarget.triggerHandler(name, transformedArgs);
                }
                catch (error) {
                    qq.log("Caught error in Fine Uploader jQuery event handler: " + error.message, "error");
                }

                /*jshint -W116*/
                if (nonJqueryCallbackRetVal != null) {
                    return nonJqueryCallbackRetVal;
                }
                return jqueryEventCallbackRetVal;
            };
        });

        newUploaderInstance._options.callbacks = callbacks;
    }

    //transform jQuery objects into HTMLElements, and pass along all other option properties
    function transformVariables(source, dest) {
        var xformed, arrayVals;

        if (dest === undefined) {
            if (source.uploaderType !== "basic") {
                xformed = { element : $el[0] };
            }
            else {
                xformed = {};
            }
        }
        else {
            xformed = dest;
        }

        $.each(source, function(prop, val) {
            if ($.inArray(prop, pluginOptions) >= 0) {
                pluginOption(prop, val);
            }
            else if (val instanceof $) {
                xformed[prop] = val[0];
            }
            else if ($.isPlainObject(val)) {
                xformed[prop] = {};
                transformVariables(val, xformed[prop]);
            }
            else if ($.isArray(val)) {
                arrayVals = [];
                $.each(val, function(idx, arrayVal) {
                    var arrayObjDest = {};

                    if (arrayVal instanceof $) {
                        $.merge(arrayVals, arrayVal);
                    }
                    else if ($.isPlainObject(arrayVal)) {
                        transformVariables(arrayVal, arrayObjDest);
                        arrayVals.push(arrayObjDest);
                    }
                    else {
                        arrayVals.push(arrayVal);
                    }
                });
                xformed[prop] = arrayVals;
            }
            else {
                xformed[prop] = val;
            }
        });

        if (dest === undefined) {
            return xformed;
        }
    }

    function isValidCommand(command) {
        return $.type(command) === "string" &&
            !command.match(/^_/) && //enforce private methods convention
            uploader()[command] !== undefined;
    }

    // Assuming we have already verified that this is a valid command, call the associated function in the underlying
    // Fine Uploader instance (passing along the arguments from the caller) and return the result of the call back to the caller
    function delegateCommand(command) {
        var xformedArgs = [],
            origArgs = Array.prototype.slice.call(arguments, 1),
            retVal;

        transformVariables(origArgs, xformedArgs);

        retVal = uploader()[command].apply(uploader(), xformedArgs);

        return maybeWrapInJquery(retVal);
    }

    // If the value is an `HTMLElement` or `HTMLDocument`, wrap it in a `jQuery` object
    function maybeWrapInJquery(val) {
        var transformedVal = val;

        // If the command is returning an `HTMLElement` or `HTMLDocument`, wrap it in a `jQuery` object
        /*jshint -W116*/
        if (val != null && typeof val === "object" &&
           (val.nodeType === 1 || val.nodeType === 9) && val.cloneNode) {

            transformedVal = $(val);
        }

        return transformedVal;
    }

    $.fn.fineUploader = function(optionsOrCommand) {
        var self = this, selfArgs = arguments, retVals = [];

        this.each(function(index, el) {
            $el = $(el);

            if (uploader() && isValidCommand(optionsOrCommand)) {
                retVals.push(delegateCommand.apply(self, selfArgs));

                if (self.length === 1) {
                    return false;
                }
            }
            else if (typeof optionsOrCommand === "object" || !optionsOrCommand) {
                init.apply(self, selfArgs);
            }
            else {
                $.error("Method " +  optionsOrCommand + " does not exist on jQuery.fineUploader");
            }
        });

        if (retVals.length === 1) {
            return retVals[0];
        }
        else if (retVals.length > 1) {
            return retVals;
        }

        return this;
    };

}(jQuery));

/*globals jQuery*/
/**
 * Simply an alias for the `fineUploader` plug-in wrapper, but hides the required `endpointType` option from the
 * integrator.  I thought it may be confusing to convey to the integrator that, when using Fine Uploader in S3 mode,
 * you need to specify an `endpointType` with a value of S3, and perhaps an `uploaderType` with a value of "basic" if
 * you want to use basic mode when uploading directly to S3 as well.  So, you can use this plug-in alias and not worry
 * about the `endpointType` option at all.
 */
(function($) {
    "use strict";

    $.fn.fineUploaderS3 = function(optionsOrCommand) {
        if (typeof optionsOrCommand === "object") {

            // This option is used to tell the plug-in wrapper to instantiate the appropriate S3-namespace modules.
            optionsOrCommand.endpointType = "s3";
        }

        return $.fn.fineUploader.apply(this, arguments);
    };

}(jQuery));

/*globals jQuery, qq*/
(function($) {
    "use strict";
    var rootDataKey = "fineUploaderDnd",
        $el;

    function init (options) {
        if (!options) {
            options = {};
        }

        options.dropZoneElements = [$el];
        var xformedOpts = transformVariables(options);
        addCallbacks(xformedOpts);
        dnd(new qq.DragAndDrop(xformedOpts));

        return $el;
    }

    function dataStore(key, val) {
        var data = $el.data(rootDataKey);

        if (val) {
            if (data === undefined) {
                data = {};
            }
            data[key] = val;
            $el.data(rootDataKey, data);
        }
        else {
            if (data === undefined) {
                return null;
            }
            return data[key];
        }
    }

    function dnd(instanceToStore) {
        return dataStore("dndInstance", instanceToStore);
    }

    function addCallbacks(transformedOpts) {
        var callbacks = transformedOpts.callbacks = {},
            dndInst = new qq.FineUploaderBasic();

        $.each(new qq.DragAndDrop.callbacks(), function(prop, func) {
            var name = prop,
                $callbackEl;

            $callbackEl = $el;

            callbacks[prop] = function() {
                var args = Array.prototype.slice.call(arguments),
                    jqueryHandlerResult = $callbackEl.triggerHandler(name, args);

                return jqueryHandlerResult;
            };
        });
    }

    //transform jQuery objects into HTMLElements, and pass along all other option properties
    function transformVariables(source, dest) {
        var xformed, arrayVals;

        if (dest === undefined) {
            xformed = {};
        }
        else {
            xformed = dest;
        }

        $.each(source, function(prop, val) {
            if (val instanceof $) {
                xformed[prop] = val[0];
            }
            else if ($.isPlainObject(val)) {
                xformed[prop] = {};
                transformVariables(val, xformed[prop]);
            }
            else if ($.isArray(val)) {
                arrayVals = [];
                $.each(val, function(idx, arrayVal) {
                    if (arrayVal instanceof $) {
                        $.merge(arrayVals, arrayVal);
                    }
                    else {
                        arrayVals.push(arrayVal);
                    }
                });
                xformed[prop] = arrayVals;
            }
            else {
                xformed[prop] = val;
            }
        });

        if (dest === undefined) {
            return xformed;
        }
    }

    function isValidCommand(command) {
        return $.type(command) === "string" &&
            command === "dispose" &&
            dnd()[command] !== undefined;
    }

    function delegateCommand(command) {
        var xformedArgs = [], origArgs = Array.prototype.slice.call(arguments, 1);
        transformVariables(origArgs, xformedArgs);
        return dnd()[command].apply(dnd(), xformedArgs);
    }

    $.fn.fineUploaderDnd = function(optionsOrCommand) {
        var self = this, selfArgs = arguments, retVals = [];

        this.each(function(index, el) {
            $el = $(el);

            if (dnd() && isValidCommand(optionsOrCommand)) {
                retVals.push(delegateCommand.apply(self, selfArgs));

                if (self.length === 1) {
                    return false;
                }
            }
            else if (typeof optionsOrCommand === "object" || !optionsOrCommand) {
                init.apply(self, selfArgs);
            }
            else {
                $.error("Method " +  optionsOrCommand + " does not exist in Fine Uploader's DnD module.");
            }
        });

        if (retVals.length === 1) {
            return retVals[0];
        }
        else if (retVals.length > 1) {
            return retVals;
        }

        return this;
    };

}(jQuery));

/*! 2014-03-07 */
