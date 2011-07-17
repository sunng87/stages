package info.sunng.stages.transaction;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/17/11
 * Time: 2:28 PM
 */
public abstract class DefaultTransactionManager<T> implements TransactionManager<T> {

    private Map<T, Transaction<T>> transactions = new ConcurrentHashMap<T, Transaction<T>>();

    @Override
    public abstract T generateTransactionId() ;

    @Override
    public Transaction<T> AddTransaction(AbstractTransactionalTask<T> task) {
        T transId = generateTransactionId();
        Transaction<T> transaction = new DefaultTransaction<T>(transId);
        task.setTransactionId(transId);
        transactions.put(transId, transaction);

        return transaction;
    }

    @Override
    public Transaction<T> getTransaction(T transactionId) {
        return transactions.get(transactionId);
    }

    @Override
    public Transaction<T> removeTransaction(T transactionId) {
        return transactions.remove(transactionId);
    }
}
