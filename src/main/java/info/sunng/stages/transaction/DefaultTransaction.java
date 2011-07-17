package info.sunng.stages.transaction;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/17/11
 * Time: 2:38 PM
 */
public class DefaultTransaction<T> implements Transaction<T> {

    private long timestamp;

    private T transId;

    private Map<String, Object> context = new HashMap<String, Object>();

    public DefaultTransaction(T transactionId) {
        this.timestamp = System.currentTimeMillis();
        this.transId = transactionId;
    }

    @Override
    public long getCreateTimestamp() {
        return timestamp;
    }

    @Override
    public T getTransactionId() {
        return transId;
    }

    @Override
    public Map<String, Object> getContext() {
        return context;
    }
}
