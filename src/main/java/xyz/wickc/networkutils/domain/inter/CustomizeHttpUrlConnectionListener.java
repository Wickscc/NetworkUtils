package xyz.wickc.networkutils.domain.inter;

import java.net.HttpURLConnection;

/**
 * Created on 2021/2/14
 *
 * @author wicks
 * @since 1.8
 */
@FunctionalInterface
public interface CustomizeHttpUrlConnectionListener {
    /**
     * 当调用此方法时会将处理完成的 HttpUrlConnection 的对象传递进去
     * 自行定制化 HttpUrlConnection 相关参数即可!
     *
     * @param httpURLConnection 原始 httpURLConnection
     * @return 处理完成的 httpURLConnection
     */
    HttpURLConnection customization(HttpURLConnection httpURLConnection);
}
