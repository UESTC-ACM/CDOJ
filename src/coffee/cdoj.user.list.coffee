# Move most common functions in a base list module
initUserList = ->
  $userList = $("#user-list")
  if $userList.length != 0
    userList = new ListModule(
      listContainer: $userList
      requestUrl: "/user/search"
      condition:
        "currentPage": null,
        "startId": undefined,
        "endId": undefined,
        "userName": undefined,
        "type": undefined,
        "school": undefined,
        "departmentId": undefined,
        "orderFields": "solved,tried,id",
        "orderAsc": "false,false,true"
      formatter: (data) ->
        adminSpan = ->
          result = ""
          if @user.userLogin && @user.currentUserType == AuthenticationType.ADMIN
            # admin
            result += """
                  <div class="btn-toolbar" role="toolbar" style="position: absolute; top: 12px; right: 30px;">
                        <div class="btn-group">
                          <button type="button" class="btn btn-default btn-sm admin-profile-edit" user-name="#{data.userName}">
                            <i class="fa fa-pencil"></i>
                          </button>
                        </div>
                      </div>
                        """
          return result
        """
          <div class="col-lg-6">
            <div class="panel panel-default">
              <div class="panel-body">
                <div class="media">
                  <a class="pull-left" href="#">
                    <img id="cdoj-users-avatar" email="#{data.email}"/>
                  </a>
                  <div class="media-body">
                    <h4 class="media-heading"><a href="/user/center/#{data.userName}">#{data.nickName} <small>#{data.userName}</small></a></h4>
                    <span><i class="fa fa-map-marker"></i>#{data.school}</span>
                    <br/>
                    <span><a href="/status/list?userName=#{data.userName}">#{data.solved}/#{data.tried}</a></span>
                    #{adminSpan()}
                  </div>
                </div>
              </div>
              <div class="panel-footer" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">Motto: #{data.motto}</div>
            </div>
          </div>
        """
      after: ->
        # get userList avatars
        $("img#cdoj-users-avatar").setAvatar(
          size: 64,
          image: "http://www.acm.uestc.edu.cn/images/akari_small.jpg"
        )
        $modal = $("#cdoj-admin-profile-edit-modal")
        $form = $("#cdoj-admin-profile-edit-form")
        $loading = $("#cdoj-admin-profile-edit-form-onloading")
        # admin editor
        $(".admin-profile-edit").click (e) =>
          $el = $(e.currentTarget)
          $modal.modal()
          userName = $el.attr("user-name")
          $.post("/user/secretProfile/#{userName}"
            (data) =>
              console.log data
              $form.setFormData(data.user)
              $form.css("display", "block")
              $loading.css("display", "none")
          )
          $modal.on("hidden.bs.modal"
            ->
              $form.css("display", "none")
              $loading.css("display", "block")
          )
          return false

        $("#cdoj-admin-profile-edit-button").click =>
          info = $form.getFormData()
          delete info["newPassword"] if info.newPassword == ""
          delete info["newPasswordRepeat"] if info.newPasswordRepeat == ""
          jsonPost("/user/adminEdit"
            info
            (data) =>
              $form.formValidate
                result: data,
                onSuccess: ->
                  $modal.modal()
                  userList.triggerRefresh()
          )
          return false
    )
