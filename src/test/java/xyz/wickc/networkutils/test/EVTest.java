package xyz.wickc.networkutils.test;

import org.junit.Test;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;
import xyz.wickc.networkutils.domain.RequestMethod;
import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.http.HttpNetworkUtilsFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2020/4/27
 *
 * @author wicks
 * @since 1.8
 */
public class EVTest {
    @Test
    public void testURL() throws Exception{
        String urlA = "https://www.fiddler2.com/UpdateCheck.aspx?isBeta=False";
        String urlB = "/UpdateCheck.aspx?isBeta=False";

        new URL(urlA);
//        new URL(urlB);
    }
}
