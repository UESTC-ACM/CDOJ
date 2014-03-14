package cn.edu.uestc.acmicpc.db.dto.impl.message;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;

@Fields({"messageId", "senderId", "userBySenderId.email", "userBySenderId.userName"
    , "receiverId", "userByReceiverId.email", "userByReceiverId.userName", "title", "time",
    "isOpened"})
public class MessageForUserDTO implements BaseDTO<Message> {

  private Integer messageId;
  private Integer senderId;
  private String senderEmail;
  private String senderUserName;
  private Integer receiverId;
  private String receiverEmail;
  private String receiverUserName;
  private String title;
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

  public Integer getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(Integer receiverId) {
    this.receiverId = receiverId;
  }

  public String getReceiverEmail() {
    return receiverEmail;
  }

  public void setReceiverEmail(String receiverEmail) {
    this.receiverEmail = receiverEmail;
  }

  public String getReceiverUserName() {
    return receiverUserName;
  }

  public void setReceiverUserName(String receiverUserName) {
    this.receiverUserName = receiverUserName;
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

  public MessageForUserDTO(Integer messageId, Integer senderId, String senderEmail,
                           String senderUserName, Integer receiverId, String receiverEmail,
                           String receiverUserName, String title, Timestamp time,
                           Boolean isOpened) {

    this.messageId = messageId;
    this.senderId = senderId;
    this.senderEmail = senderEmail;
    this.senderUserName = senderUserName;
    this.receiverId = receiverId;
    this.receiverEmail = receiverEmail;
    this.receiverUserName = receiverUserName;
    this.title = title;
    this.time = time;
    this.isOpened = isOpened;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<MessageForUserDTO> {

    private Builder() {
    }

    @Override
    public MessageForUserDTO build() {
      return new MessageForUserDTO(messageId, senderId, senderEmail, senderUserName, receiverId,
          receiverEmail, receiverUserName, title, time, isOpened);
    }

    @Override
    public MessageForUserDTO build(Map<String, Object> properties) {
      messageId = (Integer) properties.get("messageId");
      senderId = (Integer) properties.get("senderId");
      senderEmail = (String) properties.get("userBySenderId.email");
      senderUserName = (String) properties.get("userBySenderId.userName");
      receiverId = (Integer) properties.get("receiverId");
      receiverEmail = (String) properties.get("userByReceiverId.email");
      receiverUserName = (String) properties.get("userByReceiverId.userName");
      title = (String) properties.get("title");
      time = (Timestamp) properties.get("time");
      isOpened = (Boolean) properties.get("isOpened");
      return build();
    }

    private Integer messageId;
    private Integer senderId;
    private String senderEmail;
    private String senderUserName;
    private Integer receiverId;
    private String receiverEmail;
    private String receiverUserName;
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

    public Builder setReceiverId(Integer receiverId) {
      this.receiverId = receiverId;
      return this;
    }

    public Builder setReceiverEmail(String receiverEmail) {
      this.receiverEmail = receiverEmail;
      return this;
    }

    public Builder setReceiverUserName(String receiverUserName) {
      this.receiverUserName = receiverUserName;
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
