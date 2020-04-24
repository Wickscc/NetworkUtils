package xyz.wickc.networkutils.utils;

import xyz.wickc.networkutils.domain.RequestData;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

public class URLUtils {
    /**
     * 将请求参数通过URL的方式发送!
     * @param url URL地址
     * @param data 键值对形式的数据
     * @return 完整的URL地址!
     */
    public static String mergeUrl(String url, Map<String,String> data){
        if (data.keySet().size() == 0){
            return url;
        }

        int len = 0;
        StringBuilder builder = new StringBuilder(url + "?");
        Set<String> ketSet = data.keySet();
        for(String key : ketSet){

            if (key.equals("&&&")){
                continue;
            }

            String value = data.get(key);

            if (key == null || key.isEmpty()){
                throw new RuntimeException("提交的参数有误!!!");
            }

            if (value == null){
                value = "";
            }

            builder.append(key).append("=").append(value);

            if (!(len == (ketSet.size() - 1))){
                builder.append("&");
            }

            len ++;
        }

        return new String(builder);
    }

    /**
     * 将已经转好码的URL解码出参数
     * 返回值内的Map集合通过 键 {@literal &}{@literal &}{@literal &} 存储着原始URL!
     * @param url 需要解码的Url
     * @return URL键值对形式的参数
     * @throws ParseException URL创建异常
     */
    public static Map<String,String> deMergeUrl(String url) throws ParseException {
        String[] rowValue = url.split("\\?");
        if (rowValue.length != 2){
            throw new ParseException("输入的网址不正确!",0);
        }



        Map<String,String> urlValue = new HashMap<>();
        String[] strArr = rowValue[1].split("&");
        for(String rowKV : strArr){
            String[] kv = rowKV.split("=");

            if (kv.length < 2){
                kv = Arrays.copyOf(kv,2);
            }

            String key = kv[0];
            String value = kv[1];

            urlValue.put(key,value);
        }
        urlValue.put("&&&",rowValue[0]);
        return urlValue;
    }
}
