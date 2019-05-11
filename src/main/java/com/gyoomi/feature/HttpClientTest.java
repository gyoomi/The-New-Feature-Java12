package com.gyoomi.feature;

import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/5/11 22:33
 */
public class HttpClientTest {

    @Test
    public void testGet() throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://www.baidu.com")).build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = httpClient.send(request, bodyHandler);
        String result = response.body();
        System.out.println(result);
    }

    @Test
    public void testGetAsyn() throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://www.baidu.com")).build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, bodyHandler);
        response.thenApply(HttpResponse::body).thenAccept(System.out::println);
        Thread.sleep(1000);
    }
}
