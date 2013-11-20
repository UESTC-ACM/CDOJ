(function() {
  var $, Flandre, ListModule, SearchModule, avatar, getCurrentUser, initLayout, initProblemEditor, initProblemList, initProblemPage, initStatusList, initUser, initUserList, jsonPost, markdown, render;

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
      image: "http://www.acm.uestc.edu.cn/images/akari.jpg",
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

  markdown = function(content) {
    return marked(content);
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

  $.fn.markdown = function() {
    var _this = this;
    return this.each(function(id, el) {
      var $el, html, md;
      $el = $(el);
      md = $el.html().replace('<textarea>', '').replace('</textarea>', '').replace('<TEXTAREA>', '').replace('</TEXTAREA>', '').trim();
      html = $(marked(md));
      $el.empty().append(html);
      return $el.find("pre").unescapePre();
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

  $.fn.prePrettify = function() {
    var _this = this;
    return this.each(function(id, el) {
      var $el;
      $el = $(el);
      if ($el.attr("type") !== "no-prettify") {
        return $el.addClass("prettyprint linenums");
      }
    });
  };

  $.fn.prettify = function() {
    var _this = this;
    return this.find("pre").each(function(id, el) {
      var text;
      text = prettyPrintOne($(el)[0].innerText.escapeHTML());
      console.log(text);
      return $(el).empty().append(text);
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
    $("pre").prePrettify();
    return window.prettyPrint && prettyPrint();
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
      content = content.replace(/</g, '&lt;');
      content = content.replace(/>/g, '&gt;');
      content = content.replace(/\n/g, '<br>');
      content = content.replace(/<br>\s/g, '<br>&nbsp;');
      content = content.replace(/\s\s\s/g, '&nbsp; &nbsp;');
      content = content.replace(/\s\s/g, '&nbsp; ');
      content = content.replace(/^ /, '&nbsp;');
      return content;
    };

    Flandre.prototype.createTextarea = function() {
      var oldText, template;
      oldText = this.element[0].innerHTML;
      this.element.empty();
      template = "<div class=\"panel panel-default\">\n  <div class=\"panel-heading\" id=\"flandre-heading\">\n    <div class=\"btn-toolbar\" role=\"toolbar\">\n      <div class=\"btn-group\">\n        <button type=\"button\" class=\"btn btn-default btn-sm\" id=\"tool-preview\">Preview</button>\n      </div>\n      <div class=\"btn-group\">\n        <button type=\"button\" class=\"btn btn-default btn-sm\" id=\"tool-picture\"><i class=\"icon-picture\"></i></button>\n      </div>\n    </div>\n  </div>\n  <div class=\"tex2jax_ignore\" contenteditable=\"true\" id=\"flandre-editor\">" + (this.escape(oldText)) + "</div>\n  <div id=\"flandre-preview\"></div>\n</div>";
      return this.element.append(template);
    };

    Flandre.prototype.toolbar = function() {
      var editor, preview, toolPicture, toolPreview,
        _this = this;
      editor = this.element.find("#flandre-editor");
      preview = this.element.find("#flandre-preview");
      toolPreview = this.element.find("#tool-preview");
      toolPreview.click(function(e) {
        var $el, isActive, text;
        $el = $(e.currentTarget);
        isActive = $el.hasClass("active");
        editor.css("display", "none");
        preview.css("display", "none");
        if (isActive) {
          editor.css("display", "block");
        } else {
          text = editor[0].innerText.escapeHTML();
          preview.empty().append(text);
          preview.markdown();
          preview.prettify();
          preview.mathjax();
          preview.css("display", "block");
        }
        return $el.button("toggle");
      });
      return toolPicture = this.element.find("#tool-picture");
    };

    Flandre.prototype.getText = function() {
      return this.element.find("#flandre-editor")[0].innerText;
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
      var $advancedButton, $advancedResetButton, $advancedSearchButton, $conditionForm, $searchButton, $searchKeyword, initCondition,
        _this = this;
      this.father = father;
      this.search = this.father.searchGroup;
      initCondition = this.father.options.condition;
      $searchKeyword = this.search.find("#search-keyword");
      $advancedButton = this.search.find("#advanced");
      $searchButton = this.search.find("#search");
      $conditionForm = this.search.find("#condition");
      $advancedSearchButton = $conditionForm.find("#search-button");
      $advancedResetButton = $conditionForm.find("#reset-button");
      $searchButton.click(function() {
        var currentCondition;
        currentCondition = Object.merge(initCondition, {
          keyword: $searchKeyword.val()
        });
        _this.father.refresh(currentCondition);
        return false;
      });
      $advancedButton.click(function() {
        _this.toggle();
        return false;
      });
      $advancedSearchButton.click(function() {
        var currentCondition;
        currentCondition = Object.merge(initCondition, $conditionForm.getFormData());
        _this.father.refresh(currentCondition);
        return false;
      });
      $advancedResetButton.click(function() {
        $conditionForm.resetFormData();
        return false;
      });
    }

    SearchModule.prototype.toggle = function() {
      var $advancedForm, isOpen;
      $advancedForm = this.search.find("#condition");
      isOpen = $advancedForm.hasClass("open") ? true : false;
      if (isOpen) {
        return $advancedForm.removeClass("open");
      } else {
        return $advancedForm.addClass("open");
      }
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
      this.options = options;
      this.listContainer = this.options.listContainer;
      this.searchGroup = this.listContainer.find("#search-group");
      this.pageInfo = this.listContainer.find("#page-info");
      this.refreshLock = 0;
      this.refresh(this.options.condition);
      this.searchModule = new SearchModule(this);
    }

    ListModule.prototype.refresh = function(condition) {
      var _this = this;
      if (this.refreshLock === 0) {
        this.refreshLock = 1;
        this.list = this.listContainer.find("#list-container");
        this.list.empty();
        jsonPost(this.options.requestUrl, condition, function(datas) {
          _this.pageInfo.empty().append(datas.pageInfo);
          _this.pageInfo.find('a').click(function(e) {
            var $el;
            $el = $(e.currentTarget);
            if ($el.attr('href') === null) {
              return false;
            }
            condition.currentPage = $el.attr('href');
            _this.refresh(condition);
            return false;
          });
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
    var $cdojContainer, $cdojNavbar, $cdojNavbarMenu, $cdojUser, $mzry1992Container, $mzry1992Header;
    $cdojNavbar = $("#cdoj-navbar");
    $cdojContainer = $("#cdoj-container");
    $cdojUser = $cdojNavbar.find("#cdoj-user");
    $cdojNavbarMenu = $cdojNavbar.find("#cdoj-navbar-menu");
    $mzry1992Header = $cdojContainer.find("#mzry1992-header");
    $mzry1992Container = $cdojContainer.find("#mzry1992-container");
    if ($mzry1992Header.length !== 0 && $mzry1992Container.length !== 0) {
      return $mzry1992Container.css("margin-top", $mzry1992Header.outerHeight());
    }
  };

  getCurrentUser = function() {
    var $currentUser;
    $currentUser = $("#currentUser");
    this.userLogin = $currentUser.length !== 0 ? true : false;
    if (this.userLogin) {
      this.currentUser = $currentUser[0].innerHTML.trim();
      this.currentUserType = $currentUser.attr("type");
      return {
        userLogin: true,
        currentUser: this.currentUser,
        currentUserType: this.currentUserType
      };
    } else {
      return {
        userLogin: false
      };
    }
  };

  initUser = function() {
    var $userAvatar,
      _this = this;
    this.user = getCurrentUser();
    if (this.user.userLogin) {
      $userAvatar = $("#cdoj-user-avatar");
      $userAvatar.setAvatar({
        image: "http://www.acm.uestc.edu.cn/images/akari_small.jpg",
        size: $userAvatar.width() ? $userAvatar.width() : void 0
      });
    }
    if (this.user.userLogin === false) {
      $("#cdoj-login-button").click(function() {
        var $loginForm, info;
        $loginForm = $("#cdoj-login-form");
        info = $loginForm.getFormData();
        jsonPost("/user/login", info, function(data) {
          return $loginForm.formValidate({
            result: data,
            onSuccess: function() {
              return window.location.reload();
            }
          });
        });
        return false;
      });
      $("#cdoj-register-button").click(function() {
        var $registerForm, info;
        $registerForm = $("#cdoj-register-form");
        info = $registerForm.getFormData();
        jsonPost("/user/register", info, function(data) {
          return $registerForm.formValidate({
            result: data,
            onSuccess: function() {
              return window.location.reload();
            }
          });
        });
        return false;
      });
      $("#cdoj-activate-button").click(function() {
        var $activateForm, info;
        $activateForm = $("#cdoj-activate-form");
        info = $activateForm.getFormData();
        $.post("/user/sendSerialKey/" + info.userName, function(data) {
          if (data.result === "success") {
            alert("We send you an Email with the url to reset your password right now, please check your mail box.");
            return $("#cdoj-activate-modal").modal("hide");
          } else if (data.result === "failed") {
            return alert("Unknown error occurred.");
          } else {
            return alert(data.error_msg);
          }
        });
        return false;
      });
    }
    if (this.user.userLogin) {
      return $("#cdoj-logout-button").click(function() {
        $.post("/user/logout", function(data) {
          if (data.result === "success") {
            return window.location.reload();
          }
        });
        return false;
      });
    }
  };

  initProblemEditor = function() {
    var $editor, action, editors, editorsId,
      _this = this;
    $editor = $("#problem-editor");
    if ($editor.length > 0) {
      action = $("#problem-editor-title").attr("value");
      editorsId = ["description", "input", "output", "hint"];
      editors = [];
      editorsId.each(function(value, id) {
        return editors.push({
          id: value,
          editor: $("#" + value).flandre()
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
          info["problemId"] = action;
        }
        jsonPost("/problem/edit", info, function(data) {
          if (data.result === "success") {
            return window.location.reload();
          } else if (data.result === "field_error") {

          } else {
            return alert(data.error_msg);
          }
        });
        return false;
      });
    }
  };

  initProblemList = function() {
    var $problemList, problemList;
    $problemList = $("#problem-list");
    if ($problemList.length !== 0) {
      return problemList = new ListModule({
        listContainer: $problemList,
        requestUrl: "/problem/search",
        condition: {
          currentPage: null,
          startId: void 0,
          endId: void 0,
          title: void 0,
          source: void 0,
          isSpj: void 0,
          startDifficulty: void 0,
          endDifficulty: void 0,
          keyword: void 0,
          orderFields: void 0,
          orderAsc: void 0
        },
        formatter: function(data) {
          var adminSpan, difficulty, panelAC, panelDE, panelWA;
          panelAC = "panel panel-success";
          panelWA = "panel panel-danger";
          panelDE = "panel panel-default";
          this.user = getCurrentUser();
          adminSpan = function() {
            var result;
            result = "";
            if (this.user.userLogin && this.user.currentUserType === "1") {
              result += "<div class=\"btn-toolbar\" role=\"toolbar\">\n  <div class=\"btn-group\">\n    <button type=\"button\" class=\"btn btn-default btn-sm problem-visible-state-editor\" problem-id=\"" + data.problemId + "\" visible=\"" + data.isVisible + "\">\n      <i class=\"" + (data.isVisible ? "icon-eye-open" : "icon-eye-close") + "\"></i>\n    </button>\n    <button type=\"button\" class=\"btn btn-default btn-sm problem-editor\" problem-id=\"" + data.problemId + "\"><i class=\"icon-pencil\"></i></button>\n    <button type=\"button\" class=\"btn btn-default btn-sm problem-data-editor\" problem-id=\"" + data.problemId + "\"><i class=\"icon-cog\"></i></button>\n  </div>\n</div>";
            }
            return result;
          };
          difficulty = function(value) {
            var result, star, starEmpty,
              _this = this;
            star = "<i class='icon-star'></i>";
            starEmpty = "<i class='icon-star-empty'></i>";
            value = Math.max(1, Math.min(5, value));
            result = "";
            value.times(function() {
              return result += star;
            });
            (5 - value).times(function() {
              return result += starEmpty;
            });
            return result;
          };
          return "<div class=\"col-md-12\">\n  <div class=\"" + (data.status === 1 ? panelAC : data.status === 2 ? panelWA : panelDE) + "\">\n    <div class=\"panel-heading\">\n      <h3 class=\"panel-title\">\n        <a href=\"/problem/show/" + data.problemId + "\">" + data.title + "</a>\n        <span class='pull-right admin-span'>" + (adminSpan()) + "</span>\n        <span class='pull-right'>" + (difficulty(data.difficulty)) + "</span>\n      </h3>\n    </div>\n    <div class=\"panel-body\">\n      <span class=\"source\">\n        " + (data.source.trim() !== '' ? data.source : '') + "\n      </span>\n      " + (data.isSpj ? "<span class='label label-danger'>SPJ</span>" : '') + "\n    </div>\n  </div>\n</div>";
        },
        after: function() {
          var _this = this;
          this.user = getCurrentUser();
          if (this.user.userLogin && this.user.currentUserType === "1") {
            $(".problem-editor").click(function(e) {
              var $el;
              $el = $(e.currentTarget);
              window.location.href = "/problem/editor/" + ($el.attr("problem-id"));
              return false;
            });
            $(".problem-data-editor").click(function(e) {
              var $el;
              $el = $(e.currentTarget);
              window.location.href = "/problem/dataEditor/" + ($el.attr("problem-id"));
              return false;
            });
            return $(".problem-visible-state-editor").click(function(e) {
              var $el, queryString, visible;
              $el = $(e.currentTarget);
              visible = $el.attr("visible") === "true" ? true : false;
              queryString = "/problem/operator/" + ($el.attr("problem-id")) + "/isVisible/" + (!visible);
              $.post(queryString, function(data) {
                if (data.result === "success") {
                  visible = !visible;
                  $el.attr("visible", visible);
                  return $el.empty().append("<i class=\"" + (visible ? "icon-eye-open" : "icon-eye-close") + "\"></i>");
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
    var $currentUser, problemId,
      _this = this;
    if ($("#problem-show").length > 0) {
      $currentUser = $("#currentUser");
      this.userLogin = $currentUser.length !== 0 ? true : false;
      problemId = $("#problem-title").attr("value");
      if (this.userLogin) {
        return $("#submit-code").click(function() {
          var info;
          info = {
            'codeContent': $("#code-content").val(),
            'languageId': $(":radio[name='language']:checked").attr('value'),
            'contestId': null,
            'problemId': problemId
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
    }
  };

  initStatusList = function() {
    var $statusList, statusList;
    $statusList = $("#status-list");
    if ($statusList.length !== 0) {
      return statusList = new ListModule({
        listContainer: $statusList,
        requestUrl: "/status/search",
        condition: {
          "currentPage": null,
          "startId": void 0,
          "endId": void 0,
          "userName": void 0,
          "problemId": void 0,
          "languageId": void 0,
          "contestId": void 0,
          "result": void 0,
          "orderFields": "statusId",
          "orderAsc": "false"
        },
        formatter: function(data) {
          return "<div class=\"col-md-12\">\n  <div class=\"panel panel-default\">\n    <div class=\"panel-body\">\n      <div class=\"media\">\n        <div class=\"pull-left\">\n          <div class=\"status-sign\">\n            " + (data.returnTypeId === 16 ? "<i class='icon-spinner icon-spin'></i>" : "") + "\n          </div>\n        </div>\n        <div class=\"pull-right\">\n          #" + data.statusId + "\n        </div>\n        <div class=\"media-body\">\n          <h4 class=\"media-heading\">" + data.returnType + "</h4>\n          " + data.userName + " <span class=\"muted\">submitted at</span> " + (Date.create(data.time)) + "\n        </div>\n      </div>\n    </div>\n  </div>\n</div>";
        }
      });
    }
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
          console.log(data);
          return "<div class=\"col-lg-6\">\n  <div class=\"panel panel-default\">\n    <div class=\"panel-body\">\n      <div class=\"media\">\n        <a class=\"pull-left\" href=\"#\">\n          <img id=\"cdoj-users-avatar\" email=\"" + data.email + "\"/>\n        </a>\n        <div class=\"media-body\">\n          <h4 class=\"media-heading\"><a href=\"/user/center/" + data.userName + "\">" + data.nickName + " <small>" + data.userName + "</small></a></h4>\n          <i class=\"icon-map-marker\"></i>" + data.school + "\n        </div>\n      </div>\n    </div>\n    <div class=\"panel-footer\" style=\"overflow: hidden;white-space: nowrap;text-overflow: ellipsis;\">Motto: " + data.motto + "</div>\n  </div>\n</div>";
        },
        after: function() {
          return $("img#cdoj-users-avatar").setAvatar({
            size: 64,
            image: "http://www.acm.uestc.edu.cn/images/akari_small.jpg"
          });
        }
      });
    }
  };

  $ = jQuery;

  $(function() {
    initLayout();
    initUser();
    initProblemList();
    initStatusList();
    initUserList();
    initProblemPage();
    initProblemEditor();
    return render();
  });

}).call(this);
