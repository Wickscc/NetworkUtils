package xyz.wickc.networkutils.http;

import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.exception.NetworkException;

/**
 * Created on 2020/4/24
 *
 * @author wicks
 * @since 1.8
 */
public interface HttpNetworkUtils {
    /**
     * 发送请求,并且返回响应
     * @param requestData 请求数据
     * @return 响应数据
     */
    NetworkResponseData readPage(NetworkRequestData requestData);


}
