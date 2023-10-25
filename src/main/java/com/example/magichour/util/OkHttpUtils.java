package com.example.magichour.util;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Map;

@Log4j2
public class OkHttpUtils {

    /**
    * GET METHOD
    * @param url
    * @return
    * */
    public static String get(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try(Response response = client.newCall(request).execute()) {
            return response.body().string();
        }catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * post 호출
     * @param url
     * @param body
     * @param mediaType
     * @return
     */
    public static String post(String url, Map<String, String> body, MediaType mediaType) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(new Gson().toJson(body), mediaType);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * post 호출
     * @param url
     * @param body
     * @param mediaType
     * @return
     */
    public static String post(String url, Map<String, String> paramHeader, Map<String, String> body, MediaType mediaType) {
        if (ObjectUtils.isEmpty(paramHeader)) {
            throw new IllegalArgumentException("paramHeader is null");
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(new Gson().toJson(body), mediaType);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .headers(Headers.of(paramHeader))
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
