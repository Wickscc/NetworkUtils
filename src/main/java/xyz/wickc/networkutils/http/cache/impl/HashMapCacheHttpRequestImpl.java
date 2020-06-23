package xyz.wickc.networkutils.http.cache.impl;

import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.http.cache.CacheHttpRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2020/6/23
 *
 * @author wicks
 * @since 1.8
 */
public class HashMapCacheHttpRequestImpl implements CacheHttpRequest {
    private Map<String,NetworkResponseData> cacheMap;

    public HashMapCacheHttpRequestImpl() {
        cacheMap = new ConcurrentHashMap<>();
    }

    @Override
    public void strongRequest(String strongKey, NetworkResponseData responseData) {
        cacheMap.put(strongKey,responseData);
    }

    @Override
    public NetworkResponseData getStrongRequest(String strongKey) {
        return cacheMap.get(strongKey);
    }
}
