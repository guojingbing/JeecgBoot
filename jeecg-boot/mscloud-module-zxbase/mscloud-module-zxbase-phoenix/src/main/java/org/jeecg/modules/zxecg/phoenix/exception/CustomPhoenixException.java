package org.jeecg.modules.zxecg.phoenix.exception;

/**
 * @Description: 自定义Phoenix异常
 */
public class CustomPhoenixException extends RuntimeException {
    public CustomPhoenixException(String message) {
        super(message);
    }
    public CustomPhoenixException(String message, Throwable cause) {
        super(message, cause);
    }
}
