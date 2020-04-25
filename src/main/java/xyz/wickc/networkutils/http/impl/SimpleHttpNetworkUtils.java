package xyz.wickc.networkutils.http.impl;

import com.sun.deploy.net.HttpResponse;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.domain.RequestMethod;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.utils.ConnectionFactory;
import xyz.wickc.networkutils.utils.DecodeUtils;
import xyz.wickc.networkutils.utils.HttpResponseDataBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2020/4/24
 *
 * @author wicks
 * @since 1.8
 */
public class SimpleHttpNetworkUtils implements HttpNetworkUtils {
    private static final String BROTLI_CONTENT_TYPE = "br";
    private static final String GZIP_CONTENT_TYPE = "gzip";
    private static Logger logger = LoggerFactory.getLogger(SimpleHttpNetworkUtils.class);

    public SimpleHttpNetworkUtils() {

    }

    @Override
    public NetworkResponseData readPage(NetworkRequestData requestData) {
        if (requestData == null){
            throw new RuntimeException("requestData 不能为 Null");
        }

        URL url = requestData.getUrl();
        if (requestData.getQueryData() != null){
            try {
                url = new URL(requestData.getUrl().toString() + requestData.getQueryData());
            } catch (MalformedURLException e) {
                throw new RuntimeException("您提交的 QueryData 不符合规范 : " + url.toString(),e);
            }
        }

        byte[] requestBody = requestData.getRequestBodyData();
        Map<String, Set<String>> headerMap = requestData.getHeaderMap();
        RequestMethod requestMethod = requestData.getRequestMethod();

        logger.debug("Request URL : " + url.toString());
        if (requestBody != null) {
            logger.debug("Request BodyLength : " + requestBody.length);
        }
        logger.debug("Request Header : " + headerMap.toString());
        logger.debug("Request Method : " + requestMethod.name());

        HttpURLConnection connection = (HttpURLConnection) ConnectionFactory.getUrlConnection(url);

//        初始化设置,设置请求头 请求方法 和发送数据等操作
        try {
            connection.setRequestMethod(requestMethod.name());
            connection.setDoInput(true);
            connection.setDoOutput(true);

            Set<String> headerKeySet = headerMap.keySet();
            for (String headerKey : headerKeySet) {
                Set<String> valueSet = headerMap.get(headerKey);
                for (String headerValue : valueSet) {
                    connection.addRequestProperty(headerKey,headerValue);
                }
            }

            outputData(requestBody,connection.getOutputStream());
        } catch (ProtocolException e) {
            throw new RuntimeException("设置参数时出错",e);
        } catch (IOException e) {
            throw new RuntimeException("输出数据到服务器时出错!",e);
        }

//        连接 URL
        try {
            connection.connect();
        } catch (IOException e) {
            throw new RuntimeException("连接URL的时候出现错误!",e);
        }

//        返回数据处理并且返回
        try {
            int responseCode = connection.getResponseCode();
            InputStream inputStream = connection.getInputStream();
            Map<String, List<String>> respHeaderMap = connection.getHeaderFields();
            byte[] bytes = parsingResponse(inputStream,respHeaderMap);

            logger.debug("Response BodyLength : " + bytes.length);
            logger.debug("Response Header : " + respHeaderMap.toString());
            logger.debug("Response Code : " + responseCode);

            return HttpResponseDataBuilder.builderNetworkResponseData(bytes,respHeaderMap,responseCode);
        } catch (IOException e) {
            throw new RuntimeException("处理响应信息时出错!",e);
        }
    }

    private byte[] parsingResponse(InputStream connectionInputStream,Map<String,List<String>> respHeaderMap){
        List<String> strings = respHeaderMap.get("Content-Encoding");

        if (strings == null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try {
                IOUtils.copy(connectionInputStream,outputStream);
            } catch (IOException e) {
                throw new RuntimeException("數據傳輸時出錯!",e);
            }

            return outputStream.toByteArray();
        }

        String encodeMethod = strings.get(0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            IOUtils.copy(connectionInputStream,outputStream);
        } catch (IOException e) {
            throw new RuntimeException("數據傳輸時出錯!",e);
        }

        byte[] data = outputStream.toByteArray();

        if (BROTLI_CONTENT_TYPE.equals(encodeMethod)){
            return DecodeUtils.deCodeBrotliCode(data);
        }else if (GZIP_CONTENT_TYPE.equals(encodeMethod)){
            return DecodeUtils.deCodeGzipCode(data);
        }else{
            return data;
        }
    }

    private void outputData(byte[] requestBody,OutputStream connectionOutPutStream) throws IOException{
        OutputStream connectionOutputStream = null;
        ByteInputStream requestBodyInputStream = null;

        if (requestBody != null && requestBody.length != 0) {
            connectionOutputStream = connectionOutPutStream;
            requestBodyInputStream = new ByteInputStream(requestBody, requestBody.length);

            IOUtils.copy(requestBodyInputStream, connectionOutputStream);
        }

        if (connectionOutputStream != null) {
            connectionOutputStream.close();
        }
        if (requestBodyInputStream != null) {
            requestBodyInputStream.close();
        }
    }
}
