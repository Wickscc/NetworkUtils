package xyz.wickc.networkutils.test;

import org.junit.Test;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.domain.RequestMethod;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.http.HttpNetworkUtilsFactory;
import xyz.wickc.networkutils.utils.ConnectionFactory;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

/**
 * Created on 2020/4/24
 *
 * @author wicks
 * @since 1.8
 */
public class HttpUrlAria2DownloadNetworkUtilsTest {
    @Test
    public void baseTest() throws MalformedURLException {
        HttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getHttpNetworkUtils();

        NetworkRequestData requestData = new NetworkRequestData(new URL("http://rrys2019.com"), RequestMethod.GET);
        requestData.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");

        NetworkResponseData networkResponseData = httpNetworkUtils.readPage(requestData);

        System.out.println(new String(networkResponseData.getRequestBodyData()));
    }


}
