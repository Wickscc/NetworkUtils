package xyz.wickc.networkutils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wickc.networkutils.domain.RequestData;
import xyz.wickc.networkutils.domain.RequestMethod;
import xyz.wickc.networkutils.domain.ResponseData;
import xyz.wickc.networkutils.exception.NetworkException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Proxy;
import java.util.UUID;

/**
 * Created on 2020/3/10
 * 用于请求 Aria
 * @author wicks
 * @since 1.8
 */
public class Aria2Download {
    private static Logger logger = LoggerFactory.getLogger(Aria2Download.class);
    private String requestUrl;
    private String token;
    private NetworkUtils networkUtils;
    private Proxy proxy;

    /**
     * 构造方法
     * @param networkUtils networkUtils
     * @param serverName 服务器IP地址或域名
     * @param port RPC 服务器端口
     * @param token Aria2 的请求令牌,如果没有设置令牌可以直接传递 null 或者 空字符串
     * @param isusessl 是否使用 HTTPS
     */
    public Aria2Download(NetworkUtils networkUtils,String serverName, int port,String token,boolean isusessl) {
        this.token = token;
        String protocol = null;
        if (isusessl){
            protocol = "https";
        }else {
            protocol = "http";
        }

        this.requestUrl = protocol + "://" + serverName + ":" + port + "/jsonrpc";
        this.proxy = getNetworkUtilsProxy(networkUtils);
        this.networkUtils = new NetworkUtils(proxy);


        logger.debug("Proxy Object ToString : " + this.proxy);
        logger.debug("RequestUrl : " + this.requestUrl);
    }

    /**
     * 使用 Aria2 来下载指定的文件!
     * @param fileName 保存的文件名称
     * @param path 保存的路径
     * @param uriArr 下载文件链接
     * @throws NetworkException 如果网络发生了一些问题,则会抛出这个异常
     */
    public void downloadUrl(String fileName, String path,String... uriArr) throws NetworkException{
        String request = builderRequestBody(fileName,path,uriArr);

        RequestData requestData = new RequestData();
        requestData.setUrl(requestUrl);
        requestData.setRequestBody(request);
        requestData.setRequestMethod(RequestMethod.POST);
        requestData.setHread("content-type","application/json;charset=UTF-8");

        ResponseData responseData = null;

        logger.debug("SandJSON : " + request);

        try {
            responseData = networkUtils.readPage(requestData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject resultJsonObject = JSON.parseObject(responseData.getRespBody());

        JSONObject errorJsonObject = resultJsonObject.getJSONObject("error");

        if (errorJsonObject != null){
            logger.error("Aria2 AddUri Error Message : " + errorJsonObject.getString("message"));
        }
    }

    private String builderRequestBody(String fileName,String path,String... uriArr){
        JSONObject addUriJson = new JSONObject();
        addUriJson.put("jsonrpc","2.0");
        addUriJson.put("id", UUID.randomUUID().toString().replace("-",""));
        addUriJson.put("method","aria2.addUri");

        JSONArray paramsJson = new JSONArray();
        if (token != null && !token.isEmpty()){
            paramsJson.add("token:"+token);
        }

        JSONObject optionJson = new JSONObject();
        optionJson.put("dir",path);
        optionJson.put("out",fileName);

        JSONArray downloadUri = (JSONArray) JSON.toJSON(uriArr);

        paramsJson.add(downloadUri);
        paramsJson.add(optionJson);

        addUriJson.put("params",paramsJson);

        return addUriJson.toJSONString();
    }

    /**
     * 通过反射获取 NetworkUtils 中的 Proxy
     * 懒出了新的境界!
     * @return Proxy
     */
    private Proxy getNetworkUtilsProxy(NetworkUtils networkUtils){

        if (networkUtils == null){
            throw new RuntimeException("NetworkUtils 不能为空!");
        }

        Class networkUtilsClass = networkUtils.getClass();
        Field proxyField = null;
        try {
            proxyField = networkUtilsClass.getDeclaredField("proxy");
            proxyField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        assert proxyField != null;
        try {
            return (Proxy) proxyField.get(networkUtils);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
