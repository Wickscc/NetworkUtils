package xyz.wickc.networkutils.domain;

import xyz.wickc.networkutils.utils.CookieStringUtils;

import java.net.URL;
import java.util.*;

/**
 * Created on 2020/4/24
 *
 * @author wicks
 * @since 1.8
 */
public class NetworkResponseData extends NetworkData{
    private static final String SET_COOKIE_HEADER_KEY = "Set-Cookie";

    private int statusCode;

    /**
     * 只有 NetworkUtils 的工具类才能创建 NetworkResponseData 对象!
     */
    private NetworkResponseData() { }

    /**
     * 获取服务器响应的 Cookie 字符串
     *
     * 在这里返回的 Cookie 字符串同样是类似于
     * @return cookieString
     */
    public String getCookieStr(){
        Map<String, Set<String>> headerMap = super.getHeaderMap();
        Set<String> setCookieSet = headerMap.get(SET_COOKIE_HEADER_KEY.toLowerCase());
        return CookieStringUtils.getCookieString(new ArrayList<>(setCookieSet), "");
    }

    /**
     * 获取 SetCookie List
     * @return SetCookie Header List
     */
    public List<String> getCookieList(){
        Map<String, Set<String>> headerMap = super.getHeaderMap();
        Set<String> setCookieSet = headerMap.get(SET_COOKIE_HEADER_KEY.toLowerCase());
        return new ArrayList<>(setCookieSet);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
