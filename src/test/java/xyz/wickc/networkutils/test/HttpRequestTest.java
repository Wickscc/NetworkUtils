package xyz.wickc.networkutils.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.domain.RequestMethod;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.http.HttpNetworkUtilsFactory;
import xyz.wickc.networkutils.http.impl.CustomizeHttpNetworkUtils;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2020/7/1
 *
 * @author wicks
 * @since 1.8
 */
public class HttpRequestTest {
    private static final String TEST_URL = "https://baidu.com";
    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";

    private static Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    @Test
    public void httpRequestTest() throws MalformedURLException {
        HttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getHttpNetworkUtils();

        NetworkRequestData requestData = new NetworkRequestData(
                new URL(TEST_URL),RequestMethod.GET
        );

        requestData.setUserAgent(UA);

        NetworkResponseData responseData = httpNetworkUtils.readPage(requestData);

        logger.info("Cookie: " + responseData.getCookieStr());
        logger.info("CookieList: " + responseData.getCookieList());
        logger.info("Header: " + responseData.getHeaderMap());
        logger.info("RespBodyLength: " + responseData.getRequestBodyData().length);
    }

    @Test
    public void cacheHttpRequestTest() throws MalformedURLException {
        HttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getCacheHttpNetworkUtils(HttpNetworkUtilsFactory.getHttpNetworkUtils());

        for (int i = 0; i < 5; i++) {
            NetworkRequestData requestData = new NetworkRequestData(
                    new URL(TEST_URL),RequestMethod.GET
            );

            requestData.setUserAgent(UA);

            long startTime = System.currentTimeMillis();
            NetworkResponseData responseData = httpNetworkUtils.readPage(requestData);
            long endTime = System.currentTimeMillis();


            logger.info("Length: " + i);
            logger.info("Time: " + (endTime - startTime));
            logger.info("Cookie: " + responseData.getCookieStr());
            logger.info("CookieList: " + responseData.getCookieList());
            logger.info("Header: " + responseData.getHeaderMap());
            logger.info("RespBodyLength: " + responseData.getRequestBodyData().length);
        }
    }

    @Test
    public void notCacheHttpRequestTest() throws MalformedURLException {
        HttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getHttpNetworkUtils();

        for (int i = 0; i < 5; i++) {
            NetworkRequestData requestData = new NetworkRequestData(
                    new URL(TEST_URL),RequestMethod.GET
            );

            requestData.setUserAgent(UA);

            long startTime = System.currentTimeMillis();
            NetworkResponseData responseData = httpNetworkUtils.readPage(requestData);
            long endTime = System.currentTimeMillis();


            logger.info("Length: " + i);
            logger.info("Time: " + (endTime - startTime));
            logger.info("Cookie: " + responseData.getCookieStr());
            logger.info("CookieList: " + responseData.getCookieList());
            logger.info("Header: " + responseData.getHeaderMap());
            logger.info("RespBodyLength: " + responseData.getRequestBodyData().length);
        }
    }

    @Test
    public void postDataToServer() throws MalformedURLException {
        String url = "https://blog.wickc.xyz:7443/blog_api/user/login";

        HttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getHttpNetworkUtils();

        NetworkRequestData requestData = new NetworkRequestData(
                new URL(url),RequestMethod.POST
        );

        requestData.setUserAgent(UA);
        requestData.setTrustStatusCode(400);
        requestData.setRequestBodyData("password=123456789&username=123456789".getBytes());

        NetworkResponseData responseData = httpNetworkUtils.readPage(requestData);

        logger.info("Cookie: " + responseData.getCookieStr());
        logger.info("CookieList: " + responseData.getCookieList());
        logger.info("Header: " + responseData.getHeaderMap());
        logger.info("RespBodyLength: " + responseData.getRequestBodyData().length);
        logger.info("RespBody: " + new String(responseData.getRequestBodyData()));
    }

    @Test
    public void customizeTest() throws Exception{
        URL url = new URL(TEST_URL);
        URLConnection urlConnection = url.openConnection();

        CustomizeHttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getCustomizeHttpNetworkUtils((HttpURLConnection) urlConnection);
        NetworkResponseData responseData = httpNetworkUtils.readPage(null);

        System.out.println(new String(responseData.getRequestBodyData()));
    }
}