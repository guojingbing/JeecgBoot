package org.jeecg.modules.openapi.base.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 异常处理
 * @Author: Kingpin
 * @Date: 2022-01-15 10:19:42
 **/
@RestController
@ApiOperation(value = "handle filter throws exception", notes = "处理filter抛出的异常")
public class ErrorController extends BasicErrorController {

    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @Override
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        HttpStatus status = getStatus(request);

        Map<String,Object> map = new HashMap<>();
        map.put("success",false);
        map.put("code",body.get("status"));
        map.put("message",body.get("message"));
        map.put("result",null);
        map.put("timestamp",System.currentTimeMillis());
        return new ResponseEntity<>(map, status);
    }
}