package com.upc.gessi.loganalytics.app.client;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class APIClient {

    private OkHttpClient client;

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    public APIClient() {
        this.client = new OkHttpClient().newBuilder().build();
    }

    public OkHttpClient getClient() {
        return client.newBuilder().build();
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public Response get(String url, HashMap<String,String> queryParams, HashSet<String> pathSegments) {
        try {
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
            for (Map.Entry<String,String> entry : queryParams.entrySet())
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            for (String segment : pathSegments)
                urlBuilder.addPathSegments(segment);
            Request request = new Request.Builder()
                    .url(urlBuilder.build().toString())
                    .method("GET", null)
                    .build();
            return client.newCall(request).execute();
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard server");
        }
        return null;
    }
}
