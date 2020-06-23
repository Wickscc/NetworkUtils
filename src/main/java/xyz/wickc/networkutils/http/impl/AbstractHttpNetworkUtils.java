package xyz.wickc.networkutils.http.impl;

import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.exception.ConfigException;
import xyz.wickc.networkutils.exception.NetworkException;
import xyz.wickc.networkutils.http.HttpNetworkUtils;

/**
 * Created on 2020/6/23
 *
 * HttpNetworkUtils的基础实现,在此类上添加缓存的功能
 *
 * @author wicks
 * @since 1.8
 */
public abstract class AbstractHttpNetworkUtils implements HttpNetworkUtils {

    /**
     * 具有简单的重试功能和缓存功能的实现
     * @param requestData 请求数据
     * @return ResponseData
     */
    @Override
    public NetworkResponseData readPage(NetworkRequestData requestData) {

        return null;
    }


    /**
     * 发送请求
     * @param requestData 请求数据
     * @return 响应数据
     * @throws NetworkException 网络传输时抛出的异常
     * @throws ConfigException NetworkRequestData 配置错误时候抛出的异常
     */
    public abstract NetworkResponseData seanRequest(NetworkRequestData requestData) throws NetworkException, ConfigException;
}
