package xyz.wickc.networkutils.utils;

import xyz.wickc.networkutils.domain.RequestData;
import xyz.wickc.networkutils.domain.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Arrays;

/**
 * Created on 2020/2/18
 *
 * 用于将请求头的 Row 格式进行解析,Row 格式是这样的
 *
 *      [Request Method] [RequestPath] [Protocol]/[Protocol version]
 *      [Headers] : [Value]
 *      ...
 *
 *      [Request Body]
 *
 * @author wicks
 * @since 1.8
 */
public class RequestRowParsing {
    public static RequestData parsingRowRequest(Reader rowData) throws IOException {
        RequestData requestData = new RequestData();
        LineNumberReader lineNumberReader = new LineNumberReader(new BufferedReader(rowData));
        String requestPath = null;
        String requestProtocol = null;
        String host = null;
        String requestMethod = null;

        boolean isRequestBody = false;
        String requestBody = null;

        String lineData = null;
        while((lineData = lineNumberReader.readLine()) != null){
//            判断是否是第一行,如果是则将信息抽取出来!
            if(lineNumberReader.getLineNumber() == 1){
                String[] strarr = lineData.split(" ");

                requestMethod = strarr[0].toUpperCase();
                requestData.setRequestMethod(RequestMethod.valueOf(strarr[0].toUpperCase()));
                requestPath = strarr[1];

                String[] tempArr = strarr[2].split("/");
                requestProtocol = tempArr[0];

                continue;
            }

//            判断是否到了请求头与请求体之间的空行!
            if ("".equals(lineData)){
                if ("POST".equals(requestMethod)){
                    break;
                }
                isRequestBody = true;

                continue;
            }

//            如果在之前判断为请求头部分结束则开始将请求体部分输入到 String 中!
            if (isRequestBody && "POST".equals(requestMethod)){
                requestBody = lineData;

                continue;
            }

            String[] hvArr = lineData.split(": ");

            String header = hvArr[0];
            String value = null;

            try {
                value = hvArr[1];
            }catch (Exception e){
                throw new RuntimeException("ArrayError LineData=" + lineData);
            }

            if ("Host".equals(header)){
                host = value;
            }

            requestData.setHread(header,value);
        }

        requestData.setUrl(requestProtocol.toLowerCase() + "://" + host +requestPath);
        requestData.setRequestBody(requestBody);

        return requestData;
    }
}
