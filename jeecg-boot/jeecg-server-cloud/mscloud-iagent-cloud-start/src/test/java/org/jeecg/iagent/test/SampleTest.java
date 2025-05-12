package org.jeecg.iagent.test;

import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionChunk;
import com.volcengine.ark.runtime.service.ArkService;
import io.reactivex.Flowable;
import org.jeecg.MscloudIagentCloudApplication;
import org.jeecg.modules.iagent.nls.llm.LLMOperater;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = MscloudIagentCloudApplication.class)
public class SampleTest {
	@Resource(name="doubaoLLMOperaterImpl")
	LLMOperater llm;

	@Test
	public void test() {
        try {
			//调用LLM
			Map llmResult = llm.getElansenLLMAnswerAsync("1","userFlag","心律不齐危险吗",true,0,null,null);
			Flowable<BotChatCompletionChunk> response=llmResult.get("flowable")==null?null:(Flowable<BotChatCompletionChunk>)llmResult.get("flowable");
			ArkService service=llmResult.get("service")==null?null:(ArkService)llmResult.get("service");
			StringBuffer sb=new StringBuffer();

			response.doOnComplete(() -> {
				System.out.println("doOnComplete");
			}).doOnError(throwable -> {
				System.out.println("doOnError");
			});

			response.blockingForEach(choice -> {
				if (choice.getReferences() != null && !choice.getReferences().isEmpty()) {
					choice.getReferences().forEach(ref -> System.out.println(ref.getUrl()));
				}
				if (!choice.getChoices().isEmpty()) {
//					System.out.print(choice.getChoices().get(0).getMessage().getContent().toString());
					sb.append(choice.getChoices().get(0).getMessage().getContent().toString());
				}
			});
			System.out.println(sb.toString());
//			System.out.println("ok");

//			response.subscribe(new DisposableSubscriber<BotChatCompletionChunk>() {
//				@Override
//				public void onNext(BotChatCompletionChunk chatResult) {
//					String content=chatResult.getChoices().get(0).getMessage().getContent().toString();
//					if (StringUtils.isNotBlank(content)) {
//						System.out.println("content:"+content+"content end");
//						sb.append(content);
////						System.out.println(sb.toString());
//					}
//				}
//
//				@Override
//				public void onError(Throwable throwable) {
//					System.out.println("onError");
//					//通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
//					if(service!=null){
//						service.shutdownExecutor();
//					}
//				}
//
//				@Override
//				public void onComplete() {
//					System.out.println("onComplete");
//					//通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
//					if(service!=null){
//						service.shutdownExecutor();
//					}
//				}
//			});
			System.out.println("处理完成");
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}
