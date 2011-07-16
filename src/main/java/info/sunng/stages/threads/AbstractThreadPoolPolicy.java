package info.sunng.stages.threads;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 12:05 PM
 */
public abstract class AbstractThreadPoolPolicy implements ThreadPoolPolicy{

    private String name;

    @Override
    public void setName(String name) {
        this.name = name;
    };

    public String getName(){
        return this.name;
    }
}
