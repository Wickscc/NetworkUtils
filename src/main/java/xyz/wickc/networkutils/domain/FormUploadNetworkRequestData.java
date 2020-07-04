package xyz.wickc.networkutils.domain;

import xyz.wickc.networkutils.utils.FormUploadBodyBuilder;
import xyz.wickc.networkutils.utils.URLUtils;

import java.net.URL;
import java.text.ParseException;
import java.util.*;

/**
 * Created on 2020/7/2
 *
 * @author wicks
 * @since 1.8
 */
public class FormUploadNetworkRequestData extends NetworkRequestData {
    private static final String SPLINE = "--------------------------";

    private Map<String, byte[]> uploaderForm = new HashMap<>();
    private Map<String,String> textFrom = new HashMap<>();
    private String delimiter;

    /**
     * 默认情况下,需要使用 URL 地址加上请求方法来构造请求对象
     *
     * @param url           URL 地址
     */
    public FormUploadNetworkRequestData(URL url) {
        super(url, RequestMethod.POST);
    }

    public void addUploaderForm(String contentType,String fileName,String key, byte[] value){
        uploaderForm.put(key + "&" + contentType + "&" + fileName,value);
    }

    public void addTextFrom(String key,String value){
        textFrom.put(key,value);
    }

    /**
     * 强制性的转换成 Form 表单形式的 Map
     * 一旦报错,取消转换
     *
     * @param requestBodyData requestBodyData
     */
    @Override
    public void setRequestBodyData(byte[] requestBodyData) {
        Map<String, String> map = null;

        try {
            map = URLUtils.deMergeUrl("?" + new String(requestBodyData));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        map.remove("&&&");

        textFrom.putAll(map);
    }

    public Map<String, byte[]> getUploaderForm() {
        return uploaderForm;
    }

    public void setUploaderForm(Map<String, byte[]> uploaderForm) {
        this.uploaderForm = uploaderForm;
    }

    public Map<String, String> getTextFrom() {
        return textFrom;
    }

    public void setTextFrom(Map<String, String> textFrom) {
        this.textFrom = textFrom;
    }

    public String getDelimiter() {
        String temp = null;
        if (delimiter == null || delimiter.isEmpty()){
            temp = UUID.randomUUID().toString().replace("-","");
            delimiter = temp;
        }else {
            temp = this.delimiter;
        }

//        if (!delimiter.startsWith("-")){
//            temp = SPLINE + temp;
//        }

        return temp;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getContentType(){
        return "multipart/form-data; boundary=" + getDelimiter();
    }

    /**
     * 强制使用 POST 方法,设置什么都没用!
     * @param requestMethod requestMethod
     */
    @Override
    public void setRequestMethod(RequestMethod requestMethod) {

    }

    /**
     * 需要强制性的加入 Content-Type 属性
     * 并且是强制覆盖的!
     *
     * @return
     */
    @Override
    public Map<String, Set<String>> getHeaderMap() {
        Map<String, Set<String>> headerMap = super.getHeaderMap();
        headerMap.put("Content-Type", Collections.singleton(getContentType()));

        return headerMap;
    }



    @Override
    public byte[] getRequestBodyData() {
        return FormUploadBodyBuilder.builderRequestBody(this);
    }
}
