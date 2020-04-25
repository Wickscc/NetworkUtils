package xyz.wickc.networkutils.test;

import org.junit.Test;
import xyz.wickc.networkutils.utils.CookieStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2020/4/24
 *
 * @author wicks
 * @since 1.8
 */
public class CookieUtilsTest {

    private static final String testCookieString = "uuid_tt_dd=10_19018531760-1587706136922-364456; dc_session_id=10_1587706136922.920809; TY_SESSION_ID=d563d2f9-f4eb-4f55-94d8-c790ec052b8b; c_first_ref=cn.bing.com; c_first_page=https%3A//blog.csdn.net/qq_27093465/article/details/52180865; dc_tos=q9a1w9; c_ref=https%3A//cn.bing.com/; dc_sid=769e4a28b76971c56c05730bb19e5b61; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1587706139; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1587706139; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=6525*1*10_19018531760-1587706136922-364456; c-login-auto=1; remove=true; announcement=%257B%2522isLogin%2522%253Afalse%252C%2522announcementUrl%2522%253A%2522https%253A%252F%252Fblog.csdn.net%252Fblogdevteam%252Farticle%252Fdetails%252F105203745%2522%252C%2522announcementCount%2522%253A0%252C%2522announcementExpire%2522%253A3600000%257D; c_adb=1;loginToken=WicksToken1234567";

    @Test
    public void setCookieListTest(){
        List<String> setCookieList = new ArrayList<>();
        setCookieList.add("dc_session_id=10_1587706136922.920809; Expires=Thu, 01 Jan 2025 00:00:00 GMT; Path=/; Domain=.csdn.net");
        setCookieList.add("uuid_tt_dd=10_19018531760-1587706136922-364456; Expires=Thu, 01 Jan 2025 00:00:00 GMT; Path=/; Domain=.csdn.net;");

        String cookieString = CookieStringUtils.getCookieString(setCookieList, "");
        System.out.println(cookieString);
    }

    @Test
    public void cookieStringTest(){
        String cookieString = CookieStringUtils.getCookieString(testCookieString,"loginToken=WicksToken1234567");
        System.out.println(cookieString);
    }

    @Test
    public void toCookieMapTest(){
        Map<String, String> map = CookieStringUtils.toCookieMap(testCookieString);

        System.out.println(map);
    }
}
