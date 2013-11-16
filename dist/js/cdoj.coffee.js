(function() {
  var $, ListModule, SearchModule, avatar, initLayout, initProblemList, initProblemPage, initStatusList, initUser, initUserList, jsonPost, render;

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
          console.log(_this.pageInfo.find('a'));
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

  initUser = function() {
    var $currentUser, $userAvatar,
      _this = this;
    $currentUser = $("#currentUser");
    this.userLogin = $currentUser.length !== 0 ? true : false;
    if (this.userLogin) {
      this.currentUser = $currentUser[0].innerHTML.trim();
      this.currentUserType = $currentUser.attr("type");
      $userAvatar = $("#cdoj-user-avatar");
      $userAvatar.setAvatar({
        image: "http://www.acm.uestc.edu.cn/images/akari_small.jpg",
        size: $userAvatar.width() ? $userAvatar.width() : void 0
      });
    }
    if (this.userLogin === false) {
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
    if (this.userLogin) {
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
          console.log(data);
          return "<div class=\"col-md-12\">\n  <div class=\"panel panel-default\">\n    <div class=\"panel-body\">\n      <div class=\"media\">\n        <a class=\"pull-left\" href=\"#\">\n          <img />\n        </a>\n        <div class=\"media-body\" style=\"height: 70px;\">\n          <h4 class=\"media-heading\"><a href=\"/problem/show/" + data.problemId + "\">" + data.title + "</a></h4>\n          " + (data.source.trim() === '' ? "&nbsp;" : data.source) + "\n          <div>\n            " + (data.isSpj ? "<span class='label label-danger'>SPJ</span>" : "") + "\n            <span class=\"label label-default\">Tag</span>\n          </div>\n        </div>\n      </div>\n    </div>\n  </div>\n</div>";
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
    return render();
  });

}).call(this);
