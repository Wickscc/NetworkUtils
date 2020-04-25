package xyz.wickc.networkutils.test;

import org.junit.Test;
import xyz.wickc.networkutils.domain.RequestMethod;
import xyz.wickc.networkutils.http.application.Aria2Download;
import xyz.wickc.networkutils.exception.NetworkException;
import xyz.wickc.networkutils.http.application.impl.HttpUrlAria2Download;
import xyz.wickc.networkutils.utils.ConnectionFactory;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

/**
 * Created on 2020/3/10
 *
 * @author wicks
 * @since 1.8
 */
public class Aria2DownloadTest {
    private Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));

    @Test
    public void downloadTestFile() {
        ConnectionFactory.setProxy(proxy);

        Aria2Download aria2Download = null;
        try {
            aria2Download = new HttpUrlAria2Download(
                    new URL("http://192.168.123.159:6800/jsonrpc"),
                    new URL("http://192.168.123.152:8888"),
                    "ER3tNT8BIfT8q",
                    RequestMethod.POST
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        aria2Download.downloadUrl(
                "NetworkUtilsDownloadTest_" + System.currentTimeMillis() + ".html",
                "/nasDisk/aria/test",
                "https://wickc.xyz:8443/BTTVWeb"
        );
    }
}
