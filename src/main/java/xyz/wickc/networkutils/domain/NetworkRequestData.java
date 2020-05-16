package xyz.wickc.networkutils.domain;

import org.hamcrest.core.Is;
import xyz.wickc.networkutils.utils.CookieStringUtils;
import xyz.wickc.networkutils.utils.URLUtils;

import java.net.URL;
import java.util.*;

/**
 * Created on 2020/4/24
 *
 * 新版 RequestData
 *
 * @author wicks
 * @since 1.8
 */
public class NetworkRequestData extends NetworkData{
    /**
     * 信任的状态码
     */
    private int[] trustStatusCode;

    /**
     * 设置 GET 方式下的请求参数
     * 在请求方法是 POST 的情况下也会使用!
     *
     * 格式 : ?key=value&key=value
     * 可以通过 {@link URLUtils} 里边的方法使用 Map 的方式转换出来!
     */
    private String queryData;

    public int[] getTrustStatusCode() {
        if (trustStatusCode == null){
            return new int[0];
        }
        return trustStatusCode;
    }

    public void setTrustStatusCode(int ... trustStatusCode) {
        this.trustStatusCode = trustStatusCode;
    }

    /**
     * 默认情况下,需要使用 URL 地址加上请求方法来构造请求对象
     * @param url URL 地址
     * @param requestMethod 请求方法
     */
    public NetworkRequestData(URL url,RequestMethod requestMethod) {
        super(url,requestMethod);
    }

    public void setContentType(String contentType){
        super.addHeader("content-type",contentType);
    }

    /**
     * 设置Cookie
     * @param cookieString cookie请求头字符串
     */
    public void setCookie(String cookieString){
        super.addHeader("cookie", CookieStringUtils.getCookieString(cookieString));
    }

    /**
     * 设置Cookie
     * @param cookieList SetCookie响应头数据
     */
    public void setCookie(List<String> cookieList){
        super.addHeader("cookie", CookieStringUtils.getCookieString(cookieList,""));
    }

    /**
     * 设置 UA 绕过
     * @param userAgent UA String
     */
    public void setUserAgent(String userAgent){
        super.addHeader("User-Agent",userAgent);
    }

    public String getQueryData() {
        return queryData;
    }

    public void setQueryData(String queryData) {
        this.queryData = queryData;
    }
}
