package com.ghqkl.schedule.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

public class HttpClientUtil {
	
    private static PoolingHttpClientConnectionManager cm = null;

    static {
    	 SSLContext sslContext=null;
		try {
			sslContext = SSLContexts.custom()
			         .loadTrustMaterial(null, new TrustSelfSignedStrategy())
			         .build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", sslsf)
                .build();
        cm =new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(100);//多线程调用注意配置，根据线程数设定
        cm.setDefaultMaxPerRoute(20);//多线程调用注意配置，根据线程数设定
    }

    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }
    
}
