package cn.edu.uestc.acmicpc.training.action.index;

import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class IndexAction extends BaseAction {

    public String toIndex() {
        return SUCCESS;
    }
}
