package xyz.wickc.networkutils.test;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wickc.networkutils.domain.FormUploadNetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.RequestMethod;
import xyz.wickc.networkutils.utils.RequestRowParsing;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created on 2020/7/2
 *
 * @author wicks
 * @since 1.8
 */
public class RequestAndResponseTest {
    private static final String TEST_URL = "https://baidu.com";
    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";

    private static Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    @Test
    public void requestDataConfigTest() throws MalformedURLException {
        NetworkRequestData requestData = new NetworkRequestData(
                new URL(TEST_URL), RequestMethod.GET
        );

        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("k6","v6");
        queryMap.put("k1","v11");

        requestData.setUserAgent(UA);
        requestData.setCookie("_ga=GA1.3.648773220.1588651907; NID=203=oOLALR0IpnNpEpdlpqlRwKTgoxEcZD3qK-ot2CAbc8GOZHUk8NJdaSrOmZ93VxadZgpXPYt9Nim4amIOO8ecw2ZA3tOAcSWgkFkkCPUFsocrGbIiQ9N4zQ9h7WZfTyd9LoZjz_MtQgY-8zxGJ09sFeWwqNktGsJPyUtU3H6wSQQ; _gid=GA1.3.1360237607.1593600160; 1P_JAR=2020-7-1-10");
        requestData.setQueryData("?k1=v1&k2=v2&k3=v4");
        requestData.addHeader("Content-Type","application/json;charset=UTF-8");
        requestData.setQueryData(queryMap);
        requestData.setTrustStatusCode(200,300,400,500,600,700,800,900,1000);


        logger.info("RequestHeader: " + requestData.getHeaderMap());
        logger.info("RequestUrl: " + requestData.getUrl().toString());
        logger.info("RequestBodyLength: " + (requestData.getRequestBodyData() != null ? new String(requestData.getRequestBodyData()) : 0));
    }

    @Test
    public void rowRequestTestA() throws IOException {
        NetworkRequestData requestData = RequestRowParsing.parsingRowRequest(new FileReader("src/test/resources/JTRequestTemplate.txt"));

        logger.info("RequestHeader: " + requestData.getHeaderMap());
        logger.info("RequestUrl: " + requestData.getUrl().toString());
        logger.info("RequestBodyLength: " + (requestData.getRequestBodyData() != null ? new String(requestData.getRequestBodyData()) : 0));
    }

    @Test
    public void rowRequestTestB() throws IOException {
        NetworkRequestData requestData = RequestRowParsing.parsingRowRequest(new FileReader("src/test/resources/JDRequestTemplate.txt"));

        logger.info("RequestHeader: " + requestData.getHeaderMap());
        logger.info("RequestUrl: " + requestData.getUrl().toString());
        logger.info("RequestBodyLength: " + (requestData.getRequestBodyData() != null ? new String(requestData.getRequestBodyData()) : 0));
    }

    @Test
    public void formUploadRequestTest() throws Exception{
        InputStream inputStream = new FileInputStream("D:\\Users\\wicks\\Downloads\\weibo_cache_2d83a6f6b4b0d84f449ae1e06244445c.jpg");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        IOUtils.copy(inputStream,outputStream);

        FormUploadNetworkRequestData requestData = new FormUploadNetworkRequestData(
                new URL("https://wickc.xyz:8443/Images/images?key=" + UUID.randomUUID().toString().replace("-",""))
        );

        requestData.addTextFrom("fileName","WicksImages.png");
        requestData.addTextFrom("username","WicksChen");
        requestData.setRequestBodyData("k1=v1&k2=v2".getBytes());
        requestData.addUploaderForm("images/jpeg","WicksImages.jpg","file",outputStream.toByteArray());

        byte[] requestBodyData = requestData.getRequestBodyData();
        System.out.println(new String(requestBodyData));
    }
}
