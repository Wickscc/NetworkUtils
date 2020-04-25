package xyz.wickc.networkutils.exception;

/**
 * Created on 2020/4/24
 *
 * @author wicks
 * @since 1.8
 */
public class RequestMethodNotSupportException extends RuntimeException {
    public RequestMethodNotSupportException() {
        super();
    }

    public RequestMethodNotSupportException(String message) {
        super(message);
    }

    public RequestMethodNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestMethodNotSupportException(Throwable cause) {
        super(cause);
    }

    protected RequestMethodNotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
