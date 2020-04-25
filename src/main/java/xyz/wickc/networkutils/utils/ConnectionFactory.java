package xyz.wickc.networkutils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.Properties;

/**
 * @author WicksChen
 * @date 2020/04/24
 */
public class ConnectionFactory {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
    private static Proxy proxy;

    public static Proxy getProxy() {
        return proxy;
    }

    public static void setProxy(Proxy proxy) {
        ConnectionFactory.proxy = proxy;
    }

    /**
     * 通过 URL 来获取 Connection 对象
     * @param url URL 对象
     * @return Connection 对象
     */
    public static URLConnection getUrlConnection(URL url){
        URLConnection urlConnection = null;

        if (url == null){
            throw new RuntimeException("URL 不能为Null!");
        }

        try {
            if (proxy == null){
                urlConnection = url.openConnection();
            }else{
                urlConnection = url.openConnection(proxy);
            }
        } catch (IOException e) {
            throw new RuntimeException("URL对象开启 Connection 失败!",e);
        }

        return urlConnection;
    }
}
