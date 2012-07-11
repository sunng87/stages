package com.tekelec.nanjing.stages.example;

import com.tekelec.nanjing.stages.AbstractRetryableTask;
import com.tekelec.nanjing.stages.TaskException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 9:07 PM
 */
public class URLDownloadTask extends AbstractRetryableTask {

    private String url;

    public URLDownloadTask(String url){
        this.url = url;
    }

    @Override
    protected long getNextRetryDelay() {
        return 500;
    }

    @Override
    protected int getMaxRetryTimes() {
        return 5;
    }

    @Override
    protected void doRun() throws MalformedURLException, TaskException {
        try {
            // a little sleep
            Thread.sleep(2000);
            URL requestUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)requestUrl.openConnection();
            conn.setDoInput(true);
            InputStream dataIn = conn.getInputStream();

//            StringWriter sw = new StringWriter();
//            copyIO(dataIn, sw);

            DataParsingTask task = new DataParsingTask(dataIn);
            forward(Main.PARSER_STAGE, task);
            System.out.println("Finished retriving: " + url);
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException)){
                setRetry(true);
            }
            e.printStackTrace();
            throw new TaskException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void copyIO(InputStream in, Writer writer) throws IOException {
        int v;
        while ((v=in.read()) > 0) {
            writer.write(v);
        }
    }
}
