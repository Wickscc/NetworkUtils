package xyz.wickc.networkutils.utils;

import xyz.wickc.networkutils.domain.NetworkResponseData;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Created on 2020/4/24
 *
 * @author wicks
 * @since 1.8
 */
public class HttpResponseDataBuilder {
    private static final String CLASS_NAME = "xyz.wickc.networkutils.domain.NetworkResponseData";

    public static NetworkResponseData builderNetworkResponseData(
            byte[] respBodyData,
            Map<String, List<String>> header,
            int status
    ){
        NetworkResponseData networkResponseData = null;

        try {
            networkResponseData = getObject();
        } catch (Exception e) {
            throw new RuntimeException("创建对象时出错!",e);
        }

        networkResponseData.setHeaderMap(toSetMap(header));
        networkResponseData.setRequestBodyData(respBodyData);
        networkResponseData.setStatusCode(status);

        return networkResponseData;
    }

    private static NetworkResponseData getObject() throws Exception{
        Class<NetworkResponseData> networkResponseDataClass = (Class<NetworkResponseData>) Class.forName(CLASS_NAME);
        Constructor<NetworkResponseData> declaredConstructor = networkResponseDataClass.getDeclaredConstructor();

        declaredConstructor.setAccessible(true);

        return declaredConstructor.newInstance();
    }

    private static Map<String,List<String>> toListMap(Map<String, Set<String>> setMap){
        if (setMap == null){
            throw new RuntimeException("响应头Map为空!");
        }

        Map<String,List<String>> listMap = new HashMap<>();
        Set<String> keySet = setMap.keySet();
        for (String key : keySet) {
            Set<String> valueSet = setMap.get(key);
            listMap.put(key,new ArrayList<>(valueSet));
        }

        return listMap;
    }

    private static Map<String,Set<String>> toSetMap(Map<String, List<String>> listHashMap){
        if (listHashMap == null){
            throw new RuntimeException("响应头Map为空!");
        }

        Map<String,Set<String>> setHashMap = new HashMap<>();
        Set<String> keySet = listHashMap.keySet();
        for (String key : keySet) {
            List<String> valueList = listHashMap.get(key);
            setHashMap.put(key,new HashSet<>(valueList));
        }

        return setHashMap;
    }
}
