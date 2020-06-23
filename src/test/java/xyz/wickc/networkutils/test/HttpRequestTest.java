package xyz.wickc.networkutils.test;

import org.junit.Before;
import org.junit.Test;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.domain.RequestMethod;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.http.HttpNetworkUtilsFactory;
import xyz.wickc.networkutils.http.cache.CacheHttpRequest;
import xyz.wickc.networkutils.http.cache.impl.HashMapCacheHttpRequestImpl;
import xyz.wickc.networkutils.http.impl.CacheHttpNetworkUtils;

import java.net.URL;

/**
 * Created on 2020/6/23
 *
 * @author wicks
 * @since 1.8
 */
public class HttpRequestTest {
    private static final String UA = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2740.13 Safari/537.36";
    private HttpNetworkUtils httpNetworkUtils;

    @Before
    public void bef(){
        httpNetworkUtils = HttpNetworkUtilsFactory.getHttpNetworkUtils();
    }

    @Test
    public void testBaseGet() throws Exception{
        NetworkRequestData requestData = new NetworkRequestData(
                new URL("https://baidu.com"),
                RequestMethod.GET
        );

        requestData.setUserAgent(UA);

        NetworkResponseData responseData = httpNetworkUtils.readPage(requestData);
        System.out.println(new String(responseData.getRequestBodyData()));
    }

    @Test
    public void cacheGet() throws Exception{
        CacheHttpNetworkUtils cacheHttpNetworkUtils = new CacheHttpNetworkUtils(
                httpNetworkUtils,
                new HashMapCacheHttpRequestImpl()
        );

        for (int i = 0; i < 10; i++) {
            NetworkRequestData requestData = new NetworkRequestData(
                    new URL("https://www.bing.com/?mkt=zh-CN"),
                    RequestMethod.GET
            );

            requestData.setUserAgent(UA);

            NetworkResponseData responseData = cacheHttpNetworkUtils.readPage(requestData);
            System.out.println(new String(responseData.getRequestBodyData()));
        }
    }
}
