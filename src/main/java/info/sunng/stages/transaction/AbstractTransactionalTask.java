package info.sunng.stages.transaction;

import info.sunng.stages.AbstractTask;

/**
 * Transaction task for non-blocking, asynchronized style task.
 * Context could be store to transaction manager, and recoveried by
 * transaction id.
 *
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/17/11
 * Time: 1:41 PM
 */
public abstract class AbstractTransactionalTask<T> extends AbstractTask {

    private TransactionManager<T> transactionManager;

    private T transactionId;

    public T getTransactionId(){
        return transactionId;
    }

    public void setTransactionId(T transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionManager(TransactionManager<T> transactionManager) {
        this.transactionManager = transactionManager;
    }

    public TransactionManager<T> getTransactionManager() {
        return transactionManager;
    }

    public void saveAsTransaction() {
        this.setTransactionId(transactionManager.generateTransactionId());
        Transaction<T> transaction = transactionManager.AddTransaction(this);
        for (String key: getAttributeNames()) {
            transaction.getContext().put(key, getAttribute(key, Object.class));
        }
    }

    public void restoreTransaction() {
        T transactionId = this.getTransactionId();
        Transaction<T> transaction = transactionManager.removeTransaction(transactionId);
        for (String key: transaction.getContext().keySet()){
            this.setAttribute(key, transaction.getContext().get(key));
        }
    }

}
