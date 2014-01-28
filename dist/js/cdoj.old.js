(function() {
  var $, AuthenticationType, AuthorStatusType, ContestStatus, ContestType, Flandre, ListModule, OnlineJudgeReturnType, SearchModule, avatar, emotionTable, emotionsPerRow, formatEmotionId, getEmotionUrl, getParam, initArticleEditor, initContestList, initContestPage, initLayout, initProblemPage, initUI, initUserList, jsonMerge, jsonPost, openInNewTab, render;

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

  avatar = function(el, options) {
    var $el, emailAddress, url;
    $el = $(el);
    emailAddress = $el.attr("email");
    url = "http://www.gravatar.com/avatar/" + (hex_md5(emailAddress)) + ".jpg?" + (options.size ? "s=" + options.size + "&" : "") + "" + (options.rating ? "r=" + options.rating + "&" : "") + "" + (options.image ? "d=" + encodeURIComponent(options.image) : "");
    $el.attr("src", url);
    return el;
  };

  $ = jQuery;

  $.fn.setAvatar = function(userOption) {
    var options,
      _this = this;
    options = {
      size: 40,
      image: "retro",
      rating: "pg"
    };
    $.extend(options, userOption);
    return this.each(function(id, el) {
      return avatar(el, options);
    });
  };

  jsonPost = function(url, data, callback) {
    return $.ajax({
      type: "POST",
      url: url,
      dataType: "json",
      contentType: "application/json",
      data: JSON.stringify(data),
      success: callback
    });
  };

  jsonMerge = function(jsonA, jsonB) {
    return Object.merge(jsonA, jsonB, false, function(key, a, b) {
      if (a.constructor === Array) {
        a.add(b);
      } else {
        a = b;
      }
      return a;
    });
  };

  openInNewTab = function(url) {
    var win;
    win = window.open(url, "_blank");
    return win.focus();
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

  $.fn.insertAfterCursor = function(value, moveSteps) {
    var oldText, position;
    if (this.length === 0) {
      return this;
    }
    position = this.getCursorPosition();
    oldText = this.val();
    oldText = oldText.insert(value, position);
    this.val(oldText);
    return this.setCursorPosition(position + value.length + moveSteps);
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

  $.fn.getFormValue = function() {
    var $el, isType, result;
    $el = $(this);
    isType = function(_type) {
      return $el.attr("type") === _type || $el[0].localName === _type;
    };
    if (isType("radio")) {
      result = $(":radio[name='" + ($el.attr('name')) + "']:checked").val();
      if (result === "all") {
        result = void 0;
      }
      return result;
    } else if (isType("select")) {
      result = $el.val();
      if (result === -1) {
        result = void 0;
      }
      return result;
    } else {
      return $el.val();
    }
  };

  $.fn.getFormData = function() {
    var result,
      _this = this;
    result = {};
    this.each(function(id, el) {
      var $el, $input, $inputs, ignoreList, input, _i, _len, _results;
      ignoreList = ["submit", "reset", "button", "image", "file"];
      $el = $(el);
      $inputs = $el.find(":input");
      _results = [];
      for (_i = 0, _len = $inputs.length; _i < _len; _i++) {
        input = $inputs[_i];
        $input = $(input);
        if (ignoreList.none($input.attr("type"))) {
          _results.push(result[$input.attr("name")] = $input.getFormValue());
        } else {
          _results.push(void 0);
        }
      }
      return _results;
    });
    return result;
  };

  $.fn.setFormValue = function(value) {
    var $el, isType;
    $el = $(this);
    isType = function(_type) {
      return $el.attr("type") === _type || $el[0].localName === _type;
    };
    if (isType("radio")) {
      if (value === void 0) {
        value = "all";
      }
      if ($el.attr("value") === value) {
        return $el.attr("checked", true);
      } else {
        return $el.attr("checked", false);
      }
    } else if (isType("select")) {
      if (value === void 0) {
        value = -1;
      }
      return $el.val(value);
    } else {
      return $el.val(value);
    }
  };

  $.fn.setFormData = function(data) {
    var _this = this;
    return this.each(function(id, el) {
      var $el, $input, $inputs, ignoreList, input, _i, _len, _results;
      ignoreList = ["submit", "reset", "button"];
      $el = $(el);
      $inputs = $el.find(":input");
      _results = [];
      for (_i = 0, _len = $inputs.length; _i < _len; _i++) {
        input = $inputs[_i];
        $input = $(input);
        if (ignoreList.none($input.attr("type"))) {
          _results.push($input.setFormValue(data[$input.attr("name")]));
        } else {
          _results.push(void 0);
        }
      }
      return _results;
    });
  };

  $.fn.resetFormData = function() {
    var _this = this;
    return this.each(function(id, el) {
      var $el, $input, $inputs, ignoreList, input, _i, _len, _results;
      ignoreList = ["submit", "reset", "button"];
      $el = $(el);
      $inputs = $el.find(":input");
      _results = [];
      for (_i = 0, _len = $inputs.length; _i < _len; _i++) {
        input = $inputs[_i];
        $input = $(input);
        if (ignoreList.none($input.attr("type"))) {
          _results.push($input.setFormValue(void 0));
        } else {
          _results.push(void 0);
        }
      }
      return _results;
    });
  };

  $.fn.formValidate = function(userOptions) {
    var $el, isSuccess, options, result,
      _this = this;
    options = {
      result: {
        result: "success"
      },
      onSuccess: function() {},
      onFail: function() {}
    };
    $.extend(options, userOptions);
    result = options.result;
    isSuccess = false;
    if (result.result === null) {
      alert("Unknown exception.");
    } else if (result.result === "error") {
      alert(result.error_msg);
    } else if (result.result === "success") {
      options.onSuccess();
      isSuccess = true;
    } else if (result.result === "field_error") {
      $el = $(this);
      result.field.each(function(value) {
        var controlGroup, element;
        element = $el.find(":input[name='" + value.field + "']");
        if (element && element.length > 0) {
          element = $(element.first());
          controlGroup = element.closest("div.form-group");
          return controlGroup.tooltip({
            trigger: "click",
            placement: "bottom",
            title: value.defaultMessage
          }).tooltip("show");
        }
      });
    } else {
      alert("Unknown exception.");
    }
    if (!isSuccess) {
      return options.onFail();
    }
  };

  $ = jQuery;

  $.fn.unescapePre = function() {
    var _this = this;
    return this.each(function(id, el) {
      var $el, str;
      $el = $(el);
      str = ("<pre>" + $el[0].innerHTML + "</pre>").unescapeHTML();
      return $el.replaceWith($("<div>" + str + "</div>"));
    });
  };

  $.fn.unescapeCode = function() {
    var _this = this;
    return this.each(function(id, el) {
      var $el, str;
      $el = $(el);
      str = "<code>" + ($el[0].innerHTML.unescapeHTML()) + "</code>";
      return $el.replaceWith(str);
    });
  };

  $.fn.renderTable = function() {
    var _this = this;
    return this.each(function(id, el) {
      var $el;
      $el = $(el);
      return $el.addClass("table").addClass("table-bordered").addClass("table-condensed").addClass("cdoj-markdown-table");
    });
  };

  $.fn.renderImage = function() {
    var _this = this;
    return this.each(function(id, el) {
      var $el;
      return $el = $(el);
    });
  };

  $.fn.markdown = function() {
    var _this = this;
    return this.each(function(id, el) {
      var $el, html, md;
      $el = $(el);
      md = $el.html().trim().replace('<textarea>', '').replace('</textarea>', '').replace('<TEXTAREA>', '').replace('</TEXTAREA>', '').trim();
      html = $(marked(md));
      $el.empty().append(html);
      $el.find("code").unescapeCode();
      $el.find("pre").unescapePre();
      $el.find("table").renderTable();
      return $el.find("img").renderImage();
    });
  };

  $ = jQuery;

  $.fn.mathjax = function() {
    var _this = this;
    MathJax.Hub.Config({
      tex2jax: {
        inlineMath: [['$', '$'], ['\\[', '\\]']]
      }
    });
    return this.each(function(id, el) {
      return MathJax.Hub.Queue(["Typeset", MathJax.Hub, el]);
    });
  };

  $ = jQuery;

  $.fn.prettify = function() {
    var _this = this;
    return this.find("pre").each(function(id, el) {
      var $el, text;
      $el = $(el);
      if ($el.attr("type") !== "no-prettify") {
        text = prettyPrintOne($el[0].innerText.escapeHTML());
        return $el.empty().append(text);
      }
    });
  };

  render = function() {
    $("[type=\"markdown\"]").markdown();
    MathJax.Hub.Config({
      tex2jax: {
        inlineMath: [['$', '$'], ['\\[', '\\]']]
      }
    });
    MathJax.Hub.Queue(['Typeset', MathJax.Hub]);
    return $(document.body).prettify();
  };

  getParam = function() {
    var params, result;
    params = location.search;
    params = params.substr(params.indexOf("?") + 1);
    params = params.split("&");
    result = {};
    params.each(function(value, id) {
      value = value.split("=");
      if (value.length === 2) {
        return result[value[0]] = value[1];
      }
    });
    return result;
  };

  Flandre = (function() {
    function Flandre(options) {
      this.options = options;
      this.element = this.options.element;
      if (this.element === null || this.element.length === 0) {
        return null;
      }
      this.createTextarea();
      this.toolbar();
    }

    Flandre.prototype.escape = function(content) {
      return content;
    };

    Flandre.prototype.createTextarea = function() {
      var oldText, template;
      oldText = this.element[0].innerHTML.trim();
      this.element.empty();
      template = "<div class=\"panel panel-default\">\n  <div class=\"panel-heading\" id=\"flandre-heading\">\n    <div class=\"btn-toolbar\" role=\"toolbar\">\n      <div class=\"btn-group\">\n        <button type=\"button\" class=\"btn btn-default btn-sm\" id=\"tool-preview\">Preview</button>\n      </div>\n      <div class=\"btn-group flandre-tools\">\n        <a class=\"btn btn-default btn-sm\" id=\"tool-emotion\"><i class=\"fa fa-smile-o\"></i></a>\n        <a class=\"btn btn-default btn-sm\" id=\"tool-picture\"><i class=\"fa fa-picture-o\"></i></a>\n      </div>\n    </div>\n  </div>\n  <textarea class=\"tex2jax_ignore form-control\" id=\"flandre-editor\">" + (this.escape(oldText)) + "</textarea>\n  <div id=\"flandre-preview\"></div>\n</div>";
      return this.element.append(template);
    };

    Flandre.prototype.toolbar = function() {
      var editor, emotionModal, emotionModalId, emotionModalTemplate, options, pictureUploader, preview, toolEmotion, toolPicture, toolPreview,
        _this = this;
      editor = this.element.find("#flandre-editor");
      preview = this.element.find("#flandre-preview");
      options = this.options;
      editor.elastic();
      toolPreview = this.element.find("#tool-preview");
      toolPreview.click(function(e) {
        var $el, isActive, text;
        $el = $(e.currentTarget);
        isActive = $el.hasClass("active");
        editor.css("display", "none");
        preview.css("display", "none");
        if (isActive) {
          editor.css("display", "block");
          _this.element.find(".flandre-tools").css("display", "block");
        } else {
          text = editor.val().escapeHTML();
          preview.empty().append(text);
          preview.markdown();
          preview.prettify();
          preview.mathjax();
          preview.css("display", "block");
          _this.element.find(".flandre-tools").css("display", "none");
        }
        return $el.button("toggle");
      });
      toolEmotion = this.element.find("#tool-emotion");
      emotionModalId = "emotion-modal-" + (this.element.attr("id"));
      emotionModalTemplate = "<div class=\"modal fade\" id=\"" + emotionModalId + "\" tabindex=\"-1\" role=\"dialog\" aria-hidden=\"true\">\n  <div class=\"modal-dialog\">\n    <div class=\"modal-content\">\n      <div class=\"modal-header\">\n        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\n        <h4 class=\"modal-title\">\n          <ul class=\"nav nav-pills\">\n            <li class=\"active\"><a href=\"#emotion-brd\" data-toggle=\"tab\">BRD</a></li>\n          </ul>\n        </h4>\n      </div>\n      <div class=\"modal-body\">\n        <div id=\"emotion-dialog\" style=\"width: auto;\" value=\"false\">\n          <div class=\"tab-content\">\n            <div class=\"tab-pane active\" id=\"emotion-brd\">\n              " + (emotionTable("/plugins/cdoj/img/emotion/brd", "gif", 40)) + "\n            </div>\n          </div>\n        </div>\n      </div>\n    </div><!-- /.modal-content -->\n  </div><!-- /.modal-dialog -->\n</div><!-- /.modal -->";
      $(document.body).append(emotionModalTemplate);
      emotionModal = $("#" + emotionModalId);
      toolEmotion.click(function() {
        return emotionModal.modal("show");
      });
      emotionModal.on("shown.bs.modal", function() {
        var _this = this;
        return emotionModal.find("#emotion-dialog").find("td").on("click", function(e) {
          var $el, value;
          $el = $(e.currentTarget);
          value = $el.attr("value");
          editor.insertAfterCursor(value, 0);
          return emotionModal.modal("hide");
        });
      });
      toolPicture = this.element.find("#tool-picture");
      return pictureUploader = new qq.FineUploaderBasic({
        button: toolPicture[0],
        request: {
          endpoint: options.picture.uploadUrl,
          inputName: "uploadFile"
        },
        validation: {
          allowedExtensions: ["jpeg", "jpg", "gif", "png"],
          sizeLimit: 10 * 1024 * 1024
        },
        multiple: false,
        callbacks: {
          onComplete: function(id, fileName, data) {
            var value;
            if (data.success === "true") {
              value = "![" + data.uploadedFile + "](" + data.uploadedFileUrl + ")";
              return editor.insertAfterCursor(value, 0);
            } else {
              return console.log(data);
            }
          }
        }
      });
    };

    Flandre.prototype.getText = function() {
      return this.element.find("#flandre-editor").val();
    };

    return Flandre;

  })();

  $ = jQuery;

  $.fn.flandre = function(options) {
    var flandre;
    if (options !== void 0) {
      options["element"] = $(this);
    } else {
      options = {
        element: $(this)
      };
    }
    flandre = new Flandre(options);
    return flandre;
  };

  SearchModule = (function() {
    function SearchModule(father) {
      var $advancedResetButton, $advancedSearchButton, $conditionForm, $rejudgeButton, initCondition,
        _this = this;
      this.father = father;
      this.search = this.father.searchGroup;
      initCondition = this.father.options.condition;
      $conditionForm = this.search.find("#condition");
      $advancedSearchButton = $conditionForm.find("#search-button");
      $advancedResetButton = $conditionForm.find("#reset-button");
      $advancedSearchButton.click(function() {
        var currentCondition;
        currentCondition = Object.merge(initCondition, $conditionForm.getFormData());
        _this.father.options.condition = currentCondition;
        _this.father.refresh(currentCondition);
        _this.close();
        return false;
      });
      $advancedResetButton.click(function() {
        $conditionForm.resetFormData();
        return false;
      });
      $rejudgeButton = $conditionForm.find("#rejudge-button");
      if ($rejudgeButton.length > 0) {
        $rejudgeButton.click(function() {
          var currentCondition;
          currentCondition = Object.merge(initCondition, $conditionForm.getFormData());
          jsonPost("/status/count", currentCondition, function(datas) {
            if (datas.result === "success") {
              if (confirm("Rejudge all " + datas.count + " records")) {
                return jsonPost("/status/rejudge", currentCondition, function(datas) {
                  if (datas.result === "success") {
                    return alert("Done!");
                  } else {
                    return alert(datas.error_msg);
                  }
                });
              }
            } else {
              return alert(datas.error_msg);
            }
          });
          _this.father.options.condition = currentCondition;
          _this.father.refresh(currentCondition);
          _this.close();
          return false;
        });
      }
    }

    SearchModule.prototype.set = function(data) {
      var $conditionForm;
      $conditionForm = this.search.find("#condition");
      return $conditionForm.setFormData(data);
    };

    SearchModule.prototype.close = function() {
      var $advancedButton;
      $advancedButton = this.search.find("#advanced");
      return $advancedButton.dropdown('toggle');
    };

    return SearchModule;

  })();

  ListModule = (function() {
    /*
      options:
        listContainer: list container
        requestUrl: search request url
        condition: defalut condition
        format: list item format
        after: function will be called after datas apeended
    */

    function ListModule(options) {
      var params, self;
      this.options = options;
      this.listContainer = this.options.listContainer;
      this.searchGroup = this.listContainer.find("#advance-search");
      this.searchModule = new SearchModule(this);
      this.pageInfo = this.listContainer.find("#page-info");
      this.refreshLock = 0;
      params = getParam();
      this.searchModule.set(params);
      this.options.condition = jsonMerge(this.options.condition, params);
      this.refresh(this.options.condition);
      self = this;
      if (this.options.autoRefresh === true) {
        setInterval(function() {
          return self.triggerRefresh();
        }, this.options.refreshInterval === void 0 ? 1000 : this.options.refreshInterval);
      }
    }

    ListModule.prototype.triggerRefresh = function() {
      return this.refresh(this.options.condition);
    };

    ListModule.prototype.refresh = function(condition) {
      var _this = this;
      if (this.refreshLock === 0) {
        this.refreshLock = 1;
        this.list = this.listContainer.find("#list-container");
        jsonPost(this.options.requestUrl, condition, function(datas) {
          _this.pageInfo.empty();
          if (datas.pageInfo !== void 0) {
            _this.pageInfo.append(datas.pageInfo);
            _this.pageInfo.find("a").click(function(e) {
              var $el;
              $el = $(e.currentTarget);
              if ($el.attr("href") === void 0) {
                return false;
              }
              condition.currentPage = $el.attr("href");
              _this.refresh(condition);
              return false;
            });
          }
          _this.list.empty();
          datas.list.each(function(data) {
            return _this.list.append(_this.options.formatter(data));
          });
          if (_this.options.after !== void 0) {
            _this.options.after();
          }
          return _this.refreshLock = 0;
        });
      }
    };

    return ListModule;

  })();

  initLayout = function() {
    var $cdojContainer, $cdojNavbar, $cdojNavbarMenu, $cdojUser, $mzry1992Container, $mzry1992Header, currentUrl, current_position, pos, _i, _len, _ref;
    $cdojNavbar = $("#cdoj-navbar");
    $cdojContainer = $("#cdoj-container");
    $cdojUser = $cdojNavbar.find("#cdoj-user");
    $cdojNavbarMenu = $cdojNavbar.find("#cdoj-navbar-menu");
    $mzry1992Header = $cdojContainer.find("#mzry1992-header");
    $mzry1992Container = $cdojContainer.find("#mzry1992-container");
    if ($mzry1992Header.length !== 0 && $mzry1992Container.length !== 0) {
      $mzry1992Container.css("margin-top", $mzry1992Header.outerHeight());
    }
    currentUrl = window.location.pathname;
    current_position = "home";
    _ref = ["problem", "contest", "status", "user", "admin"];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      pos = _ref[_i];
      if (currentUrl.startsWith("/" + pos + "/")) {
        current_position = pos;
      }
    }
    return $cdojNavbarMenu.find('#menu-item-' + current_position).addClass('cdoj-menu-selected');
  };

  initArticleEditor = function() {
    var $editor, action, editors, editorsId,
      _this = this;
    $editor = $("#article-editor");
    if ($editor.length > 0) {
      action = $("#article-editor-title").attr("value");
      editorsId = ["content"];
      editors = [];
      editorsId.each(function(value, id) {
        return editors.push({
          id: value,
          editor: $("#" + value).flandre({
            picture: {
              uploadUrl: "/picture/uploadPicture/article/" + action
            }
          })
        });
      });
      return $("#submit").click(function() {
        var info;
        info = {
          sampleInput: $("textarea#sample-input").val(),
          sampleOutput: $("textarea#sample-output").val(),
          title: $("#title").val(),
          source: $("#source").val()
        };
        editors.each(function(value, id) {
          return info[value.id] = value.editor.getText();
        });
        if (action === "new") {
          info["action"] = "new";
        } else {
          info["action"] = "edit";
          info["articleId"] = action;
        }
        jsonPost("/article/edit", info, function(data) {
          if (data.result === "success") {
            return window.location.href = "/article/show/" + data.articleId;
          } else if (data.result === "field_error") {
            return alert("Please enter a valid title!");
          } else {
            return alert(data.error_msg);
          }
        });
        return false;
      });
    }
  };

  initContestPage = function() {
    var $contest, $processBar, contestId, contestProblemSummary, contestStatus, contestStatusTabHref, contestTab, contestType, currentTime, currentUser, endTime, startTime,
      _this = this;
    $contest = $("#contest-show");
    if ($contest.length > 0) {
      contestId = $("#contest-title").attr("value");
      currentTime = $("#contest-current-time").attr("value");
      startTime = $("#contest-start-time").attr("value");
      endTime = $("#contest-end-time").attr("value");
      contestType = $("#contest-type").attr("value");
      contestStatus = $("#contest-status").attr("value");
      contestTab = $("#contest-show-tab");
      contestProblemSummary = $("#contest-problem-summary");
      contestStatusTabHref = contestTab.find("a[href='#tab-contest-status']");
      $processBar = $("#contest-progress-bar");
      if (contestStatus === ContestStatus.PENDING) {

      } else if (contestStatus === ContestStatus.RUNNING) {

      } else if (contestStatus === ContestStatus.ENDED) {
        $processBar.addClass("progress-bar-success").css("width", "100%");
      }
      contestProblemSummary.find("a").click(function(e) {
        var $el, href, tabHref;
        $el = $(e.currentTarget);
        href = $el.attr("href");
        if (href.startsWith("#")) {
          tabHref = contestTab.find("a[href='" + href + "']");
          if (tabHref.length > 0) {
            tabHref.tab("show");
          }
          return false;
        } else {
          return true;
        }
      });
      currentUser = getCurrentUser();
      if (currentUser.userLogin) {
        $("#submit-code").click(function() {
          var info;
          info = {
            codeContent: $("#code-content").val(),
            languageId: $(":radio[name='language']:checked").attr('value'),
            contestId: contestId,
            problemId: $("#problem-selector").find("option:selected").val()
          };
          jsonPost("/status/submit", info, function(data) {
            if (data.result === "error") {
              return alert(data.error_msg);
            } else if (data.result === "success") {
              return contestStatusTabHref.tab("show");
            }
          });
          return false;
        });
      }
      initContestStatusList(contestId);
    }
  };

  initContestList = function() {
    var $contestList, contestList;
    $contestList = $("#contest-list");
    if ($contestList.length !== 0) {
      return contestList = new ListModule({
        listContainer: $contestList,
        requestUrl: "/contest/search",
        condition: {
          "currentPage": null,
          "startId": void 0,
          "endId": void 0,
          "keyword": void 0,
          "title": void 0,
          "orderFields": "id",
          "orderAsc": "false"
        },
        formatter: function(data) {
          var adminSpan;
          console.log(data);
          this.user = getCurrentUser();
          adminSpan = function() {
            var result;
            result = "";
            if (this.user.userLogin && this.user.currentUserType === AuthenticationType.ADMIN) {
              result += "<div class=\"btn-toolbar\" role=\"toolbar\">\n  <div class=\"btn-group\">\n    <button type=\"button\" class=\"btn btn-default btn-sm contest-visible-state-editor\" contest-id=\"" + data.contestId + "\" visible=\"" + data.isVisible + "\">\n      <i class=\"" + (data.isVisible ? "fa fa-eye" : "fa fa-eye-slash") + "\"></i>\n    </button>\n    <button type=\"button\" class=\"btn btn-default btn-sm contest-editor\" contest-id=\"" + data.contestId + "\"><i class=\"fa fa-pencil\"></i></button>\n  </div>\n</div>";
            }
            return result;
          };
          return "<div class=\"col-md-12\">\n  <div class=\"panel panel-default\">\n    <div class=\"panel-heading\">\n      <h3 class=\"panel-title\">\n        <a href=\"/contest/show/" + data.contestId + "\" target=\"_blank\">" + data.title + "</a>\n        <span class='pull-right admin-span'>" + (adminSpan()) + "</span>\n      </h3>\n    </div>\n    <div class=\"panel-body\">\n      <span>" + (Date.create(data.time).format("{yyyy}-{MM}-{dd} {HH}:{mm}:{ss}")) + "--" + (Date.create(data.time + data.length * 1000).format("{yyyy}-{MM}-{dd} {HH}:{mm}:{ss}")) + "</span>\n    </div>\n  </div>\n</div>";
        },
        after: function() {
          var _this = this;
          this.user = getCurrentUser();
          if (this.user.userLogin && this.user.currentUserType === AuthenticationType.ADMIN) {
            $(".contest-editor").click(function(e) {
              var $el;
              $el = $(e.currentTarget);
              openInNewTab("/contest/editor/" + ($el.attr("contest-id")));
              return false;
            });
            return $(".contest-visible-state-editor").click(function(e) {
              var $el, queryString, visible;
              $el = $(e.currentTarget);
              visible = $el.attr("visible") === "true" ? true : false;
              queryString = "/contest/operator/" + ($el.attr("contest-id")) + "/isVisible/" + (!visible);
              $.post(queryString, function(data) {
                if (data.result === "success") {
                  visible = !visible;
                  $el.attr("visible", visible);
                  return $el.empty().append("<i class=\"" + (visible ? "fa fa-eye" : "fa fa-eye-slash") + "\"></i>");
                }
              });
              return false;
            });
          }
        }
      });
    }
  };

  initProblemPage = function() {
    var $currentUser, $statusList, problemId, statusList,
      _this = this;
    if ($("#problem-show").length > 0) {
      $currentUser = $("#currentUser");
      this.userLogin = $currentUser.length !== 0 ? true : false;
      problemId = $("#problem-title").attr("value");
      if (this.userLogin) {
        $("#submit-code").click(function() {
          var info;
          info = {
            codeContent: $("#code-content").val(),
            languageId: $(":radio[name='language']:checked").attr('value'),
            contestId: null,
            problemId: problemId
          };
          jsonPost("/status/submit", info, function(data) {
            if (data.result === "error") {
              return alert(data.error_msg);
            } else if (data.result === "success") {
              return window.location.href = "/status/list";
            }
          });
          return false;
        });
      }
      $statusList = $("#cdoj-best-solutions");
      if ($statusList.length !== 0) {
        return statusList = new ListModule({
          listContainer: $statusList,
          requestUrl: "/status/search",
          condition: {
            "currentPage": null,
            "countPerPage": 10,
            "startId": void 0,
            "endId": void 0,
            "userName": void 0,
            "problemId": problemId,
            "languageId": void 0,
            "contestId": void 0,
            "result": "OJ_AC",
            "orderFields": "timeCost,memoryCost,Length,statusId",
            "orderAsc": "true,true,true,true"
          },
          formatter: function(data) {
            var currentUser, getCodeInfo, getCostInformation, getReturnType;
            getCostInformation = function(timeCost, memoryCost) {
              return "<td style=\"text-align: center;\">" + memoryCost + " KB</td>\n<td style=\"text-align: center;\">" + timeCost + " MS</td>";
            };
            currentUser = getCurrentUser();
            getReturnType = function(returnType, returnTypeId, statusId, userName) {
              if (returnTypeId === 7) {
                if (currentUser.userLogin && (currentUser.currentUserType === "1" || currentUser.currentUser === userName)) {
                  return "<a href=\"#\" value=\"" + statusId + "\" class=\"ce-link\">" + returnType + "</a>";
                } else {
                  return returnType;
                }
              } else {
                return returnType;
              }
            };
            getCodeInfo = function(length, language, statusId, userName) {
              if (currentUser.userLogin && (currentUser.currentUserType === "1" || currentUser.currentUser === userName)) {
                return "<a href=\"#\" value=\"" + statusId + "\" class=\"code-link\">" + length + " B</a>";
              } else {
                return "" + length + " B";
              }
            };
            return "<tr>\n  <td style=\"text-align: center;\">" + data.statusId + "</td>\n  <td style=\"text-align: center;\"><a href=\"/user/center/" + data.userName + "\" target=\"_blank\">" + data.userName + "</a></td>\n\n  " + (data.returnTypeId === 1 ? getCostInformation(data.timeCost, data.memoryCost) : "<td></td><td></td>") + "\n\n  <td style=\"text-align: center;\">" + data.language + "</td>\n\n  <td style=\"text-align: center;\">" + (getCodeInfo(data.length, data.language, data.statusId, data.userName)) + "</td>\n  <td style=\"text-align: center;\">" + (Date.create(data.time).format("{yyyy}-{MM}-{dd} {HH}:{mm}:{ss}")) + "</td>\n  <td></td>\n</tr>";
          },
          after: function() {
            var _this = this;
            return $(".code-link").click(function(e) {
              var $el, statusId;
              $el = $(e.currentTarget);
              statusId = $el.attr("value");
              $.post("/status/info/" + statusId, function(data) {
                var $modal, code;
                code = "";
                if (data.result === "success") {
                  code = data.code;
                } else {
                  code = data.error_msg;
                }
                code = code.trim().escapeHTML();
                console.log(code);
                $modal = $("#code-modal");
                $modal.find(".modal-body").empty().append("<pre>" + code + "</pre>");
                $modal.find(".modal-body").prettify();
                return $modal.modal("toggle");
              });
              return false;
            });
          }
        });
      }
    }
  };

  initUI = function() {
    var _this = this;
    $('.dropdown-menu').find('form').click(function(e) {
      return e.stopPropagation();
    });
    return $("img[type='avatar']").each(function(id, el) {
      var $el;
      $el = $(el);
      return $el.setAvatar({
        size: $el.width() ? $el.width() : void 0
      });
    });
  };

  initUserList = function() {
    var $userList, userList;
    $userList = $("#user-list");
    if ($userList.length !== 0) {
      return userList = new ListModule({
        listContainer: $userList,
        requestUrl: "/user/search",
        condition: {
          "currentPage": null,
          "startId": void 0,
          "endId": void 0,
          "userName": void 0,
          "type": void 0,
          "school": void 0,
          "departmentId": void 0,
          "orderFields": "solved,tried,id",
          "orderAsc": "false,false,true"
        },
        formatter: function(data) {
          var adminSpan;
          adminSpan = function() {
            var result;
            result = "";
            if (this.user.userLogin && this.user.currentUserType === AuthenticationType.ADMIN) {
              result += "<div class=\"btn-toolbar\" role=\"toolbar\" style=\"position: absolute; top: 12px; right: 30px;\">\n      <div class=\"btn-group\">\n        <button type=\"button\" class=\"btn btn-default btn-sm admin-profile-edit\" user-name=\"" + data.userName + "\">\n          <i class=\"fa fa-pencil\"></i>\n        </button>\n      </div>\n    </div>";
            }
            return result;
          };
          return "<div class=\"col-lg-6\">\n  <div class=\"panel panel-default\">\n    <div class=\"panel-body\">\n      <div class=\"media\">\n        <a class=\"pull-left\" href=\"#\">\n          <img id=\"cdoj-users-avatar\" email=\"" + data.email + "\"/>\n        </a>\n        <div class=\"media-body\">\n          <h4 class=\"media-heading\"><a href=\"/user/center/" + data.userName + "\" target=\"_blank\">" + data.nickName + " <small>" + data.userName + "</small></a></h4>\n          <span><i class=\"fa fa-map-marker\"></i>" + data.school + "</span>\n          <br/>\n          <span><a href=\"/status/list?userName=" + data.userName + "\" target=\"_blank\">" + data.solved + "/" + data.tried + "</a></span>\n          " + (adminSpan()) + "\n        </div>\n      </div>\n    </div>\n    <div class=\"panel-footer\" style=\"overflow: hidden;white-space: nowrap;text-overflow: ellipsis;\">Motto: " + data.motto + "</div>\n  </div>\n</div>";
        },
        after: function() {
          var $form, $loading, $modal,
            _this = this;
          $("img#cdoj-users-avatar").setAvatar({
            size: 64
          });
          $modal = $("#cdoj-admin-profile-edit-modal");
          $form = $("#cdoj-admin-profile-edit-form");
          $loading = $("#cdoj-admin-profile-edit-form-onloading");
          $(".admin-profile-edit").click(function(e) {
            var $el, userName;
            $el = $(e.currentTarget);
            $modal.modal();
            userName = $el.attr("user-name");
            $.post("/user/secretProfile/" + userName, function(data) {
              console.log(data);
              $form.setFormData(data.user);
              $form.css("display", "block");
              return $loading.css("display", "none");
            });
            $modal.on("hidden.bs.modal", function() {
              $form.css("display", "none");
              return $loading.css("display", "block");
            });
            return false;
          });
          return $("#cdoj-admin-profile-edit-button").click(function() {
            var info;
            info = $form.getFormData();
            if (info.newPassword === "") {
              delete info["newPassword"];
            }
            if (info.newPasswordRepeat === "") {
              delete info["newPasswordRepeat"];
            }
            jsonPost("/user/adminEdit", info, function(data) {
              return $form.formValidate({
                result: data,
                onSuccess: function() {
                  $modal.modal('hide');
                  return userList.triggerRefresh();
                }
              });
            });
            return false;
          });
        }
      });
    }
  };

  $ = jQuery;

  $(function() {
    initUI();
    initLayout();
    initUserList();
    initContestPage();
    initArticleEditor();
    return render();
  });

}).call(this);
