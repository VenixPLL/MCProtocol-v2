/*
 * MCProtocol-v2
 * Copyright (C) 2022.  VenixPLL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
        } catch (Exception ignored) {}
    }
}