package cn.edu.uestc.acmicpc.util.dto;

import java.sql.Timestamp;

/**
 * Description
 */
public class RecentContestDTO {
  private Integer id;
  private String oj;
  private String link;
  private String name;
  private Timestamp start_time;
  private String week;
  private String access;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getOj() {
    return oj;
  }

  public void setOj(String oj) {
    this.oj = oj;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Timestamp getStart_time() {
    return start_time;
  }

  public void setStart_time(Timestamp start_time) {
    this.start_time = start_time;
  }

  public String getWeek() {
    return week;
  }

  public void setWeek(String week) {
    this.week = week;
  }

  public String getAccess() {
    return access;
  }

  public void setAccess(String access) {
    this.access = access;
  }
}
