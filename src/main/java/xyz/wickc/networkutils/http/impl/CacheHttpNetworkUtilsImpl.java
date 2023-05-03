package xyz.wickc.networkutils.http.impl;

import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.http.CacheHttpNetworkUtils;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.http.cache.CacheHttpRequest;

/**
 * Created on 2020/6/23
 *
 * @author wicks
 * @since 1.8
 */
public class CacheHttpNetworkUtilsImpl implements CacheHttpNetworkUtils {
    private final HttpNetworkUtils httpNetworkUtils;
    private final CacheHttpRequest cacheHttpRequest;

    public CacheHttpNetworkUtilsImpl(HttpNetworkUtils httpNetworkUtils, CacheHttpRequest cacheHttpRequest) {
        this.httpNetworkUtils = httpNetworkUtils;
        this.cacheHttpRequest = cacheHttpRequest;
    }

    /**
     * 使用包装类的方式来增强
     *
     * @param requestData 请求数据
     * @return 如果缓存被命中, 那么返回的是缓存的内容!
     */
    @Override
    public NetworkResponseData readPage(NetworkRequestData requestData) {
        String strongKey = requestData.getUrl().toString();

        NetworkResponseData strongRequest = cacheHttpRequest.getStrongRequest(strongKey);
        if (strongRequest != null) {
            return strongRequest;
        }

        NetworkResponseData responseData = httpNetworkUtils.readPage(requestData);
        if (responseData.getCookieStr() != null) {
            requestData.setCookie(responseData.getCookieStr());
        }

        cacheHttpRequest.strongRequest(requestData.getUrl().toString(), responseData);


        return responseData;
    }

    @Override
    public void setReadTimeOut(Integer readTimeOut) {
        httpNetworkUtils.setReadTimeOut(readTimeOut);
    }
}
