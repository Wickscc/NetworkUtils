package xyz.wickc.networkutils;

import org.apache.commons.io.IOUtils;
import xyz.wickc.networkutils.domain.RequestData;
import xyz.wickc.networkutils.domain.ResponseData;
import xyz.wickc.networkutils.exception.ConfigException;
import xyz.wickc.networkutils.exception.NetworkException;
import xyz.wickc.networkutils.utils.DecodeUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created on 2019/10/10
 *
 * @author wicks
 * @since 1.8
 */
public class NetworkUtils {
    /**
     *  获取失败重试最多次数
     */
    private int MAX_TRY = 3;

    /**
     * 可信任的状态码
     */
    private List<Integer> trustCode  = new ArrayList<>();

    // 获取Json缓存,其中的键为获取的URL包含GET参数!
    private Map<URL,ResponseData> responseDataHashMap = new HashMap<>();

    private boolean useCache = true;

    public int getMAX_TRY() {
        return MAX_TRY;
    }

    public void setMAX_TRY(int MAX_TRY) {
        this.MAX_TRY = MAX_TRY;
    }

    public boolean isUseCache() {
        return useCache;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public void setTrustCode(int respCode) throws NetworkException {
        if (respCode < 200 || respCode > 500){
            throw new NetworkException("设置错误,状态码应该是在 200 - 500之间的数值");
        }else{
            trustCode.add(respCode);
        }
    }

    public List<Integer> getTrustCode() {
        return trustCode;
    }

    public void setTrustCode(List<Integer> trustCode) {
        this.trustCode = trustCode;
    }

    private Proxy proxy = null;

    public NetworkUtils() {
    }

    public NetworkUtils(Proxy proxy) {
        this.proxy = proxy;
    }

    /**
     * 获取网页返回数据,如果需要发送数据直接在requestData中设置reqBody即可
     * @param requestData 请求对象,包含了请求头 请求体,请求方法
     * @return 响应对象,包含 Cookie 响应头
     * @throws NetworkException 网络异常
     * @throws IOException 网页读取时期异常
     */
    public ResponseData readPage(RequestData requestData) throws NetworkException, IOException {
        ResponseData bufferedRespData = responseDataHashMap.get(requestData.getUrl());

        // 如果使用缓存,并且查询到对应的Resp
        if (bufferedRespData != null && useCache){
            return bufferedRespData;
        }

        URL url = requestData.getUrl();
        HttpURLConnection connection = null;

        if (proxy == null){
            connection = (HttpURLConnection) url.openConnection();
        }else{
            connection = (HttpURLConnection) url.openConnection(proxy);
        }

        connection.setDoOutput(true);
        connection.setDoInput(true);

        Set<String> keySet = requestData.getRequestHread().keySet();
        for(String key : keySet){
            String value = requestData.getRequestHread().get(key);
            connection.addRequestProperty(key,value);
        }

        try {
            connection.setRequestMethod(requestData.getRequestMethod());
        } catch (ProtocolException e) {
            throw new ConfigException(e);
        }

        if (requestData.getRequestBody() != null){
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),"UTF-8"));
                writer.write(requestData.getRequestBody());
                writer.flush();
                writer.close();
            }catch (IOException e){
                throw new NetworkException(e);
            }
        }

        int tryLen = 0;

        while (true){
            try {
                connection.connect();
                if (connection.getResponseCode() != 200 && !trustCode.contains(connection.getResponseCode())){
                    throw new IOException();
                }else{
                    break;
                }
            } catch (IOException e) {
                try {
                    System.out.println("响应码 : "+ connection.getResponseCode());
                    System.out.println("响应信息 : " + connection.getResponseMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println("连接失败,正在重试" + tryLen + "/" + MAX_TRY);
            }

            tryLen ++;

            if (tryLen == MAX_TRY) {
                throw new NetworkException("重连次数过多,请检查网络与代理设置是否正常!");
            }
        }

        Map<String, List<String>> respHread = connection.getHeaderFields();

        byte[] bytes = null;

        if (respHread.get("Content-Encoding") != null &&"gzip".equals(respHread.get("Content-Encoding").get(0))){
            bytes = DecodeUtils.deCodeGzipCode(connection.getInputStream());
        }else{
            try {
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                IOUtils.copy(inputStream,byteArrayOutputStream);

                bytes = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        assert bytes != null;
        String resp = new String(bytes, StandardCharsets.UTF_8);

        ResponseData responseData = new ResponseData(respHread,resp);
        responseDataHashMap.put(requestData.getUrl(),responseData);
        responseData.setRowRespBody(bytes);

        return responseData;
    }

    /**
     * 下载文件到指定的目录下,在Jar包或者项目路径下创建Download文件夹,并且创建每一个天日期的文件夹
     * 之所以不使用目录打散是因为并不需要!
     * 如果文件已经存在,那么就直接跳过!
     * @param path 下载的目录
     * @param requestData 请求头相关设置
     * @param fileName 表示文件下载到文件目录中后的文件名称!
     * @throws NetworkException 网络错误抛出
     */
    public void downloadUrl(String fileName,File path,RequestData requestData) throws NetworkException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

//        File file = new File(path.getAbsolutePath() + "//" + simpleDateFormat.format(new Date(System.currentTimeMillis())));

        File file = new File(path.getAbsolutePath());
        file.mkdirs();

        HttpURLConnection connection = (HttpURLConnection) ConnectionFactory.getUrlConnection(requestData.getUrl().toString());
        connection.setDoOutput(true);
        connection.setDoInput(true);

        Set<String> keySet = requestData.getRequestHread().keySet();
        for(String key : keySet){
            String value = requestData.getRequestHread().get(key);
            connection.addRequestProperty(key,value);
        }

        try {
            connection.setRequestMethod(requestData.getRequestMethod());
            connection.connect();

            File downloadFile = new File(file.getAbsoluteFile() + "//" + fileName);
            if (downloadFile.exists()){
                return;
            }

            OutputStream outputStream = new FileOutputStream(downloadFile);
            InputStream inputStream = connection.getInputStream();

            IOUtils.copy(inputStream,outputStream);
        } catch (ProtocolException e) {
            throw new ConfigException(e);
        } catch (IOException e) {
            throw new NetworkException(e);
        }
    }
}
