package xyz.wickc.networkutils.domain;

import xyz.wickc.networkutils.utils.CookieStringUtils;
import xyz.wickc.networkutils.utils.URLUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019/10/10
 *
 * @author wicks
 * @since 1.8
 */
public class RequestData {
    private Map<String,String> requestHread;
    private Map<String,String> data;
    private String requestBody;

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public RequestData(Map<String, String> requestHread, Map<String, String> data, String requestBody, RequestMethod requestMethod, URL url) {
        this.requestHread = requestHread;
        this.data = data;
        this.requestBody = requestBody;
        this.requestMethod = requestMethod;
        this.url = url;
    }

    private RequestMethod requestMethod;
    private URL url;

    @Override
    public String toString() {
        return "RequestData{" +
                "requestHread=" + requestHread +
                ", data=" + data +
                ", requestMethod=" + requestMethod +
                ", url=" + url +
                '}';
    }

    public void setUrl(String url){
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public URL getUrl() {
        try {
            return new URL(URLUtils.mergeUrl(url.toString() ,data));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public RequestData(Map<String, String> requestHread, Map<String, String> data, RequestMethod requestMethod, URL url) {
        this.requestHread = requestHread;
        this.data = data;
        this.requestMethod = requestMethod;
        this.url = url;
    }

    public Map<String, String> getRequestHread() {
        return requestHread;
    }

    public void setRequestHread(Map<String, String> requestHread) {
        requestHread.putAll(requestHread);
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        data.putAll(data);
    }

    public RequestData() {
        this.requestHread = new HashMap<>();
        this.data = new HashMap<>();
    }

    public RequestData(Map<String, String> requestHread, Map<String, String> data, RequestMethod requestMethod) {
        this.requestHread = requestHread;
        this.data = data;
        this.requestMethod = requestMethod;
    }

    public void setData(String key, String value){
        data.put(key,value);
    }

    public String getData(String key){
        return data.get(key);
    }

    public void setHread(String key,String value){
        requestHread.put(key,value);
    }

    public String getHread(String key,String value){
        return requestHread.get(key);
    }

    public void setCookie(String cookieStr){
        if (requestHread.get("cookie") != null){
            requestHread.put("cookie", CookieStringUtils.getCookieString(requestHread.get("cookie"),cookieStr));
        }else {
            requestHread.put("cookie", cookieStr);
        }
    }

    public void delCookie(){
        requestHread.put("cookie","");
    }

    public String getCookie(){
        return requestHread.get("cookie");
    }

    public void setUA(String ua){
        requestHread.put("user-agent",ua);
    }

    public String getRequestMethod() {
        if (requestMethod == null){
            return "GET";
        }else {
            return requestMethod.toString();
        }
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }
}
