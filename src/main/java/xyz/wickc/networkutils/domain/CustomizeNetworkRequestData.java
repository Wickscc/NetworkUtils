package xyz.wickc.networkutils.domain;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created on 2021/2/14
 *
 * 自定义的 NetworkRequestData 对象, 可以自己定义 HttpUrlConnection!
 *
 * @author wicks
 * @since 1.8
 */
public class CustomizeNetworkRequestData extends NetworkRequestData{
    private static URL DEFAULT_URL;

    static {
        try {
            DEFAULT_URL = new URL("");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private HttpURLConnection httpURLConnection;

    public CustomizeNetworkRequestData() {
        super(DEFAULT_URL,RequestMethod.GET);
    }

    public HttpURLConnection getHttpURLConnection() {
        return httpURLConnection;
    }

    public void setHttpURLConnection(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }
}
