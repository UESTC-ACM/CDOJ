package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
public class CodeDTO extends BaseDTO<Code> {
    private Integer codeId;
    private String content;

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected Class<Code> getReferenceClass() {
        return Code.class;
    }

    @Override
    public Code getEntity() throws AppException {
        return super.getEntity();
    }

    @Override
    public void updateEntity(Code code) {
        super.updateEntity(code);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
