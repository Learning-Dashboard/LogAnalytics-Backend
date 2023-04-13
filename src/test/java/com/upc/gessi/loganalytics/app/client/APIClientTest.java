package com.upc.gessi.loganalytics.app.client;

import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class APIClientTest {

    @Test
    void getClient() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        APIClient apiClient = new APIClient();
        assertEquals(client.getClass(), apiClient.getClient().getClass());
    }

    @Test
    void get() throws IOException {
        APIClient apiClient = new APIClient();
        OkHttpClient httpClient = new OkHttpClient();
        apiClient.setClient(httpClient);

        String url = "http://example.com/";
        Map<String, String> params = new HashMap<>();
        params.put("param1", "value1");
        params.put("param2", "value2");
        Request request = new Request.Builder()
                .url(Objects.requireNonNull(HttpUrl.parse(url)).newBuilder()
                        .addQueryParameter("param1", "value1")
                        .addQueryParameter("param2", "value2")
                        .build())
                .build();
        ResponseBody responseBody = ResponseBody.create("Hello, World!", MediaType.parse("application/json"));
        Response response = new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(responseBody)
                .build();
        int statusCode = response.code();
        assertEquals(200, statusCode);
        if (response.body() != null)
            assertEquals(response.body().string(), "Hello, World!");
    }
}