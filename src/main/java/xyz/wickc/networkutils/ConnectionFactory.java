package xyz.wickc.networkutils;

import java.io.IOException;
import java.net.*;
import java.util.Properties;

public class ConnectionFactory {
    private static Proxy proxy;
    private static Object proxyType;
    private static boolean userProxy;

    private static void init(){
        Properties properties = new Properties();

        try {
            properties.load(ConnectionFactory.class.getClassLoader().getResourceAsStream("project.config"));
        } catch (IOException e) {
            System.out.println("Config文件未找到,使用无代理模式!");
            userProxy = false;
            return;
        }

        userProxy = Boolean.parseBoolean(properties.getProperty("userProxy"));
        String ipaddr = properties.getProperty("proxyServer");
        String port = properties.getProperty("proxyPort");
        String type = properties.getProperty("proxyType");

        switch (type){
            case "SOCKS":
                proxyType = Proxy.Type.SOCKS;
                break;
            case "HTTP":
            case "HTTPS":
                proxyType = Proxy.Type.HTTP;
                break;
            case "DIRECT":
                proxyType = Proxy.Type.DIRECT;
                break;
            default:
                System.out.println("配置文件配置项目 ProxyType出错,以不使用代理服务器代替!");
                userProxy = false;
                break;
        }

        if (!userProxy){
            proxy = null;
        }

        if (userProxy){
            proxy = new Proxy((Proxy.Type) proxyType,new InetSocketAddress(ipaddr, Integer.parseInt(port)));
        }
    }



    public static URLConnection getUrlConnection(String url){
        URL urlObj = null;

        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (urlObj == null){
            throw new RuntimeException("URL错误!");
        }

        URLConnection connection = null;

        if (userProxy){
            try {
                connection = urlObj.openConnection(proxy);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                connection = urlObj.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }
}
