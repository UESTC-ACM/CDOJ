package cn.edu.uestc.acmicpc.db.dto.impl.message;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

@Fields({ "messageId", "senderId", "userBySenderId.email", "userBySenderId.userName"
    , "title", "time", "isOpened" })
public class MessageForReceiverDto implements BaseDto<Message> {

  public MessageForReceiverDto() {
  }

  public MessageForReceiverDto(Integer messageId, Integer senderId, String senderEmail,
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MessageForReceiverDto)) {
      return false;
    }

    MessageForReceiverDto that = (MessageForReceiverDto) o;
    return Objects.equals(this.isOpened, that.isOpened)
        && Objects.equals(this.messageId, that.messageId)
        && Objects.equals(this.senderEmail, that.senderEmail)
        && Objects.equals(this.senderId, that.senderId)
        && Objects.equals(this.senderUserName, that.senderUserName)
        && Objects.equals(this.time, that.time)
        && Objects.equals(this.title, that.title);
  }

  @Override
  public int hashCode() {
    int result = messageId != null ? messageId.hashCode() : 0;
    result = 31 * result + (senderId != null ? senderId.hashCode() : 0);
    result = 31 * result + (senderEmail != null ? senderEmail.hashCode() : 0);
    result = 31 * result + (senderUserName != null ? senderUserName.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (isOpened != null ? isOpened.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<MessageForReceiverDto> {

    private Builder() {
    }

    @Override
    public MessageForReceiverDto build() {
      return new MessageForReceiverDto(messageId, senderId, senderEmail, senderUserName, title,
          time, isOpened);
    }

    @Override
    public MessageForReceiverDto build(Map<String, Object> properties) {
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
