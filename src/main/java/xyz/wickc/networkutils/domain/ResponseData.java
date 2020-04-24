package xyz.wickc.networkutils.domain;

import xyz.wickc.networkutils.utils.CookieStringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created on 2019/10/10
 *
 * @author wicks
 * @since 1.8
 */
public class ResponseData {
    private Map<String, List<String>> responseHread;
    private String respBody;
    private byte[] rowRespBody;

    public List<String> getCookie(){
        return responseHread.get("Set-Cookie");
    }

    public byte[] getRowRespBody() {
        return rowRespBody;
    }

    public void setRowRespBody(byte[] rowRespBody) {
        this.rowRespBody = rowRespBody;
    }

    public String getCookieStr(){
        List<String> cookieList = responseHread.get("Set-Cookie");
        if(cookieList == null || cookieList.isEmpty()){
            return "";
        }

        return CookieStringUtils.getCookieString(cookieList,"");
    }

    public Map<String, List<String>> getResponseHread() {
        return responseHread;
    }

    public String getRespBody() {
        return respBody;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "responseHread=" + responseHread +
                ", respBody='" + respBody + '\'' +
                '}';
    }

    public ResponseData() {
    }

    public ResponseData(Map<String, List<String>> responseHread, String respBody) {
        this.responseHread = responseHread;
        this.respBody = respBody;
    }
}
