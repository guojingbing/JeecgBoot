package org.jeecg.modules.iagent.websocket.vo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @Description: 语音识别消息
 * @Author: Kingpin
 * @Date: 2024-12-14 15:10:01
 **/
@Data
public class WebsocketMessage {
    private String taskId;//会话认为ID，唯一标记一轮会话
    private String content;//消息内容
    private Integer progress;//进度标记：1、进行中；2、结束
    private String extra;//其他附加消息
    private Integer type;//消息类型：1、普通消息；2、语音识别结果；3、音频流；4、LLM结果文本

    public WebsocketMessage(String taskId, String content, Integer progress, Integer type){
        this.taskId=taskId;
        this.content=content;
        this.progress=progress;
        this.type=type;
    }

    public WebsocketMessage(String taskId, String content, String extra, Integer progress, Integer type){
        this.taskId=taskId;
        this.content=content;
        this.extra=extra;
        this.progress=progress;
        this.type=type;
    }

    public String toString(){
        return JSON.toJSONString(this);
    }
}
