package xyz.wickc.networkutils.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CookieStringUtils {
    public static String getCookieString(List<String> setCookieList,String botCookie){
        StringBuilder stringBuilder = new StringBuilder(botCookie);
        for (int i = 0;i < setCookieList.size();i ++){
            String cookieString = setCookieList.get(i);

            if (!stringBuilder.toString().endsWith(";")){
                stringBuilder.append(";");
            }

            String[] cookieArr = cookieString.split(";");
            stringBuilder.append(cookieArr[0]);

            if (i != setCookieList.size() - 1){
                stringBuilder.append(";");
            }
        }

        String cookieStr = stringBuilder.toString();
        if (cookieStr.startsWith(";")){
            cookieStr = new String(cookieStr.toCharArray(),1,cookieStr.length() - 1);
        }

        return cookieStr;
    }

    public static String getCookieString(String botCookie,String ... cookie){
        List<String> stringList = new ArrayList<>();
        for (String cookieStr : cookie){
            stringList.add(cookieStr);
        }

        return getCookieString(stringList,botCookie);
    }

    @Test
    public void fun(){
        List<String> cookieList = new ArrayList<>();
        cookieList.add("PHPSESSID=d7ubk793584cocrvrml6mfth77; path=/");

        System.out.println(getCookieString(cookieList,"__cfduid=d1db3caf275aa45e5a9812038fb49ed211567254723; cf_clearance=e703a05486e4de59da775928743502d3a2dfd5b4-1567254723-1800-150"));
    }
}
