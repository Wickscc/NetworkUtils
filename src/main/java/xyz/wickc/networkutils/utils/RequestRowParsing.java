package xyz.wickc.networkutils.utils;

import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.URL;
import java.util.*;

/**
 * Created on 2020/2/18
 * <p>
 * 用于将请求头的 Row 格式进行解析,Row 格式是这样的
 * <p>
 * [Request Method] [RequestPath] [Protocol]/[Protocol version]
 * [Headers] : [Value]
 * ...
 * <p>
 * [Request Body]
 *
 * @author wicks
 * @since 1.8
 */
public class RequestRowParsing {

    /**
     * 将原始的 HTTP 请求转换成 RequestData
     * 并不支持多部件请求的操作!
     *
     * @param rowData 原始数据
     * @return RequestDataObject
     * @throws IOException 读取文件时出错抛出的异常
     */
    public static NetworkRequestData parsingRowRequest(Reader rowData) throws IOException {
        List<String> fileList = readRowFile(rowData);

        if (fileList.isEmpty()) {
            throw new RuntimeException("指定的请求文件为空!");
        }

        Map<String, Set<String>> headerMap = new HashMap<>(10);
        fileList.stream()
                .skip(1)
                .forEach(s -> {
                    String[] kvArrays = s.split(":");

                    if (kvArrays[0].isEmpty()){
                        return;
                    }

                    if (kvArrays.length == 1) {
                        headerMap.put(kvArrays[0], new HashSet<>());
                    } else if (kvArrays.length == 2) {
                        Set<String> stringList = headerMap.get(kvArrays[0]);
                        if (stringList == null) {
                            stringList = new HashSet<>();
                        }

                        stringList.add(kvArrays[1].trim());
                        headerMap.put(kvArrays[0], stringList);
                    } else{
//                        2020-05-16 更新,优化在请求原始文件中有多个 ":" 符号的情况!
                        String key = kvArrays[0];
                        Set<String> stringSet = new HashSet<>();
                        stringSet.add(s.replace(key + ": ","").trim());

                        headerMap.put(key, stringSet);
                    }
                });

        RequestMethod method = null;
        String url = null;
        String protocol = null;
        URL urlObject = null;

        String[] headerStrArr = fileList.get(0).split(" ");

        if (headerStrArr.length < 2){
            throw new RuntimeException("HTTP 原始格式文件不合法!");
        }

        method = RequestMethod.valueOf(headerStrArr[0]);
        url = headerStrArr[1];


        if (!url.startsWith("https://")){
            String host = headerMap.get("Host").iterator().next();

            if (!url.startsWith("/")){
                url = "/" + url;
            }

            urlObject = new URL("http://" + host.trim() + url.trim());
        }else {
            urlObject = new URL(url);
        }

        NetworkRequestData requestData = new NetworkRequestData(urlObject,method);
        requestData.setHeaderMap(headerMap);

        if (method == RequestMethod.POST){
            requestData.setRequestBodyData(fileList.get(fileList.size() - 1).getBytes("UTF-8"));
        }

        return requestData;
    }

    /**
     * 将 NetworkRequestData 逆向转换成 HTTP ROW 格式的数据
     * @param requestData HTTP 请求对象
     * @return HTTP ROW
     */
    public static String parsingToRow(NetworkRequestData requestData){
        StringBuilder stringBuilder = new StringBuilder();

//        HTTP 请求首行
        URL dataUrl = requestData.getUrl();
        String firstRow = requestData.getRequestMethod().name().toUpperCase() + " " + dataUrl.toString() + " HTTP/1.1";

        stringBuilder.append(firstRow);
        stringBuilder.append("\n");

        requestData.addHeader("Host",dataUrl.getHost());

        Map<String, Set<String>> headerMap = requestData.getHeaderMap();
        Set<String> headerKeySet = headerMap.keySet();
        for (String headerKey : headerKeySet) {
            Set<String> valueSet = headerMap.get(headerKey);
            for (String value : valueSet) {
                stringBuilder.append(headerKey).append(": ").append(value);
                stringBuilder.append("\n");
            }
        }

        boolean hasRequestBody = requestData.getRequestBodyData() != null && requestData.getRequestBodyData().length != 0;
        if (requestData.getRequestMethod() == RequestMethod.POST && hasRequestBody) {
            stringBuilder.append("\n");
            stringBuilder.append(
                    new String(requestData.getRequestBodyData())
            );
        }

        return stringBuilder.toString();
    }

    /**
     * 将文件每一行都读取出来存储在 List 中
     * @param reader 文件的 reader 流
     * @return HTTP HEAD List
     * @throws IOException 读取文件时错误
     */
    private static List<String> readRowFile(Reader reader) throws IOException {

        if (reader == null) {
            throw new RuntimeException("Reader 不能为空!");
        }

        List<String> fileList = new ArrayList<>();
        LineNumberReader lineNumberReader = new LineNumberReader(reader);

        String line = null;
        while ((line = lineNumberReader.readLine()) != null) {
            fileList.add(line);
        }

        return fileList;
    }
}
