package cn.edu.uestc.acmicpc.training.action.team;

import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class TeamAction extends BaseAction {

    public String toTeamInfo() {
        return SUCCESS;
    }
}
