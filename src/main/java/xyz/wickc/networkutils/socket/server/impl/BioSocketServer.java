package xyz.wickc.networkutils.socket.server.impl;

import xyz.wickc.networkutils.socket.listener.ServerReceiveListener;
import xyz.wickc.networkutils.socket.server.SocketServer;

/**
 * Created on 2020/10/21
 *
 * @author wicks
 * @since 1.8
 */
public class BioSocketServer implements SocketServer {
    @Override
    public void registerListener(ServerReceiveListener receiveListener) {

    }

    @Override
    public boolean sendData() {
        return false;
    }
}
