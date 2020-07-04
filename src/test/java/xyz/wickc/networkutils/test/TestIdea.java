package xyz.wickc.networkutils.test;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;
import xyz.wickc.networkutils.domain.FormUploadNetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkData;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.RequestMethod;

import java.io.*;
import java.net.*;
import java.util.UUID;

/**
 * Created on 2020/7/3
 *
 * @author wicks
 * @since 1.8
 */
public class TestIdea {
    private static Logger logger = LoggerFactory.getLogger(TestIdea.class);

    @Test
    public void eqClass() throws MalformedURLException {
        NetworkRequestData requestData = new NetworkRequestData(
                new URL("http://wickc.xyz:8443/BTTVWeb"),
                RequestMethod.GET
        );

        FormUploadNetworkRequestData uploadNetworkRequestData = new FormUploadNetworkRequestData(
                new URL("http://wickc.xyz:8443/BTTVWeb")
        );

        NetworkRequestData requestData2 = uploadNetworkRequestData;

        System.out.println(requestData.getClass().getName());
        System.out.println(uploadNetworkRequestData.getClass().getName());

        System.out.println(requestData2 instanceof FormUploadNetworkRequestData);
    }

    @Test
    public void testOutput() throws IOException {
        InputStream inputStream = new FileInputStream("src/test/resources/ALSTON - LOGO W.png");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write("------Test 231241254213513551\r\nContent-Type:images/jpeg\r\n\r\n".getBytes());
        IOUtils.copy(inputStream,outputStream);

        byte[] imagesByte = outputStream.toByteArray();
        System.out.println(new String(imagesByte));
    }

    @Test
    public void testIDEA(){
        String target = "http://127.0.0.1:8080/Images?key=" + "07e8ea7de8734ba089d4ee152f806761"; // 要提交的目标地址
        String BOUNDARY = UUID.randomUUID().toString().replace("-","");
        String boundaryPrefix = "--";
        String newLine = "\r\n";
        URL url;
        try {
            url = new URL(target);
            HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(8888))); // 创建一个HTTP连接
            urlConn.setRequestMethod("POST"); // 指定使用POST请求方式
            urlConn.setDoInput(true); // 向连接中写入数据
            urlConn.setDoOutput(true); // 从连接中读取数据
            urlConn.setUseCaches(false); // 禁止缓存
            urlConn.setInstanceFollowRedirects(true); // 自动执行HTTP重定向
            urlConn.setRequestProperty("connection", "Keep-Alive");
            urlConn.setRequestProperty("Charset", "UTF-8");
            urlConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY); // 设置内容类型
            urlConn.setRequestProperty("token","405Jkg/npueknD6r5WKA7OlMNCYdJIT4UnXUYaa8AGfUOzzDa8Vxx43zWOwJGE3ccBLwjNh+16F0HYQSYxoW8C1cBTUwoPXkoxCP7hCh2rE=");
            DataOutputStream out = new DataOutputStream(
                    urlConn.getOutputStream()); // 获取输出流

            // 上传文件
            File file = new File("E:\\Users\\wicks\\Pictures\\NewHP.png");
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);

            /**
             *文件参数,photo参数名可以随意修改
             *photo 为服务器的key
             *如果服务器设置了这个key就要改成响应的参数
             */
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
                    + file.getName() + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);
            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());
            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(
                    new FileInputStream(file));
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY
                    + boundaryPrefix + newLine).getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush(); // 输出缓存
            out.close(); // 关闭数据输出流
            logger.info("getResponseCode:" + urlConn.getResponseCode());
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 判断是否响应成功
                InputStreamReader in1 = new InputStreamReader(
                        urlConn.getInputStream(), "utf-8"); // 获得读取的内容，utf-8获取内容的编码
                BufferedReader buffer = new BufferedReader(in1); // 获取输入流对象
                String inputLine = null;
                logger.info("inputLine:" + buffer.readLine());
                while ((inputLine = buffer.readLine()) != null) {
                    System.out.println(inputLine);
                }

                in1.close(); // 关闭字符输入流
            }
            urlConn.disconnect(); // 断开连接
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
