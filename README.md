# NetworkUtils
用简单的方式获取网页上的内容

目前完成的功能有
   * 远程调用 Aria2 下载 (JSONRPC)
   * 基本的发送请求
   * ~~原始 HTTP 格式的文件读取 (暂时失效,下个版本补上)~~ 2020年5月5日00:14:34 已经更新!
   * 网络请求缓存
   * 发生错误自动重试
   * 网络代理
   
示例:
```java
public class HttpRequestTest{
    @Test
    public void httpRequestTest() throws MalformedURLException {
        // 获取 HttpNetworkUtils 对象
        HttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getHttpNetworkUtils();
    
        // 创建请求参数对象
        NetworkRequestData requestData = new NetworkRequestData(
                new URL(TEST_URL),RequestMethod.GET
        );
    
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("k6","v6");
        queryMap.put("k1","v11");
        
        // 设置请求头和 URL Query 参数
        requestData.setUserAgent(UA);
        requestData.setCookie(COOKIE_STRING);
        requestData.setQueryData(URL_QUERY);
        requestData.addHeader("Content-Type","application/json;charset=UTF-8");
        requestData.setQueryData(queryMap);

        // 设置 HttpNetworkUtils 出现错误代码时信任的 HTTP 请求码
        requestData.setTrustStatusCode(200,300,400,500,600,700,800,900,1000);
        
        // 设置请求体                   
        requestData.setRequestBodyData("k1=123456789&k2=123456789".getBytes());

        // 发送请求
        NetworkResponseData responseData = httpNetworkUtils.readPage(requestData);
    
        // 获取响应数据
        logger.info("Cookie: " + responseData.getCookieStr());
        logger.info("CookieList: " + responseData.getCookieList());
        logger.info("Header: " + responseData.getHeaderMap());
        logger.info("RespBodyLength: " + responseData.getRequestBodyData().length);
    }
}
```