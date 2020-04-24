package xyz.wickc.networkutils.test;

import org.junit.Test;
import xyz.wickc.networkutils.Aria2Download;
import xyz.wickc.networkutils.NetworkUtils;
import xyz.wickc.networkutils.exception.NetworkException;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Created on 2020/3/10
 *
 * @author wicks
 * @since 1.8
 */
public class Aria2DownloadTest {

    private Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",8888));

    @Test
    public void initMethodTest() throws NetworkException {
        NetworkUtils networkUtils = new NetworkUtils(proxy);
        Aria2Download aria2Download = new Aria2Download(networkUtils,"192.168.123.159",6800,"ER3tNT8BIfT8q",false);
        aria2Download.downloadUrl("BTTVWebIndex.html","/nasDisk/aria/html","https://wickc.xyz:8443/BTTVWeb/index.html");
    }
}
