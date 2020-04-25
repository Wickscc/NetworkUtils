package xyz.wickc.networkutils.http.application;

import xyz.wickc.networkutils.exception.NetworkException;

import java.net.URL;
import java.util.Map;

/**
 * Created on 2020/4/24
 *
 * @author wicks
 * @since 1.8
 */
public interface Aria2Download {
    /**
     * 使用 Aria2 下载链接
     * @param urlArr 下载链接地址
     * @param path 保存的路径
     */
    void downloadUrl(String path,String ... urlArr);

    /**
     * 使用 Aria2 下载链接
     * @param fileName 保存的文件名
     * @param path 下载路径
     * @param uri 下载地址
     */
    void downloadUrl(String fileName,String path,String uri);
}
