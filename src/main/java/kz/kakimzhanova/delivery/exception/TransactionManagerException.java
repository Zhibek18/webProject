package kz.kakimzhanova.delivery.exception;

public class TransactionManagerException extends Exception {
    public TransactionManagerException() {
    }

    public TransactionManagerException(String s) {
        super(s);
    }

    public TransactionManagerException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionManagerException(Throwable throwable) {
        super(throwable);
    }
}
