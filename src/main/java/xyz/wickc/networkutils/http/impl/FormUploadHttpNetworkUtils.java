package xyz.wickc.networkutils.http.impl;

import xyz.wickc.networkutils.domain.FormUploadNetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.http.HttpNetworkUtilsFactory;
import xyz.wickc.networkutils.utils.ConnectionFactory;

import java.net.HttpURLConnection;
import java.net.URLConnection;

/**
 * Created on 2020/7/2
 *
 * @author wicks
 * @since 1.8
 */
public class FormUploadHttpNetworkUtils extends SimpleHttpNetworkUtils implements HttpNetworkUtils {

    /**
     * 如果需要实现表单上传的功能,那么就得传递的是 FormUploadNetworkRequestData 对象!
     * @param requestData 请求数据
     * @return NetworkResponseData
     */
    @Override
    public NetworkResponseData readPage(NetworkRequestData requestData) {
        if (!(requestData instanceof FormUploadNetworkRequestData)){
            return super.readPage(requestData);
        }

        return null;
    }
}
