package xyz.wickc.networkutils.http.impl;

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
import java.net.*;
import java.util.Arrays;
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
    /**
     * Brotli 压缩的识别键
     */
    protected static final String BROTLI_CONTENT_TYPE = "br";

    /**
     * Gzip 压缩的识别键
     */
    protected static final String GZIP_CONTENT_TYPE = "gzip";

    private static Logger logger = LoggerFactory.getLogger(SimpleHttpNetworkUtils.class);

    public SimpleHttpNetworkUtils() {
    }

    @Override
    public NetworkResponseData readPage(NetworkRequestData requestData) {
        HttpURLConnection connection = parsingRequestData(requestData);

//        连接 URL
        try {
            connection.connect();
        } catch (IOException e) {
            if(requestData != null){
                throw new RuntimeException("连接URL[" + requestData.getUrl() + "]的时候出现错误!", e);
            }else{
                throw new RuntimeException("连接URL的时候出现错误!", e);
            }
        }

//        返回数据处理并且返回
        try {
            int responseCode = connection.getResponseCode();

            InputStream inputStream;
            boolean b = false;

            if (requestData != null) {
                b = Arrays.stream(requestData.getTrustStatusCode()).anyMatch(i -> responseCode == i);
            }

            if (responseCode != 200 && b) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }

            Map<String, List<String>> respHeaderMap = connection.getHeaderFields();
            byte[] bytes = parsingResponse(inputStream, respHeaderMap);

            logger.debug("Response BodyLength : " + bytes.length);
            logger.debug("Response Header : " + respHeaderMap.toString());
            logger.debug("Response Code : " + responseCode);

            return HttpResponseDataBuilder.builderNetworkResponseData(bytes, respHeaderMap, responseCode);
        } catch (IOException e) {
            throw new RuntimeException("处理响应信息时出错!", e);
        }
    }

    /**
     * 解析 NetworkRequestData 成 HttpUrlConnection
     *
     * @param requestData NetworkRequestData
     * @return HttpUrlConnection
     */
    protected HttpURLConnection parsingRequestData(NetworkRequestData requestData) {
        if (requestData == null) {
            throw new RuntimeException("requestData 不能为 Null");
        }

        URL url = requestData.getUrl();
        String queryData = requestData.getQueryData();
        if (queryData != null) {
            if (!queryData.startsWith("?")) {
                queryData = "?" + queryData;
            }

            try {
                url = new URL(requestData.getUrl().toString() + requestData.getQueryData());
            } catch (MalformedURLException e) {
                throw new RuntimeException("您提交的 QueryData 不符合规范 : " + url.toString(), e);
            }
        }

//        解析 NetworkRequestData 包含的数据
        byte[] requestBody = requestData.getRequestBodyData();
        Map<String, Set<String>> headerMap = requestData.getHeaderMap();
        RequestMethod requestMethod = requestData.getRequestMethod();

        logger.debug("Request URL : " + url.toString());

        if (requestBody != null) {
            logger.debug("Request BodyLength : " + requestBody.length);
        }

        logger.debug("Request Header : " + headerMap.toString());
        logger.debug("Request Method : " + requestMethod.name());

        HttpURLConnection connection = null;
        if (requestData.isUseProxy()){
            connection = (HttpURLConnection) ConnectionFactory.getUrlConnection(url);
        }else {
            connection = (HttpURLConnection) ConnectionFactory.getNotProxyUrlConnection(url);
        }

//        初始化设置,设置请求头 请求方法 和发送数据等操作
        try {
            connection.setRequestMethod(requestMethod.name());
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setReadTimeout(1000 * 5);

            Set<String> headerKeySet = headerMap.keySet();
            for (String headerKey : headerKeySet) {
                Set<String> valueSet = headerMap.get(headerKey);
                for (String headerValue : valueSet) {
                    connection.addRequestProperty(headerKey, headerValue);
                }
            }

//            当 Connection 获取到 outputStream 的时候,自动将请求方式改成 POST
//            outputData(requestBody,connection.getOutputStream());
            outputData(requestBody, connection);
        } catch (ProtocolException e) {
            throw new RuntimeException("设置参数时出错", e);
        } catch (IOException e) {
            throw new RuntimeException("输出数据到服务器时出错!", e);
        }

        return connection;
    }

    /**
     * 解析响应体
     *
     * @param connectionInputStream 响应体的输入流
     * @param respHeaderMap         响应头的Map
     * @return 响应体数据
     */
    protected byte[] parsingResponse(InputStream connectionInputStream, Map<String, List<String>> respHeaderMap) {
        List<String> strings = respHeaderMap.get("Content-Encoding");

//        判断是否存在有响应体数据的编码，如果没有那么就直接读取流并且转码
        if (strings == null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try {
                IOUtils.copy(connectionInputStream, outputStream);
            } catch (IOException e) {
                throw new RuntimeException("數據傳輸時出錯!", e);
            }

            return outputStream.toByteArray();
        }

        String encodeMethod = strings.get(0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            IOUtils.copy(connectionInputStream, outputStream);
        } catch (IOException e) {
            throw new RuntimeException("數據傳輸時出錯!", e);
        }

        byte[] data = outputStream.toByteArray();

        if (BROTLI_CONTENT_TYPE.equals(encodeMethod)) {
            return DecodeUtils.deCodeBrotliCode(data);
        } else if (GZIP_CONTENT_TYPE.equals(encodeMethod)) {
            return DecodeUtils.deCodeGzipCode(data);
        } else {
            return data;
        }
    }

    /**
     * 发送数据给客户端
     *
     * @param requestBody 请求体
     * @param connection  HttpURLConnection 对象,用于获取 outputStream
     * @throws IOException 输出时发生错误
     */
    protected void outputData(byte[] requestBody, HttpURLConnection connection) throws IOException {
        OutputStream connectionOutputStream = null;
        ByteArrayInputStream requestBodyInputStream = null;

        if (requestBody != null && requestBody.length != 0) {
            connectionOutputStream = connection.getOutputStream();
            requestBodyInputStream = new ByteArrayInputStream(requestBody);

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
