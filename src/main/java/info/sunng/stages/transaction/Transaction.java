package info.sunng.stages.transaction;

import java.util.Map;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/17/11
 * Time: 2:01 PM
 */
public interface Transaction<T> {

    public long getCreateTimestamp();

    public T getTransactionId();

    public Map<String, Object> getContext();

}
