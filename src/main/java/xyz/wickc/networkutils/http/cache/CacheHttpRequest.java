package xyz.wickc.networkutils.http.cache;

/**
 * Created on 2020/6/23
 *
 * @author wicks
 * @since 1.8
 */
public interface CacheHttpRequest {

    /**
     * 存储 Cache
     * @param strongKey
     * @param bytesData
     */
    void strongRequest(String strongKey,byte[] bytesData);
}
