package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import java.sql.Timestamp;

/**
 * Contest entity data transform object.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 3
 */
@SuppressWarnings("UnusedDeclaration")
public class ContestDTO extends BaseDTO<Contest> {
    private Integer contestId;
    private String title;
    private String description;
    private Byte type;
    private Timestamp time;
    private Integer length;
    private Boolean isVisible;

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public ContestDTO() {
        super();
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean visible) {
        isVisible = visible;
    }

    @Override
    protected Class<Contest> getReferenceClass() {
        return Contest.class;
    }
}
