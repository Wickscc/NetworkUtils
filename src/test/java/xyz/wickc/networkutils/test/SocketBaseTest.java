package xyz.wickc.networkutils.test;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created on 2020/10/21
 *
 * @author wicks
 * @since 1.8
 */
public class SocketBaseTest {
//    @Test
    public void serverTest() throws IOException {
        ServerSocket serverSocket = new ServerSocket(4563);
        Socket clientSocket = serverSocket.accept();

        InputStream inputStream = clientSocket.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        IOUtils.copy(inputStream,outputStream);

        System.out.println(new String(outputStream.toByteArray()));

        clientSocket.close();
    }

//    @Test
    public void clientTest() throws Exception{
        Socket socket = new Socket();
        socket.connect(
                new InetSocketAddress(
                        "wickc.xyz",8443
                )
        );

        if (!socket.isConnected()){
            return;
        }

        String sendStr = "HelloWorld";
        OutputStream outputStream = socket.getOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sendStr.getBytes());

        IOUtils.copy(inputStream,outputStream);

        socket.close();
    }
}
