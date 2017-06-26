package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.service.iface.RecentContestService;
import cn.edu.uestc.acmicpc.util.dto.RecentContestDto;
import com.alibaba.fastjson.JSON;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Description
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Lazy(false)
public class RecentContestServiceImpl extends AbstractService implements RecentContestService {

  /**
   * Crawling interval, every 3 hours.
   */
  private final long INTERVAL = 3L;

  private List<RecentContestDto> recentContestList = new LinkedList<>();

  @PostConstruct
  private void init() {
    Timer timer = new Timer(true);
    timer.schedule(new TimerTask() {

      @Override
      public void run() {
        crawling();
      }
    }, 0, INTERVAL * 1000L * 60L * 60L);
  }

  private final String REQUEST_URL = "http://contests.acmicpc.info/contests.json";

  /**
   * Crawling from REQUEST_URL
   */
  private void crawling() {
    try {
      URL url = new URL(REQUEST_URL);

      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      // The wait time limit is 10 seconds
      connection.setConnectTimeout(10000);
      // Set request type
      connection.setDoInput(true);
      connection.setRequestMethod("GET");
      int respCode = connection.getResponseCode();
      // If result is OK
      if (respCode == 200) {
        // Get input stream
        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Temporary buffer store
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
          out.write(buffer, 0, length);
        }
        // Get json string
        String jsonStr = new String(out.toByteArray());

        // Parse recent contest list from json string
        recentContestList = JSON.parseArray(jsonStr, RecentContestDto.class);
      }
    } catch (IOException ignored) {
      // Just ignore this exception and do not change the content.
    }
  }

  @Override
  public List<RecentContestDto> getRecentContestList() {
    return recentContestList;
  }
}
