# NetworkUtils
用简单的方式获取网页上的内容

目前完成的功能有
   * 远程调用 Aria2 下载 (JSONRPC)
   * 基本的发送请求
   * ~~原始 HTTP 格式的文件读取 (暂时失效,下个版本补上)~~ 2020年5月5日00:14:34 已经更新!
   * 网络请求缓存
   * 发生错误自动重试
   * 网络代理
   * 多部件上传
   
简单收发请求示例:
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

多部件上传示例:
```java
public class TestUploadFile{
        @Test
        public void formUploader() throws MalformedURLException {
            // 读取文件 获取 Byte 数据
            byte[] data = new byte[1024];
    
            FormUploadNetworkRequestData requestData = new FormUploadNetworkRequestData(
                    new URL("http://127.0.0.1:8080/Images?key=" + "07e8ea7de8734ba089d4ee152f806761")
            );
    
            // 设置文字表单数据
            requestData.addTextFrom("fileName","WicksImages.png");
            requestData.addTextFrom("username","WicksChen");

            // 设置文件上传数据
            requestData.addUploaderForm("images/jpeg","WicksImages.jpg","file",outputStream.toByteArray());

            // 设置信任请求码
            requestData.setTrustStatusCode(404,400);
    
            // 设置请求头            
            requestData.addHeader("token","405Jkg/npueknD6r5WKA7OlMNCYdJIT4UnXUYaa8AGfUOzzDa8Vxx43zWOwJGE3ccBLwjNh+16F0HYQSYxoW8C1cBTUwoPXkoxCP7hCh2rE=");
       
            HttpNetworkUtils httpNetworkUtils = HttpNetworkUtilsFactory.getHttpNetworkUtils();
            NetworkResponseData responseData = httpNetworkUtils.readPage(requestData);
    
            logger.info("Cookie: " + responseData.getCookieStr());
            logger.info("CookieList: " + responseData.getCookieList());
            logger.info("Header: " + responseData.getHeaderMap());
            logger.info("RespBodyLength: " + responseData.getRequestBodyData().length);
            logger.info("RespBody: " + new String(responseData.getRequestBodyData()));
        }
}
```


### 使用方式
1. 直接使用 Releases 中提供的 Jar 包 (可能会更新较慢)
2. 通过 ```git clone``` 的方式克隆到本地后自行用 Maven 安装到本地仓库使用 

测试冲突情况 本地