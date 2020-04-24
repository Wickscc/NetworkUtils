package xyz.wickc.networkutils.exception;

/**
 * Created on 2019/10/10
 *
 * @author wicks
 * @since 1.8
 */
public class NetworkException extends Exception {
    public NetworkException() {
    }

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkException(Throwable cause) {
        super(cause);
    }

    public NetworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
