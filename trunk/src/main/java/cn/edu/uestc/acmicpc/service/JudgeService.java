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

package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.ioc.util.SettingsAware;
import cn.edu.uestc.acmicpc.service.entity.Judge;
import cn.edu.uestc.acmicpc.service.entity.JudgeItem;
import cn.edu.uestc.acmicpc.service.entity.Scheduler;
import cn.edu.uestc.acmicpc.util.Settings;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Judge main service, use multi-thread architecture to process judge
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@Transactional
public class JudgeService implements ApplicationContextAware, SettingsAware {
    private Thread schedulerThread;
    private static Thread[] judgeThreads;
    /**
     * Judging Queue.
     */
    private static final BlockingQueue<JudgeItem> judgeQueue = new LinkedBlockingQueue<>();

    /**
     * Spring application context
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Fetch global Settings for judge.
     */
    @Autowired
    private Settings settings;

    /**
     * Initialize the judge threads.
     */
    public void init() {
        Scheduler scheduler = applicationContext.getBean("scheduler", Scheduler.class);
        scheduler.setJudgeQueue(judgeQueue);
        schedulerThread = new Thread(scheduler);
        judgeThreads = new Thread[settings.JUDGE_LIST.size()];
        Judge[] judges = new Judge[settings.JUDGE_LIST.size()];
        for (int i = 0; i < judgeThreads.length; ++i) {
            judges[i] = applicationContext.getBean("judge", Judge.class);
            judges[i].setJudgeQueue(judgeQueue);
            judges[i].setWorkPath(settings.JUDGE_TEMP_PATH + "/" + settings.JUDGE_LIST.get(i).get("name") + "/");
            judges[i].setTempPath(settings.JUDGE_TEMP_PATH + "/" + settings.JUDGE_LIST.get(i).get("name") + "/temp/");
            judges[i].setJudgeName(settings.JUDGE_LIST.get(i).get("name"));
            judgeThreads[i] = new Thread(judges[i]);
            judgeThreads[i].start();
        }
        schedulerThread.start();
    }

    /**
     * Destroy the judge threads.
     */
    public void destroy() {
        try {
            if (schedulerThread.isAlive()) {
                schedulerThread.interrupt();
            }
            for (Thread judgeThread : judgeThreads) {
                if (judgeThread.isAlive()) {
                    judgeThread.interrupt();
                }
            }
        } catch (SecurityException ignored) {
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
