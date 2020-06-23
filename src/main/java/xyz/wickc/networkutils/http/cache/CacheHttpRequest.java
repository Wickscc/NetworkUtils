package xyz.wickc.networkutils.http.cache;

import xyz.wickc.networkutils.domain.NetworkResponseData;

/**
 * Created on 2020/6/23
 *
 * @author wicks
 * @since 1.8
 */
public interface CacheHttpRequest {

    /**
     * 缓存存储
     * @param strongKey
     * @param responseData
     */
    void strongRequest(String strongKey,NetworkResponseData responseData);

    /**
     * 读取缓存
     * @param strongKey
     * @return NetworkResponseData
     */
    NetworkResponseData getStrongRequest(String strongKey);
}
