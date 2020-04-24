package xyz.wickc.networkutils.exception;

/**
 * Created on 2019/10/10
 *
 * @author wicks
 * @since 1.8
 */
public class ConfigNotFindException extends ConfigException{
    public ConfigNotFindException() {
    }

    public ConfigNotFindException(String message) {
        super(message);
    }

    public ConfigNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigNotFindException(Throwable cause) {
        super(cause);
    }

    public ConfigNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
