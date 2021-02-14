package xyz.wickc.networkutils.http.impl;

import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.domain.inter.CustomizeHttpUrlConnectionListener;

import java.net.HttpURLConnection;

/**
 * Created on 2021/2/14
 *
 * @author wicks
 * @since 1.8
 */
public class HaifaCustomizeHttpNetworkUtils extends SimpleHttpNetworkUtils {
    private final CustomizeHttpUrlConnectionListener listener;

    public HaifaCustomizeHttpNetworkUtils(CustomizeHttpUrlConnectionListener listener) {
        this.listener = listener;
    }

    @Override
    protected HttpURLConnection parsingRequestData(NetworkRequestData requestData) {
        HttpURLConnection httpURLConnection = super.parsingRequestData(requestData);
        return listener.customization(httpURLConnection);
    }
}
