package xyz.wickc.networkutils.http;

import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.exception.ConfigException;
import xyz.wickc.networkutils.exception.NetworkException;

import java.net.HttpURLConnection;

/**
 * Created on 2020/7/1
 *
 * @author wicks
 * @since 1.8
 */
public interface CustomizeHttpNetworkUtils {
    /**
     * 发送请求数据 , 定制 Connection 对象发送
     * @param connection connection
     * @param requestData requestData
     * @return RESP DATA
     * @throws NetworkException 网络链接出现问题
     * @throws ConfigException requestData 配置出现问题
     */
    NetworkResponseData seanRequest(HttpURLConnection connection, NetworkRequestData requestData)
            throws NetworkException, ConfigException;
}
