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
        """
					<div class="media">
						<a class="pull-left" href="#">
							<img class="media-object" data-src="holder.js/64x64"
									alt="64x64" style="width: 64px; height: 64px;"
									src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAADI0lEQVR4Ae2aS0/bQBSFD3m6eSgEQUihqKAApTRA1cICCZXfzgaVqoAoaRqgUerwUDAhjxocFzu0Ywcah1kxEQs011LimbF9Zu653x1vPFCr1f5C4sMncexu6GQAESC5A1QCkgMAIoAIkNwBKgHJAaBNkEqASkByB6gEJAeA3gJUAlQCkjtAJSA5APQWoBKQvQQC/Rhw0zjBXq6IK/MWvkgSMwvzGIuHPJImjrZ3cN4CXs4vIzPkvea5zdN8Ck2PPNcULwGzgi9fC2iaFsKRICyjivzWLhq33Tmuy9+h1g2YpoGW7bnQvaW39RSavTNwPWECGscqTCaXnF7Fx8kotF9HuGwxP504HVvtKvYO6w8mtFH+to2TqzYio3N4n4kxQnZx0WpjZPYDRpoimg+meGRXmADbbrtTXZ3nsLmxgUPGefL1FAbvLD3L7cNAEtnshGdJAYy9GoJhGKiWCvhxsM8I0WFYMYylFIhpeuQFmoIG3ODi0nCns3QdFku7qWvIbX6GdsOSXztCvtpGKruAdLQzhd/XcSYwNIPF8Qh71sDpsUOIH9PLWUQhrikQ9/9HBA0IIRELuiLpxU9YX1/HdMLP+ga0mo5SQXWv+UwNRbXmtutnJdSMzj6QejuHuDvK/hIZTMadZfSneS/32LOgAWy5YSdgIBxyMutD7M4Qp/5ty72Eys8CShXd7RiaivJvhgc7GsUiOqOs0yzjpANTX5qusMCf8CYYSw4CpxWouV1gNIyzU2dLDGJ4MI7h1TVMuZthAFY1j628htTcMrJpBbguY6/UdO9NMGqaTROFnQOk1t5AWFMg8PtHhAlQ0vNYnGAgm3WoaoXtA0Bqdglp9qoPhBQoCvsxOl6EOqUSDEUYHDaKuUP33sj4O6ysLCHhrMQ8xn5Zh5jmfShi54F+vxGyjWv8YV8Z+cNRKMI89S7+KTR7Z+j2+jagK/U8W8Il8DzD5VdNBvCeyDVCBMiVbz5aIoD3RK4RIkCufPPREgG8J3KNEAFy5ZuPlgjgPZFrhAiQK998tEQA74lcI0SAXPnmoyUCeE/kGiEC5Mo3H630BPwDzaF0R430+Y4AAAAASUVORK5CYII="/>
						</a>
						<div class="media-body">
							<h4 class="media-heading">#{data.title}</h4>
							#{data.source}
						</div>
					</div>
        """
    )
