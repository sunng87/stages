package info.sunng.stages.transaction;

/**
 *
 *
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/17/11
 * Time: 1:18 PM
 */
public interface TransactionManager<T> {

    public T generateTransactionId();

    public Transaction<T> AddTransaction(AbstractTransactionalTask<T> task);

    public Transaction<T> getTransaction(T transactionId);

    public Transaction<T> removeTransaction(T transactionId);

}
