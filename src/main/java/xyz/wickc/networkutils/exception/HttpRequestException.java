package xyz.wickc.networkutils.exception;

public class HttpRequestException extends RuntimeException {
    private final Integer responseCode;

    private byte[] responseData;

    public HttpRequestException(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public HttpRequestException(String message, Integer responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public HttpRequestException(String message, Throwable cause, Integer responseCode) {
        super(message, cause);
        this.responseCode = responseCode;
    }

    public HttpRequestException(Throwable cause, Integer responseCode) {
        super(cause);
        this.responseCode = responseCode;
    }

    public HttpRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer responseCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseCode = responseCode;
    }

    public HttpRequestException(Integer responseCode, byte[] responseData) {
        this.responseCode = responseCode;
        this.responseData = responseData;
    }

    public HttpRequestException(String message, Integer responseCode, byte[] responseData) {
        super(message);
        this.responseCode = responseCode;
        this.responseData = responseData;
    }

    public HttpRequestException(String message, Throwable cause, Integer responseCode, byte[] responseData) {
        super(message, cause);
        this.responseCode = responseCode;
        this.responseData = responseData;
    }

    public HttpRequestException(Throwable cause, Integer responseCode, byte[] responseData) {
        super(cause);
        this.responseCode = responseCode;
        this.responseData = responseData;
    }

    public HttpRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer responseCode, byte[] responseData) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseCode = responseCode;
        this.responseData = responseData;
    }
}
