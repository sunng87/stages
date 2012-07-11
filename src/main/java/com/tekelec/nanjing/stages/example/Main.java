package com.tekelec.nanjing.stages.example;

import java.util.concurrent.CountDownLatch;

import com.tekelec.nanjing.stages.DefaultStageManager;
import com.tekelec.nanjing.stages.StageManager;
import com.tekelec.nanjing.stages.threads.FixedThreadPoolPolicy;
import com.tekelec.nanjing.stages.threads.SingleThreadPerCorePolicy;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 9:06 PM
 */
public class Main {

    public static final String PARSER_STAGE = "parsing";
    public static final String DOWNLOADER_STAGE = "downloader";

    public static final String URL_TEMPLATE = "http://api.douban.com/book/subject/";

    public static final String COUNT_DOWN_LATCH = "countDownLatch";

    public static final int TASK_COUNT = 20;

    public static StageManager setUpStages() {
        StageManager sm = new DefaultStageManager();

        sm.register(DOWNLOADER_STAGE, new FixedThreadPoolPolicy(10));
        sm.register(PARSER_STAGE, new SingleThreadPerCorePolicy());

        sm.start();
        return sm;
    }

    public static void main(String args[]) {
        StageManager sm = setUpStages();
        CountDownLatch cdl = new CountDownLatch(TASK_COUNT);

        int idCodeBase = 6397550;
        for (int i=0; i<TASK_COUNT; i++){
            int code = idCodeBase + i;
            String url = URL_TEMPLATE + code;

            URLDownloadTask task = new URLDownloadTask(url);
            task.setAttribute(COUNT_DOWN_LATCH, cdl);
            sm.getStage(DOWNLOADER_STAGE).assign(task);
        }

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sm.shutdown();
    }
}
