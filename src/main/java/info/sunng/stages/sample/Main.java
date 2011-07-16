package info.sunng.stages.sample;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import info.sunng.stages.DefaultStageManager;
import info.sunng.stages.StageManager;
import info.sunng.stages.threads.FixedThreadPoolPolicy;
import info.sunng.stages.threads.NamedThreadFactory;
import info.sunng.stages.threads.SingleThreadPerCorePolicy;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 9:06 PM
 */
public class Main {

    public static final String PARSER_STAGE = "parsing";
    public static final String DOWNLOADER_STAGE = "downloader";

    public static final String URL_TEMPLATE = "http://api.douban.com/book/subject/";

    public static final int TASK_COUNT = 20;

    public static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(TASK_COUNT);

    public static StageManager setUpStages() {
        StageManager sm = new DefaultStageManager();

        sm.register(DOWNLOADER_STAGE, new FixedThreadPoolPolicy(10));
        sm.register(PARSER_STAGE, new SingleThreadPerCorePolicy());

        sm.start();
        return sm;
    }

    public static void main(String args[]) {
        StageManager sm = setUpStages();

        int idCodeBase = 6397550;
        for (int i=0; i<TASK_COUNT; i++){
            int code = idCodeBase + i;
            String url = URL_TEMPLATE + code;

            URLDownloadTask task = new URLDownloadTask(url);
            sm.getStage(DOWNLOADER_STAGE).assign(task);
        }

        try {
            COUNT_DOWN_LATCH.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sm.shutdown();
    }
}
