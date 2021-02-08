package xyz.wickc.networkutils.socket.server;

import xyz.wickc.networkutils.socket.listener.ServerReceiveListener;

/**
 * Created on 2020/5/11
 *
 * @author wicks
 * @since 1.8
 */
public interface SocketServer {

    /**
     * 注册消息接收监听器
     * @param receiveListener 监听器 interface 实现
     */
    void registerListener(ServerReceiveListener receiveListener);

    /**
     *
     * @return
     */
    boolean sendData();
}
