package info.sunng.stages.sample;

import info.sunng.stages.AbstractRetryableTask;
import info.sunng.stages.TaskException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: Sun Ning<classicning@gmail.com>
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
    protected void doRun() throws TaskException {
        try {
            System.out.println(url);
            URL requestUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)requestUrl.openConnection();
            conn.setDoInput(true);
            InputStream dataIn = conn.getInputStream();

//            StringWriter sw = new StringWriter();
//            copyIO(dataIn, sw);

            DataParsingTask task = new DataParsingTask(dataIn);
            forward(Main.PARSER_STAGE, task);
            System.out.println("Finished retriving: " + url);
        } catch (MalformedURLException e) {
            throw new TaskException(e);
        } catch (IOException e) {
            setRetry(true);
            e.printStackTrace();
            throw new TaskException(e);
        }
    }

    private void copyIO(InputStream in, Writer writer) throws IOException {
        int v;
        while ((v=in.read()) > 0) {
            writer.write(v);
        }
    }
}
