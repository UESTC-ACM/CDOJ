package cn.edu.uestc.acmicpc.db.dto.impl.message;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;

@Fields({"messageId", "senderId", "receiverId", "title", "content", "time", "isOpened"})
public class MessageDTO implements BaseDTO<Message> {

  public MessageDTO() {
  }

  private MessageDTO(Integer messageId, Integer senderId, Integer receiverId, String title,
                     String content, Timestamp time, Boolean isOpened) {
    this.messageId = messageId;
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.title = title;
    this.content = content;
    this.time = time;
    this.isOpened = isOpened;
  }

  private Integer messageId;
  private Integer senderId;
  private Integer receiverId;
  private String title;
  private String content;
  private Timestamp time;
  private Boolean isOpened;

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

  public Integer getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(Integer receiverId) {
    this.receiverId = receiverId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    MessageDTO that = (MessageDTO) o;

    if (content != null ? !content.equals(that.content) : that.content != null) {
      return false;
    }
    if (isOpened != null ? !isOpened.equals(that.isOpened) : that.isOpened != null) {
      return false;
    }
    if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) {
      return false;
    }
    if (receiverId != null ? !receiverId.equals(that.receiverId) : that.receiverId != null) {
      return false;
    }
    if (senderId != null ? !senderId.equals(that.senderId) : that.senderId != null) {
      return false;
    }
    if (time != null ? !time.equals(that.time) : that.time != null) {
      return false;
    }
    if (title != null ? !title.equals(that.title) : that.title != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = messageId != null ? messageId.hashCode() : 0;
    result = 31 * result + (senderId != null ? senderId.hashCode() : 0);
    result = 31 * result + (receiverId != null ? receiverId.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (content != null ? content.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (isOpened != null ? isOpened.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<MessageDTO> {

    private Builder() {
    }

    @Override
    public MessageDTO build() {
      return new MessageDTO(messageId, senderId, receiverId, title, content, time, isOpened);
    }

    @Override
    public MessageDTO build(Map<String, Object> properties) {
      messageId = (Integer) properties.get("messageId");
      senderId = (Integer) properties.get("senderId");
      receiverId = (Integer) properties.get("receiverId");
      title = (String) properties.get("title");
      content = (String) properties.get("content");
      time = (Timestamp) properties.get("time");
      isOpened = (Boolean) properties.get("isOpened");
      return build();

    }

    private Integer messageId;
    private Integer senderId;
    private Integer receiverId;
    private String title;
    private String content;
    private Timestamp time;
    private Boolean isOpened;

    public Integer getMessageId() {
      return messageId;
    }

    public Builder setMessageId(Integer messageId) {
      this.messageId = messageId;
      return this;
    }

    public Integer getSenderId() {
      return senderId;
    }

    public Builder setSenderId(Integer senderId) {
      this.senderId = senderId;
      return this;
    }

    public Integer getReceiverId() {
      return receiverId;
    }

    public Builder setReceiverId(Integer receiverId) {
      this.receiverId = receiverId;
      return this;
    }

    public String getTitle() {
      return title;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public String getContent() {
      return content;
    }

    public Builder setContent(String content) {
      this.content = content;
      return this;
    }

    public Timestamp getTime() {
      return time;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public Boolean getIsOpened() {
      return isOpened;
    }

    public Builder setIsOpened(Boolean isOpened) {
      this.isOpened = isOpened;
      return this;
    }
  }
}
