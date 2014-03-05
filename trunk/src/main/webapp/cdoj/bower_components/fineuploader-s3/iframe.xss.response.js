/*!
* Fine Uploader
*
* Copyright 2013, Widen Enterprises, Inc. info@fineuploader.com
*
* Version: 4.1.1
*
* Homepage: http://fineuploader.com
*
* Repository: git://github.com/Widen/fine-uploader.git
*
* Licensed under GNU GPL v3, see LICENSE
*
* Third-party credits:
*   MegaPixImageModule (MIT)
*       https://github.com/stomita/ios-imagefile-megapixel
*       Copyright (c) 2012 Shinichi Tomita <shinichi.tomita@gmail.com>
*/ 


(function() {
    "use strict";
    var match = /(\{.*\})/.exec(document.body.innerHTML);
    if (match) {
        parent.postMessage(match[1], "*");
    }
}());
