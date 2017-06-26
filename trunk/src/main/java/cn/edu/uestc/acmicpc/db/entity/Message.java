package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Message information.
 */
@Table(name = "message")
@Entity
@KeyField("messageId")
public class Message implements Serializable {

  private static final long serialVersionUID = -5394211914105594037L;
  private Integer messageId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "messageId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getMessageId() {
    return messageId;
  }

  public void setMessageId(Integer messageId) {
    this.messageId = messageId;
  }

  private String title = "";

  @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private String content;

  @Column(name = "content", nullable = false, insertable = true, updatable = true, length = 65535,
      precision = 0)
  @Basic
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  private Timestamp time;

  @Column(name = "time", nullable = false, insertable = true, updatable = true, length = 19,
      precision = 0)
  @Basic
  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  private Boolean isOpened = false;

  @Column(name = "isOpened", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getIsOpened() {
    return isOpened;
  }

  public void setIsOpened(Boolean opened) {
    isOpened = opened;
  }

  private Integer receiverId;

  @Column(name = "receiverId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(Integer receiverId) {
    this.receiverId = receiverId;
  }

  private Integer senderId;

  @Column(name = "senderId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getSenderId() {
    return senderId;
  }

  public void setSenderId(Integer senderId) {
    this.senderId = senderId;
  }

  private User userByReceiverId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "receiverId", referencedColumnName = "userId", nullable = false,
      insertable = false, updatable = false)
  public User getUserByReceiverId() {
    return userByReceiverId;
  }

  public void setUserByReceiverId(User userByReceiverId) {
    this.userByReceiverId = userByReceiverId;
  }

  private User userBySenderId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "senderId", referencedColumnName = "userId", nullable = false,
      insertable = false, updatable = false)
  public User getUserBySenderId() {
    return userBySenderId;
  }

  public void setUserBySenderId(User userBySenderId) {
    this.userBySenderId = userBySenderId;
  }
}
