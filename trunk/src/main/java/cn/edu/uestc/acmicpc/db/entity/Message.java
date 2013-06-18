/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Message information.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
@Table(name = "message", schema = "", catalog = "uestcoj")
@Entity
@KeyField("messageId")
public class Message implements Serializable {
    private static final long serialVersionUID = -5394211914105594037L;
    private Integer messageId;

    private Integer version;

    @Version
    @Column(name = "OPTLOCK")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "messageId", nullable = false, insertable = true,
            updatable = true, length = 10, precision = 0, unique = true)
    @Id
    @GeneratedValue
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    private String title;

    @Column(name = "title", nullable = false, insertable = true, updatable = true,
            length = 50, precision = 0)
    @Basic
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String content;

    @Column(name = "content", nullable = false, insertable = true, updatable = true,
            length = 65535, precision = 0)
    @Basic
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private Timestamp time;

    @Column(name = "time", nullable = false, insertable = true, updatable = true,
            length = 19, precision = 0)
    @Basic
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    private Boolean isOpened;

    @Column(name = "isOpened", nullable = false, insertable = true, updatable = true,
            length = 0, precision = 0)
    @Basic
    public Boolean getIsOpened() {
        return isOpened;
    }

    public void setIsOpened(Boolean opened) {
        isOpened = opened;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (isOpened != message.isOpened) return false;
        if (!messageId.equals(message.messageId)) return false;
        if (content != null ? !content.equals(message.content) : message.content != null) return false;
        if (time != null ? !time.equals(message.time) : message.time != null) return false;
        if (title != null ? !title.equals(message.title) : message.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (isOpened ? 1 : 0);
        return result;
    }

    private User userByReceiverId;

    @ManyToOne
    @JoinColumn(name = "receiverId", referencedColumnName = "userId", nullable = false)
    public User getUserByReceiverId() {
        return userByReceiverId;
    }

    public void setUserByReceiverId(User userByReceiverId) {
        this.userByReceiverId = userByReceiverId;
    }

    private User userBySenderId;

    @ManyToOne
    @JoinColumn(name = "senderId", referencedColumnName = "userId", nullable = false)
    public User getUserBySenderId() {
        return userBySenderId;
    }

    public void setUserBySenderId(User userBySenderId) {
        this.userBySenderId = userBySenderId;
    }
}
