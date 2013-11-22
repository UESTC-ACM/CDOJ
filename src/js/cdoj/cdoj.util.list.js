var ListModule = function(element) {
  var $list = $(element);
  var $searchForm = $list.find('#search-group');
  var $conditionForm = $searchForm.find('#condition');

  $.each($list.find('.orderButton'), function(){
    var field = $(this).attr('field');
    $(this).setButton({
      callback: function(){
        this.changeOrder(field);
      }
    });
  });


  this.refreshList();
};

ListModule.prototype.refreshList = function() {};
ListModule.prototype.currentCondition = {
  "currentPage": null,
  "keyword": undefined,
  "orderFields": undefined,
  "orderAsc": undefined
};
ListModule.prototype.changeOrder = function(field) {
  if (this.currentCondition.orderFields == field)
    this.currentCondition.orderAsc = (this.currentCondition.orderAsc === "true" ? "false" : "true");
  else {
    this.currentCondition.orderFields = field;
    this.currentCondition.orderAsc = "false";
  }
  this.refreshList();
};
ListModule.prototype.toggleSearchMenu = function(e) {
  var $this = $(this);
  var isActive = $this.hasClass('open');

  $this.focus();
};

/*
  function trigger() {
    if ($conditionForm.hasClass('open')) {
      $conditionForm.removeClass('open');

    } else {
      $conditionForm.addClass('open');
    }
  }

  $(document).click(function() {
    if ($conditionForm.hasClass('open') && !$conditionForm.hasClass('hover')) {
      trigger();
      return false;
    }
  });

  $searchForm.find('#advanced').setButton({
    callback: function() {
      if (!$conditionForm.hasClass('open')) {
        trigger();
      }
    }
  });

  $conditionForm.find('#search-button').setButton({
    callback: function () {
      currentCondition = $conditionForm.getFormData();
      currentCondition.currentPage = 1;
      refreshList(currentCondition);
      trigger();
    }
  });

  $conditionForm.find('#reset-button').setButton({
    callback: function () {
      $conditionForm.resetFormData();
    }
  });

  $searchForm.find('#search').setButton({
    callback: function () {
      currentCondition = {
        "currentPage": 1,
        "keyword": $searchForm.find('#search-keyword').val()
      };
      refreshList(currentCondition);
    }
  });

  this.refreshList = refreshList;
  return this;
};
*/