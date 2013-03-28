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

package cn.edu.uestc.acmicpc.service.entity;

import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.ioc.util.SettingsAware;
import cn.edu.uestc.acmicpc.util.FileUtil;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.Settings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

/**
 * Problem judge component.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class Judge implements Runnable, SettingsAware {
    public void setJudgeName(String judgeName) {
        this.judgeName = judgeName;
    }

    public void setWorkPath(String workPath) {
        this.workPath = workPath;
    }

    /**
     * Judge's name.
     */
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
    private String judgeName;
    /**
     * Judge's work path.
     */
    private String workPath;

    /**
     * Global setting entity.
     */
    private Settings settings;

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    /**
     * Temp files path.
     */
    private String tempPath;

    @SuppressWarnings("SameParameterValue")
    public void setJudgeQueue(BlockingQueue<JudgeItem> judgeQueue) {
        this.judgeQueue = judgeQueue;
    }

    /**
     * Global judge queue.
     */
    private BlockingQueue<JudgeItem> judgeQueue;

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true) {
                if (judgeQueue.size() > 0)
                    judge(judgeQueue.take());
                else
                    Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildJudgeShellCommand(int problemId, int currentTestCase, JudgeItem judgeItem) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(workPath);
        stringBuilder.append("/");
        stringBuilder.append(settings.JUDGE_JUDGE_CORE);
        stringBuilder.append(" -u ");
        stringBuilder.append(judgeItem.status.getStatusId());
        stringBuilder.append(" -s ");
        stringBuilder.append(judgeItem.getSourceName());
        stringBuilder.append(" -n ");
        stringBuilder.append(problemId);
        stringBuilder.append(" -D ");
        stringBuilder.append(settings.JUDGE_DATA_PATH);
        stringBuilder.append("/").append(judgeItem.status.getProblemByProblemId()).append("/");
        stringBuilder.append(" -d ");
        stringBuilder.append(tempPath);
        stringBuilder.append(" -t ");
        stringBuilder.append(judgeItem.status.getProblemByProblemId().getTimeLimit());
        stringBuilder.append(" -m ");
        stringBuilder.append(judgeItem.status.getProblemByProblemId().getMemoryLimit());
        stringBuilder.append(" -o ");
        stringBuilder.append(judgeItem.status.getProblemByProblemId().getOutputLimit());
        if (judgeItem.status.getProblemByProblemId().getIsSpj())
            stringBuilder.append(" -S");
        stringBuilder.append(" -l ");
        stringBuilder.append(judgeItem.status.getLanguageByLanguageId());
        stringBuilder.append(" -I ");
        stringBuilder.append(settings.JUDGE_DATA_PATH).append("/")
                .append(judgeItem.status.getProblemByProblemId())
                .append("/").append(currentTestCase).append(".in");
        stringBuilder.append(" -O ");
        stringBuilder.append(settings.JUDGE_DATA_PATH).append("/")
                .append(judgeItem.status.getProblemByProblemId())
                .append("/").append(currentTestCase).append(".out");
        if (currentTestCase == 1)
            stringBuilder.append(" -C");
        return stringBuilder.toString();
    }

    public String[] getCallBackString(String shellCommand) {
        Process p;
        String callBackString = "";
        try {
            p = Runtime.getRuntime().exec(shellCommand);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                callBackString += line;
            }
        } catch (Exception ignored) {
        }
        System.out.println("call back string: " + callBackString);
        return callBackString.split(" ");
    }

    /**
     * Judge judgeItem by judge core.
     *
     * @param judgeItem judge item to be judged
     */
    void judge(JudgeItem judgeItem) {
        try {
            int numberOfTestCase = judgeItem.status.getProblemByProblemId().getDataCount();
            boolean isAccepted = true;
            for (int currentTestCase = 1; isAccepted && currentTestCase <= numberOfTestCase; ++currentTestCase) {
                judgeItem.status.setCaseNumber(currentTestCase);
                FileUtil.saveToFile(judgeItem.status.getCodeByCodeId().getContent(),
                        tempPath + "/" + judgeItem.getSourceName());
                int problemId = judgeItem.status.getProblemByProblemId().getProblemId();
                String shellCommand = buildJudgeShellCommand(problemId, currentTestCase, judgeItem);
                String[] callBackString = getCallBackString(shellCommand);
                isAccepted = updateJudgeItem(callBackString, judgeItem);
            }
            if (isAccepted) {
                judgeItem.status.setResult(Global.OnlineJudgeReturnType.OJ_AC.ordinal());
                judgeItem.update();
            }
        } catch (Exception e) {
            e.printStackTrace();
            judgeItem.status.setResult(Global.OnlineJudgeReturnType.OJ_SE.ordinal());
            judgeItem.update();
        }
    }

    private boolean updateJudgeItem(String[] callBackString, JudgeItem judgeItem) {
        boolean isAccepted = true;
        if (callBackString != null && callBackString.length >= 3) {
            try {
                int result = Integer.parseInt(callBackString[0]);
                if (result == Global.OnlineJudgeReturnType.OJ_AC.ordinal()) {
                    result = Global.OnlineJudgeReturnType.OJ_RUNNING.ordinal();
                } else {
                    isAccepted = false;
                }
                judgeItem.status.setResult(result);
                judgeItem.status.setMemoryCost(Integer.parseInt(callBackString[1]));
                judgeItem.status.setTimeCost(Integer.parseInt(callBackString[2]));
            } catch (NumberFormatException e) {
                judgeItem.status.setResult(Global.OnlineJudgeReturnType.OJ_SE.ordinal());
            }
        } else {
            judgeItem.status.setResult(Global.OnlineJudgeReturnType.OJ_SE.ordinal());
        }

        if (judgeItem.status.getResult() == Global.OnlineJudgeReturnType.OJ_CE.ordinal()) {
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(workPath + "/temp/stderr_compiler.txt"));
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.replace(workPath + "/temp/" + judgeItem.getSourceName(), "");
                    sb.append(line).append('\n');
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            judgeItem.compileInfo = new CompileInfo();
            judgeItem.compileInfo.setContent(sb.toString());
        } else judgeItem.compileInfo = null;

        judgeItem.update();
        return isAccepted;
    }

    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
