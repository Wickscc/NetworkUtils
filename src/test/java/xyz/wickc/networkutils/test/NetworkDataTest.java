package xyz.wickc.networkutils.test;

import org.junit.Test;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.RequestMethod;

import java.net.URL;

/**
 * Created on 2020/6/23
 *
 * @author wicks
 * @since 1.8
 */
public class NetworkDataTest {

    @Test
    public void testURL() throws Exception{
        NetworkRequestData requestData = new NetworkRequestData(new URL("https://wickc.xyz:8443/BTTVWeb?apiKey=1231456677"), RequestMethod.GET);
        requestData.setQueryData("name=wicks&password=wicks");

        System.out.println(requestData.getUrl().toString());
    }
}
