package xyz.wickc.networkutils.http.application.aria2.impl;

import xyz.wickc.networkutils.http.application.aria2.Aria2Download;

import java.net.URL;

/**
 * Created on 2020/4/25
 *
 * 使用 Aria2 来下载 HTTP 协议的资源
 *
 * @author wicks
 * @since 1.8
 */
public class HttpUrlAria2Download implements Aria2Download {

    @Override
    public void downloadUrl(String path, String... urlArr) {

    }

    @Override
    public void downloadUrl(String fileName, String path, String uri) {

    }
}
