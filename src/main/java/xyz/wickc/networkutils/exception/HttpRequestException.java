package xyz.wickc.networkutils.exception;

import xyz.wickc.networkutils.domain.NetworkResponseData;

import java.nio.charset.StandardCharsets;

public class HttpRequestException extends RuntimeException {
    private final Integer responseCode;

    private byte[] responseBody;

    private NetworkResponseData responseData;

    public HttpRequestException(Integer responseCode, byte[] responseBody, NetworkResponseData responseData) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.responseData = responseData;
    }

    public HttpRequestException(String message, Integer responseCode, byte[] responseBody, NetworkResponseData responseData) {
        super(message);
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.responseData = responseData;
    }

    public HttpRequestException(String message, Throwable cause, Integer responseCode, byte[] responseBody, NetworkResponseData responseData) {
        super(message, cause);
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.responseData = responseData;
    }

    public HttpRequestException(Throwable cause, Integer responseCode, byte[] responseBody, NetworkResponseData responseData) {
        super(cause);
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.responseData = responseData;
    }

    public HttpRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer responseCode, byte[] responseBody, NetworkResponseData responseData) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.responseData = responseData;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(byte[] responseBody) {
        this.responseBody = responseBody;
    }

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
        this.responseBody = responseData;
    }

    public HttpRequestException(String message, Integer responseCode, byte[] responseData) {
        super(message);
        this.responseCode = responseCode;
        this.responseBody = responseData;
    }

    public HttpRequestException(String message, Throwable cause, Integer responseCode, byte[] responseData) {
        super(message, cause);
        this.responseCode = responseCode;
        this.responseBody = responseData;
    }

    public HttpRequestException(Throwable cause, Integer responseCode, byte[] responseData) {
        super(cause);
        this.responseCode = responseCode;
        this.responseBody = responseData;
    }

    public HttpRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer responseCode, byte[] responseData) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseCode = responseCode;
        this.responseBody = responseData;
    }

    public String getResponseBodyStr() {
        if (getResponseBody() == null) {
            return "";
        }

        return new String(getResponseBody(), StandardCharsets.UTF_8);
    }
}
