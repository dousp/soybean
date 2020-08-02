package com.dsp.soybean.demo.po;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author dsp
 * @date 2020-08-03
 */
public class HttpUtil {

    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    public static void get(String url, Map<String,String> params, Callback responseCallback) {
        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        Request request = new Request.Builder().url(httpBuilder.build()).build();
        client.newCall(request).enqueue(responseCallback);
    }

    public static String httpGet(String url, Map<String, String> params){
        HttpUrl.Builder builder = new HttpUrl.Builder().host(url);
        for(Map.Entry<String, String> param : params.entrySet()) {
            builder.addQueryParameter(param.getKey(),param.getValue());
        }
        HttpUrl httpUrl = builder.build();
        Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }catch (IOException e){
            return "EXCEPTION";
        }
    }


}
