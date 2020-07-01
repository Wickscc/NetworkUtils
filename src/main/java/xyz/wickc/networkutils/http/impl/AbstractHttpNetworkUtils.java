package xyz.wickc.networkutils.http.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.exception.ConfigException;
import xyz.wickc.networkutils.exception.NetworkException;
import xyz.wickc.networkutils.http.CustomizeHttpNetworkUtils;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.utils.ConnectionFactory;

import java.net.HttpURLConnection;
import java.net.URLConnection;

/**
 * Created on 2020/6/23
 *
 * HttpNetworkUtils的基础实现,在此类上添加缓存的功能
 *
 * @author wicks
 * @since 1.8
 */
public abstract class AbstractHttpNetworkUtils implements HttpNetworkUtils, CustomizeHttpNetworkUtils {
    private static final int MAX_TRY = 3;
    private static Logger logger = LoggerFactory.getLogger(AbstractHttpNetworkUtils.class);

    /**
     * 具有简单的重试功能的实现
     * @param requestData 请求数据
     * @return ResponseData
     */
    @Override
    public NetworkResponseData readPage(NetworkRequestData requestData) {
        NetworkResponseData responseData = null;
        URLConnection urlConnection = ConnectionFactory.getUrlConnection(requestData.getUrl());

        int teyLength = 0;
        while (true){
            try {
                responseData = seanRequest((HttpURLConnection) urlConnection,requestData);
            } catch (Exception e) {
                teyLength += 1;

                logger.debug("请求时发生错误 \n 错误类型: " + e.getClass().getName() + "\n 错误信息: " + e.getMessage());
                if (logger.isDebugEnabled()){
                    e.printStackTrace();
                }

                if (teyLength == MAX_TRY){
                    throw new RuntimeException("URL 链接失败! URL地址: " + requestData.getUrl().toString(),e);
                }else{
                    continue;
                }
            }

            break;
        }

        return responseData;
    }


//    /**
//     * 发送请求
//     * @param requestData 请求数据
//     * @return 响应数据
//     * @throws NetworkException 网络传输时抛出的异常
//     * @throws ConfigException NetworkRequestData 配置错误时候抛出的异常
//     */
//    public abstract NetworkResponseData seanRequest(NetworkRequestData requestData)
//            throws NetworkException, ConfigException;

    /**
     * 发送请求
     * @param requestData 请求数据
     * @param connection Connection 对象,需要自行关闭 Connection 对象!
     * @return 响应数据
     * @throws NetworkException 网络传输时抛出的异常
     * @throws ConfigException NetworkRequestData 配置错误时候抛出的异常
     */
    @Override
    public abstract NetworkResponseData seanRequest(HttpURLConnection connection,NetworkRequestData requestData)
            throws NetworkException, ConfigException;
}
