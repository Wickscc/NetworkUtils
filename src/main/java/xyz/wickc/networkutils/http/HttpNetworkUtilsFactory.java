package xyz.wickc.networkutils.http;

import xyz.wickc.networkutils.http.cache.impl.HashMapCacheHttpRequestImpl;
import xyz.wickc.networkutils.http.impl.CacheHttpNetworkUtilsImpl;
import xyz.wickc.networkutils.http.impl.CustomizeHttpNetworkUtilsImpl;
import xyz.wickc.networkutils.http.impl.ErrorCacheHttpNetworkUtilsImpl;
import xyz.wickc.networkutils.http.impl.SimpleHttpNetworkUtils;

/**
 * Created on 2020/4/24
 *
 * @author wicks
 * @since 1.8
 */
public class HttpNetworkUtilsFactory {
    public static HttpNetworkUtils getHttpNetworkUtils(){
        return new SimpleHttpNetworkUtils();
    }

    public static CacheHttpNetworkUtils getCacheHttpNetworkUtils(){
        return new CacheHttpNetworkUtilsImpl(
                getHttpNetworkUtils(),
                new HashMapCacheHttpRequestImpl()
        );
    }

    public static ErrorCacheHttpNetworkUtilsImpl getErrorCatchHttpNetworkUtils(){
        return new ErrorCacheHttpNetworkUtilsImpl(
                getHttpNetworkUtils()
        );
    }

    public static CustomizeHttpNetworkUtils getCustomizeHttpNetworkUtils(){
        return new CustomizeHttpNetworkUtilsImpl(
                getHttpNetworkUtils()
        );
    }

    public static CacheHttpNetworkUtils getCacheHttpNetworkUtils(HttpNetworkUtils httpNetworkUtils){
        if (httpNetworkUtils == null){
            throw new RuntimeException("不能为空!");
        }

        return new CacheHttpNetworkUtilsImpl(
                httpNetworkUtils,
                new HashMapCacheHttpRequestImpl()
        );
    }

    public static ErrorCacheHttpNetworkUtilsImpl getErrorCatchHttpNetworkUtils(HttpNetworkUtils httpNetworkUtils){
        if (httpNetworkUtils == null){
            throw new RuntimeException("不能为空!");
        }

        return new ErrorCacheHttpNetworkUtilsImpl(
                httpNetworkUtils
        );
    }

    public static CustomizeHttpNetworkUtils getCustomizeHttpNetworkUtils(HttpNetworkUtils httpNetworkUtils){
        if (httpNetworkUtils == null){
            throw new RuntimeException("不能为空!");
        }

        return new CustomizeHttpNetworkUtilsImpl(
                httpNetworkUtils
        );
    }
}
