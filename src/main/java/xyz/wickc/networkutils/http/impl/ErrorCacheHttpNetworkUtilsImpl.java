package xyz.wickc.networkutils.http.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.exception.HttpRequestException;
import xyz.wickc.networkutils.http.ErrorCatchHttpNetworkUtils;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.http.entity.HttpErrorDeterminer;
import xyz.wickc.networkutils.http.entity.HttpErrorHandler;

/**
 * Created on 2020/7/2
 *
 * @author wicks
 * @since 1.8
 */
public class ErrorCacheHttpNetworkUtilsImpl implements ErrorCatchHttpNetworkUtils {
    private static Logger logger = LoggerFactory.getLogger(ErrorCacheHttpNetworkUtilsImpl.class);
    private final HttpNetworkUtils httpNetworkUtils;

    private int MAX_TRY = 3;
    private HttpErrorHandler errorHandler;
    private HttpErrorDeterminer httpErrorDeterminer;

    public ErrorCacheHttpNetworkUtilsImpl(HttpNetworkUtils httpNetworkUtils) {
        this.httpNetworkUtils = httpNetworkUtils;
    }

    public ErrorCacheHttpNetworkUtilsImpl(HttpNetworkUtils httpNetworkUtils, int maxTry) {
        this.httpNetworkUtils = httpNetworkUtils;
        this.MAX_TRY = maxTry;
    }

    @Override
    public NetworkResponseData readPage(NetworkRequestData requestData) {
        NetworkResponseData responseData = null;

        int teyLength = 0;
        while (true) {
            try {
                responseData = httpNetworkUtils.readPage(requestData);
            } catch (Exception e) {
                teyLength += 1;

                logger.debug("请求时发生错误 \n 错误类型: " + e.getClass().getName() + "\n 错误信息: " + e.getMessage());
                if (logger.isDebugEnabled()) {
                    e.printStackTrace();
                }

                if (teyLength == MAX_TRY) {
                    throw e;
                } else {
                    continue;
                }
            }

            break;
        }

        return responseData;
    }

    @Override
    public void setReadTimeOut(Integer readTimeOut) {
        httpNetworkUtils.setReadTimeOut(readTimeOut);
    }

    @Override
    public void setMaxTry(int maxTry) {
        this.MAX_TRY = maxTry;
    }

    @Override
    public void setErrorHandler(HttpErrorHandler errorHandler) {

    }

    @Override
    public void setErrorDeterminer(HttpErrorDeterminer errorDeterminer) {

    }
}
