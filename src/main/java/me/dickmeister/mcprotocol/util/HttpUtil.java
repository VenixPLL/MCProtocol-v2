package me.dickmeister.mcprotocol.util;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;

public final class HttpUtil {
    private HttpUtil() {
    }

    /**
     * Sending JSON Data to target URL
     *
     * @param url     URL of the server to send
     * @param content JSON Data to send
     * @param proxy   proxy to connect through
     */
    public static void postJsonRequest(String url, String content, Proxy proxy) {
        final HttpClient httpClient = (Objects.nonNull(proxy) && proxy != Proxy.NO_PROXY
                ? HttpClientBuilder.create()
                .setProxy(new HttpHost(((InetSocketAddress) proxy.address()).getAddress(), ((InetSocketAddress) proxy.address()).getPort(), "http"))
                .build()
                : HttpClientBuilder.create()
                .build()
        );

        try {
            final HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("content-type", "application/json; utf-8");
            httpPost.setEntity(new StringEntity(content));
            httpClient.execute(httpPost);
        } catch (Exception ignored) {
        }
    }
}
