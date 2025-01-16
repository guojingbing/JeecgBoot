package org.jeecg.modules.iagent.nls.llm;

import com.alibaba.xingchen.ApiException;
import com.alibaba.xingchen.model.*;
import com.alibaba.xingchen.model.ext.chat.ChatContext;
import io.reactivex.Flowable;
import org.jeecg.modules.iagent.nls.llm.impl.XingchenLLMOperaterImpl;

import java.util.Arrays;

/**
 * @Description: 通义星尘
 * @Author: Kingpin
 * @Date: 2024-12-06 16:48:19
 **/
public class ChatApiSseExample {
    public static void main(String[] args) throws ApiException {
        LLMOperater llm=new XingchenLLMOperaterImpl();
        long st1=System.currentTimeMillis();
//        System.out.println(System.currentTimeMillis());
//        String sseType="enable";
//        System.out.println(System.currentTimeMillis());
//        ChatApiSub api = new ChatApiSub();
//        System.out.println(System.currentTimeMillis());
//        ApiClient apiClient = new ApiClient();
//        System.out.println(System.currentTimeMillis());
//        apiClient.setBasePath("https://nlp.aliyuncs.com");
//        apiClient.addDefaultHeader("X-DashScope-SSE", sseType); // 开启SSE输出
//        // Configure HTTP bearer authorization: Authorization
//        System.out.println(System.currentTimeMillis());
//        HttpBearerAuth authorization = (HttpBearerAuth) apiClient.getAuthentication("Authorization");
//        System.out.println(System.currentTimeMillis());
//        authorization.setBearerToken("lm-+Il29AvnTIZfs0MTNdQ7ew==");
//        System.out.println(System.currentTimeMillis());
//        api.setApiClient(apiClient);
//        System.out.println(System.currentTimeMillis());
//        if(sseType.equalsIgnoreCase("disable")){
//            ResultDTOChatResult result = api.chat(buildChatReqParams());
//            System.out.println(result.getData());
//            long st2=System.currentTimeMillis();
//            System.out.println("非流式输出响应耗时："+(st2-st1));
//        }else{
//            System.out.println(System.currentTimeMillis());
//            Flowable<ChatResult> response =  api.streamOut(buildChatReqParams());
//            final int[] line = {0};
//            System.out.println(System.currentTimeMillis());
//            response.blockingForEach(message -> {
//                if(line[0] ==0){
//                    long st2=System.currentTimeMillis();
//                    System.out.println("流式输出响应耗时："+(st2-st1));
//                }
//                line[0]++;
//                System.out.println(message.getChoices().get(0).getMessages().get(0).getContent());
//                long st3=System.currentTimeMillis();
//                System.out.println("流式输出 耗时："+(st3-st1));
//            });
//        }
        try {
            Flowable<ChatResult> response = llm.getAnswerAsync("1234","小明","你今年多大?",true);

            final int[] line = {0};
            response.blockingForEach(message -> {
                if (line[0] == 0) {
                    long st2 = System.currentTimeMillis();
                    System.out.println("流式输出响应耗时：" + (st2 - st1));
                }
                line[0]++;
                System.out.println(message.getChoices().get(0).getMessages().get(0).getContent());
                long st3 = System.currentTimeMillis();
                System.out.println("流式输出 耗时：" + (st3 - st1));
            });

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
            long st11=System.currentTimeMillis();
            LLMOperater llm2=new XingchenLLMOperaterImpl();
            Flowable<ChatResult> response2 = llm2.getAnswerAsync("1234","小明","你今年多大?",false);
            final int[] line2 = {0};
            response2.blockingForEach(message -> {
                if (line2[0] == 0) {
                    long st2 = System.currentTimeMillis();
                    System.out.println("流式输出响应耗时：" + (st2 - st11));
                }
                line2[0]++;
                System.out.println(message.getChoices().get(0).getMessages().get(0).getContent());
                long st3 = System.currentTimeMillis();
                System.out.println("流式输出 耗时：" + (st3 - st11));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ChatReqParams buildChatReqParams() {
        return ChatReqParams.builder()
                .botProfile(
                        CharacterKey.builder()
                                // 星尘预制角色
                                .characterId("3551657009cd48fab823ed0010e60159")
                                .name("小婉")
                                .build()
                )
                .modelParameters(
                        ModelParameters.builder()
                                .seed(1683806810L)
                                .incrementalOutput(false)
                                .build()
                )
                .userProfile(
                        UserProfile.builder()
                                .userId("1234")
                                .build()
                )
                .messages(
                        Arrays.asList(
                                // 注意，自定义角色 prompt，用户问题需放到messages最后一条
                                Message.builder()
                                        .name("小明")
                                        .content("你今年多大?")
                                        .role("user")
                                        .build()
                        )
                )
                .context(
                        ChatContext.builder()
                                .useChatHistory(true) // 使用平台对话历史，messages 只能包含用户提问消息，不能包含其他信息
                                .build()
                )
                .build();
    }
}
