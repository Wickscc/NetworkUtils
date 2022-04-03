package xyz.wickc.networkutils.utils;

import org.junit.Test;

import java.util.*;

public class CookieStringUtils {
    /**
     * 将 SetCookie 的集合转换成 Cookie 请求头数据
     *
     * @param setCookieList SetCookie 响应头 List
     * @param botCookie     Cookie请求头,可以为空字符串!
     * @return Cookie 请求头字符串
     */
    public static String getCookieString(List<String> setCookieList, String botCookie) {
        StringBuilder stringBuilder = new StringBuilder(botCookie);
        for (int i = 0; i < setCookieList.size(); i++) {
            String cookieString = setCookieList.get(i);

            if (!stringBuilder.toString().endsWith(";")) {
                stringBuilder.append(";");
            }

            String[] cookieArr = cookieString.split(";");
            stringBuilder.append(cookieArr[0]);

            if (i != setCookieList.size() - 1) {
                stringBuilder.append(";");
            }
        }

        String cookieStr = stringBuilder.toString();
        if (cookieStr.startsWith(";")) {
            cookieStr = new String(cookieStr.toCharArray(), 1, cookieStr.length() - 1);
        }

        return cookieStr;
    }

    /**
     * 将 SetCookie 的集合转换成 Cookie 请求头数据
     *
     * @param botCookie Cookie请求头,可以为空字符串!
     * @param cookie    SetCookie 响应头 List
     * @return cookie字符串
     */
    public static String getCookieString(String botCookie, String... cookie) {
        List<String> stringList = new ArrayList<>(Arrays.asList(cookie));
        return getCookieString(stringList, botCookie);
    }


    /**
     * 通过 Cookie 请求头字符串转换Map
     *
     * @param cookieStr Cookie请求头
     * @return CookieMap
     */
    public static Map<String, String> toCookieMap(String cookieStr) {
        List<String> cookieList = toCookieList(cookieStr);
        Map<String, String> cookieMap = new LinkedHashMap<>();

        for (String s : cookieList) {
            String[] rowCookieString = s.split("=");

//            判断是否是没有值的 Cookie 键
            if (rowCookieString.length >= 2) {
                cookieMap.put(rowCookieString[0], rowCookieString[1]);
            } else {
                cookieMap.put(rowCookieString[0], "");
            }
        }

        return cookieMap;
    }

    public static String buildCookie(Map<String, String> cookieMap) {
        StringBuilder stringBuilder = new StringBuilder();

        Set<String> keySet = cookieMap.keySet();
        for (String key : keySet) {
            stringBuilder.append(key).append("=").append(cookieMap.get(key)).append(";");
        }

        return stringBuilder.toString();
    }

    /**
     * 通过Cookie请求头转换成单个Cookie列表
     *
     * @param cookieStr Cookie 请求头
     * @return Cookie List
     */
    public static List<String> toCookieList(String cookieStr) {
        String[] rowCookieArray = cookieStr.split(";");
        for (int i = 0; i < rowCookieArray.length; i++) {
            rowCookieArray[i] = rowCookieArray[i].trim();
        }

        return Arrays.asList(rowCookieArray);
    }

    @Test
    public void fun() {
        List<String> cookieList = new ArrayList<>();
        cookieList.add("PHPSESSID=d7ubk793584cocrvrml6mfth77; path=/");

        System.out.println(getCookieString(cookieList, "__cfduid=d1db3caf275aa45e5a9812038fb49ed211567254723; cf_clearance=e703a05486e4de59da775928743502d3a2dfd5b4-1567254723-1800-150"));
    }
}
