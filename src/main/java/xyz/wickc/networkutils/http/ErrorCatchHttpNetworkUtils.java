package xyz.wickc.networkutils.http;

import xyz.wickc.networkutils.http.entity.HttpErrorDeterminer;
import xyz.wickc.networkutils.http.entity.HttpErrorHandler;

/**
 * Created on 2020/7/2
 *
 * @author wicks
 * @since 1.8
 */
public interface ErrorCatchHttpNetworkUtils extends HttpNetworkUtils {

    /**
     * 设置最大试错次数, 如果超过最大错误次数那么就会调用错误处理程序
     *
     * @param maxTry 最大错误次数
     */
    void setMaxTry(int maxTry);

    /**
     * 设置错误处理器
     *
     * @param errorHandler 错误处理器
     */
    void setErrorHandler(HttpErrorHandler errorHandler);

    /**
     * 设置错误判断器
     *
     * @param errorDeterminer 错误判断器
     */
    void setErrorDeterminer(HttpErrorDeterminer errorDeterminer);
}
