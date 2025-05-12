package org.jeecg.modules.iagent.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.xingchen.model.ChatResult;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChunk;
import com.volcengine.ark.runtime.service.ArkService;
import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.iagent.nls.llm.LLMOperater;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,classes = SampleTest.class)
public class SampleTest {
	@Resource(name="doubaoLLMOperaterImpl")
	LLMOperater llm;

	@Test
	public void test() {
		final int[] index = {0};
		ArkService service=null;
		//调用LLM
        Map llmResult = null;
        try {
            llmResult = llm.getElansenLLMAnswerAsync("1","userFlag","房颤的治疗步骤",true,0,null,null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Flowable<?> response=llmResult.get("flowable")==null?null:(Flowable<?>)llmResult.get("flowable");
		service=llmResult.get("service")==null?null:(ArkService)llmResult.get("service");
		ArkService finalService=service;

		response.subscribe(new DisposableSubscriber<Object>() {
			@Override
			public void onNext(Object chatResult) {
				String content=null;
				if(chatResult instanceof ChatCompletionChunk){
					content=((ChatCompletionChunk)chatResult).getChoices().get(0).getMessage().getContent().toString();
				}else if(chatResult instanceof ChatResult){
					content=((ChatResult)chatResult).getChoices().get(0).getMessages().get(0).getContent();
				}
				if (StringUtils.isNotBlank(content)) {
					index[0]++;
					JSONObject object = new JSONObject();
					object.put("seq", index[0]);
					object.put("content", content);
					System.out.println(content);
				}
			}

			@Override
			public void onError(Throwable throwable) {
				//通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
			}

			@Override
			public void onComplete() {
				//通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
			}
		});
	}
}
