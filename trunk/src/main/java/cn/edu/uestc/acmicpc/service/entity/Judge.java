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
 * @version 2
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

    /**
     * Judge judgeItem by judge core.
     *
     * @param judgeItem judge item to be judged
     */
    public void judge(JudgeItem judgeItem) {
        try {
            int numberOfTestCase = judgeItem.status.getProblemByProblemId().getDataCount();
            boolean isAccepted = true;
            for (int i = 0; i < numberOfTestCase; ++i) {
                judgeItem.status.setCaseNumber(i + 1);
                FileUtil.saveToFile(judgeItem.status.getCodeByCodeId().getContent(), workPath + "/" + judgeItem.getSourceName());
                int problemId = judgeItem.status.getProblemByProblemId().getProblemId();
                String bash = "sudo ./" + workPath + settings.JUDGE_JUDGE_CORE + " -l " + judgeItem.parseLanguage();
                bash += " -u " + judgeItem.status.getStatusId();
                bash += " -s " + workPath + "/temp/" + judgeItem.getSourceName();
                bash += " -n " + problemId;
                bash += " -D " + settings.JUDGE_DATA_PATH + '/' + problemId + " -d " + tempPath;
                if (judgeItem.status.getLanguageByLanguageId().getExtension().equals(".java")) {
                    bash += " -t " + judgeItem.status.getProblemByProblemId().getJavaTimeLimit();
                    bash += " -m " + judgeItem.status.getProblemByProblemId().getJavaMemoryLimit();
                } else {
                    bash += " -t " + judgeItem.status.getProblemByProblemId().getTimeLimit();
                    bash += " -m " + judgeItem.status.getProblemByProblemId().getMemoryLimit();
                }
                bash += " -o " + judgeItem.status.getProblemByProblemId().getOutputLimit();

                Process p;
                String res = "";
                try {
                    p = Runtime.getRuntime().exec(bash);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        res += line;
                    }
                } catch (Exception ignored) {
                }
                String[] ret = res.split(" ");
                if (ret != null && ret.length >= 3) {
                    int result = Integer.parseInt(ret[0]);
                    if (result == Global.OnlineJudgeReturnType.OJ_AC.ordinal()) {
                        result = Global.OnlineJudgeReturnType.OJ_RUNNING.ordinal();
                    } else {
                        isAccepted = false;
                    }
                    judgeItem.status.setResult(result);
                    judgeItem.status.setMemoryCost(Integer.parseInt(ret[1]));
                    judgeItem.status.setTimeCost(Integer.parseInt(ret[2]));
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
            }
            if (isAccepted) {
                judgeItem.status.setResult(Global.OnlineJudgeReturnType.OJ_AC.ordinal());
                judgeItem.update();
            }
        } catch (Exception e) {
            judgeItem.status.setResult(Global.OnlineJudgeReturnType.OJ_SE.ordinal());
            judgeItem.update();
        }
    }

    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
