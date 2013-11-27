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
        # format time style
        # $('.cdoj-time').formatTimeStyle();
    )
