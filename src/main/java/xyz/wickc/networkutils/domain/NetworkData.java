package xyz.wickc.networkutils.domain;

import java.net.URL;
import java.util.*;

/**
 * Created on 2020/4/24
 *
 * @author wicks
 * @since 1.8
 */
public class NetworkData {
    /**
     * 请求头 Map
     */
    private Map<String, Set<String>> headerMap = new HashMap<>();

    /**
     * 请求体数据
     */
    private byte[] requestBodyData;

    /**
     * 请求方式
     */
    private RequestMethod requestMethod;

    /**
     * 请求地址
     */
    private URL url;

    protected NetworkData(URL url,RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    protected NetworkData(){

    }

    /**
     * 添加请求头参数
     * @param key 请求头键
     * @param value 请求头值
     */
    public void addHeader(String key,String ... value){
        Set<String> strings = this.headerMap.get(key);

        if (strings == null) {
            strings = new HashSet<>();
        }

        strings.addAll(Arrays.asList(value));

        this.headerMap.put(key,strings);
    }

    public Map<String, Set<String>> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, Set<String>> headerMap) {
        this.headerMap = headerMap;
    }

    public byte[] getRequestBodyData() {
        return requestBodyData;
    }

    public void setRequestBodyData(byte[] requestBodyData) {
        this.requestBodyData = requestBodyData;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
