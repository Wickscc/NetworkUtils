package xyz.wickc.networkutils.http.application.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.domain.RequestMethod;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.http.HttpNetworkUtilsFactory;
import xyz.wickc.networkutils.http.application.Aria2Download;

import java.net.Proxy;
import java.net.URL;
import java.util.Map;

/**
 * Created on 2020/4/25
 *
 * 使用 Aria2 来下载 HTTP 协议的资源
 *
 * @author wicks
 * @since 1.8
 */
public class HttpUrlAria2Download implements Aria2Download {
    /**
     * 连接 Aria2 JsonRpc API 的地址
     */
    private URL aria2ConnectionUrl;

    /**
     * 代理服务器的 URL 地址,格式如下
     *  [http://][USER:PASSWORD@]HOST[:PORT]
     *
     * 使用 Aria2 来下载时候是否需要用到 Proxy
     * 这里用的 Proxy 是用在 Aria2 服务器上使用的 Proxy!
     * 如果想要在发送数据给Aria2服务器时使用 Proxy 您应该使用 {@link xyz.wickc.networkutils.utils.ConnectionFactory}
     * 的 setProxy(Proxy proxy) 方法!
     */
    private URL proxy;

    /**
     * Aria2 连接时的Token
     */
    private String token;

    /**
     * Aria2 请求方式
     */
    private RequestMethod requestMethod;

    private static Logger logger = LoggerFactory.getLogger(HttpUrlAria2Download.class);

    public HttpUrlAria2Download(URL aria2ConnectionUrl, URL proxy, String token, RequestMethod requestMethod) {
        this.aria2ConnectionUrl = aria2ConnectionUrl;
        this.proxy = proxy;
        this.token = token;
        this.requestMethod = requestMethod;
    }

    public HttpUrlAria2Download(URL aria2ConnectionUrl, String token, RequestMethod requestMethod) {
        this.aria2ConnectionUrl = aria2ConnectionUrl;
        this.token = token;
        this.requestMethod = requestMethod;
    }

    @Override
    public void downloadUrl(String fileName, String path, String url) {
        JSONObject sandJson = builderSandJson(fileName,path,url);
        sandJsonObject(sandJson);
    }

    @Override
    public void downloadUrl(String path,String... urlArr) {
        JSONObject sandJson = builderSandJson(null,path,urlArr);
        sandJsonObject(sandJson);
    }

    /**
     * 构建一个用于发送请求给 RPC 服务器的 JSONObject
     * @return json
     */
    private JSONObject builderSandJson(String fileName, String path, String ...uri){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc","2.0");
        jsonObject.put("method","aria2.addUri");
        jsonObject.put("id","Aria2AddDownloadUrl,T=" + System.currentTimeMillis());

        JSONArray paramsArray = new JSONArray();
        JSONObject optionJson = new JSONObject();

        if (proxy != null){
            optionJson.put("http-proxy",proxy.toString());
            optionJson.put("https-proxy",proxy.toString());
        }

        if (path != null){
            optionJson.put("dir",path);
        }

        if (token != null){
            paramsArray.add("token:"+token);
        }

        if (fileName != null){
            optionJson.put("out",fileName);
        }

        paramsArray.add(JSON.toJSON(uri));
        paramsArray.add(optionJson);

        jsonObject.put("params",paramsArray);

        return jsonObject;
    }

    /**
     * 发送请求给 Aria2 服务器
     */
    private void sandJsonObject(JSONObject sandJson){
        HttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getHttpNetworkUtils();
        NetworkRequestData requestData = new NetworkRequestData(aria2ConnectionUrl,requestMethod);
        requestData.setRequestBodyData(sandJson.toJSONString().getBytes());

        NetworkResponseData responseData = httpNetworkUtils.readPage(requestData);
        JSONObject jsonObject = JSON.parseObject(new String(responseData.getRequestBodyData()));

        JSONObject errorJson = jsonObject.getJSONObject("error");
        if (errorJson != null){
            logger.warn("Aria2 添加任务时出错,返回值为 : " + errorJson.getString("message"));
        }
    }
}
