package xyz.wickc.networkutils.http;

import xyz.wickc.networkutils.http.cache.impl.HashMapCacheHttpRequestImpl;
import xyz.wickc.networkutils.http.impl.CacheHttpNetworkUtilsImpl;
import xyz.wickc.networkutils.http.impl.CustomizeHttpNetworkUtils;
import xyz.wickc.networkutils.http.impl.ErrorCacheHttpNetworkUtilsImpl;
import xyz.wickc.networkutils.http.impl.SimpleHttpNetworkUtils;

import java.net.HttpURLConnection;

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

    /**
     * 获取一个可以定制 Connection 对象的 HttpNetworkUtils 对象
     * @param connection Connection 对象
     * @return CustomizeHttpNetworkUtils
     */
    public static CustomizeHttpNetworkUtils getCustomizeHttpNetworkUtils(HttpURLConnection connection){
        return new CustomizeHttpNetworkUtils(
                connection
        );
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
}
