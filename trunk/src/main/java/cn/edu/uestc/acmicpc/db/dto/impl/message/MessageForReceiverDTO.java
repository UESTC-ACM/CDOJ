package cn.edu.uestc.acmicpc.db.dto.impl.message;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;

@Fields({ "messageId", "senderId", "userBySenderId.email", "userBySenderId.userName"
    , "title", "time", "isOpened" })
public class MessageForReceiverDTO implements BaseDTO<Message> {

  public MessageForReceiverDTO() {
  }

  public MessageForReceiverDTO(Integer messageId, Integer senderId, String senderEmail,
                               String senderUserName, String title, Timestamp time,
                               Boolean isOpened) {

    this.messageId = messageId;
    this.senderId = senderId;
    this.senderEmail = senderEmail;
    this.senderUserName = senderUserName;
    this.title = title;
    this.time = time;
    this.isOpened = isOpened;
  }

  public Integer getMessageId() {
    return messageId;
  }

  public void setMessageId(Integer messageId) {
    this.messageId = messageId;
  }

  public Integer getSenderId() {
    return senderId;
  }

  public void setSenderId(Integer senderId) {
    this.senderId = senderId;
  }

  public String getSenderEmail() {
    return senderEmail;
  }

  public void setSenderEmail(String senderEmail) {
    this.senderEmail = senderEmail;
  }

  public String getSenderUserName() {
    return senderUserName;
  }

  public void setSenderUserName(String senderUserName) {
    this.senderUserName = senderUserName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public Boolean getIsOpened() {
    return isOpened;
  }

  public void setIsOpened(Boolean isOpened) {
    this.isOpened = isOpened;
  }

  private Integer messageId;
  private Integer senderId;
  private String senderEmail;
  private String senderUserName;
  private String title;
  private Timestamp time;
  private Boolean isOpened;

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<MessageForReceiverDTO> {

    private Builder() {
    }

    @Override
    public MessageForReceiverDTO build() {
      return new MessageForReceiverDTO(messageId, senderId, senderEmail, senderUserName, title,
          time, isOpened);
    }

    @Override
    public MessageForReceiverDTO build(Map<String, Object> properties) {
      messageId = (Integer) properties.get("messageId");
      senderId = (Integer) properties.get("senderId");
      senderEmail = (String) properties.get("userBySenderId.email");
      senderUserName = (String) properties.get("userBySenderId.userName");
      title = (String) properties.get("title");
      time = (Timestamp) properties.get("time");
      isOpened = (Boolean) properties.get("isOpened");
      return build();
    }

    private Integer messageId;
    private Integer senderId;
    private String senderEmail;
    private String senderUserName;
    private String title;
    private Timestamp time;
    private Boolean isOpened;

    public Builder setMessageId(Integer messageId) {
      this.messageId = messageId;
      return this;
    }

    public Builder setSenderId(Integer senderId) {
      this.senderId = senderId;
      return this;
    }

    public Builder setSenderEmail(String senderEmail) {
      this.senderEmail = senderEmail;
      return this;
    }

    public Builder setSenderUserName(String senderUserName) {
      this.senderUserName = senderUserName;
      return this;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public Builder setIsOpened(Boolean isOpened) {
      this.isOpened = isOpened;
      return this;
    }
  }
}
