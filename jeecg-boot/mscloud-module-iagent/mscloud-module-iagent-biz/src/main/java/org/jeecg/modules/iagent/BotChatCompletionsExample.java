package org.jeecg.modules.iagent;

import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2025-03-28 16:58:50
 **/
public class BotChatCompletionsExample {
    static String apiKey = "7ef00bf7-cf18-4284-af27-4ee5f1c8669c";
    static ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
    static Dispatcher dispatcher = new Dispatcher();
    static ArkService service = ArkService.builder().dispatcher(dispatcher).connectionPool(connectionPool).baseUrl("https://ark.cn-beijing.volces.com/api/v3/").apiKey(apiKey).build();

    public static void main(String[] args) {
//        System.out.println("\n----- standard request -----");
//        final List<ChatMessage> messages = new ArrayList<>();
//        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是豆包，是由字节跳动开发的 AI 人工智能助手").build();
//        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content("房颤的治疗步骤").build();
//        messages.add(systemMessage);
//        messages.add(userMessage);
//
//        BotChatCompletionRequest chatCompletionRequest = BotChatCompletionRequest.builder()
//                .botId("bot-20250205170416-5xwwx")
//                .messages(messages)
//                .build();
//
//        BotChatCompletionResult chatCompletionResult =  service.createBotChatCompletion(chatCompletionRequest);
//        chatCompletionResult.getChoices().forEach(choice -> System.out.println(choice.getMessage().getContent()));
//        // the references example
//        if (chatCompletionResult.getReferences() != null) {
//            chatCompletionResult.getReferences().forEach(ref -> System.out.println(ref.getUrl()));
//        }

        System.out.println("\n----- streaming request -----");
        final List<ChatMessage> streamMessages = new ArrayList<>();
        final ChatMessage streamSystemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是豆包，是由字节跳动开发的 AI 人工智能助手").build();
        final ChatMessage streamUserMessage = ChatMessage.builder().role(ChatMessageRole.USER).content("房颤的治疗步骤").build();
        streamMessages.add(streamSystemMessage);
        streamMessages.add(streamUserMessage);

        BotChatCompletionRequest streamChatCompletionRequest = BotChatCompletionRequest.builder()
                .botId("bot-20250205170416-5xwwx")
                .messages(streamMessages)
                .build();

        StringBuffer sb=new StringBuffer();
        service.streamBotChatCompletion(streamChatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(
                        choice -> {
                            if (choice.getReferences() != null && !choice.getReferences().isEmpty()) {
                                choice.getReferences().forEach(ref -> System.out.println(ref.getUrl()));
                            }
                            if (!choice.getChoices().isEmpty()) {
                                System.out.print(choice.getChoices().get(0).getMessage().getContent().toString());
                                sb.append(choice.getChoices().get(0).getMessage().getContent().toString());
                            }
                        }
                );

        System.out.println(sb.toString());
        // shutdown service after all requests is finished
        service.shutdownExecutor();
    }

}
