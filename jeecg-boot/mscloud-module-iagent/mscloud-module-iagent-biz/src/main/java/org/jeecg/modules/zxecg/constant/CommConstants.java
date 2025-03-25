package org.jeecg.modules.zxecg.constant;

import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import okio.BufferedSink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * 接口调用常量
 * @author Administrator
 * @date 2016/10/20
 */
public class CommConstants {
    public static class ZXTHEALTH {
        public static final String ZXTHEALTH_TOKEN_KEY="zxecg_oapi_access_token";
    }

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
        String json="{\"question\":\"你好\"}";
        RequestBody body=RequestBody.create(json,mediaType);
        Request request = new Request.Builder()
                .url("http://localhost:9999/oapi/iagent/elansen/llm/qa")
//                .url("http://localhost:9999/iagent/test/sse")
                .header("X-Access-Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3Mzk4MjkwMTcsInVzZXJuYW1lIjoienhlY2ctaWFnZW50In0.J3Nt---oOtWnh8Xy7qmmbnO6jidT0lej72Cr4JuuBbQ")
                .header("Accept", "*/*")
                .header("Host", "localhost:9999")
                .header("Connection", "keep-alive")
                .post(body)
                .build();

        EventSource.Factory factory = EventSources.createFactory(client);
        EventSource eventSource = factory.newEventSource(request, new EventSourceListener() {
            @Override
            public void onOpen(EventSource eventSource, Response response) {
                System.out.println("Connected to server");
            }

            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                System.out.println("Received event: " + data);

            }

            @Override
            public void onClosed(EventSource eventSource) {
                System.out.println("Connection closed");
            }

            @Override
            public void onFailure(EventSource eventSource, Throwable t, Response response) {
                if(t!=null){
                    t.printStackTrace();
                }
                System.out.println("Connection onFailure");
            }
        });

        // 保持程序运行，以便接收事件
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
