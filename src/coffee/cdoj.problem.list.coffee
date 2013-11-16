# Move most common functions in a base list module
initProblemList = ->
  $problemList = $("#problem-list")
  if $problemList.length != 0
    problemList = new ListModule(
      listContainer: $problemList
      requestUrl: "/problem/search"
      condition:
        currentPage: null,
        startId: undefined,
        endId: undefined,
        title: undefined,
        source: undefined,
        isSpj: undefined,
        startDifficulty: undefined,
        endDifficulty: undefined,
        keyword: undefined,
        orderFields: undefined,
        orderAsc: undefined
      formatter: (data) ->
        console.log(data);
        # TODO Fix the height! We now simply use a constant to set the height, it's danger!
        """
          <div class="col-md-12">
            <div class="panel panel-default">
              <div class="panel-body">
                <div class="media">
                  <a class="pull-left" href="#">
                    <img />
                  </a>
                  <div class="media-body" style="height: 70px;">
                    <h4 class="media-heading"><a href="/problem/show/#{data.problemId}">#{data.title}</a></h4>
                    #{if data.source.trim() == '' then "&nbsp;" else data.source}
                    <div>
                      #{if data.isSpj then "<span class='label label-danger'>SPJ</span>" else ""}
                      <span class="label label-default">Tag</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        """
    )
