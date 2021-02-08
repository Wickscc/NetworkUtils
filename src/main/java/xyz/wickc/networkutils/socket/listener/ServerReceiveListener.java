package xyz.wickc.networkutils.socket.listener;

/**
 * Created on 2020/10/21
 *
 * @author wicks
 * @since 1.8
 */
public interface ServerReceiveListener {

    /**
     * 当 Listener 被注册到 Server 对象上的时候就会调用此方法!
     * 返回的是客户端返回的 Data
     *
     * @return Client OutPut Data
     */
    byte[] receiveData();
}
