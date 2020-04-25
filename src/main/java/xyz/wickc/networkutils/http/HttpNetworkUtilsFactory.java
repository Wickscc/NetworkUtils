package xyz.wickc.networkutils.http;

import com.sun.deploy.net.HttpResponse;
import xyz.wickc.networkutils.http.impl.SimpleHttpNetworkUtils;

/**
 * Created on 2020/4/24
 *
 * @author wicks
 * @since 1.8
 */
public class HttpNetworkUtilsFactory {
    public static HttpNetworkUtils getHttpNetworkUtils(){
        return new SimpleHttpNetworkUtils();
    }
}
