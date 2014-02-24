(function() {
  var $, AuthenticationType, AuthorStatusType, ContestStatus, ContestType, OnlineJudgeReturnType, emotionTable, emotionsPerRow, formatEmotionId, getEmotionUrl;

  OnlineJudgeReturnType = {
    OJ_WAIT: 0,
    OJ_AC: 1,
    OJ_PE: 2,
    OJ_TLE: 3,
    OJ_MLE: 4,
    OJ_WA: 5,
    OJ_OLE: 6,
    OJ_CE: 7,
    OJ_RE_SEGV: 8,
    OJ_RE_FPE: 9,
    OJ_RE_BUS: 10,
    OJ_RE_ABRT: 11,
    OJ_RE_UNKNOWN: 12,
    OJ_RF: 13,
    OJ_SE: 14,
    OJ_RE_JAVA: 15,
    OJ_JUDGING: 16,
    OJ_RUNNING: 17,
    OJ_REJUDGING: 18
  };

  ContestType = {
    PUBLIC: 0,
    PRIVATE: 1,
    DIY: 2,
    INVITED: 3
  };

  ContestStatus = {
    PENDING: "Pending",
    RUNNING: "Running",
    ENDED: "Ended"
  };

  AuthenticationType = {
    NORMAL: "0",
    ADMIN: "1",
    CONSTANT: "2"
  };

  AuthorStatusType = {
    NONE: 0,
    PASS: 1,
    FAIL: 2
  };

  $ = jQuery;

  $.fn.getCursorPosition = function() {
    if (this.lengh === 0) {
      return -1;
    }
    return $(this).getSelectionStart();
  };

  $.fn.setCursorPosition = function(position) {
    if (this.lengh === 0) {
      return this;
    }
    return $(this).setSelection(position, position);
  };

  $.fn.getSelection = function() {
    var e, s;
    if (this.lengh === 0) {
      return -1;
    }
    s = $(this).getSelectionStart();
    e = $(this).getSelectionEnd();
    return this[0].value.substring(s, e);
  };

  $.fn.getSelectionStart = function() {
    var input, pos, r;
    if (this.lengh === 0) {
      return -1;
    }
    input = this[0];
    pos = input.value.length;
    if (input.createTextRange) {
      r = document.selection.createRange().duplicate();
      r.moveEnd('character', input.value.length);
      if (r.text === '') {
        pos = input.value.length;
      }
      pos = input.value.lastIndexOf(r.text);
    } else if (typeof input.selectionStart !== "undefined") {
      pos = input.selectionStart;
    }
    return pos;
  };

  $.fn.getSelectionEnd = function() {
    var input, pos, r;
    if (this.lengh === 0) {
      return -1;
    }
    input = this[0];
    pos = input.value.length;
    if (input.createTextRange) {
      r = document.selection.createRange().duplicate();
      r.moveStart('character', -input.value.length);
      if (r.text === '') {
        pos = input.value.length;
      }
      pos = input.value.lastIndexOf(r.text);
    } else if (typeof input.selectionEnd !== "undefined") {
      pos = input.selectionEnd;
    }
    return pos;
  };

  $.fn.setSelection = function(selectionStart, selectionEnd) {
    var input, range;
    if (this.lengh === 0) {
      return this;
    }
    input = this[0];
    if (input.createTextRange) {
      range = input.createTextRange();
      range.collapse(true);
      range.moveEnd('character', selectionEnd);
      range.moveStart('character', selectionStart);
      range.select();
    } else if (input.setSelectionRange) {
      input.focus();
      input.setSelectionRange(selectionStart, selectionEnd);
    }
    return this;
  };

  emotionsPerRow = 8;

  formatEmotionId = function(id) {
    if (id > 9) {
      return "" + id;
    } else {
      return "0" + id;
    }
  };

  getEmotionUrl = function(url, id, extension) {
    return "" + url + "/" + (formatEmotionId(id + 1)) + "." + extension;
  };

  emotionTable = function(url, extension, count) {
    var tableContent, template;
    tableContent = "";
    count.times(function(i) {
      if (i % emotionsPerRow === 0) {
        if (i > 0) {
          tableContent += "</tr>";
        }
        tableContent += "<tr>";
      }
      return tableContent += "<td value=\"![" + (i + 1) + "](" + (getEmotionUrl(url, i, extension)) + ")\">\n  <span><img src=\"" + (getEmotionUrl(url, i, extension)) + "\" class=\"img-rounded\" style=\"width: 50px; height: 50px\"/></span>\n</td>";
    });
    tableContent += "</tr>";
    template = "<table class=\"table table-bordered table-condensed\">\n  <tbody>\n    " + tableContent + "\n  </tbody>\n</table>";
    return template;
  };

  $ = jQuery;

  $.fn.mathjax = function() {
    MathJax.Hub.Config({
      tex2jax: {
        inlineMath: [['$', '$'], ['\\[', '\\]']]
      }
    });
    return this.each((function(_this) {
      return function(id, el) {
        return MathJax.Hub.Queue(["Typeset", MathJax.Hub, el]);
      };
    })(this));
  };

  $ = jQuery;

  $.fn.prettify = function() {
    return this.find("pre").each((function(_this) {
      return function(id, el) {
        var $el, text;
        $el = $(el);
        if ($el.attr("type") !== "no-prettify") {
          text = prettyPrintOne($el[0].innerText.escapeHTML());
          return $el.empty().append(text);
        }
      };
    })(this));
  };

}).call(this);
