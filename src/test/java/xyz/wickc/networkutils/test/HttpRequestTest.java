package xyz.wickc.networkutils.test;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wickc.networkutils.domain.FormUploadNetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.domain.RequestMethod;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.http.HttpNetworkUtilsFactory;
import xyz.wickc.networkutils.http.impl.CustomizeHttpNetworkUtils;
import xyz.wickc.networkutils.utils.ConnectionFactory;
import xyz.wickc.networkutils.utils.SslUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created on 2020/7/1
 *
 * @author wicks
 * @since 1.8
 */
public class HttpRequestTest {
    private static final String TEST_URL = "https://www.baidu.com/";
    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";

    private static Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    static {
        ConnectionFactory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)));

        try {
            SslUtils.ignoreSsl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void httpRequestTest() throws MalformedURLException {
        HttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getHttpNetworkUtils();

        NetworkRequestData requestData = new NetworkRequestData(
                new URL(TEST_URL), RequestMethod.GET
        );

        requestData.setUserAgent(UA);
        requestData.setAllTrust(true);

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
                    new URL(TEST_URL), RequestMethod.GET
            );

            requestData.setUserAgent(UA);
            requestData.setAllTrust(true);

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
                    new URL(TEST_URL), RequestMethod.GET
            );

            requestData.setUserAgent(UA);
            requestData.setAllTrust(true);

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
                new URL(url), RequestMethod.POST
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
    public void customizeTest() throws Exception {
        URL url = new URL(TEST_URL);
        URLConnection urlConnection = url.openConnection(
//                new Proxy(Proxy.Type.HTTP,new InetSocketAddress(8888))
        );

        CustomizeHttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getCustomizeHttpNetworkUtils((HttpURLConnection) urlConnection);
        NetworkResponseData responseData = httpNetworkUtils.readPage(null);

        System.out.println(new String(responseData.getRequestBodyData()));
    }

    @Test
    public void formUploader() throws MalformedURLException {
        ByteArrayOutputStream outputStream = null;

        try {
            InputStream inputStream = new FileInputStream("D:\\Users\\wicks\\Downloads\\weibo_cache_2d83a6f6b4b0d84f449ae1e06244445c.jpg");
            outputStream = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FormUploadNetworkRequestData requestData = new FormUploadNetworkRequestData(
                new URL(" https://weibo.local.wickc.xyz/api/face_endpoint/parse_photo?result_len=100&face_list_type=" + "twitter")
        );

//        requestData.setQueryData("key=23230f4d3b24465184bf8c2cac7c14de");
        requestData.addTextFrom("fileName", "WicksImages.png");
        requestData.addTextFrom("username", "WicksChen");
        requestData.addUploaderForm("images/jpeg", "WicksImages.jpg", "pic", outputStream.toByteArray());
        requestData.setTrustStatusCode(404, 400);
        requestData.addHeader("token", "405Jkg/npueknD6r5WKA7OlMNCYdJIT4UnXUYaa8AGfUOzzDa8Vxx43zWOwJGE3ccBLwjNh+16F0HYQSYxoW8C1cBTUwoPXkoxCP7hCh2rE=");
        requestData.setReadTimeout(1000 * 10 * 6);
//        requestData.setUseProxy(false);

//        System.out.println(new String(requestData.getRequestBodyData()));

        HttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getHttpNetworkUtils();
        NetworkResponseData responseData = httpNetworkUtils.readPage(requestData);

        logger.info("Cookie: " + responseData.getCookieStr());
        logger.info("CookieList: " + responseData.getCookieList());
        logger.info("Header: " + responseData.getHeaderMap());
        logger.info("RespBodyLength: " + responseData.getRequestBodyData().length);
        logger.info("RespBody: " + new String(responseData.getRequestBodyData()));
    }
}