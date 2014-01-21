# Get emotions
emotionsPerRow = 8
formatEmotionId = (id) ->
  if id > 9
    return "" + id
  else
    return "0" + id
getEmotionUrl = (url, id, extension) ->
  return "#{url}/#{formatEmotionId(id + 1)}.#{extension}"

emotionTable = (url, extension, count) ->
  tableContent = ""
  count.times((i) ->
    if i % emotionsPerRow == 0
      if i > 0
        tableContent += "</tr>"
      tableContent += "<tr>"
    tableContent += """
      <td value="![#{i + 1}](#{getEmotionUrl(url, i, extension)})">
        <span><img src="#{getEmotionUrl(url, i, extension)}" class="img-rounded" style="width: 50px; height: 50px"/></span>
      </td>
      """
  )
  tableContent += "</tr>"

  template = """
    <table class="table table-bordered table-condensed">
      <tbody>
        #{tableContent}
      </tbody>
    </table>
    """
  return template