# Move most common functions in a base list module
initStatusList = ->
  $statusList = $("#status-list")
  if $statusList.length != 0
    statusList = new ListModule(
      listContainer: $statusList
      requestUrl: "/status/search"
      condition:
        "currentPage": null,
        "startId": undefined,
        "endId": undefined,
        "userName": undefined,
        "problemId": undefined,
        "languageId": undefined,
        "contestId": undefined,
        "result": undefined,
        "orderFields": "statusId",
        "orderAsc": "false"
      formatter: (data) ->
        console.log(data)
        """
          <div class="col-md-12">
            <div class="panel panel-default">
              <div class="panel-body">
                <div class="media">
                  <a class="pull-left" href="#">
                    <img />
                  </a>
                  <div class="media-body">
                    <h4 class="media-heading">#{data.returnType}</h4>
                    #{data.userName} | We can use media image to show the state... | TODO
                  </div>
                </div>
              </div>
            </div>
          </div>
        """
    )
