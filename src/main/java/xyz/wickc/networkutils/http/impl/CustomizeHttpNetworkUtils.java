package xyz.wickc.networkutils.http.impl;

import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;

import java.net.HttpURLConnection;

/**
 * Created on 2020/7/2
 *
 * 此类直接继承的是 SimpleHttpNetworkUtils 而非实现接口 , 所以此类是可以作为包装类的根类使用的!
 *
 * @author wicks
 * @since 1.8
 */
public class CustomizeHttpNetworkUtils extends SimpleHttpNetworkUtils{
    private HttpURLConnection httpURLConnection;

    public CustomizeHttpNetworkUtils(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }

    public HttpURLConnection getHttpURLConnection() {
        return httpURLConnection;
    }

    /**
     * 重新对 httpURLConnection 赋值
     * @param httpURLConnection connection对象
     */
    public void setHttpURLConnection(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }


    /**
     * 忽略 requestData 直接返回构造方法中传入的 httpURLConnection 对象
     * @param requestData NetworkRequestData
     * @return this.httpURLConnection
     */
    @Override
    protected HttpURLConnection parsingRequestData(NetworkRequestData requestData) {
        return this.httpURLConnection;
    }
}
