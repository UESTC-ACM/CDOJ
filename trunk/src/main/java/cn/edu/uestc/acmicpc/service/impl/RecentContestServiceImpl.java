package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.service.iface.RecentContestService;
import cn.edu.uestc.acmicpc.util.dto.RecentContestDTO;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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

  private List<RecentContestDTO> recentContestList = new LinkedList<>();


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

  /**
   * Crawling from contests.acmicpc.info/contests.json
   */
  private void crawling() {
    try {
      URL url = new URL("http://contests.acmicpc.info/contests.json");

      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setConnectTimeout(10000);
      connection.setDoInput(true);
      connection.setRequestMethod("GET");
      int respCode = connection.getResponseCode();
      // If result is OK
      if (respCode == 200) {
        // Get input stream
        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
          out.write(buffer, 0, len);
        }
        // Get jsonString
        String jsonStr = new String(out.toByteArray());

        recentContestList = JSON.parseArray(jsonStr, RecentContestDTO.class);
      }
    } catch (IOException ignored) {
      // Just ignore this exception and do not change the content.
    }
  }

  @Override
  public List<RecentContestDTO> getRecentContestList() {
    return recentContestList;
  }
}
