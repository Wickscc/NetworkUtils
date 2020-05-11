package xyz.wickc.networkutils.http.impl;

import xyz.wickc.networkutils.http.HttpNetworkUtils;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.NetworkResponseData;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020/4/24
 *
 * 尚未完成。。。
 *
 * @author wicks
 * @since 1.8
 */
public class MultiThreadHttpNetworkUtils implements HttpNetworkUtils {
    private static class NetworkRequestRunnable implements Runnable{
        @Override
        public void run() {

        }
    }

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            10,
            20,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r,"NetworkUtils Thread");
                }
            }
    );

    protected MultiThreadHttpNetworkUtils() {

    }

    @Override
    public NetworkResponseData readPage(NetworkRequestData requestData) {
        return null;
    }
}
