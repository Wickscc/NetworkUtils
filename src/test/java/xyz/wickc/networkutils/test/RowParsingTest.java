package xyz.wickc.networkutils.test;

import org.junit.Test;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.RequestMethod;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.utils.RequestRowParsing;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created on 2020/10/20
 *
 * @author wicks
 * @since 1.8
 */
public class RowParsingTest {
    @Test
    public void toRow() throws MalformedURLException {
        NetworkRequestData networkRequestData = new NetworkRequestData(
                new URL("https://bttv.wickc.xyz:8443"),
                RequestMethod.POST
        );

        networkRequestData.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");
        networkRequestData.addHeader("Referer","https://translate.google.cn/");

        networkRequestData.setContentType("application/json;charset=UTF-8");
        networkRequestData.setCookie("JSEISSONID=12345678901234567890123456789012;USER_TOKEN=12345678901234567890123456789012");
        networkRequestData.setRequestBodyData("{\"TEST_DATA\":\"TEST\"}".getBytes());

        String rowRequest = RequestRowParsing.parsingToRow(networkRequestData);
        System.out.println(rowRequest);
    }
}